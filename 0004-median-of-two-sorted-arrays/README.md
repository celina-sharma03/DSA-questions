# 4. Median of Two Sorted Arrays

**Difficulty:** Hard
**Topics:** Array, Binary Search, Divide and Conquer
**Link:** https://leetcode.com/problems/median-of-two-sorted-arrays/

## Problem

Given two sorted arrays, return the median of their combined contents.

```
[1,3] and [2]     -> 2.0     (merged: [1,2,3])
[1,2] and [3,4]   -> 2.5     (merged: [1,2,3,4], average of the middle two)
```

The problem requires **O(log(m + n))** runtime.

## Approach used here: merge, then read the middle

Standard two-pointer merge, taking the smaller head each time, then draining
whichever array still has elements. The median is read straight out of the merged
array — the middle element when the total is odd, the average of the two middle
elements when it's even.

The two drain loops after the main merge are what handle unequal lengths: the
first loop stops as soon as *either* array is exhausted, so one of them may still
have a tail to copy.

- **Time:** O(m + n)
- **Space:** O(m + n) for the merged array

Correct and accepted. It does not meet the stated O(log(m + n)) requirement.

### Measured scaling

| Input size | Time |
|---|---|
| 1,000 + 1,000 | 6 µs |
| 10,000 + 10,000 | 74 µs |
| 100,000 + 100,000 | 665 µs |
| 1,000,000 + 1,000,000 | 10,060 µs |

Ten times the input, roughly ten times the time — the signature of linear work. A
genuine O(log(m + n)) solution would stay nearly flat across all four rows.

At the actual constraint limit (1000 elements each) none of this matters, which
is why the judge accepts it.

## The intended approach: binary search on the partition

The insight is that the median doesn't require merging anything. It only requires
knowing **where to cut**.

Split each array into a left and right part such that:

1. the left parts together hold exactly half the total elements, and
2. every element in the left parts is `<=` every element in the right parts

Once such a cut exists, the median is determined by the four elements adjacent to
it — `maxLeft1`, `maxLeft2`, `minRight1`, `minRight2` — with no merged array
anywhere.

Choosing the cut in the *smaller* array fixes the cut in the larger one, since the
left sizes must sum to half the total. So there is only one free variable, and it
can be binary searched:

- `maxLeft1 > minRight2` → cut too far right in array 1, move left
- `maxLeft2 > minRight1` → cut too far left, move right
- otherwise → the cut is valid, read off the median

Binary searching over the smaller array gives **O(log(min(m, n)))** time and
**O(1)** space — better than the required bound.

The fiddly part is the boundaries: when a cut sits at index 0 or at the end, the
missing neighbour is treated as `-infinity` or `+infinity` so the comparisons
still work. That bookkeeping is most of why this problem is rated Hard, and why
the merge version — which is genuinely easy — is so commonly submitted instead.

## Note on the even case

```java
return (nums3[totalLength / 2] + nums3[totalLength / 2 - 1]) / 2.0;
```

The addition happens in `int` before the division promotes to `double`. Values
reach ±10⁶, so the sum stays within ±2 × 10⁶ — far inside `int` range, no overflow.
Dividing by `2.0` rather than `2` is what keeps the `.5`; integer division would
silently truncate `2.5` to `2`.

## Verification

**15,875 array pairs** — every pair of sorted arrays of length 0 to 4 drawn from
values 0 to 4 — matched against a concatenate-and-sort reference. Plus 300,000
random pairs with values spanning ±10⁶ and lopsided sizes, both arrays at the
1000-element constraint limit, and the value extremes that would expose an
overflow in the even-median addition.
