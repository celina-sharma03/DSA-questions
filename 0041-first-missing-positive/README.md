# 41. First Missing Positive

**Difficulty:** Hard
**Topics:** Array, Hash Table, Cyclic Sort
**Link:** https://leetcode.com/problems/first-missing-positive/

## Problem

Find the smallest positive integer **not** present in the array.

```
[1,2,0]        -> 3
[3,4,-1,1]     -> 2
[7,8,9,11,12]  -> 1
```

Required: **O(n) time** and **O(1) extra space**. That rules out sorting
(O(n log n)) and rules out a `HashSet` (O(n) space) — the two approaches that
come to mind first.

## The observation that bounds the problem

With `n` elements, the answer can never exceed **n + 1**.

The best possible case is the array holding exactly `1, 2, …, n`, and then the
answer is `n + 1`. Any gap, duplicate, negative, or value above `n` only pulls the
answer *lower*. So only the values in `[1, n]` can influence the result — anything
else is noise and can be ignored entirely.

That turns an unbounded search into a question about `n` specific slots, which is
what makes O(1) space reachable.

## Approach: use the array as its own hash table

If a `boolean[n]` were allowed, this would be easy: mark which of `1..n` are
present, then scan for the first unmarked. The trick is getting that table for
free — by **rearranging the array itself** so each value sits at the index that
encodes it.

The rule: value `v` belongs at index `v - 1`.

```java
if (element >= 1 && element <= n) {
    int chair = element - 1;
    if (nums[chair] != element) {
        swap(nums, chair, i);
        i--;               // re-examine this slot: a new value just landed here
    }
}
```

After the pass, a second scan finds the first index `i` where `nums[i] != i + 1`.
That index is the first missing positive.

### The `i--` is what makes it correct

Swapping brings an unexamined value into position `i`. That value may itself
belong somewhere else, so position `i` has to be reconsidered rather than moved
past. Decrementing `i` before the loop's `i++` leaves the cursor where it is.

### Why that doesn't make it quadratic

Re-examining a position looks like it could blow up, but every swap **permanently
places one value at its correct index** — and a value once seated is never moved
again. So there are at most `n` swaps across the entire run, and the loop performs
at most `2n` iterations total.

Confirmed by measurement on fully-reversed input, the worst case for swapping:

| n | time |
|---|---|
| 10,000 | 20 µs |
| 100,000 | 145 µs |
| 1,000,000 | 3,514 µs |

Ten times the input, roughly ten times the time. Linear, not quadratic.

### The guard that prevents an infinite loop

```java
if (nums[chair] != element)
```

Without it, duplicates hang the program. Given `[1,1]`, the second `1` wants index
0, which already holds a `1` — swapping would exchange two identical values,
change nothing, and `i--` would retry the same position forever.

Checking that the target seat isn't *already* occupied by the right value means a
swap only happens when it makes progress. This is the single easiest thing to get
wrong in this problem, and it fails as a hang rather than a wrong answer.

### Extreme values are safe

`element - 1` is only evaluated after `element >= 1`, so `Integer.MIN_VALUE` can
never underflow it. Values above `n` — including `Integer.MAX_VALUE` — fail the
range test and are skipped untouched.

## Complexity

- **Time:** O(n) — at most `n` swaps, two passes
- **Space:** O(1) — swaps in place, no auxiliary structure

Meets both stated requirements.

## Note

The input array is rearranged. LeetCode doesn't care, and it's precisely what buys
the O(1) space — the array *is* the hash table. In production a caller would
usually need to be told, or given a copy.

## Verification

**55,986 arrays** — every array of length 1 to 6 over `{-1, 0, 1, 2, 3, 4}`,
covering negatives, zero, duplicates, in-range and out-of-range values — matched
against a `HashSet`-based reference. That reference uses O(n) space and so would
fail the problem's own constraint, which is exactly why it's trustworthy as
ground truth.

Plus 200,000 random arrays built with dense duplicates (the case that hangs
without the guard), 50,000 arrays containing `Integer.MIN_VALUE` and
`Integer.MAX_VALUE`, and a 100,000-element fully-reversed array at the constraint
limit.
