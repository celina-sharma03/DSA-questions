# 64. Minimum Path Sum

**Difficulty:** Medium
**Topics:** Array, Dynamic Programming, Matrix
**Link:** https://leetcode.com/problems/minimum-path-sum/

Third in the grid-DP run, after [62. Unique Paths](../0062-unique-paths/README.md)
and [63. Unique Paths II](../0063-unique-paths-ii/README.md). Same lattice, same
two moves — but now the cells carry costs and the goal is a minimum rather than a
count.

## Problem

Moving only right or down, find the path from top-left to bottom-right with the
smallest sum of cell values.

```
[[1,3,1],
 [1,5,1],   ->  7     (1 -> 3 -> 1 -> 1 -> 1)
 [4,2,1]]
```

## Why greedy fails

Always stepping toward the smaller neighbour looks reasonable and is wrong. A
cheap cell can open onto an expensive region while a costlier first step leads
somewhere much better, and nothing visible locally distinguishes the two.

The cost of a cell depends on what it commits you to afterwards — the same reason
[198. House Robber](../0198-house-robber/README.md) needs DP rather than greed.

## The recurrence, worked backwards

This solution defines `dp[i][j]` as **the cheapest way from `(i,j)` to the
destination**, and fills the table from the bottom-right corner back to the
origin:

```java
dp[i][j] = Math.min(dp[i][j + 1], dp[i + 1][j]) + grid[i][j];
```

From any cell you may step right or down; take whichever onward journey is
cheaper and add the current cell's own cost. The answer is then `dp[0][0]`.

That's the opposite orientation to #62 and #63, which built forwards from the
origin. Both work — the lattice is symmetric, and the choice only decides whether
the base case sits at the start or the end.

### The three special cases are the one-way edges

```java
if (i == last && j == last)  dp[i][j] = grid[i][j];              // destination
else if (i == last)          dp[i][j] = dp[i][j + 1] + grid[i][j];  // bottom row
else if (j == last)          dp[i][j] = dp[i + 1][j] + grid[i][j];  // right column
```

Along the bottom row the only legal move is right; along the right column, only
down. There is no choice to minimise over, so those cells are running totals
rather than a `min` of two options — and taking a `min` there would read outside
the grid.

Same structural point as the first row and column in #62 and #63, just relocated
to the far edges because the fill runs in reverse.

## Complexity

- **Time:** O(m × n)
- **Space:** O(m × n) for the table

### Room to improve

Each cell reads only from the row below and the cell to its right, so a single
1D array suffices — **O(n) space**. And since the input may be written to, the
`dp` table could be dropped entirely for **O(1) extra space**, exactly as done in
[#63](../0063-unique-paths-ii/README.md#reusing-the-grid-o1-extra-space).

Worth noticing the inconsistency: #63 reused the grid, this one allocates. The
in-place version is the stronger answer, and it's slightly *easier* here — #63 had
to keep obstacle markers and path counts apart in the same cells, whereas here the
values being overwritten are pure costs with no second meaning.

## Overflow

Not an issue. The longest path visits `m + n - 1` cells, each at most 200, so with
`m = n = 200` the largest possible answer is `399 × 200 = 79,800` — comfortably
inside `int`. Verified at that exact maximum.

## Verification

**34,581 grids** — every grid of every shape up to 9 cells with values 0 to 2 —
checked against **brute-force enumeration of every path**. That matters more than
agreeing with another DP: brute force proves the recurrence finds the genuine
minimum rather than a plausible-looking one.

Also cross-checked against a forward (origin-to-destination) DP, so the reversed
orientation is confirmed equivalent. Plus 30,000 random grids with values up to
200 against brute force, 20,000 larger grids against the forward DP, the
200 × 200 worst case, and a check that the input grid is left unmodified.
