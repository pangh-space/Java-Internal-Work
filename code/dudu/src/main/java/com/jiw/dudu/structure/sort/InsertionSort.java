package com.jiw.dudu.structure.sort;

/**
 * @Description 插入排序
 * @Author pangh
 * @Date 2022年11月14日
 * @Version v1.0.0
 */
public class InsertionSort {

    public int[] sortArray(int[] nums){
        if(nums.length == 0){
            return nums;
        }
        // 当前排序数据，该元素之前的元素均已被排序过
        int currentValue;
        for(int i=0;i<nums.length-1;i++){
            // 已被排序数据的索引
            int preIndex = i;
            currentValue = nums[preIndex + 1];
            System.out.println("待排序元素索引：" + (i + 1) + "，值为：" + currentValue + "，已被排序数据的索引：" + preIndex);
            // 在已被排序过的数据中倒序寻找合适的位置，如果当前待排序数据比比较的元素要小，将比较的元素后移一位
            while (preIndex >= 0 && currentValue < nums[preIndex]){
                // 将当前元素后移一位
                nums[preIndex + 1] = nums[preIndex];
                preIndex--;
                PrintArray.print(nums);
            }
            // while循环结束时，说明已经找到了当前待排序数据的合适位置，插入
            nums[preIndex + 1] = currentValue;
            System.out.println("本轮被插入排序后的数组");
            PrintArray.print(nums);
            System.out.println("-----------------------");
        }
        return nums;
    }

    public static void main(String[] args) {
        PrintArray.print(PrintArray.SRC);
        System.out.println("==========================================");
        int[] dest = new InsertionSort().sortArray(PrintArray.SRC);
        PrintArray.print(dest);
    }

}