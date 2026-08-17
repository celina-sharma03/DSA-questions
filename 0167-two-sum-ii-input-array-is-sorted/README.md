# 167. Two Sum II - Input Array Is Sorted

**Difficulty:** Medium
**Topics:** Array, Two Pointers, Binary Search
**Link:** https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/

The sorted-input variant of [1. Two Sum](../0001-two-sum/README.md).

## Problem

Given a **1-indexed**, **sorted** array and a target, return the positions of the
two numbers that add to it. Exactly one solution is guaranteed, and the same
element cannot be used twice.

Required to use **constant extra space**.

Two differences from #1 that matter:

- indices are **1-based**, so the answer is `{i + 1, j + 1}`
- the array is **sorted**, which is the point of the problem

## Approach used here: check every pair

Two nested loops over all pairs, returning the first that sums to the target.

- **Time:** O(n²)
- **Space:** O(1) — satisfies the constant-space requirement

Correct, and accepted. But it never looks at the fact that the input is sorted —
this is exactly the brute force that would solve #1, where the array is
unordered.

## The intended approach: two pointers

Sorted input allows one pointer at each end, moved inward based on the sum:

```java
int left = 0, right = numbers.length - 1;
while (left < right) {
    int sum = numbers[left] + numbers[right];
    if (sum == target) return new int[] { left + 1, right + 1 };
    if (sum < target) left++;    // need a larger sum
    else right--;                // need a smaller sum
}
```

The reasoning behind each move is what makes this valid:

- `sum < target` — `numbers[left]` paired with the **largest** available value is
  still too small, so it cannot work with any smaller partner either. Every pair
  involving `left` is eliminated at once, not just this one.
- `sum > target` — symmetrically, `numbers[right]` is too large for any partner.

Each step discards an entire row or column of the pair space rather than a single
pair, so one pass suffices.

- **Time:** O(n)
- **Space:** O(1)

## Measured difference

At the constraint limit of 30,000 elements, worst case (the answer being the
final pair):

| Approach | Time | Comparisons |
|---|---|---|
| Nested loops | 110 ms | ~450,000,000 |
| Two pointers | 20 µs | ~30,000 |

Roughly **5,500× slower**. Both are accepted by the judge — 110 ms is inside the
time limit — but the gap is what separates a solution that uses the given
structure from one that ignores it.

## Verification

100,000 random sorted arrays, each with a planted answer: every call returned a
valid 1-indexed pair summing to the target, with the first index strictly less
than the second. Cross-checked against a two-pointer implementation.
