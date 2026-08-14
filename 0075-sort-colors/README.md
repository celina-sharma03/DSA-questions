# 75. Sort Colors

**Difficulty:** Medium
**Topics:** Array, Two Pointers, Sorting, Counting Sort
**Link:** https://leetcode.com/problems/sort-colors/

## Problem

Sort an array containing only `0`, `1` and `2` **in place**, so all 0s come
first, then all 1s, then all 2s.

Not allowed to call a library sort. The follow-up asks for a **one-pass**
algorithm using **constant** extra space.

## Approach: counting sort

With only three possible values there's no need to compare anything. Count how
many of each there are, then overwrite the array with that many of each in order.

```
[2,0,2,1,1,0]  ->  counts: {0:2, 1:2, 2:2}  ->  [0,0,1,1,2,2]
```

Comparison sorts are bounded by O(n log n) because they can only learn about
order by comparing pairs. Counting sort escapes that bound by using the values
themselves as indices — possible precisely because the value range is tiny and
known in advance.

## Complexity

- **Time:** O(n) — one pass to count, one to write
- **Space:** O(1) — the map holds exactly three entries no matter how big the
  input is

## Simplification worth making

The `HashMap` works but is heavier than the problem needs. Keys are `0`, `1`, `2`
— already valid array indices — so a three-element `int[]` does the same job with
no hashing and no `Integer` boxing:

```java
int[] count = new int[3];
for (int k : nums) count[k]++;

int index = 0;
for (int i = 0; i < 3; i++) {
    while (count[i]-- > 0) nums[index++] = i;
}
```

Same algorithm, same complexity, roughly half the code. Whenever keys are small
non-negative integers, an array beats a map.

## The follow-up: one pass, Dutch National Flag

This solution makes **two** passes. The follow-up asks for one, which is the
classic three-pointer partition — named by Dijkstra after the Dutch flag's three
stripes:

```java
int low = 0, mid = 0, high = nums.length - 1;
while (mid <= high) {
    if (nums[mid] == 0) {
        swap(nums, low++, mid++);
    } else if (nums[mid] == 1) {
        mid++;
    } else {
        swap(nums, mid, high--);   // note: mid does NOT advance
    }
}
```

The invariant: everything before `low` is 0, everything between `low` and `mid`
is 1, everything after `high` is 2, and the region from `mid` to `high` is
unexamined.

The subtle part is the last branch. After swapping a `2` toward the back, `mid`
stays put — the value just pulled in from `high` hasn't been looked at yet and
could itself be a `0` or `2`. Advancing `mid` there is the standard bug in this
problem, and it only misfires on particular arrangements, so it survives casual
testing.

Both are O(n) time and O(1) space. Two passes over 300 elements is not a real
performance concern; the follow-up is about whether you can maintain a
three-way invariant, which is why interviewers ask for it.

## Verification

Every possible array of 0s, 1s and 2s up to length 12 — 797,160 arrays in total —
sorted and compared against `Arrays.sort`. Plus random arrays at the 300-element
constraint limit.
