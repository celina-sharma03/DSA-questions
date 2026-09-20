# 54. Spiral Matrix

**Difficulty:** Medium
**Topics:** Array, Matrix, Simulation
**Link:** https://leetcode.com/problems/spiral-matrix/

## Problem

Return all elements of a matrix in spiral order — right along the top, down the
right side, left along the bottom, up the left side, then inward and repeat.

```
[[1,2,3],
 [4,5,6],   ->  [1,2,3,6,9,8,7,4,5]
 [7,8,9]]
```

## Approach: four shrinking boundaries

Track the four edges of the not-yet-visited region — `top`, `bottom`, `left`,
`right` — and walk one complete leg at a time, retracting the corresponding
boundary after each:

| leg | direction | then |
|---|---|---|
| top row, `left → right` | rightwards | `top++` |
| right column, `top → bottom` | downwards | `right--` |
| bottom row, `right → left` | leftwards | `bottom--` |
| left column, `bottom → top` | upwards | `left++` |

The outer loop continues while `top <= bottom && left <= right` — that is, while
any unvisited region remains.

Maintaining boundaries rather than a `visited` grid is what keeps this O(1) in
extra space: the four integers *are* the record of what's been consumed.

## The guards are the whole difficulty

```java
if (top <= bottom) { /* bottom row */ }
if (left <= right) { /* left column */ }
```

Without them, thin matrices are traversed twice.

Take a single row, `[[1,2,3]]`. The top-row leg consumes all of it and sets
`top = 1`, which now exceeds `bottom = 0` — the matrix is finished. But the
bottom-row leg sits in the same iteration and would happily walk row 0 again,
backwards, emitting `3,2,1` on top of what was already collected.

The same applies to a single column and the left-column leg.

Checking before the third and fourth legs — but not before the first two, which
can't have been invalidated yet within the same iteration — is precisely the right
placement. Shifting those checks to the loop condition alone doesn't work, because
the boundaries can cross *midway through* an iteration.

## A formatting note

The original submission wrote the `for` loops without braces:

```java
for (int i = left; i <= right; i++)
    ord.add(matrix[top][i]);
    top++;                      // NOT part of the loop, despite the indentation
```

This is correct — a braceless `for` takes exactly the next statement as its body,
so `top++` runs once after the loop, which is what's wanted. But the indentation
suggests otherwise, and a reader has to stop and count.

Braces have been added here. Nothing about the behaviour changed; it just no
longer requires a second look. Worth making a habit, since the same pattern is a
genuine bug source when someone later adds a second line to the loop body and it
silently lands outside it.

## Complexity

- **Time:** O(m × n) — every cell emitted exactly once
- **Space:** O(1) extra, not counting the output list

## Verification

**Every shape from 1×1 to 10×10** — all 100 of them, the full constraint range —
checked three ways:

- the order matches a reference that walks with a direction vector and a `visited`
  grid, turning right when the next step would leave the grid or revisit a cell —
  a completely different mechanism from boundary tracking
- the output length is exactly `rows × cols`
- every cell appears exactly once, so nothing is skipped or emitted twice

All three held for all 100 shapes, including every single-row and single-column
case. Plus 50,000 random matrices with values spanning -100 to 100, confirming
nothing depends on the contents.
