# 118. Pascal's Triangle

**Difficulty:** Easy
**Topics:** Array, Dynamic Programming
**Link:** https://leetcode.com/problems/pascals-triangle/

## Problem

Return the first `numRows` of Pascal's triangle, where each number is the sum of
the two directly above it.

```
                1
              1   1
            1   2   1
          1   3   3   1
        1   4   6   4   1
```

## Approach

Build row by row, each from the one before it.

Every row starts and ends with `1`. The interior entries are the only ones that
need computing, and each is the sum of the two entries above:

```java
currRow.add(prevRow.get(j - 1) + prevRow.get(j));
```

The inner loop runs `j` from `1` to `i - 1`, which is exactly the interior — the
two `1`s are appended outside it. That's why row `i = 1` produces `[1,1]`
correctly: the loop body never executes, and the two hardcoded `1`s are the whole
row.

This is dynamic programming in its simplest form. Each row is a subproblem, and
row `i` is built directly from the stored solution to row `i-1` rather than
recomputed from scratch.

## Why not compute each entry directly?

Entry `j` of row `i` is the binomial coefficient C(i, j) = i! / (j!(i-j)!), so
each could be calculated independently. That's worse here:

- factorials overflow fast — 21! already exceeds `long`
- it's more arithmetic per entry than a single addition
- the previous row is already sitting in memory

Building on the previous row turns each entry into one addition. That
"reuse the last answer instead of recomputing" move is the whole idea behind DP,
and it appears here in about as plain a form as it ever gets.

## Complexity

Row `i` holds `i + 1` entries, so the triangle holds `1 + 2 + … + numRows`
entries — that's `numRows(numRows + 1) / 2`.

- **Time:** O(numRows²)
- **Space:** O(numRows²) for the output

Quadratic is optimal here, since the output itself is quadratic in size — you
can't produce that many numbers in less time than it takes to write them down.

## Overflow

Constraints cap `numRows` at 30. The largest entry is then C(29, 14) =
77,558,760, comfortably inside `int`. Push much past row 33 and `int` would
overflow — Pascal's triangle grows roughly like 2ⁿ across each row.

## Verification

Rows 1 through 30 checked entry by entry against binomial coefficients computed
by an independent multiplicative formula, not the additive recurrence — so a bug
in the recurrence could not hide behind a reference that shares it. Row sums were
also confirmed to equal 2ⁱ, and every row confirmed symmetric.
