package com.jiw.dudu.structure;

/**
 * @Description 相交链表
 * @Author pangh
 * @Date 2022年09月15日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/intersection-of-two-linked-lists/
 * 解题思路：
 * 1.
 */
public class IntersectionOfTwoLinkedLists160 {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null){
            return null;
        }
        ListNode pA = headA;
        ListNode pB = headB;
        while (pA != pB){
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;

    }

}