package com.jiw.dudu.structure;

import java.util.Stack;

/**
 * @Description 用栈实现队列
 * @Author pangh
 * @Date 2022年09月25日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/implement-queue-using-stacks/
 * 解题思路：
 *
 */
public class MyQueue {

    private static Stack<Integer> inStack;
    private static Stack<Integer> outStack;

    public MyQueue() {
        inStack = new Stack<>();
        outStack = new Stack<>();
    }

    public void push(int x) {
        inStack.push(x);
    }

    public int pop() {
        if(outStack.isEmpty()){
            in2out();
        }
        return outStack.pop();
    }

    public int peek() {
        if(outStack.isEmpty()){
            in2out();
        }
        return outStack.peek();
    }

    public boolean empty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    private void in2out(){
        while (!inStack.isEmpty()){
            outStack.push(inStack.pop());
        }
    }

}