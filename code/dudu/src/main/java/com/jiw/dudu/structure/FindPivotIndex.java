package com.jiw.dudu.structure;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Description 寻找数组的中心下标
 * @Author pangh
 * @Date 2022年09月05日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/find-pivot-index/
 * 解题思路：
 * 1. 先计算出数组所有下标的总大小prefixSum
 * 2. 循环遍历，每移动一位，将移动的数据累加到leftSum
 * 3. 计算leftSum*2==prefixSum-当前下标位的值
 */
@Slf4j
public class FindPivotIndex {

    @Test
    public void test(){
        int[] nums = new int[]{1, 7, 3, 6, 5, 6};
        pivotIndex(nums);
    }

    public int pivotIndex(int[] nums) {
        int prefixSum;
        int leftSum = 0;
        prefixSum = Arrays.stream(nums).sum();
        for(int i=0;i<nums.length;i++){
            if(leftSum * 2 == prefixSum - nums[i]){
                return i;
            }
            leftSum+=nums[i];
        }
        return -1;
    }

}