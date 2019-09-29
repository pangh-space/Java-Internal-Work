[TOC]



## 一、什么是JVM

JVM是Java Virtual Machine（Java 虚拟机）的缩写，JVM是一种用于计算设备的规范，它是一个虚构出来的计算机，是通过在实际的计算机上仿真模拟各种计算机功能来实现的。

Java语言的一个非常重要的特点就是平台无关性。而使用Java虚拟机是实现这一特点的关键。一般的高级语言如果要在不同的平台上运行，至少需要编译成不同的目标代码。而引入Java语言虚拟机后，Java语言在不同平台上运行时不需要重新编译。Java语言使用Java虚拟机屏蔽了与具体平台相关的信息，使得Java语言编译程序只需生成在Java虚拟机上运行的目标代码（字节码），就可以在多种平台上不加修改地运行。Java虚拟机在执行字节码时，把字节码解释成具体平台上的机器指令执行。这就是Java的能够“一次编译，到处运行”的原因。

## 二、JVM总体概述

JVM总体上是由类装载子系统（ClassLoader）、运行时数据区、执行引擎、垃圾收集这四个部分组成。其中我们最为关注的运行时数据区，也就是JVM的内存部分则是由方法区（Method Area）、JAVA堆（Java Heap）、虚拟机栈（JVM Stack）、程序计数器、本地方法栈（Native Method Stack）这几部分组成。

## 三、JVM体系结构

![JVM总体架构图](https://java-internal-work.oss-cn-beijing.aliyuncs.com/20190929/jvm%E6%9E%B6%E6%9E%84%E5%9B%BE.png)