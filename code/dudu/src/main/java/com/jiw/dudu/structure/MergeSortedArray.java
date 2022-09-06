package com.jiw.dudu.structure;

import org.junit.Test;

/**
 * @Description 合并两个有序数组
 * @Author pangh
 * @Date 2022年09月06日
 * @Version v1.0.0
 *
 * 力扣算法题_88：https://leetcode.cn/problems/merge-sorted-array/
 * 解题思路：
 * 1. 对两个数组，只循环一遍，考虑使用双指针方法
 * 2. 循环遍历最大的数组长度
 * 3. 通过对比两个数组槽位的大小，较小的数组要放到一个临时数组中，并且指针后移，较大的数组指针不动
 * 4. 直到循环完毕，可有序合并两个数组
 */
public class MergeSortedArray {

    @Test
    public void test(){
        merge(new int[]{1,2,3,0,0,0},3,new int[]{2,5,6},3);
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int k = m+n;
        int[] tempNums = new int[k];
        // nums1的指针
        int leftPointer = 0;
        // nums2的指针
        int rightPointer = 0;
        // 开始循环两个数组
        for(int i =0;i<k;i++){
            if(leftPointer >= m){
                tempNums[i] = nums2[rightPointer++];
            }else if(rightPointer >= n){
                tempNums[i] = nums1[leftPointer++];
            }else if(nums1[leftPointer] >= nums2[rightPointer]){
                tempNums[i] = nums2[rightPointer++];
            }else{
                tempNums[i] = nums1[leftPointer++];
            }
        }
        for(int i=0;i<k;i++){
            nums1[i] = tempNums[i];
        }

    }



}