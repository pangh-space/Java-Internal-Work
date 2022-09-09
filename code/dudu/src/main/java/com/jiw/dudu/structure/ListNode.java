package com.jiw.dudu.structure;

/**
 * @Description 单链表中节点的定义
 * @Author pangh
 * @Date 2022年09月09日
 * @Version v1.0.0
 */
public class ListNode {
    int val;
    ListNode next;
    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public ListNode setNext(ListNode next) {
        this.next = next;
        return this.next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                '}';
    }
}
