package com.jiw.dudu.structure;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 二叉树的后序遍历，记忆法则：左右根
 * 参考博客：https://blog.csdn.net/u010414589/article/details/115415388
 * 1. 遍历左子树，按照规则，进行输出
 * 2. 再遍历右子树，按照规则，进行输出
 * @Author pangh
 * @Date 2022年11月08日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/binary-tree-postorder-traversal/
 * 解题思路：
 *
 */
public class BinaryTreePostorderTraversal145 {

    public List<Integer> postorderTraversal(TreeNode root) {
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
        accessTree(root.left,res);
        accessTree(root.right,res);
        res.add(root.val);
    }

    public List<Integer> postorderTraversalWithLoop(TreeNode root){
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode prevAccess = null;
        while (root != null || !stack.isEmpty()){
            while (root != null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if(root.right == null || root.right == prevAccess){
                res.add(root.val);
                prevAccess = root;
                root = null;
            }else{
                stack.push(root);
                root = root.right;
            }
        }
        return res;
    }

}