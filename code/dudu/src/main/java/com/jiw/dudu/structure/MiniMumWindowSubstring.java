package com.jiw.dudu.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 最小覆盖子串
 * @Author pangh
 * @Date 2022年08月22日
 * @Version v1.0.0
 * <p>
 * 力扣算法题：https://leetcode.cn/problems/minimum-window-substring/
 *
 * 解题思路：
 *
 */
public class MiniMumWindowSubstring {

    public static String minWindow(String s, String t) {
        // 目标字符串各个字母出现的次数
        Map<Character, Integer> needMap = new HashMap<>();
        // 窗口中的相应字符出现的次数
        Map<Character, Integer> windowMap = new HashMap<>();

        // 遍历目标字符串，出现的次数
        for (char ch : t.toCharArray()) {
            needMap.put(ch, needMap.getOrDefault(ch, 0) + 1);
        }
        // 左右指针初始位置
        int left = 0, right = 0;

        // 窗口中满足need 的字符个数
        // 如果valid = need.size 说明窗口已满足条件，已经完全覆盖了串 T
        int valid = 0;

        // 记录最小子串的起始索引及长度
        int start = 0;

        // 满足条件的窗口长度
        int len = Integer.MAX_VALUE;

        while (right < s.length()) {
            // 记录右指针扫描过的字符串
            char c = s.charAt(right);
            // 扩大窗口，向右扩大走
            right++;
            // 进行窗口内数据的一系列更新
            if (needMap.containsKey(c)) {
                windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);
                if (windowMap.get(c).equals(needMap.get(c))) {
                    valid++;
                }
            }
            // 判断左侧窗口是否要收缩
            while (valid == needMap.size()) {
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }

                char d = s.charAt(left);
                left++;
                // 进行窗口内数据的一系列更新
                if (needMap.containsKey(d)) {
                    if (windowMap.get(d).equals(needMap.get(d))) {
                        valid--;
                    }
                    windowMap.put(d, windowMap.getOrDefault(d, 0) - 1);
                }
            }
        }

        // 返回最小覆盖子串
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);

    }

    public static void main(String[] args) {
        String s = minWindow("ADOBECODEBANC", "ABC");

        System.out.println(s);
        System.out.println(s.length());
    }

}