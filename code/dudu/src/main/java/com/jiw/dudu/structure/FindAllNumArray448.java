package com.jiw.dudu.structure;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 找到所有数组中消失的数字
 * @Author pangh
 * @Date 2022年09月08日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/find-all-numbers-disappeared-in-an-array/
 * 解题思路：
 * 1.
 */
public class FindAllNumArray448 {

    @Test
    public void test(){

        System.out.println(findDisappearedNumbers1(new int[]{4,3,2,7,8,2,3,1}));

    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for(int num : nums){
            int subscript = Math.abs(num) -1;
            if(nums[subscript] > 0){
                nums[subscript] = -nums[subscript];
            }
        }
        for(int i=0;i<nums.length;i++){
            if(nums[i] > 0){
                list.add(i+1);
            }
        }
        return list;
    }

    public List<Integer> findDisappearedNumbers1(int[] nums){
        int n = nums.length;
        for (int num : nums) {
            int x = (num - 1) % n;
            nums[x] += n;
        }
        List<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            if (nums[i] <= n) {
                ret.add(i + 1);
            }
        }
        return ret;
    }

}