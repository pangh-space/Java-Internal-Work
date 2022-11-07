package com.jiw.dudu.structure;

/**
 * @Description 两两交换链表中的节点
 * @Author pangh
 * @Date 2022年11月06日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/swap-nodes-in-pairs/
 * 解题思路：
 */
public class WwapNodesInPairs {

    public ListNode swapPairs(ListNode head) {
        ListNode virtualHead = new ListNode(-1,head);
        ListNode prev = virtualHead;
        // 可以两两交换的条件
        while (prev.next != null && prev.next.next != null){
            // 初始化
            ListNode node1 = prev.next;
            ListNode node2 = node1.next;
            ListNode nextNode = node2.next;
            // 交换
            node1.next = nextNode;
            node2.next = node1;
            prev.next = node2;
            // 前移一步
            prev = node1;
        }
        return virtualHead.next;
    }

}