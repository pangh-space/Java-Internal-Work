package com.jiw.dudu.structure;

/**
 * @Description 对称二叉树
 * @Author pangh
 * @Date 2022年11月09日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/symmetric-tree/
 * 解题思路：
 *
 */
public class SymmetricTree101 {

    public boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        return deepCheck(root.left,root.right);
    }


    boolean deepCheck(TreeNode left ,TreeNode right){
        // 递归的终止条件是两个节点都为空
        // 或者两个节点中有一个为空
        // 或者两个节点的值不相等
        if(left == null && right == null){
            return true;
        }
        if(left == null || right == null){
            return false;
        }
        if(left.val != right.val){
            return false;
        }
        return deepCheck(left.left,right.right) && deepCheck(left.right,right.left);
    }

}