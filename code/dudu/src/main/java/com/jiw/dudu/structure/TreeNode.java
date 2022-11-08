package com.jiw.dudu.structure;

/**
 * @Description 树节点的定义
 * @Author pangh
 * @Date 2022年11月08日
 * @Version v1.0.0
 */
public class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode() {}
     TreeNode(int val)
     { this.val = val; }
     TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
         this.left = left;
         this.right = right;
     }
}
