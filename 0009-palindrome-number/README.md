# 9. Palindrome Number

**Difficulty:** Easy
**Topics:** Math, Two Pointers
**Link:** https://leetcode.com/problems/palindrome-number/

## Problem

Given an integer `x`, return `true` if `x` reads the same forward and backward.

For example `121` is a palindrome, while `-121` is not (`121-` backwards) and
`10` is not (`01` backwards).

## Approach

Convert the number to a string, then walk two pointers inward from both ends.
If the characters ever differ, it isn't a palindrome. If the pointers cross
without a mismatch, it is.

The negative case falls out for free — `-121` becomes `"-121"`, and the leading
`-` never matches a trailing digit, so the first comparison already returns
`false`. No special-casing needed.

## Complexity

Let `d` be the number of digits in `x`.

- **Time:** O(d) — one comparison per pair of digits
- **Space:** O(d) — the string copy of the number

Since `d = log₁₀(x)`, both are O(log x) in terms of the input value.

## Follow-up

LeetCode asks whether this can be done without converting to a string. It can:
reverse only the *second half* of the number arithmetically (`% 10` to peel
digits off the end) and compare it against the first half. That drops space to
O(1). The string version is more readable and plenty fast here, but the
arithmetic one is worth knowing since interviewers often ask for it.
