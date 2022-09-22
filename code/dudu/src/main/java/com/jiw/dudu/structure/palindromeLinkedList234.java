package com.jiw.dudu.structure;

/**
 * @Description 回文链表
 * @Author pangh
 * @Date 2022年09月22日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/palindrome-linked-list/
 * 解题思路：
 *
 */
public class palindromeLinkedList234 {

    public boolean isPalindrome(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }

        if(fast != null){
            slow = slow.next;
        }
        slow = reverse(slow);

        fast = head;
        while (slow != null){
            if (slow.val != fast.val){
                return false;
            }
            slow = slow.next;
            fast = fast.next;
        }

        return true;

    }


    /*反转链表*/
    public ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }


}