# 62. Unique Paths

**Difficulty:** Medium
**Topics:** Math, Dynamic Programming, Combinatorics
**Link:** https://leetcode.com/problems/unique-paths/

## Problem

A robot starts at the top-left of an `m × n` grid and can only move **right** or
**down**. How many distinct paths reach the bottom-right?

```
m = 3, n = 7   ->  28
m = 3, n = 2   ->  3
```

## The recurrence

Every cell can only be entered from **above** or from the **left**, and those two
sets of paths are disjoint — a path arriving from above is never also a path
arriving from the left. So the counts simply add:

```java
dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
```

### The base cases are the reachable-only-one-way cells

```java
for (int i = 0; i < m; i++) dp[i][0] = 1;   // first column
for (int j = 0; j < n; j++) dp[0][j] = 1;   // first row
```

Reaching anything in column 0 means moving down the whole way — no choices, one
path. Same for row 0, moving right. Filling those first is what gives the
recurrence something to read from, and it means the inner loops can start at 1
without any bounds checking.

## This is Pascal's triangle, rotated

`dp[i][j]` works out to C(i + j, i), so the grid is exactly
[Pascal's triangle](../0118-pascals-triangle/README.md) turned 45°. Each entry is
the sum of the two above it — in #118 that's "up-left and up-right", here it's
"above and left", but it's the same recurrence on the same numbers.

Which means the answer has a **closed form**:

```
uniquePaths(m, n) = C(m + n - 2, m - 1)
```

Any path is a sequence of `m-1` downs and `n-1` rights in some order, so counting
paths is just choosing which of the `m+n-2` moves are the downs. That's O(m)
arithmetic with no table at all — though it needs care to avoid overflowing
mid-computation.

## Complexity

- **Time:** O(m × n)
- **Space:** O(m × n) for the table

### The O(n)-space version

Each cell reads only from directly above and directly left, so one row suffices:

```java
int[] row = new int[n];
Arrays.fill(row, 1);
for (int i = 1; i < m; i++)
    for (int j = 1; j < n; j++)
        row[j] = row[j] + row[j - 1];
return row[n - 1];
```

`row[j]` still holds the previous row's value when it's read (that's the "above"
term) and `row[j-1]` has already been updated for this row (the "left" term). The
two terms the recurrence needs are both sitting in the same array at the right
moment.

Fourth time this collapse has come up — same as #119, #198 and #44.

## What the constraints are really doing

The stated constraint is `1 <= m, n <= 100`, but there's a second one that does far
more work: *the answer is guaranteed to be at most 2 × 10⁹*.

Checking every one of the 10,000 possible `(m, n)` pairs against exact
`BigInteger` arithmetic:

| | count |
|---|---|
| answer fits in `int` | **1,775** |
| answer exceeds `int` | 8,225 |
| within LeetCode's stated 2 × 10⁹ bound | **1,773** |

So barely **18%** of the grids in the nominal range are legal inputs. The smallest
one that overflows is **17 × 19**, and the largest valid square grid is **17 × 17**
(601,080,390 paths).

An `int` DP is correct for every legal input and cannot be correct for the rest —
the value genuinely doesn't fit. The guarantee is what makes `int` the right return
type.

One number worth noticing: an 18 × 18 grid gives **2,333,606,220** paths. That's
C(34,17) — the exact value that
[#119 Pascal's Triangle II](../0119-pascals-triangle-ii/README.md#why-the-constraint-stops-at-33)
identified as the first row of Pascal's triangle to exceed `int`. Same boundary,
same reason, two different problems.

## Verification

Every grid from 1×1 to 100×100 — all 10,000 pairs — evaluated against exact
`BigInteger` values of C(m+n-2, m-1). For all **1,775** whose true answer fits in
`int`, the solution matched exactly, and also agreed with the O(n)-space
formulation. The remaining 8,225 are outside what `int` can represent and outside
what the problem permits.
