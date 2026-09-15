# 46. Permutations

**Difficulty:** Medium
**Topics:** Array, Backtracking
**Link:** https://leetcode.com/problems/permutations/

Third backtracking problem in the repo, after
[22. Generate Parentheses](../0022-generate-parentheses/README.md) and
[39. Combination Sum](../0039-combination-sum/README.md). Here order *does*
matter — `[1,2,3]` and `[3,2,1]` are different answers, where in #39 they would
have been the same combination.

## Problem

Given an array of **distinct** integers, return all possible permutations.

```
[1,2,3] -> [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
```

## Approach

Build a permutation one position at a time. At each position, try every value
that hasn't been used yet; once the working list reaches full length, it's a
complete permutation.

```java
if (tempList.size() == nums.length) {
    resultList.add(new ArrayList<>(tempList));
    return;
}
for (int number : nums) {
    if (tempList.contains(number)) continue;   // already placed
    tempList.add(number);
    backtrack(resultList, tempList, nums);
    tempList.remove(tempList.size() - 1);      // undo
}
```

### The loop starts from 0 every time — deliberately

This is the structural difference from #39. There, the loop began at `start` so
that each combination was built in one canonical order, making duplicates
impossible. Here the loop runs over the whole array at every level, because
**every ordering is a distinct answer**. `[1,2,3]` and `[2,1,3]` must both appear.

Same skeleton, opposite requirement, and the loop bound is what switches between
them.

### `contains` is the "already used" test

Since the values are distinct, "have I placed this number?" and "is this number
in my working list?" are the same question, and `contains` answers it directly —
no separate bookkeeping needed.

### The undo

`tempList` is one shared mutable list, so the value added before recursing must be
removed after. Same requirement as #39, and for the same reason — and again,
solutions are stored as `new ArrayList<>(tempList)`, a copy, since the list itself
keeps changing underneath.

## Where this breaks: duplicate values

The `contains` check depends entirely on the values being distinct. With
duplicates it doesn't merely miscount — it collapses:

```
permute([1,1,2])  ->  []
```

Empty. After the first `1` is placed, the second `1` is indistinguishable from it,
so `contains` skips it. No branch ever reaches full length, and nothing is
emitted at all.

The problem guarantees distinct integers, so this never arises here. But it's
worth knowing exactly where the assumption is load-bearing —
[47. Permutations II](https://leetcode.com/problems/permutations-ii/) is this
problem with duplicates allowed, and it needs tracking **positions used** rather
than **values used**, plus a sort and a skip rule to avoid emitting the same
permutation twice.

## Complexity

- **Time:** O(n² × n!) — there are `n!` permutations, each level loops over all `n`
  candidates, and each `contains` scan costs up to O(n)
- **Space:** O(n) for the recursion depth and working list, plus O(n × n!) for the
  output

Swapping `contains` for a `boolean[] used` indexed by position makes the check
O(1) and brings it to the standard **O(n × n!)**. With `n` capped at 6 that's the
difference between 118 microseconds and slightly fewer — irrelevant here, but it's
the version to write if asked.

The output alone is `n! × n` numbers, so no algorithm can beat O(n × n!).

## Verification

**1,800 randomly generated distinct arrays** across the full constraint range
(n = 1 to 6, values -10 to 10), each checked four ways:

- the count is exactly `n!`
- no permutation appears twice
- every permutation is a genuine rearrangement of the input (same multiset)
- the result set matches a reference built by repeatedly applying
  **next-permutation** to the sorted array — a completely different mechanism
  from recursive backtracking

All four held in every case. At the maximum `n = 6`, all 720 permutations are
produced in 118 microseconds.
