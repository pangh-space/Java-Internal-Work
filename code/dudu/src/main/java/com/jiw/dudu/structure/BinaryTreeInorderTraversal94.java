package com.jiw.dudu.structure;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 二叉树的中序遍历，记忆法则：左根右
 * 参考博客：https://blog.csdn.net/u010414589/article/details/115415373
 * 1. 需要先从根节点遍历
 * 2. 接着找根节点的左子树一直找到左子树不存在左子树为止
 * 3. 然后输出左子树->根->右子树
 * 4. 之后输出完跟节点以后，按照遍历规则在寻找右子树
 * @Author pangh
 * @Date 2022年11月08日
 * @Version v1.0.0
 *
 * 力扣算法题：https://leetcode.cn/problems/binary-tree-inorder-traversal/
 * 解题思路：
 *
 */
public class BinaryTreeInorderTraversal94 {

    public List<Integer> inorderTraversal(TreeNode root) {
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
        res.add(root.val);
        accessTree(root.right,res);
    }

    /**
     * 通过循环遍历树
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversalWithLoop(TreeNode root){
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        while (root != null || !stack.isEmpty()){
            while (root != null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }

}