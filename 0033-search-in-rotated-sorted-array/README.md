# 33. Search in Rotated Sorted Array

**Difficulty:** Medium
**Topics:** Array, Binary Search
**Link:** https://leetcode.com/problems/search-in-rotated-sorted-array/

Builds on [35. Search Insert Position](../0035-search-insert-position/README.md) —
same binary search skeleton, but the array is no longer globally sorted.

## Problem

A sorted array of distinct values has been rotated at some unknown pivot:
`[0,1,2,4,5,6,7]` might arrive as `[4,5,6,7,0,1,2]`. Find the index of `target`,
or `-1`. Must run in **O(log n)**.

## The obstacle

Plain binary search needs `nums[mid] < target` to imply "the answer is to the
right." Rotation destroys that. In `[4,5,6,7,0,1,2]` with `mid` on `7`, a target
of `1` is smaller — yet it lies to the *right*, not the left.

## The key observation

Cut a rotated array anywhere and **at least one of the two halves is still
sorted**. There is only one discontinuity, so it can only fall in one half.

That's the entire problem. Identify the sorted half, and inside it the ordinary
range test works again.

```java
if (nums[low] <= nums[mid])   // left half is sorted
```

Then check whether the target falls inside that half's known range:

| Sorted half | Target in its range? | Go |
|---|---|---|
| left | `nums[low] <= target < nums[mid]` | left |
| left | otherwise | right |
| right | `nums[mid] < target <= nums[high]` | right |
| right | otherwise | left |

Both halves get used: the sorted one is where a definitive range test is
possible, and if the target isn't in it, the answer must be in the other one.
Either way half the array is discarded each iteration, so it stays O(log n).

Tracing `[4,5,6,7,0,1,2]`, target `0`:

| low | high | mid | nums[mid] | sorted half | reasoning | next |
|---|---|---|---|---|---|---|
| 0 | 6 | 3 | 7 | left (`4 <= 7`) | `0` not in `[4,7)` | go right |
| 4 | 6 | 5 | 1 | left (`0 <= 1`) | `0` **is** in `[0,1)` | go left |
| 4 | 4 | 4 | 0 | — | found | return **4** |

## Why `<=` and not `<`

`nums[low] <= nums[mid]` uses `<=` deliberately. When `low == mid` — which
happens whenever the window narrows to one or two elements — a strict `<` would
call that half unsorted and send the search the wrong way. A single element is
trivially sorted, so `<=` is correct.

This is the single most common bug in this problem, and it only shows up on
particular pivot positions, which is why it survives casual testing.

## Complexity

- **Time:** O(log n) — one half discarded per iteration, as in plain binary search
- **Space:** O(1)

## Note

The problem guarantees **distinct** values. With duplicates allowed
([81. Search in Rotated Sorted Array II](https://leetcode.com/problems/search-in-rotated-sorted-array-ii/))
this approach breaks: given `[3,1,3,3,3]`, `nums[low] == nums[mid]` reveals
nothing about which side is sorted. The fix is to shrink the window by one and
retry, which degrades the worst case to O(n).

`(low + high) / 2` can overflow in principle — see the
[#35 write-up](../0035-search-insert-position/README.md#the-overflow-footnote).
Not reachable here: the array is capped at 5000 elements.

## Verification

Every rotation of every array size from 1 to 60, against every target both
present and absent — 151,280 cases, all matched against a linear scan. Plus
200,000 randomly generated rotated arrays, and a 5-million-element array to
confirm the runtime really is logarithmic.
