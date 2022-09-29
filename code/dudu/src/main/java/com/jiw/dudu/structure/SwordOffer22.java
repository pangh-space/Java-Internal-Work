package com.jiw.dudu.structure;

/**
 * @Description 链表中倒数第k个节点
 * @Author pangh
 * @Date 2022年09月29日
 * @Version v1.0.0
 *
 * 剑指offer：https://leetcode.cn/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/?favorite=xb9nqhhg
 * 解题思路：
 * 1. 定义临时节点 pTemp 和返回节点 pKNode
 * 2. 将临时加点移动到K正序所在节点
 * 3. 临时节点不为空，从头开始同时移动返回节点和临时节点
 * 4. 当临时节点移动到链表末尾，返回节点就是要找节点
 */
public class SwordOffer22 {

    /**
     * 验证数据：6，0，11，8，9，5，4
     *
     * @param args
     */
    public static void main(String[] args) {
        ListNode test = new ListNode(6);
        test.setNext(new ListNode(0))
                .setNext(new ListNode(11))
                .setNext(new ListNode(8))
                .setNext(new ListNode(9))
                .setNext(new ListNode(5))
                .setNext(new ListNode(4));
        System.out.println(getKthFromEnd(test,3));
    }

    public static ListNode getKthFromEnd(ListNode head, int k) {
        if(k <= 0 || head == null){
            return null;
        }
        ListNode pTemp = head,pKNode = null;
        for(int count =1;count<k;count++){
            if(pTemp != null){
                pTemp = pTemp.next;
            }
        }
        while (pTemp != null){
            if(pKNode == null){
                pKNode = head;
            }else{
                pKNode = pKNode.next;
            }
            pTemp = pTemp.next;
        }
        if(pKNode != null){
            return pKNode;
        }
        return null;
    }

}