package com.jiw.dudu.structure;

/**
 * @Description LeetCode002
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 */
public class LeetCode002 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //创建虚拟头结点
        ListNode virtualHead = new ListNode(-1, null);
        //前置指针
        ListNode prev = virtualHead;
        //进位保存
        int carry = 0;
        //判断链表向前走的条件
        while (l1 != null || l2 != null || carry != 0) {
            //两数相加sum，相加后可能是一位，也可能是两位 8+5=13
            int sum = carry;
            //如果l1 != null ，开始加
            if (l1 != null) {
                sum = sum + l1.val;
                l1 = l1.next;
            }
            //如果l2 != null，开始加
            if (l2 != null) {
                sum = sum + l2.val;
                l2 = l2.next;
            }
            //构建新的Node，前置prev节点-当前节点-后置next指针，没个节点只能存一位数字
            ListNode newNode = new ListNode(sum % 10);
            //第一次prev指针就是虚拟头节点，从第二次以后后续值
            prev.next = newNode;
            // 0 <= Node.val <= 9，进位carry不是0就是1
            carry = sum / 10;
            //前置指针向后走一步
            prev = newNode;
        }

        return virtualHead.next;
    }

    public static void main(String[] args) {

        ListNode list1 = new ListNode(2);
        ListNode node1_1 = new ListNode(4);
        ListNode node1_2 = new ListNode(3);
        node1_1.next = node1_2;
        list1.next = node1_1;

        ListNode list2 = new ListNode(5);
        ListNode node2_1 = new ListNode(6);
        ListNode node2_2 = new ListNode(4);
        node2_1.next = node2_2;
        list2.next = node2_1;

        LeetCode002 leetCode002 = new LeetCode002();
        ListNode resultNode = leetCode002.addTwoNumbers(list1, list2);
        while (resultNode != null){
            System.out.print(resultNode.val);
            resultNode = resultNode.next;
        }
        System.out.println();
        System.out.println("==================");

        System.out.println(17 / 10); //1
        System.out.println(17 % 10); //7

        System.out.println(7 / 10); // 0
        System.out.println(7 % 10); // 7
    }

}