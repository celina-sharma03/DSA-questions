# 112. Path Sum

**Difficulty:** Easy  
**Topics:** Tree, DFS, Binary Tree  
**Link:** https://leetcode.com/problems/path-sum/

## Problem

Given the root of a binary tree and an integer `targetSum`, determine if the tree has a **root-to-leaf path** whose values add up to `targetSum`.

## Approach: DFS

Use recursion to subtract each node's value from `targetSum`.

At a leaf node, check whether the remaining sum equals the node's value.

```text
targetSum → subtract current node → move left/right
                         ↓
                    reach leaf
                         ↓
              remaining sum == value
```

## Java Solution

```java
class Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null)
            return false;

        if (root.left == null && root.right == null)
            return targetSum == root.val;

        return hasPathSum(root.left, targetSum - root.val)
            || hasPathSum(root.right, targetSum - root.val);
    }
}
```

## Complexity

| | Time | Space |
|---|---|---|
| DFS | O(n) | O(h) |

`n` is the number of nodes and `h` is the height of the tree.

## Key Takeaway

At every node, **subtract its value from the target**. At a leaf, check whether the remaining target equals the leaf's value.