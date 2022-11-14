package com.jiw.dudu.structure;

import java.util.PriorityQueue;

/**
 * @Description  数组中的第K个最大元素
 * @Author pangh
 * @Date 2022年11月14日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/kth-largest-element-in-an-array/
 * 解题思路：
 *
 */
public class KthLargestElementInAnArray215 {

    public int findKthLargest(int[] nums, int k) {

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        for(int i=0;i<nums.length;i++){
            if(priorityQueue.size() < k){
                priorityQueue.offer(nums[i]);
            }else{
                if(nums[i] > priorityQueue.peek()){
                    //目前已最小堆为完全二叉树，假如新进来的元素比二叉堆root大
                    priorityQueue.poll();
                    priorityQueue.offer(nums[i]);
                }
            }
        }
        return priorityQueue.peek();

    }

}