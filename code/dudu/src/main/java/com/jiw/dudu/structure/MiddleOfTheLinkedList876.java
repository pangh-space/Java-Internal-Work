package com.jiw.dudu.structure;

/**
 * @Description 链表的中间节点
 * @Author pangh
 * @Date 2022年09月23日
 * @Version v1.0.0
 * 力扣题目：https://leetcode.cn/problems/middle-of-the-linked-list/
 * 解题思路：
 * 1. 使用快慢指针，快指针每次移动两个节点
 * 2. 慢指针每次移动一个节点
 * 3. 当快指针制动到链表尾部的时候，慢指针指向的就是中间节点
 */
public class MiddleOfTheLinkedList876 {

    public ListNode middleNode(ListNode head) {

        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;

    }

}