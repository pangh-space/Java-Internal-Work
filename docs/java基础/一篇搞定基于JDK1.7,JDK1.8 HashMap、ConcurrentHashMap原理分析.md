## **一、开篇**

HashMap、CurrentHashMap 面试时都要问烂了，用也用烂了。但是网上的解析要不就是不够全面，要么就是copy来copy去，连基于JDK版本有的都很混乱。这篇文章带你解析 基于jdk1.7、jdk1.8不同的hashMap和ConcurrentHashMap简略分析（很多代码和HashMap都是重复的）。希望看完后有所收获。

## **二、提出问题**

1、我们都知道，HashMap是线程不安全的，那它的不安全体现在哪些方面呢？
2、HashMap在JDK1.8引入了红黑树，引入后有什么好处呢？除了引入红黑树于1.7还有什么不同呢？
3、ConcurrentHashMap怎样解决的HashMap的并发问题，用synchronized不行吗？JDK1.8又有什么改变呢？

## **三、源码分析**

- **基于jdk1.7的HashMap**

首先来看1.7的存储元素类Entry类

```java
static class Entry<K,V> implements Map.Entry<K,V> {
        final K key;
        V value;
        Entry<K,V> next;
        int hash;

        /**
         * Creates new entry.
         */
        Entry(int h, K k, V v, Entry<K,V> n) {
            value = v;
            next = n;
            key = k;
            hash = h;
        }
        ......
      }
```

hash()方法：

```java
如果Key是字符串类型，则使用专门为字符串设计的Hash方法，否则使用一连串的异或操作增加hash随机性   
   final int hash(Object k) {
        int h = 0;
        if (useAltHashing) {
            if (k instanceof String) {
                return sun.misc.Hashing.stringHash32((String) k);
            }
            h = hashSeed;
        }

        h ^= k.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
   
```

接下来是put()方法:

```java
 public V put(K key, V value) {
        if (key == null)  
            return putForNullKey(value); //专门放置NullKey
        int hash = hash(key);//获取hash值
        int i = indexFor(hash, table.length);//获取数组下标 h & (length-1);
       
       //这个for循环进行检测，如果找到key相同的元素则进行覆盖
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }
        modCount++;//修改次数+1
        
        
        addEntry(hash, key, value, i);
        return null;
    }
```

可以看出addEntry()方法进行添加元素，进去看一下:

```java
 void addEntry(int hash, K key, V value, int bucketIndex) {
       //扩容检测
        if ((size >= threshold) && (null != table[bucketIndex])) {
            resize(2 * table.length);
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }
         
        //创建entry
        createEntry(hash, key, value, bucketIndex);
    }
    
    
  void createEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K,V> e = table[bucketIndex];//获取原先数组位置的元素
        table[bucketIndex] = new Entry<>(hash, key, value, e);//该数组位置替换新元素，next为原元素（头插）
        size++;
    }
```

以上put方法就完成了，但是有一个重点是扩容方法，让我们点进去看下resize方法():

```java
 void resize(int newCapacity) {
        Entry[] oldTable = table//原数组
        int oldCapacity = oldTable.length;
        //当扩容到允许最大扩容数时不再扩容
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }

        //新数组
        Entry[] newTable = new Entry[newCapacity];
        
        boolean oldAltHashing = useAltHashing;
        useAltHashing |= sun.misc.VM.isBooted() &&
                (newCapacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
        boolean rehash = oldAltHashing ^ useAltHashing;//是否需要再Hash
        transfer(newTable, rehash);//进行数组移动
        table = newTable;//新数组
        threshold = (int)Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);//扩大Threshold
    }
    
    
  
      void transfer(Entry[] newTable, boolean rehash) {
        int newCapacity = newTable.length;
        //不断将原元素放进新数组
        for (Entry<K,V> e : table) {
            while(null != e) {
                Entry<K,V> next = e.next;
                if (rehash) {
                    e.hash = null == e.key ? 0 : hash(e.key);
                }
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            }
        }
    }
```

get()方法就相对简单些:

```java
public V get(Object key) {
        if (key == null)
            return getForNullKey();//get null key
        Entry<K,V> entry = getEntry(key);

        return null == entry ? null : entry.getValue();
    }
    
    
    final Entry<K,V> getEntry(Object key) {
        int hash = (key == null) ? 0 : hash(key);//计算hash值
        
        //链表查询
        for (Entry<K,V> e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash &&
                ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }
  
```

到这里为止1.7HashMap主要方法就分析完毕，1.7的hashMap实现还是很容易理解的。但是如果出现并发，到底在哪里有问题呢。

```
分析put方法：
①modCount++;不是原子的，所以修改次数不准确，size++不是原子的，所以hashMap的size也不准确
②在createEntry()方法中，假设两个线程同时获取数组头结点，一个线程修改后另一个线程还使用原来的数组元素头结点会造成数据丢失
③扩容死循环问题，在transfer方法中，假设线程1、2同时扩容，则可能出现头插法互相引用扩容死循环的问题
```

------

- **2. 基于jdk1.8的HashMap**

让我们以同样的招式看一看在jdk1.8中HashMap有什么变化
首先看下1.8存储元素的Node类

```java
和1.7中的Entry类似，不过换了个名字
 static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
        ......
       }
  
当晋升为树形结构时，则采用TreeNode
  static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
        TreeNode<K,V> parent;  // red-black tree links
        TreeNode<K,V> left;
        TreeNode<K,V> right;
        TreeNode<K,V> prev;    // needed to unlink next upon deletion
        boolean red;
        TreeNode(int hash, K key, V val, Node<K,V> next) {
            super(hash, key, val, next);
        }
        ......
      }
```

hash方法:

```java
     可以看出Hash算法被优化了，使得高位参与运算，减少了hash冲突
    
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
```

接下来是put方法【方法变长了o(╥﹏╥)o】：

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)//如果tabl未创建或table长度为0，则初始化（初始化过程也放入扩容方法中）
            n = (tab = resize()).length;
        if ((p = tab[i = (n - 1) & hash]) == null)//计算数组位置，如果该位置没有元素直接放入数组中
            tab[i] = newNode(hash, key, value, null);
        else {                                    //该位置有元素
            Node<K,V> e; K k;
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;                         //如果该位置与元素相同key直接覆盖值
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);//如果该结构为树形，则加入TreeNode
            else {                            //如果还是链表型结构
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);//将节点加入链表
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);//如果链表长度过长（8），转换为红黑树
                        break;
                    }
                    //如果链表中存在相同Key则直接覆盖
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);//LinkedHashMap使用，保证顺序(HashMap实现为空)
                return oldValue;//返回oldValue
            }
        }
        ++modCount;//操作次数加1
        if (++size > threshold)
            resize();//如果size>容量则进行扩容
        afterNodeInsertion(evict);//LinkedHashMap使用，用来回调移除最早放入Map的对象（HashMap实现为空）
        return null;
    }
```

OK,put方法分析完后就着重分析下扩容方法了resize():【吐槽一下我一直觉得jdk1.8可读性变差了，不仅体现在hashMap上，是提高了性能原因吗？】

```java
final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;     //老的数组
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE; //如果已达到最大容量不在扩容
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY) //扩容到原来的两倍
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) 
            newCap = oldThr;
        else {               // 如果原容量为0，则进行初始化
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;//   新可容纳元素个数终于确定了
        @SuppressWarnings({"rawtypes","unchecked"})
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];//新数组
        table = newTab;
        if (oldTab != null) {                  //排出初始化情况
            for (int j = 0; j < oldCap; ++j) { //开始循环数组
                Node<K,V> e;                   
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;           //如果数组位置不为null取值
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;//如果该位置元素没有next节点，将该元素放入新数组
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap); //如果是树形节点
                    else { // preserve order                
                          //如果是链表且有next节点 ,以原位置移动到新位置
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
```

get()方法:

```java
 public V get(Object key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;// 判断key为null的情况
    }
    
    
     final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {                 //确保数组被初始化
            if (first.hash == hash &&                           // 检查第一个元素
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                if (first instanceof TreeNode)             //树查找
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                do {
                    if (e.hash == hash &&                 //链表查找
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }
```

1.8的HahMap主要方法也分析完毕了。那么1.8还会有死循环问题吗？可以看出保在移入新数组中保证了链表的原顺序，所以不会有这个问题了。但是 ,还会有统计数问题和数据丢失问题。

- **基于jdk1.7的currentHashMap**

concurrentHashMap介绍就不能像hashmap一样直接进入方法了，有些同学不了解它的属性，让我们先看一下

```java
HashEntry :用来存储元素
static final class HashEntry<K,V> {
        final int hash;
        final K key;
        volatile V value;
        volatile HashEntry<K,V> next;

        HashEntry(int hash, K key, V value, HashEntry<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
Segment桶  实现线程的关键类（部分属性为列出，太长了。。）
static final class Segment<K,V> extends ReentrantLock implements Serializable {   
  
       //可以看出Segment继承自ReentrantLock，是一个天然的锁
       
       
      transient volatile HashEntry<K,V>[] table;

       ···
        transient int count;

      
        transient int modCount;

       
        transient int threshold;

        
       
        final float loadFactor;  transient volatile HashEntry<K,V>[] table;

        transient int count;

       
        transient int modCount;

       
        transient int threshold;

       
        final float loadFactor;
        
        ···
}  
```

put()方法：

```java
public V put(K key, V value) {
        Segment<K,V> s;
        if (value == null)
            throw new NullPointerException();  //不予许null value
        int hash = hash(key);                   //或许hash
        int j = (hash >>> segmentShift) & segmentMask;
        if ((s = (Segment<K,V>)UNSAFE.getObject           
             (segments, (j << SSHIFT) + SBASE)) == null) 
            s = ensureSegment(j);              //根据hash获取对应的桶
        return s.put(key, hash, value, false); //put
    }
    
    
   //可以看出put方法和hashMap非常像，只不过多了一个加解锁的过程
 final V put(K key, int hash, V value, boolean onlyIfAbsent) {
            HashEntry<K,V> node = tryLock() ? null :
                scanAndLockForPut(key, hash, value);//tryLock失败会重复执行tryLock则进行lock【阻塞】，等待锁的释放
            V oldValue;
            try {
                HashEntry<K,V>[] tab = table;
                int index = (tab.length - 1) & hash;
                HashEntry<K,V> first = entryAt(tab, index);
                for (HashEntry<K,V> e = first;;) {
                    if (e != null) {
                        K k;
                        if ((k = e.key) == key ||
                            (e.hash == hash && key.equals(k))) {
                            oldValue = e.value;
                            if (!onlyIfAbsent) {
                                e.value = value;
                                ++modCount;
                            }
                            break;
                        }
                        e = e.next;
                    }
                    else {
                        if (node != null)
                            node.setNext(first);
                        else
                            node = new HashEntry<K,V>(hash, key, value, first);
                        int c = count + 1;
                        if (c > threshold && tab.length < MAXIMUM_CAPACITY)
                            rehash(node);
                        else
                            setEntryAt(tab, index, node);
                        ++modCount;
                        count = c;
                        oldValue = null;
                        break;
                    }
                }
            } finally {
                unlock();
            }
            return oldValue;
        }
   
    
```

get()方法：

```java
//get方法是不加锁的，所有共享变量都是通过volatile修饰的，确保或许最新值
public V get(Object key) {
        Segment<K,V> s; 
        HashEntry<K,V>[] tab;
        int h = hash(key);
        long u = (((h >>> segmentShift) & segmentMask) << SSHIFT) + SBASE;
        if ((s = (Segment<K,V>)UNSAFE.getObjectVolatile(segments, u)) != null &&  //获取volatile
            (tab = s.table) != null) {
            for (HashEntry<K,V> e = (HashEntry<K,V>) UNSAFE.getObjectVolatile  //获取volatile
                     (tab, ((long)(((tab.length - 1) & h)) << TSHIFT) + TBASE);
                 e != null; e = e.next) {
                K k;
                if ((k = e.key) == key || (e.hash == h && key.equals(k)))
                    return e.value;
            }
        }
        return null;
    }
```

- **基于jdk1.8的ConcurrentHashMap**

存储节点:

```java
 static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        volatile V val;
        volatile Node<K,V> next;
```

put()方法:

```java
可以看出jdk1.8中取消了桶的设计，使用了Node + CAS + Synchronized来保证并发安全进行实现
  
final V putVal(K key, V value, boolean onlyIfAbsent) {
        if (key == null || value == null) throw new NullPointerException();
        int hash = spread(key.hashCode());
        int binCount = 0;
        for (Node<K,V>[] tab = table;;) {
            Node<K,V> f; int n, i, fh;
            if (tab == null || (n = tab.length) == 0)
                tab = initTable();            //初始化
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {    //如果该位置无元素则使用cas插入
                if (casTabAt(tab, i, null,
                             new Node<K,V>(hash, key, value, null)))
                    break;                   // no lock when adding to empty bin
            }
            else if ((fh = f.hash) == MOVED)
                tab = helpTransfer(tab, f);
            else {
                V oldVal = null;     
                synchronized (f) {          //如果有元素或cas插入失败 则使用synchronized 锁
                    if (tabAt(tab, i) == f) {
                        if (fh >= 0) {
                            binCount = 1;
                            for (Node<K,V> e = f;; ++binCount) {
                                K ek;
                                if (e.hash == hash &&
                                    ((ek = e.key) == key ||
                                     (ek != null && key.equals(ek)))) {
                                    oldVal = e.val;
                                    if (!onlyIfAbsent)
                                        e.val = value;
                                    break;
                                }
                                Node<K,V> pred = e;
                                if ((e = e.next) == null) {
                                    pred.next = new Node<K,V>(hash, key,
                                                              value, null);
                                    break;
                                }
                            }
                        }
                        else if (f instanceof TreeBin) {
                            Node<K,V> p;
                            binCount = 2;
                            if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                           value)) != null) {
                                oldVal = p.val;
                                if (!onlyIfAbsent)
                                    p.val = value;
                            }
                        }
                    }
                }
                if (binCount != 0) {
                    if (binCount >= TREEIFY_THRESHOLD)
                        treeifyBin(tab, i);
                    if (oldVal != null)
                        return oldVal;
                    break;
                }
            }
        }
        addCount(1L, binCount);
        return null;
    }
```

get()方法

```java
 // 也是不加锁的，直接获取volatile
 public V get(Object key) {
        Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
        int h = spread(key.hashCode());
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (e = tabAt(tab, (n - 1) & h)) != null) {
            if ((eh = e.hash) == h) {
                if ((ek = e.key) == key || (ek != null && key.equals(ek)))
                    return e.val;
            }
            else if (eh < 0)
                return (p = e.find(h, key)) != null ? p.val : null;
            while ((e = e.next) != null) {
                if (e.hash == h &&
                    ((ek = e.key) == key || (ek != null && key.equals(ek))))
                    return e.val;
            }
        }
        return null;
    }
```

**三、总结**
JDK1.7中HashMap采用了数组+链表的数据结构，有线程安全问题（统计不准确，丢失数据，死循环cpu100%），1.8中采用了数组+链表+红黑树的结构，有线程安全问题（统计不准确，丢失数据）。1.8中优化了hash算法，并且每次扩容不需要重新计算hash值。
JDK1.7中concurentHashMap使用了segment保证线程安全，但是在1.8中又把它优化掉了，直接使用cas+synchronized