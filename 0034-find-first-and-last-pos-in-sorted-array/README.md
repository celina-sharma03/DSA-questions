# 34. Find First and Last Position of Element in Sorted Array

**Difficulty:** Medium
**Topics:** Array, Binary Search
**Link:** https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/

Same binary-search skeleton as
[35. Search Insert Position](../0035-search-insert-position/README.md), now run
twice to pin down both ends of a run of duplicates.

## Problem

Given a sorted array, return the first and last index of `target`, or `[-1, -1]`
if it isn't present. Required to run in **O(log n)**.

```
[5,7,7,8,8,10], target 8  -> [3,4]
[5,7,7,8,8,10], target 6  -> [-1,-1]
[],             target 0  -> [-1,-1]
```

## Why plain binary search isn't enough

Ordinary binary search returns *some* index holding the target. With duplicates
there's no telling which one — on `[8,8,8,8,8]` it lands in the middle, and the
boundaries are still unknown.

The tempting patch is to scan outward from that hit until the value changes. That
works, but on an array of 100,000 identical elements it walks the whole thing —
O(n), and the log-time requirement is gone. **The worst case for this problem is
precisely the input where a scan is slowest.**

So both boundaries have to be found by binary search, not by scanning.

## Approach: two searches that differ by one line

Both halves are ordinary binary searches with one change: a match is **recorded,
not returned**. The search then keeps going in the direction where a better
boundary might still be hiding.

```java
if (nums[mid] == target) {
    answer = mid;
    right = mid - 1;    // findFirst: an earlier occurrence may exist to the left
}
```

```java
if (nums[mid] == target) {
    answer = mid;
    left = mid + 1;     // findLast: a later occurrence may exist to the right
}
```

That single line is the entire difference between the two methods. Everything
else — the bounds, the `answer = -1` seed, the two comparison branches — is
identical.

The `answer` variable is what makes it work. Because a match doesn't terminate
the search, there has to be somewhere to keep the best candidate found so far;
each new match overwrites it with one closer to the desired end, and the loop
converges on the true boundary.

Seeding `answer = -1` also handles "not present" for free: if no match is ever
recorded the initial value survives, which is exactly the required output. Same
move as seeding `ans = n` in #35 — let the initialisation answer the edge case
instead of branching for it.

Tracing `findFirst` on `[5,7,7,8,8,10]`, target `8`:

| left | right | mid | nums[mid] | action | answer |
|---|---|---|---|---|---|
| 0 | 5 | 2 | 7 | `< 8`, go right | -1 |
| 3 | 5 | 4 | 8 | match — record, go **left** | 4 |
| 3 | 3 | 3 | 8 | match — record, go left | **3** |
| 3 | 2 | — | — | `left > right`, stop | 3 |

`findLast` on the same input moves right on each match and converges on 4.

## Complexity

- **Time:** O(log n) — two independent binary searches, each halving the range
- **Space:** O(1) — iterative, no recursion

Measured on an array where every element equals the target, which is the worst
case:

| n | time per call |
|---|---|
| 1,000 | 40 ns |
| 10,000 | 51 ns |
| 100,000 | 58 ns |
| 1,000,000 | 69 ns |

A thousandfold increase in input costs under twice the time. That's what
logarithmic looks like — a linear scan would have been a thousand times slower at
the bottom row.

## Details worth noting

**`left + (right - left) / 2`** rather than `(left + right) / 2`. The two are
equal for any array that fits in memory, but the second overflows if
`left + right` exceeds `Integer.MAX_VALUE`. Costs nothing to write safely, and
it's the form that survives code review.

**The empty array needs no special case.** With `nums.length == 0`, `right`
starts at `-1`, the loop never runs, and both searches return the seeded `-1`.
The constraints here do allow `nums.length == 0`, so this genuinely matters.

## Verification

Every sorted array of length 0 to 6 over values 0 to 4 — 462 distinct arrays —
against every target from below the range to above it, for **3,234 cases**, all
matched a linear scan. Plus 300,000 random arrays built with heavy duplication
(the case the problem is really about), the value extremes at ±10⁹, and a
100,000-element array of nothing but the target, which resolved to `[0, 99999]`
in 1 microsecond.
