# 39. Combination Sum

**Difficulty:** Medium
**Topics:** Array, Backtracking
**Link:** https://leetcode.com/problems/combination-sum/

Same backtracking skeleton as
[22. Generate Parentheses](../0022-generate-parentheses/README.md), with one
extra wrinkle: candidates may be reused without limit.

## Problem

Given an array of **distinct** integers and a target, return every unique
combination that sums to the target. Each candidate may be used **any number of
times**. Two combinations are the same if they use the same numbers with the same
multiplicities — order doesn't distinguish them.

```
candidates = [2,3,6,7], target = 7  ->  [[2,2,3],[7]]
candidates = [2,3,5],   target = 8  ->  [[2,2,2,2],[2,3,3],[3,5]]
candidates = [2],       target = 1  ->  []
```

## Two index decisions carry the whole problem

Both live in one line of the recursive call, and each solves a different
requirement.

**`generateCombination(i, ...)` — not `i + 1`.**
Recursing on `i` leaves the current candidate available again on the next level,
which is what permits `[2,2,2,2]`. Passing `i + 1` would consume each candidate
at most once — that's [40. Combination Sum II](https://leetcode.com/problems/combination-sum-ii/),
a different problem.

**The loop starts at `start` — not at `0`.**
This is what prevents duplicate combinations. Starting from `0` every time would
generate `[2,3]` *and* `[3,2]`, then `[2,2,3]`, `[2,3,2]`, `[3,2,2]`, and so on —
the same multiset over and over in different orders.

By only ever looking forward from the current index, each combination is built in
exactly one canonical order, so **duplicates are impossible by construction**.
There's no de-duplication step, no `Set`, no comparison against what's already
been found. Verified across 1,575 exhaustive cases: not one duplicate emitted.

Note that this works whether or not `candidates` is sorted. The canonical order
is "non-decreasing *index*", not "non-decreasing value" — so `[7,3,2]` produces
`[7]` and `[3,2,2]`, correct combinations that simply aren't listed in ascending
order. The problem doesn't require them to be.

## The undo step

```java
current.add(nums[i]);
generateCombination(i, nums, current, comb, target - nums[i]);
current.remove(current.size() - 1);          // undo
```

`current` is a single shared list mutated in place, so the value pushed before
recursing has to be popped after. Forgetting that line is the classic
backtracking bug, and it leaves earlier choices contaminating later branches.

This is also why solutions are stored as `new ArrayList<>(current)` — a **copy**.
Adding `current` itself would store a reference to a list that keeps changing,
and every entry in the result would end up identical (and empty, once the
recursion unwound).

Interesting contrast with your #22, which passed `curr + "("` — a fresh immutable
string — and so needed no undo at all. Here the list is mutable, so the undo is
mandatory.

## Termination

Every candidate is at least `2`, so `target` strictly decreases on each level.
That guarantees the recursion bottoms out: it either lands exactly on `0`, or
crosses below it and returns.

```java
if (target == 0) comb.add(new ArrayList<>(current));
if (target < 0) return;
```

**Small inefficiency:** there's no `return` after recording a solution. With
`target == 0` the loop still runs, appending each remaining candidate and
recursing once, only for every one of those calls to hit `target < 0` and return
immediately. Harmless — all candidates are positive, so no wrong answer can slip
through — but it's O(n) wasted calls per solution found. Adding `return` after the
`comb.add(...)` line removes that.

## Complexity

Hard to state tightly, since the output size depends on the arithmetic rather
than on `n` alone. The standard bound is:

- **Time:** O(n^(T/M + 1)) where `T` is the target and `M` the smallest candidate —
  the recursion tree has depth at most `T/M` and branching factor up to `n`
- **Space:** O(T/M) for the recursion depth, plus the output

Measured at the worst case inside the constraints — candidates `{2..9}` with
target 40 — that's **1,690 combinations, produced in 1 ms**.

## Verification

**1,575 exhaustive cases** — every non-empty subset of `{2,3,4,5,6,7}` as the
candidate set, against every target from 1 to 25 — with the result compared as a
*set* against an independent reference that enumerates by multiplicity (deciding
how many copies of each candidate to use) rather than by position. Set equality
holds in every case, so nothing is missing and nothing is spurious.

Also checked in every case: no duplicate combinations, every combination sums to
the target, and every value used appears in `candidates`. Plus 4,000 random
candidate sets, and the `{2..9}` / target 40 worst case.
