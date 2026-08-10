# 35. Search Insert Position

**Difficulty:** Easy
**Topics:** Array, Binary Search
**Link:** https://leetcode.com/problems/search-insert-position/

## Problem

Given a sorted array of distinct integers and a target, return the index of the
target if present — otherwise the index where it would be inserted to keep the
array sorted. Required to run in **O(log n)**.

```
[1,3,5,6], target 5 -> 2   (found)
[1,3,5,6], target 2 -> 1   (insert between 1 and 3)
[1,3,5,6], target 7 -> 4   (insert past the end)
[1,3,5,6], target 0 -> 0   (insert at the front)
```

## Approach

Both cases — found and not found — are the same question:

> **What is the index of the first element `>= target`?**

If the target exists, that index is where it sits. If it doesn't, that's exactly
where it belongs. Reframing the problem this way removes the special case
entirely; there's no "did we find it?" branch anywhere in the solution.

This is the **lower bound** search. The loop narrows toward it:

- `nums[mid] >= target` — `mid` is a *candidate* answer. Record it, then keep
  looking left for an even earlier one.
- `nums[mid] < target` — `mid` is too small, so the answer is strictly right of it.

`ans` starts at `n`, one past the end. That's the answer when no element is
`>= target`, i.e. the target belongs after everything — and since the loop then
never assigns `ans`, the initial value carries straight through. The "insert at
the end" case is handled by the initialisation rather than by any branch.

Tracing `[1,3,5,6]`, target `2`:

| low | high | mid | nums[mid] | vs target | action | ans |
|-----|------|-----|-----------|-----------|--------|-----|
| 0 | 3 | 1 | 3 | `>= 2` | record, search left | 1 |
| 0 | 0 | 0 | 1 | `< 2` | search right | 1 |
| 1 | 0 | — | — | — | `low > high`, stop | **1** |

## Complexity

- **Time:** O(log n) — the search range halves each iteration
- **Space:** O(1) — iterative, no recursion stack

## The overflow footnote

`(low + high) / 2` can overflow when `low + high` exceeds `Integer.MAX_VALUE`,
producing a negative `mid` and an `ArrayIndexOutOfBoundsException`. The overflow-safe
form is:

```java
int mid = low + (high - low) / 2;
```

It cannot happen here — the constraints cap the array at 10⁴ elements, so
`low + high` never approaches 2³¹. The code is correct as submitted.

It's worth knowing anyway: this exact bug sat in `java.util.Arrays.binarySearch`
in the JDK for nine years before being found in 2006. Interviewers ask about it
precisely because the buggy version looks obviously fine.

## Related

The same lower-bound skeleton solves a lot of problems once you recognise it:
first/last occurrence in an array with duplicates, `Arrays.binarySearch`'s
insertion-point return value, and the "minimum value satisfying a condition"
family (Koko Eating Bananas, Capacity to Ship Packages) where the array is
replaced by a predicate over a numeric range.
