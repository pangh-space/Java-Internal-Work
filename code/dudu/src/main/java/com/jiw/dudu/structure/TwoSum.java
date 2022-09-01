package com.jiw.dudu.structure;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 力扣两数之和：https://leetcode.cn/problems/two-sum/
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * 你可以按任意顺序返回答案。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/two-sum
 * @Author pangh
 * @Date 2022年09月01日
 * @Version v1.0.0
 */
@Slf4j
public class TwoSum {

    @Test
    public void test(){
        int[] nums = new int[]{2, 11, 15, 7};
        int target = 9;
        log.info("递归方法数：" + Arrays.toString(this.twoSum(nums,target)));
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> storeNums = new HashMap<>();
        int[] result = new int[2];
        for(int i=0;i<nums.length;i++){
            int another = target - nums[i];
            Integer anotherIndex = storeNums.get(another);
            if(null != anotherIndex){
                result[0] = anotherIndex;
                result[1] = i;
                break;
            }else{
                storeNums.put(nums[i],i);
            }
        }
        return result;
    }

}