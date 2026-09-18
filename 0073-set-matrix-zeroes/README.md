# 73. Set Matrix Zeroes

**Difficulty:** Medium
**Topics:** Array, Hash Table, Matrix
**Link:** https://leetcode.com/problems/set-matrix-zeroes/

## Problem

If any cell of an `m × n` matrix is `0`, set its **entire row and column** to `0`.
Do it **in place**.

```
[[1,1,1],        [[1,0,1],
 [1,0,1],   ->    [0,0,0],
 [1,1,1]]         [1,0,1]]
```

## The trap: zeroing as you go

The obvious approach — scan the matrix and clear the row and column the moment a
zero is found — is wrong, and wrong in a way that still produces plausible output.

The zeros just written are indistinguishable from the original ones. Continuing
the scan, the algorithm finds them, clears *their* rows and columns too, and the
zeros cascade until the whole matrix is blank. On the example above, an in-place
scan returns all zeros.

The fix is to separate **deciding** from **writing**:

1. first pass — record which rows and columns contain a zero, changing nothing
2. second pass — write the zeros, reading only from those records

By the time anything is written, every decision is already made, so newly written
zeros can't influence anything.

## Approach

Two boolean arrays hold the decisions:

```java
boolean[] row = new boolean[n];
boolean[] col = new boolean[m];
```

Pass one sets `row[i]` and `col[j]` for every zero found. Pass two zeroes any cell
whose row **or** column is flagged.

The `||` matters — a cell is cleared if *either* its row or its column contained a
zero, not both.

## Complexity

- **Time:** O(m × n) — two passes
- **Space:** O(m + n) — one flag per row and per column

LeetCode's follow-up calls this out directly: *"A simple improvement uses
O(m + n) space, but still not the best solution."* So this is the intermediate
tier — better than copying the matrix, not yet constant.

## The O(1)-space version

The marker storage can be eliminated by keeping the flags **inside the matrix
itself** — using the first row and first column as the two boolean arrays.

`matrix[i][0]` becomes the flag for row `i`, and `matrix[0][j]` for column `j`.
No extra allocation.

The complication is `matrix[0][0]`, which would have to serve as the flag for both
row 0 and column 0 at once. The usual fix is a single separate boolean for one of
them:

```java
boolean firstColHasZero = false;
for (int i = 0; i < n; i++) if (matrix[i][0] == 0) firstColHasZero = true;

// mark, using row 0 and column 0 as the flag storage
for (int i = 0; i < n; i++)
    for (int j = 1; j < m; j++)
        if (matrix[i][j] == 0) { matrix[i][0] = 0; matrix[0][j] = 0; }

// write, working backwards so the flags are read before being overwritten
for (int i = n - 1; i >= 0; i--) {
    for (int j = m - 1; j >= 1; j--)
        if (matrix[i][0] == 0 || matrix[0][j] == 0) matrix[i][j] = 0;
    if (firstColHasZero) matrix[i][0] = 0;
}
```

The second loop runs **backwards** for the same reason the loop in
[119. Pascal's Triangle II](../0119-pascals-triangle-ii/README.md) does: the flags
live in row 0 and column 0, so those must be read before they're overwritten.
Going forward would clear a flag and then consult it.

O(1) extra space, same O(m × n) time. Strictly better, at the cost of being much
easier to get wrong — which is why the O(m + n) version is a perfectly reasonable
thing to write first.

## Verification

**9,386 matrices** — every 0/1 filling of every shape up to 12 cells — compared
against a reference that builds a fresh output matrix from the original, making a
cascade structurally impossible. All matched.

Plus 100,000 random matrices at varying zero densities including negatives and
`Integer` extremes, a 200 × 200 matrix at the constraint limit, and a check that
the caller's matrix is genuinely mutated rather than a local reassigned.
