package com.jiw.dudu.structure;

/**
 * @Description 环形链表
 * @Author pangh
 * @Date 2022年09月13日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/linked-list-cycle/
 * 解题思路：
 * 1.
 */
public class LinkedListCycle141 {

    public boolean hasCycle(ListNode head) {

        if(head == null){
            return false;
        }
        ListNode slowPointer = head;
        ListNode fastPointer = head;
        while (fastPointer.next != null && fastPointer.next.next != null){
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
            if(slowPointer == fastPointer){
                return true;
            }
        }
        return false;
    }

}