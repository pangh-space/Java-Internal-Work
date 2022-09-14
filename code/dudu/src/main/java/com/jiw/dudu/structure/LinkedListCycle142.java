package com.jiw.dudu.structure;

/**
 * @Description 环形链表ii
 * @Author pangh
 * @Date 2022年09月14日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/linked-list-cycle-ii/
 * 解题思路：
 * 1.
 */
public class LinkedListCycle142 {

    public ListNode hasCycle(ListNode head) {

        if(head == null){
            return null;
        }
        ListNode slowPointer = head;
        ListNode fastPointer = head;
        boolean isCycle = false;
        while (fastPointer.next != null && fastPointer.next.next != null){
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
            if(slowPointer == fastPointer){
                isCycle = true;
                break;
            }
        }
        if(isCycle){
            slowPointer = head;
            while (slowPointer != fastPointer){
                slowPointer = slowPointer.next;
                fastPointer = fastPointer.next;
            }
            return slowPointer;
        }

        return null;
    }

}