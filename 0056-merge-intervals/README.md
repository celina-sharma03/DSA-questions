# 56. Merge Intervals

**Difficulty:** Medium
**Topics:** Array, Sorting
**Link:** https://leetcode.com/problems/merge-intervals/

## Problem

Merge every group of overlapping intervals into one, and return the result.

```
[[1,3],[2,6],[8,10],[15,18]]  ->  [[1,6],[8,10],[15,18]]
[[1,4],[4,5]]                 ->  [[1,5]]
```

Intervals that merely **touch** count as overlapping: `[1,4]` and `[4,5]` share
the point 4, so they merge.

## Why sort first

Unsorted, deciding which intervals overlap needs every pair compared — O(n²). Once
sorted by start, an interval can only ever overlap the **most recent** merged
group. Anything earlier ended before the latest group started, and every later
interval starts even further right, so it can't reach back.

That reduces the whole problem to a single left-to-right pass that only ever looks
at the last output interval.

```java
Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
```

`Comparator.comparingInt` rather than `(a, b) -> a[0] - b[0]` is also the safer
habit: subtraction-based comparators overflow when the values are large enough to
span more than `Integer.MAX_VALUE`. Not reachable here — values stop at 10⁴ — but
it's the idiom that doesn't need thinking about.

## The pass

For each interval, compare its start against the end of the last merged group:

```java
if (a <= ans.get(index)[1]) {          // overlaps or touches
    if (b > ans.get(index)[1]) {       // extend only if it reaches further
        ans.get(index)[1] = b;
    }
} else {
    ans.add(new int[] { a, b });       // gap: start a new group
    index++;
}
```

### `<=`, not `<`

Using `<` would treat `[1,4]` and `[4,5]` as separate. The problem counts touching
intervals as overlapping, so the comparison has to include equality. This is the
classic off-by-one here.

### Only ever extend, never replace

The inner `if (b > ...)` is doing the work of `Math.max`. It matters on
containment: with `[1,10]` followed by `[2,3]`, setting the end to the new
interval's `3` would *shrink* the group and lose `[4,10]` entirely. Taking the
larger end keeps it at 10.

Sorting by start doesn't prevent this — `[2,3]` starts after `[1,10]` and still
sits wholly inside it — so the guard is genuinely necessary, not defensive.

### Copies, not references

```java
ans.add(new int[] { intervals[0][0], intervals[0][1] });
```

Each output interval is a fresh array. That's what makes it safe to overwrite
`ans.get(index)[1]` in place — adding `intervals[i]` directly would mean the
extension silently rewrote the caller's input as well.

### The first interval is processed twice — harmlessly

`intervals[0]` seeds `ans` and is then visited again by the loop. On that visit its
start is `<=` its own end, so it takes the merge branch, finds nothing further to
extend, and does nothing. Correct, if slightly redundant — starting the loop at
index 1 would skip it.

`index` always equals `ans.size() - 1`, so it could be replaced by reading the last
element directly; keeping it separate is a readability choice.

## Complexity

- **Time:** O(n log n) — the sort dominates; the pass is O(n)
- **Space:** O(n) for the output, plus O(log n) for the sort

At the constraint limit of 10⁴ intervals: 28 ms.

## Note

`Arrays.sort` reorders the caller's `intervals` array in place. LeetCode doesn't
care, but it's a side effect worth knowing about in shared code.

## Verification

**54,240 interval sequences** — every sequence of 1 to 4 intervals drawn from all
15 possible intervals over coordinates 0 to 4. Sequences rather than sets, so every
input *ordering* is exercised, not just sorted ones. Each result was checked
against an independent reference that involves no sorting or merging at all: it
paints each interval onto a **doubled** number line (`[s,e]` covers points `2s`
to `2e`) and reads off the contiguous runs.

The doubling is what makes that reference trustworthy on the edge cases that
matter. `[1,2]` and `[3,4]` cover doubled points 2–4 and 6–8, leaving a gap at 5, so
they correctly stay separate; `[1,4]` and `[4,5]` both cover point 8, so they
correctly join. A plain integer number line would wrongly merge the first pair.

Plus 50,000 random inputs checked the same way, with a structural check that the
output is always sorted and strictly non-overlapping, and a 10,000-interval run at
the constraint limit.
