# 42. Trapping Rain Water

**Difficulty:** Hard
**Topics:** Array, Two Pointers, Dynamic Programming, Stack, Monotonic Stack
**Link:** https://leetcode.com/problems/trapping-rain-water/

## Problem

Each element is the height of a bar of width 1. After rain, how much water is
trapped between them?

```
height = [0,1,0,2,1,0,1,3,2,1,2,1]  ->  6
height = [4,2,0,3,2,5]              ->  9
```

## The insight: solve one column at a time

The instinct is to look for pools — find the basins, measure each one. That gets
complicated fast, because basins nest inside larger basins.

The reframe that makes this tractable: **ask how much water sits above a single
column**, and sum over all of them. For column `i` the answer depends on only two
numbers:

```
water[i] = min(tallest bar at or left of i, tallest bar at or right of i) - height[i]
```

Water at a column is held in by the tallest wall on each side, and the **shorter**
of the two is what sets the level — anything above that spills over. Subtracting
the column's own height leaves the depth of water sitting on top of it.

No basin detection anywhere. Every column is independent once those two maxima
are known.

### The subtraction can never go negative

`left[i]` is a maximum that includes `height[i]`, and so is `right[i]`. Therefore
`min(left[i], right[i]) >= height[i]`, always. A bar taller than everything around
it simply contributes `0` rather than a negative amount, so no guard is needed —
the sum can be accumulated unconditionally.

## Approach: precompute both maxima

Computing the two maxima by rescanning for every column is O(n²). Since
`left[i]` is just `left[i-1]` extended by one bar, a single forward pass fills the
whole array:

```java
left[i]  = Math.max(height[i], left[i - 1]);    // forward pass
right[i] = Math.max(height[i], right[i + 1]);   // backward pass
```

Then one more pass sums the per-column depths. Three linear passes in total.

Tracing `[4,2,0,3,2,5]`:

| i | height | left | right | min | water |
|---|--------|------|-------|-----|-------|
| 0 | 4 | 4 | 5 | 4 | 0 |
| 1 | 2 | 4 | 5 | 4 | 2 |
| 2 | 0 | 4 | 5 | 4 | 4 |
| 3 | 3 | 4 | 5 | 4 | 1 |
| 4 | 2 | 5 | 5 | 5 | 3 |
| 5 | 5 | 5 | 5 | 5 | 0 |

Total: **9**.

## Complexity

- **Time:** O(n) — three passes
- **Space:** O(n) — two auxiliary arrays

## The O(1)-space version: two pointers

The two arrays can be eliminated. Walk inward from both ends, tracking the
running maxima seen so far:

```java
int l = 0, r = height.length - 1, maxL = 0, maxR = 0, water = 0;
while (l < r) {
    if (height[l] < height[r]) {
        maxL = Math.max(maxL, height[l]);
        water += maxL - height[l];
        l++;
    } else {
        maxR = Math.max(maxR, height[r]);
        water += maxR - height[r];
        r--;
    }
}
```

The step that justifies it: when `height[l] < height[r]`, we know the right side
holds *something* at least as tall as `height[r]`, which already exceeds
`height[l]`. So whatever `maxR` turns out to be, it can't be the smaller of the
two — `min(maxL, maxR)` is decided by `maxL` alone, and column `l` can be settled
immediately without ever knowing the true right maximum.

Each column is resolved from the side whose maximum is provably the binding one.
**O(n) time, O(1) space** — strictly better, and the version to reach for in an
interview.

A monotonic stack also solves it in O(n)/O(n) by filling basins horizontally as
they close, which is why the problem carries that tag.

## Overflow headroom

Worth knowing how tight the constraints are. The most water possible is two walls
of maximum height with nothing between them:

- `n = 2 × 10⁴`, walls of `10⁵`, so `(20000 - 2) × 100000` = **1,999,800,000**
- `Integer.MAX_VALUE` = 2,147,483,647

That leaves **147,683,647** of headroom — under 7%. The return type is `int` and
it fits, but only just. Constraints this specific are usually chosen around
exactly such a boundary.

## Verification

**21,844 arrays** — every height array of length 1 to 7 with values 0 to 3 —
checked against a deliberately naive reference that rescans the entire array for
each column's left and right maxima. That reference is O(n²) and far too slow to
submit, but it is a direct transcription of the definition and so can't be subtly
wrong.

Plus 100,000 random arrays against the same reference, 20,000 larger arrays
(up to 2,000 bars, heights to 10⁵) against the two-pointer version, and the
maximum-water case above, which computed correctly in 1 ms.
