package com.jiw.dudu.structure;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Description 合并两个有序链表
 * @Author pangh
 * @Date 2022年09月09日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/merge-two-sorted-lists/
 * 解题思路：
 * 1. 可以使用双指针
 */
@Slf4j
public class MergeTwoSortedList21 {

    @Test
    public void test(){
        ListNode list1 = new ListNode(1);
        ListNode node1_1 = new ListNode(2);
        ListNode node1_2 = new ListNode(4);
        node1_1.next = node1_2;
        list1.next = node1_1;

        ListNode list2 = new ListNode(1);
        ListNode node2_1 = new ListNode(3);
        ListNode node2_2 = new ListNode(4);
        node2_1.next = node2_2;
        list2.next = node2_1;

        ListNode listNode = this.mergeTwoLists(list1, list2);

        System.out.println("合并后结果：" + listNode);

    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1 == null){
            return list2;
        }
        if(list2 == null){
            return list1;
        }
        ListNode resultNode = new ListNode(0);
        ListNode p = resultNode;
        while (list1 != null && list2 != null){
            if(list1.val < list2.val){
                p.next = list1;
                list1 = list1.next;
            }else{
                p.next = list2;
                list2 = list2.next;
            }
            p = p.next;
        }
        if(list1 != null){
            p.next = list1;
        }
        if(list2 != null){
            p.next = list2;
        }
        return  resultNode.next;
    }

}