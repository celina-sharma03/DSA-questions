# 130. Surrounded Regions

**Difficulty:** Medium  
**Topics:** Array, DFS, Matrix  
**Link:** https://leetcode.com/problems/surrounded-regions/

## Problem

Given an `m x n` board containing `X` and `O`, capture all regions of `O` that are completely surrounded by `X`. Any `O` connected to the boundary cannot be captured.

## Approach: DFS

Instead of finding surrounded regions directly, start DFS from every **boundary `O`** and mark all connected `O`s as `T`.

After that:
- Remaining `O` → `X`
- `T` → `O`

This works because only `O`s connected to the boundary are safe.

## Java Solution

```java
class Solution {
    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;

        int m = board.length;
        int n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == 0 || i == m - 1 || j == 0 || j == n - 1)
                        && board[i][j] == 'O') {
                    dfs(board, i, j);
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O')
                    board[i][j] = 'X';
                else if (board[i][j] == 'T')
                    board[i][j] = 'O';
            }
        }
    }

    private void dfs(char[][] board, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 ||
            j >= board[0].length || board[i][j] != 'O')
            return;

        board[i][j] = 'T';

        dfs(board, i + 1, j);
        dfs(board, i - 1, j);
        dfs(board, i, j + 1);
        dfs(board, i, j - 1);
    }
}
```

## Complexity

| | Time | Space |
|---|---|---|
| DFS | O(m × n) | O(m × n) |

## Key Takeaway

**Protect boundary-connected `O`s first. Then every remaining `O` must be surrounded and can be changed to `X`.**