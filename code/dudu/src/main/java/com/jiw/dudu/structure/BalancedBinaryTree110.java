package com.jiw.dudu.structure;

/**
 * @Description 平衡二叉树
 * @Author pangh
 * @Date 2022年11月10日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/balanced-binary-tree/
 * 解题思路：
 *
 */
public class BalancedBinaryTree110 {

    public boolean isBalanced(TreeNode root) {
        if(root == null){
            return true;
        }
        return depth(root) != -1;
    }

    private int depth(TreeNode root){
        if(root == null ){
            return 0;
        }
        int left = depth(root.left);
        int right = depth(root.right);
        if(left == -1 || right == -1 || Math.abs(left - right) > 1){
            return -1;
        }
        return Math.max(left, right) + 1;
    }
}