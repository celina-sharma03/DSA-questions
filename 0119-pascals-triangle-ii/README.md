# 119. Pascal's Triangle II

**Difficulty:** Easy
**Topics:** Array, Dynamic Programming
**Link:** https://leetcode.com/problems/pascals-triangle-ii/

Follow-on from [118. Pascal's Triangle](../0118-pascals-triangle/README.md) —
that one returns the whole triangle, this one returns a single row using only
O(k) space.

## Problem

Return row `rowIndex` of Pascal's triangle (0-indexed).

```
rowIndex = 3  ->  [1,3,3,1]
rowIndex = 0  ->  [1]
```

The follow-up asks for **O(rowIndex) extra space** — so building all the rows and
returning the last one, as in #118, doesn't qualify.

## Approach: update one row in place

Keep a single list and transform it from row `i` into row `i+1`, in place.

Each step appends a `1`, then rewrites the interior entries by adding each to its
left neighbour:

```java
triangle.add(1);
for (int j = i - 1; j > 0; j--) {
    triangle.set(j, triangle.get(j - 1) + triangle.get(j));
}
```

Watch `[1,3,3,1]` become `[1,4,6,4,1]`:

| step | list | note |
|---|---|---|
| start | `[1,3,3,1]` | row 3 |
| append 1 | `[1,3,3,1,1]` | |
| `j=3` | `[1,3,3,4,1]` | `3 + 1` |
| `j=2` | `[1,3,6,4,1]` | `3 + 3` |
| `j=1` | `[1,4,6,4,1]` | `1 + 3` |

## Why the loop runs backwards

This is the whole trick, and it's the one line that makes the solution correct.

Each new value needs the **old** value at `j-1`. Walking left-to-right would
overwrite position `j-1` before position `j` reads it, so `j` would consume the
already-updated value and produce garbage.

Going right-to-left, every position is read before anything overwrites it. Index
`j-1` is only touched *after* index `j` is done with it.

The same pattern turns up throughout dynamic programming — it's exactly the trick
that lets the 0/1 knapsack collapse from a 2D table to a 1D array by iterating
capacity in reverse.

The loop stops at `j > 0` because position `0` is always `1` and must not be
touched, and the freshly appended `1` at the end is already correct.

## Complexity

- **Time:** O(rowIndex²) — unavoidable, since row `k` depends on every row above it
- **Space:** O(rowIndex) — one list, which is the output itself; no extra
  structure is allocated

Compare with #118: same time, but space drops from O(k²) to O(k).

## Why the constraint stops at 33

`rowIndex` is capped at 33 because that's the last row that fits in `int`:

| | value | vs `Integer.MAX_VALUE` (2,147,483,647) |
|---|---|---|
| largest entry in row 33 | C(33,16) = 1,166,803,110 | fits |
| largest entry in row 34 | C(34,17) = 2,333,606,220 | overflows |

Not an arbitrary limit — row 34 is precisely where `int` gives out.

## Verification

Rows 0 through 33 checked entry by entry against a multiplicative binomial
formula, independent of the additive recurrence used here. Every row also
confirmed symmetric, summing to 2ⁱ, and of length `i + 1`. Row 33's largest entry
verified against the `int` boundary above.
