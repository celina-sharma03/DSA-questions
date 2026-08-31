import java.util.ArrayList;
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        helper(root,list);
        return list;
    }
    public void helper(TreeNode root,List<Integer> list){
        if(root != null){
            helper(root.left,list);
            list.add(root.val);
            helper(root.right,list);
        }
    }
}