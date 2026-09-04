# 213. House Robber II

**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming  
**Link:** https://leetcode.com/problems/house-robber-ii/

## Problem

Given a circular row of houses, return the **maximum amount of money** you can rob without robbing two adjacent houses.

Since the first and last houses are also adjacent, they cannot both be robbed.

## Approach: Dynamic Programming

Because the houses are circular, split the problem into two cases:

1. Rob from `0` to `n - 2` → exclude the last house.
2. Rob from `1` to `n - 1` → exclude the first house.

Find the maximum of these two cases.

For each range, use DP with two choices:

- **Skip** the current house.
- **Take** the current house and move to `i + 2`.

```text
take = nums[i] + dp[i + 2]
skip = dp[i + 1]

dp[i] = max(take, skip)
```

## Java Solution

```java
class Solution {
    public int rob(int[] nums) {
        if (nums.length == 1)
            return nums[0];

        if (nums.length == 2)
            return Math.max(nums[0], nums[1]);

        return Math.max(
            robHelper(nums, 0, nums.length - 2),
            robHelper(nums, 1, nums.length - 1)
        );
    }

    private int robHelper(int[] nums, int start, int end) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, -1);
        return robRecursive(nums, start, end, dp);
    }

    private int robRecursive(int[] nums, int i, int end, int[] dp) {
        if (i > end)
            return 0;

        if (dp[i] != -1)
            return dp[i];

        int skip = robRecursive(nums, i + 1, end, dp);
        int take = nums[i] + robRecursive(nums, i + 2, end, dp);

        return dp[i] = Math.max(skip, take);
    }
}
```

## Complexity

| | Time | Space |
|---|---|---|
| DP + Memoization | O(n) | O(n) |

## Key Takeaway

**Break the circular array into two linear cases:** exclude the first house or exclude the last house, then solve each using the standard House Robber DP.