package com.jiw.dudu.structure;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 二叉树的前序遍历，记忆法则：根左右
 * 参考博客：https://blog.csdn.net/u010414589/article/details/115415226
 * 1. 直接输出根节点
 * 2. 然后遍历根节点的左子树，按照【根左右】法则继续遍历
 * 3. 最后遍历根几点的右子树，按照【根左右】法则继续遍历完成
 * @Author pangh
 * @Date 2022年11月08日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/binary-tree-preorder-traversal/
 * 解题思路：
 *
 */
public class BinaryTreePreorderTraversal144 {

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        accessTree(root,res);
        return res;
    }

    /**
     * 递归遍历树
     *
     * @param root
     * @param res
     */
    public void accessTree(TreeNode root,List<Integer> res){
        if(root == null){
            return;
        }
        res.add(root.val);
        accessTree(root.left,res);
        accessTree(root.right,res);
    }


    /**
     * 通过循环遍历树
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversalWithLoop(TreeNode root){
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        while (root != null || !stack.isEmpty()){
            while (root != null){
                res.add(root.val);
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            root = root.right;
        }
        return res;
    }

}