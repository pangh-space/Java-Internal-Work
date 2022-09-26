package com.jiw.dudu.structure;

/**
 * @Description 数组中重复的数字
 * @Author pangh
 * @Date 2022年09月25日
 * @Version v1.0.0
 *
 * 剑指offer：https://leetcode.cn/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof/?favorite=xb9nqhhg
 * 解题思路：
 */
public class SwordOffer03 {

    public int findRepeatNumber(int[] nums) {
        int temp;
        for(int i=0;i<nums.length;i++){
            while (nums[i] != i){
                if(nums[i] == nums[nums[i]]){
                    return nums[i];
                }
                temp = nums[i];
                nums[i] = nums[temp];
                nums[temp] = temp;
            }
        }
        return -1;
    }

}