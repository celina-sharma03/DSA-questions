# 31. Next Permutation

**Difficulty:** Medium  
**Topics:** Array, Two Pointers  
**Link:** https://leetcode.com/problems/next-permutation/

## Problem

Given an integer array `nums`, rearrange it into the **next lexicographically greater permutation**. If no greater permutation exists, rearrange it into the lowest possible order.

## Approach

The solution uses three steps:

1. Find the **breaking point** `ind1` from the right where `nums[i] < nums[i + 1]`.
2. Find the smallest element greater than `nums[ind1]` from the right and swap them.
3. Reverse the elements after `ind1` to get the smallest possible suffix.

If no breaking point exists, the array is already the largest permutation, so simply reverse the entire array.

## Java Solution

```java
class Solution {
    public void nextPermutation(int[] nums) {
        int ind1 = -1;
        int ind2 = -1;

        // Step 1: Find breaking point
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                ind1 = i;
                break;
            }
        }

        // No greater permutation exists
        if (ind1 == -1) {
            reverse(nums, 0);
        } else {
            // Step 2: Find next greater element and swap
            for (int i = nums.length - 1; i >= 0; i--) {
                if (nums[i] > nums[ind1]) {
                    ind2 = i;
                    break;
                }
            }

            swap(nums, ind1, ind2);

            // Step 3: Reverse the suffix
            reverse(nums, ind1 + 1);
        }
    }

    void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    void reverse(int[] nums, int start) {
        int i = start;
        int j = nums.length - 1;

        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }
}
```

## Complexity

| | Time | Space |
|---|---|---|
| Next Permutation | O(n) | O(1) |

## Key Takeaway

**Find the first increasing pair from the right → swap with the next greater element → reverse the suffix.**