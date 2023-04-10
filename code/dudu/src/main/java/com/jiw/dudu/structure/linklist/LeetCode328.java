package com.jiw.dudu.structure.linklist;

/**
 * @Description LeetCode328奇偶链表
 * @Author pangh
 * @Date 2023年04月10日
 * @Version v1.0.0
 * <p>
 * 力扣算法题：https://leetcode.cn/problems/odd-even-linked-list/
 */

public class LeetCode328 {
    private class ListNode {
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
    }

    public ListNode oddEvenList(ListNode head) {

        if(head == null || head.next == null){
            return head;
        }

        // 初始化各种节点和头指针
        ListNode oddHead = head;
        ListNode oddCurrent = head;
        ListNode evenHead = head.next;
        ListNode evenCurrent = head.next;

        // 遍历
        while (evenCurrent != null && evenCurrent.next != null){
            oddCurrent.next = oddCurrent.next.next;
            evenCurrent.next = evenCurrent.next.next;

            oddCurrent = oddCurrent.next;
            evenCurrent = evenCurrent.next;
        }

        oddCurrent.next = evenHead;

        return oddHead;
    }
}
