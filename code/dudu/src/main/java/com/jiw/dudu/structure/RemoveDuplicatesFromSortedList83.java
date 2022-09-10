package com.jiw.dudu.structure;

import org.junit.Test;

/**
 * @Description 删除排序链表中的重复元素
 * @Author pangh
 * @Date 2022年09月10日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/remove-duplicates-from-sorted-list/
 * 解题思路：
 *
 */
public class RemoveDuplicatesFromSortedList83 {

    @Test
    public void test(){
        ListNode list1 = new ListNode(1);
        ListNode node1_1 = new ListNode(1);
        ListNode node1_2 = new ListNode(2);
        ListNode node1_3 = new ListNode(3);
        ListNode node1_4 = new ListNode(3);
        node1_3.next = node1_4;
        node1_2.next = node1_3;
        node1_1.next = node1_2;
        list1.next = node1_1;

        ListNode listNode = this.deleteDuplicates(list1);

        System.out.println("去重后的结果：" + listNode);

    }

    public ListNode deleteDuplicates(ListNode head) {
        if(head == null){
            return head;
        }

        ListNode currentNode = head;
        while (null != currentNode.next){
            if(currentNode.next.val == currentNode.val){
                currentNode.next = currentNode.next.next;
            }else{
                currentNode = currentNode.next;
            }
        }
        return head;
    }

}