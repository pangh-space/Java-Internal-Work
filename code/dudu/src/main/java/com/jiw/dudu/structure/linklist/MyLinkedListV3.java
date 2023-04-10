package com.jiw.dudu.structure.linklist;

import lombok.Getter;
import lombok.ToString;

/**
 * @auther zzyy
 * @create 2022-04-03 17:04
 * 含有虚拟头结点，virtualHead
 */
public class MyLinkedListV3<E>
{
    private class Node<E>
    {
        public E e;
        public Node<E> next;

        public Node() {
            this(null,null);
        }

        public Node(E e) {
            this(e,null);
        }

        public Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" + "e=" + e + '}';
        }

    }

    @Getter
    private Node<E> virtualHead;
    private int     size;

    public MyLinkedListV3()
    {
        this.virtualHead = new Node<>(null,null);//占位
        this.size = 0;
    }

    public int getSize()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * index 要求插入的位置，比如我index=3要求在3号位插入，那就是需要找到prev=2号位
     * @param index
     * @param e
     */
    public void add(int index,E e) {
        if(index <0 || index > size){
            throw new RuntimeException("插入范围不合适......");
        }
        //1 从头开始查找加入位置index的前一个节点，index前一个位置
        Node<E> prev = virtualHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        //2 新增节点入链,prev.next = new Node<>(e,prev.next);可代替下面3行
        Node<E> node = new Node<>(e);
        node.next = prev.next;
        prev.next = node;
        //3 实际总数加1
        size++;

    }
    public void addFirst(E e)
    {
        add(0,e);
    }
    public void addLast(E e)
    {
        add(size,e);
    }

    //2022.4.10号分享内容如下：
    public E remove(int index)
    {
        if(index < 0 || index >= size) {
            throw new IllegalArgumentException("节点范围不对");
        }

        Node<E> prev = virtualHead;//从负一开始
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node<E> result = prev.next;
        prev.next = result.next;
        result.next = null;//help GC
        size--;

        return result.e;
    }
    public E removeFirst()
    {
        return remove(0);
    }
    public E removeLast()
    {
        return remove(size-1);
    }


    public E set(int index ,E newValue)
    {
        if(isEmpty()){
            throw new IllegalArgumentException("空链表不能修改");
        }
        if(index < 0 || index >= size) {
            throw new IllegalArgumentException("节点范围不对");
        }
        //按照车厢编号index修改
        Node<E> currentNode = virtualHead.next;//从零开始
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.e = newValue;
    }

    public E get(int index)
    {
        if(index < 0 || index >= size) {
            throw new IllegalArgumentException("节点范围不对");
        }

        Node<E> currentNode = virtualHead.next;//从零开始,写法1
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.e;

        /*Node<E> currentNode = virtualHead;//从负一开始,写法2
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.next.e;*/
    }

    public E getFirst()
    {
        return get(0);
    }

    public E getLast()
    {
        return get(size-1);
    }

    public boolean contains(E e)
    {
        Node<E> currentNode = virtualHead.next;//从零开始
        while(currentNode != null)
        {
            if(currentNode.e.equals(e))
            {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    public String forEach_V1()
    {
        StringBuffer stringBuffer = new StringBuffer();
        //遍历链表写法1
        Node<E> currentNode = virtualHead.next;
        while(currentNode != null){
            stringBuffer.append(currentNode.e+"->");
            currentNode = currentNode.next;
        }
        stringBuffer.append("null");
        return "forEach_V1"+"\t"+stringBuffer.toString();
    }

    public String forEach_V2()
    {
        StringBuffer stringBuffer = new StringBuffer();
        //遍历链表写法2
        for(Node currentNode = virtualHead.next;currentNode != null; currentNode = currentNode.next)
        {
            stringBuffer.append(currentNode.e+"->");
        }
        stringBuffer.append("null");
        return "forEach_V2"+"\t"+stringBuffer.toString();
    }
}

/**
 * 笔记：
 * 1 add  remove 写操作，index的前一个位置，从负1开始检索     Node prev = virtualHead
 *         Node<E> prev = virtualHead;
 *         for (int i = 0; i < index; i++) {
 *             prev = prev.next;
 *         }
 *
 * 2 get set contain, forEach遍历       index当前位置， 从零开始检索   Node currentNode = virtualHead.next;
 *        Node<E> currentNode = virtualHead.next;
 *         for (int i = 0; i < index; i++)
 *         {
 *             currentNode = currentNode.next;
 *         }
 */
