package com.jiw.dudu.structure.sort;

/**
 * @Description 选择排序
 * @Author pangh
 * @Date 2022年11月14日
 * @Version v1.0.0
 */
public class ChoiceSort {

    public int[] sortArray(int[] nums){
        if(nums.length == 0){
            return nums;
        }
        for(int i=0;i<nums.length;i++){
            // 最小数的下标，每个循环开始总是假设第一个数最小
            int minIndex = i;
            for(int j=i;j<nums.length;j++){
                if(nums[j] < nums[minIndex]){
                    minIndex = j;
                }
            }
            System.out.println("最小数为：" + nums[minIndex]);
            int temp = nums[minIndex];
            nums[minIndex] = nums[i];
            nums[i] = temp;
            PrintArray.print(nums);
            System.out.println("-----------------");
        }
        return nums;
    }

    public static void main(String[] args) {
        PrintArray.print(PrintArray.SRC);
        System.out.println("=========================================");
        ChoiceSort choiceSort = new ChoiceSort();
        int[] dest = choiceSort.sortArray(PrintArray.SRC);
        PrintArray.print(dest);
    }

}