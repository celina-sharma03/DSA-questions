# 303. Range Sum Query - Immutable

**Difficulty:** Easy
**Topics:** Array, Design, Prefix Sum
**Link:** https://leetcode.com/problems/range-sum-query-immutable/

## Problem

Design a class over a fixed array that answers "what is the sum of elements from
index `left` to `right` inclusive?" — repeatedly, for arbitrary ranges.

```java
NumArray na = new NumArray(new int[]{-2, 0, 3, -5, 2, -1});
na.sumRange(0, 2);   // 1
na.sumRange(2, 5);   // -1
na.sumRange(0, 5);   // -3
```

The word **immutable** in the title is the hint. The array never changes, so any
work done once up front can be reused by every query that follows.

## Approach: prefix sums

Precompute a running total in the constructor: `prefix[i]` holds the sum of
`nums[0..i]`.

```
nums    = [-2,  0,  3, -5,  2, -1]
prefix  = [-2, -2,  1, -4, -2, -3]
```

Then any range sum is the difference of two of those totals. The sum of
`nums[left..right]` is everything up to `right` minus everything before `left`:

```java
return prefix[right] - prefix[left - 1];
```

The `left == 0` case is handled separately, since `prefix[-1]` doesn't exist and
there is nothing to subtract — `prefix[right]` is already the answer.

## Why this is the point of the problem

The naive `sumRange` loops from `left` to `right` and adds. That's O(1)
construction and O(n) per query.

Prefix sums flip it: O(n) once, then **O(1) per query, no matter how wide the
range**. Summing 10,000 elements costs the same as summing two.

Measured: **1,000,000 full-width queries on a 10,000-element array in 5 ms**.
Direct summation would have performed about 10 billion additions to answer the
same questions; the prefix version does about 1 million.

That trade — pay once, answer cheaply forever — is what "design" problems are
usually testing, and it only works *because* the array is immutable. If elements
could change, every update would invalidate the prefix array from that index
onward, which is why the mutable version
([307. Range Sum Query - Mutable](https://leetcode.com/problems/range-sum-query-mutable/))
needs a Fenwick tree or segment tree instead.

## Complexity

| | Time | Space |
|---|---|---|
| Constructor | O(n) | O(n) |
| `sumRange` | O(1) | O(1) |

## A note on overflow

Values reach ±10⁵ and the array can hold 10⁴ of them, so a prefix value can reach
±10⁹ — inside `int`, which caps at about 2.15 × 10⁹.

The subtraction is safe too. `prefix[right] - prefix[left-1]` equals the sum of an
actual subarray, so it's bounded by ±10⁹ as well; the two extremes can't combine
into something larger. Verified at the limit with every element at +100000 and at
-100000.

## Avoiding the special case

Sizing the prefix array at `n + 1` with a leading zero removes the `left == 0`
branch entirely:

```java
prefix = new int[n + 1];
for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];

public int sumRange(int left, int right) {
    return prefix[right + 1] - prefix[left];
}
```

The extra slot acts as "the sum of nothing", so `left == 0` subtracts zero and
needs no branch. Same complexity, one fewer edge case — the same "make the
exception disappear" move as seeding `ans = n` in
[35. Search Insert Position](../0035-search-insert-position/README.md).

It also makes the current code's dependence on a non-empty array explicit:
`prefix[0] = nums[0]` throws on an empty input. The constraints guarantee
`1 <= nums.length`, so it can't happen here.

## Verification

Every array size from 1 to 80 against every possible `[left, right]` pair —
88,560 queries — all matched direct summation. Plus the magnitude extremes at the
10⁴ constraint limit, and a timing run confirming query cost doesn't grow with
range width.
