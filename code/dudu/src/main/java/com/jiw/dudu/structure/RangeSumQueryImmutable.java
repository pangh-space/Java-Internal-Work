package com.jiw.dudu.structure;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description 区域和检索 - 数组不可变
 * @Author pangh
 * @Date 2022年09月05日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/range-sum-query-immutable/
 * 解题思路：
 * 1. 定义一个前置数组，前置数组的长度为传入数据长度+1
 * 2. 前置数据从下标1开始算起，使用公式（前置数组[i] = 前置数组[i-1]+传入数组[i-1]）填充前置数组数据
 * 3. 计算给定下标索引（包括索引之间的所有数据）的和，使用公式：前置数组[end+1]-前置数组[start]
 */
@Slf4j
public class RangeSumQueryImmutable {


    public static void main(String[] args) {
        int[] nums = new int[]{2,5,3,0,-7,4,-3,-6};
        RangeSumQueryImmutable rangeSumQueryImmutable = new RangeSumQueryImmutable(nums);
        log.info("区间2~5，计算结果：{}",rangeSumQueryImmutable.sumRange(2,5));
        log.info("区间3~6，计算结果：{}",rangeSumQueryImmutable.sumRange(3,6));
    }


    // 定义前置数组
    private int[] prefixNum;
    public RangeSumQueryImmutable(int[] nums){
        prefixNum = new int[nums.length+1];
        for(int i=1;i<prefixNum.length;i++){
            prefixNum[i] = prefixNum[i-1] + nums[i-1];
        }
    }


    public int sumRange(int left, int right) {
        return prefixNum[right + 1] - prefixNum[left];
    }

}