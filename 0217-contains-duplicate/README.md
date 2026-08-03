# 217. Contains Duplicate

**Difficulty:** Easy
**Topics:** Array, Hash Table, Sorting
**Link:** https://leetcode.com/problems/contains-duplicate/

## Problem

Given an integer array `nums`, return `true` if any value appears at least twice,
and `false` if every element is distinct.

## Approach

Walk the array once, inserting each element into a `HashSet`. If an insert fails,
we've seen that value before and can return immediately.

`HashSet.add()` returns `false` when the element was already present, so
`if (!set.add(num))` does the lookup and the insert in a single operation — no
separate `contains()` call needed.

The early return matters: an array whose duplicate sits near the front exits
almost immediately, without touching the rest of the input.

## Complexity

- **Time:** O(n) — one pass, with O(1) average hashing per element
- **Space:** O(n) — worst case (all distinct) the set holds every element

## Alternatives

**Sort first, then compare neighbours.** Duplicates end up adjacent after
sorting, so a single pass over the sorted array finds them. O(n log n) time but
O(1) extra space if you're allowed to modify the input — the right trade when
memory is tight.

**Brute force.** Compare every pair with a nested loop. O(n²), too slow for the
constraint of 10⁵ elements. Worth knowing as the baseline the hash set improves on.
