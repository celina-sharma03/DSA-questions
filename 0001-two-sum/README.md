# 1. Two Sum

**Difficulty:** Easy
**Topics:** Array, Hash Table
**Link:** https://leetcode.com/problems/two-sum/

## Problem

Given an array of integers `nums` and an integer `target`, return the indices of
the two numbers that add up to `target`.

You may assume each input has exactly one solution, and you may not use the same
element twice.

## Approach

Single pass with a hash map. For each element, check whether its complement
(`target - nums[i]`) has already been seen. If yes, we have our pair. If not,
store the current value with its index and move on.

The brute-force version compares every pair in a nested loop, which is O(n²).
Trading memory for speed with the hash map gets the lookup down to O(1).

## Complexity

- **Time:** O(n) — one pass over the array
- **Space:** O(n) — worst case the map holds every element
