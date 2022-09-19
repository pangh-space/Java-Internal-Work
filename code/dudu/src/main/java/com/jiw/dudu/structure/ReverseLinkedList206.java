package com.jiw.dudu.structure;

import java.util.List;

/**
 * @Description 反转链表
 * @Author pangh
 * @Date 2022年09月19日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/reverse-linked-list/
 * 解题思路：
 *
 */
public class ReverseLinkedList206 {

    public ListNode reverseList(ListNode head) {
        ListNode preNode = null;
        ListNode currentNode = head;
        while (currentNode != null){
            ListNode nextNode = currentNode.next;
            currentNode.next = preNode;
            preNode = currentNode;
            currentNode = nextNode;
        }
        return preNode;
    }

}