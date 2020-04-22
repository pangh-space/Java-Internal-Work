# ThreadLocal 用法及实现原理

### 一、ThreadLocal的用法，直接上代码

```Java
package com.ph.juc;

/**
 * @author panghui
 * @version 1.0
 * @since 2019-09-23
 */
public class ThreadLocalDemo {

    public static void main(String[] args) {

        ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();
        threadLocal.set(true);

        System.out.println("线程："+Thread.currentThread().getName()+"，设置的值为："+threadLocal.get());

        new Thread(()->{
            threadLocal.set(false);
            System.out.println("线程："+Thread.currentThread().getName()+"，设置的值为："+threadLocal.get());
        },"T1").start();


        new Thread(()->{
            System.out.println("线程："+Thread.currentThread().getName()+"，设置的值为："+threadLocal.get());
        },"T2").start();
    }

}

```

