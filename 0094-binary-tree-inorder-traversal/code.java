import java.util.ArrayList;
import java.util.List;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    public List<Integer> inorderTraversal(TreeNode root) {

        List<Integer> list = new ArrayList<>();

        helper(root, list);

        return list;
    }

    public void helper(TreeNode root, List<Integer> list) {

        if (root != null) {

            // Left
            helper(root.left, list);

            // Root
            list.add(root.val);

            // Right
            helper(root.right, list);
        }
    }
}