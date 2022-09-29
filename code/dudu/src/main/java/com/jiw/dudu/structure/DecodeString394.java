package com.jiw.dudu.structure;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 字符串解码
 * @Author pangh
 * @Date 2022年09月29日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/decode-string/
 * 解题思路：
 *
 */
public class DecodeString394 {

    int ptr;

    public String decodeString(String s) {
        LinkedList<String> stk = new LinkedList<>();
        ptr = 0;
        while (ptr < s.length()){
            char cur = s.charAt(ptr);
            if(Character.isDigit(cur)){
                // 处理数字，使数字完整
                String digits = getDigits(s);
                stk.addLast(digits);
            }else if(Character.isLetter(cur) || cur == '['){
                // 处理普通字符和"["
                stk.addLast(String.valueOf(s.charAt(ptr++)));
            }else{
                // 遇见了"]"，处理相匹配的"["之间的字符
                ++ptr;
                // 使用另一个List，将字符串进行组合
                LinkedList<String> sub = new LinkedList<>();
                while (!"[".equals(stk.peekLast())){
                    sub.addLast(stk.removeLast());
                }
                // 因为栈的特点，导致组合的字符串和原本的字符串相比是倒序的，需要反转一次
                Collections.reverse(sub);
                // 左括号出栈
                stk.removeLast();
                // 此时栈顶为当前 sub 对应的字符串应该出现的次数
                int repTime = Integer.parseInt(stk.removeLast());
                StringBuffer t = new StringBuffer();
                String o = getString(sub);
                // 构造字符串
                while (repTime-- > 0){
                    t.append(o);
                }
                stk.addLast(t.toString());
            }
        }
        return getString(stk);
    }

    public String getDigits(String s) {
        StringBuffer ret = new StringBuffer();
        while (Character.isDigit(s.charAt(ptr))) {
            ret.append(s.charAt(ptr++));
        }
        return ret.toString();
    }

    public String getString(LinkedList<String> v) {
        StringBuffer ret = new StringBuffer();
        for (String s : v) {
            ret.append(s);
        }
        return ret.toString();
    }

}