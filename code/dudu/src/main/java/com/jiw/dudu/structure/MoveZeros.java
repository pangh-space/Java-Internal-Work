package com.jiw.dudu.structure;

/**
 * @Description 移动零题目
 * @Author pangh
 * @Date 2022年09月07日
 * @Version v1.0.0
 *
 * 力扣算法题_283：https://leetcode.cn/problems/move-zeroes/
 * 解题思路：
 * 1. 使用双指针思路来解题
 * 2. 使用i 来遍历所有数组元素，使用currentPoint来标识当前数据已经移动到那个位置
 * 3. 遍历结束后，再重新从currentPoint 的位置遍历剩余下标，置零操作
 */
public class MoveZeros {

    public void moveZeroes(int[] nums) {

        int currentPoint = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i] != 0){
                nums[currentPoint++] = nums[i];
            }
        }
        for(int i=currentPoint;i<nums.length;i++){
            nums[i] = 0;
        }

    }

}