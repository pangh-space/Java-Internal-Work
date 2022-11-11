package com.jiw.dudu.structure;

/**
 * @Description 反转二叉树
 * @Author pangh
 * @Date 2022年11月11日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/invert-binary-tree/
 * 解题思路：
 *
 */
public class InvertBinaryTree226 {

    public TreeNode invertTree(TreeNode root) {
        if(root == null){
            return null;
        }
        invertTree(root.left);
        invertTree(root.right);
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        return root;
    }

}