package com.jiw.dudu.structure;

/**
 * @Description 二叉树的最大深度
 * @Author pangh
 * @Date 2022年11月10日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/maximum-depth-of-binary-tree/
 * 解题思路：
 *
 */
public class MaximumDepthOfBinaryTree104 {

    public int maxDepth(TreeNode root){
        if(root == null){
            return 0;
        }else{
            return Math.max(maxDepth(root.left),maxDepth(root.right)) + 1;
        }
    }

}