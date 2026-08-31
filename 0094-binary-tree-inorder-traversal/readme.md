# 94. Binary Tree Inorder Traversal

**Difficulty:** Easy
**Topics:** Stack, Tree, Depth-First Search
**Link:** https://leetcode.com/problems/binary-tree-inorder-traversal/

## Problem

Given the root of a binary tree, return its **inorder traversal**.

Inorder means:

**Left → Root → Right**

For example:

```text
    1
     \
      2
     /
    3
```

Output:

`[1, 3, 2]`

## Approach: Iterative Inorder Traversal

Use a `Stack` to simulate the recursive traversal.

The idea is:

1. Keep moving to the left child and push every node into the stack.
2. When there is no left child, pop a node and add its value to the result.
3. Move to its right child.
4. Repeat until both the current node and stack are empty.

The stack allows us to remember the nodes whose left subtree has already been processed but whose value is still pending.

## Java Solution

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {

        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {

            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            result.add(current.val);

            current = current.right;
        }

        return result;
    }
}
```

## Example

For:

```text
    1
     \
      2
     /
    3
```

Traversal happens as:

`1 → 3 → 2`

So:

`[1, 3, 2]`

## Complexity

|                     | Time | Space |
| ------------------- | ---- | ----- |
| Iterative Traversal | O(n) | O(h)  |

Every node is visited once. The stack can hold up to the height `h` of the tree.

## Key Takeaway

Inorder traversal always follows:

**Left → Root → Right**

The iterative pattern to remember is:

`go left → push → pop & process → go right`

The stack simply replaces the recursion call stack.
