# 26. Remove Duplicates from Sorted Array

**Difficulty:** Easy
**Topics:** Array, Two Pointers
**Link:** https://leetcode.com/problems/remove-duplicates-from-sorted-array/

## Problem

Given a sorted array, remove the duplicates **in place** so each value appears
once, and return the count `k` of unique values.

The grader checks the first `k` slots of the modified array, not the return value
alone. Whatever sits beyond index `k-1` is ignored — no need to clear it.

## Approach

Two pointers moving at different speeds over the same array:

- **`i`** — the write pointer. Marks the last slot of the unique section being
  built at the front of the array.
- **`j`** — the read pointer. Scans forward looking for the next new value.

Because the input is sorted, all copies of a value sit together. So
`nums[i] != nums[j]` is enough to identify a new value — there's no need to
remember anything already seen, and no set is required.

On finding one, advance `i` and write the value into `nums[i + 1]`.

Tracing `[0,0,1,1,1,2]`:

| j | nums[j] | vs nums[i] | action | array so far | i |
|---|---------|-----------|--------|--------------|---|
| 1 | 0 | same | skip | `[0,0,1,1,1,2]` | 0 |
| 2 | 1 | differs | write at 1 | `[0,1,1,1,1,2]` | 1 |
| 3 | 1 | same | skip | `[0,1,1,1,1,2]` | 1 |
| 4 | 1 | same | skip | `[0,1,1,1,1,2]` | 1 |
| 5 | 2 | differs | write at 2 | `[0,1,2,1,1,2]` | 2 |

Returns `i + 1` = 3, and the first three slots are `[0,1,2]`. The tail is left as
scratch, which the problem explicitly permits.

The write never overtakes the read — `i` only advances when `j` does, and `j`
starts ahead — so the value being copied is never one that hasn't been read yet.

## Complexity

- **Time:** O(n) — single pass
- **Space:** O(1) — modifies in place, no auxiliary structure

## Edge case worth knowing

On an empty array this returns `1` rather than `0`. The loop never runs, so
`i` stays `0` and `i + 1` comes back as `1`.

It doesn't matter on LeetCode — the constraints guarantee
`1 <= nums.length <= 3 * 10^4`, so an empty array is never submitted, and the
solution is accepted. But as a standalone function it's a real gap. A guard
closes it:

```java
if (nums.length == 0) return 0;
```

Left out here to keep the accepted submission intact. Worth remembering, since
"what does your function do on empty input?" is a standard interview follow-up.
