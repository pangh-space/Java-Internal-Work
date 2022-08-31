package com.jiw.dudu.structure;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 力扣爬楼梯
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 注意：给定 n 是一个正整数。
 *
 * @Author pangh
 * @Date 2022年08月31日
 * @Version v1.0.0
 */
@Slf4j
public class ClimbingStairs {

    @Test
    public void test(){
        log.info("递归方法数：" + climbStairsWithRecursive(40));
    }

    /**
     * 使用纯递归方法
     *
     * @param n
     * @return
     */
    public int climbStairsWithRecursive(int n){
        if(n == 1){
            return 1;
        }
        if(n == 2){
            return 2;
        }

        return climbStairsWithRecursive(n -1) + climbStairsWithRecursive(n -2);
    }

    /**
     * 递归算法，用HashMap存储中间结果
     */
    private Map<Integer,Integer> storeMap = new HashMap<>();
    public int climbStairs(int n){
        if(n == 1){
            return 1;
        }
        if(n == 2){
            return 2;
        }
        if(null != storeMap.get(n)){
            return storeMap.get(n);
        }else{
            int result = climbStairs(n -1) + climbStairs(n -2);
            storeMap.put(n,result);
            return result;
        }
    }



}