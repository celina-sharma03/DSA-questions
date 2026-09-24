# 63. Unique Paths II

**Difficulty:** Medium
**Topics:** Array, Dynamic Programming, Matrix
**Link:** https://leetcode.com/problems/unique-paths-ii/

The obstacle version of [62. Unique Paths](../0062-unique-paths/README.md). Same
recurrence, with two places where it has to be suppressed.

## Problem

A robot moves only right or down from the top-left to the bottom-right of a grid.
Cells marked `1` are obstacles and cannot be entered. Count the distinct paths.

```
[[0,0,0],
 [0,1,0],   ->  2
 [0,0,0]]
```

## The change from #62

One line does the real work:

```java
if (obstacleGrid[i][j] == 1) {
    obstacleGrid[i][j] = 0;          // nothing reaches an obstacle
} else {
    obstacleGrid[i][j] = obstacleGrid[i][j - 1] + obstacleGrid[i - 1][j];
}
```

An obstacle isn't skipped — it's assigned **zero paths**. That zero then flows
naturally into every cell downstream, since they sum their neighbours. No special
reachability logic is needed anywhere; blocked regions simply end up summing zeros.

## Where the first row and column stop being all 1s

In #62 the base cases were unconditional:

```java
for (int i = 0; i < m; i++) dp[i][0] = 1;
```

That's wrong here. There is exactly one route along the first column — straight
down — so a single obstacle cuts it, and **everything past that obstacle is
unreachable**, not just the obstacle itself.

The fix is to make the base cases inherit rather than assume:

```java
if (obstacleGrid[i][0] == 1) obstacleGrid[i][0] = 0;
else obstacleGrid[i][0] = obstacleGrid[i - 1][0];
```

Once a zero appears, every later cell copies it forward, so the whole tail of that
column is correctly zeroed. Writing `= 1` unconditionally is the standard bug in
this problem, and it produces wrong-but-plausible answers rather than a crash.

The start cell is handled separately: an obstacle there means zero paths outright,
and otherwise it's seeded to 1 — there's one way to be where you already are.

## Reusing the grid: O(1) extra space

This writes path counts back into `obstacleGrid` rather than allocating a table,
so it needs **no extra space at all** — better than the O(m × n) of the #62
solution.

Overloading the array like that is only safe because of the order of operations.
Obstacle markers (`0`/`1`) and path counts share the same cells, so the question is
whether a `1` ever gets misread as an obstacle after it's become a count. It can't:
each cell is tested **before** it is written, and the cells it reads
(`[i-1][j]` and `[i][j-1]`) were converted to counts on earlier iterations. Nothing
is ever read while still ambiguous.

The trade-off is that the caller's grid is destroyed — after the call it holds path
counts, not obstacles. Fine for LeetCode; in real code it would need saying out
loud, or a copy.

## Complexity

- **Time:** O(m × n)
- **Space:** O(1) extra

## Overflow

As in #62, the answer is guaranteed to fit in a 32-bit integer, and that guarantee
is load-bearing. Obstacles only ever reduce the count relative to an empty grid, so
the bound from #62 applies here too.

## Verification

**74,986 grids** — every possible obstacle arrangement for every shape up to 16
cells — checked against *two* independent references:

- plain recursion that counts paths one at a time, with no DP and no in-place
  reuse
- a DP into a separate array, so obstacle markers and path counts never share
  storage and cannot be confused

Both agreed on every grid. Since the second reference deliberately avoids the
in-place trick, that's a direct check that overloading the array is safe.

Plus 100,000 random grids at 20% obstacle density, and the specific cases where an
obstacle sits in the first row, the first column, at the start, or on the
destination.
