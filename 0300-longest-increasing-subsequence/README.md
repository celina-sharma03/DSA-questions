# Longest Increasing Subsequence (LIS)
 
A Java solution to [LeetCode 300 — Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/), using bottom-up dynamic programming.
 
## Problem
 
Given an integer array `nums`, return the length of the longest **strictly increasing subsequence**.
 
A subsequence is a sequence derived from the array by deleting zero or more elements without changing the order of the remaining ones.
 
**Example**
 
```
Input:  nums = [10, 9, 2, 5, 3, 7, 101, 18]
Output: 4
Explanation: The LIS is [2, 3, 7, 101], so the length is 4.
```
 
## Approach
 
The idea is to define a subproblem that ends at a fixed index, which makes the recurrence easy to state.
 
Let `dp[i]` = the length of the longest increasing subsequence that **ends at index `i`** (and therefore must include `nums[i]`).
 
Every element is an increasing subsequence of length 1 on its own, so `dp` starts filled with `1`. Then for each `i`, we look back at every earlier index `j`:
 
- If `nums[i] > nums[j]`, the subsequence ending at `j` can be extended by `nums[i]`, giving a candidate length of `dp[j] + 1`.
- We keep the best candidate: `dp[i] = max(dp[i], dp[j] + 1)`.
Because the LIS can end anywhere in the array, the answer is not `dp[n - 1]` but the maximum over the whole `dp` array.
 
**Recurrence**
 
```
dp[i] = 1 + max{ dp[j] : j < i and nums[j] < nums[i] }   (1 if no such j exists)
answer = max(dp[0..n-1])
```
 
## Solution
 
```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
 
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
 
        int max = 0;
        for (int len : dp) {
            max = Math.max(max, len);
        }
        return max;
    }
}
```
 
> Requires `import java.util.Arrays;` when compiled outside the LeetCode editor.
 
## Walkthrough
 
For `nums = [10, 9, 2, 5, 3, 7, 101, 18]`:
 
| i | nums[i] | dp[i] | Subsequence ending here |
|---|---------|-------|-------------------------|
| 0 | 10      | 1     | [10]                    |
| 1 | 9       | 1     | [9]                     |
| 2 | 2       | 1     | [2]                     |
| 3 | 5       | 2     | [2, 5]                  |
| 4 | 3       | 2     | [2, 3]                  |
| 5 | 7       | 3     | [2, 3, 7]               |
| 6 | 101     | 4     | [2, 3, 7, 101]          |
| 7 | 18      | 4     | [2, 3, 7, 18]           |
 
Maximum of `dp` is **4**.
 
## Complexity
 
| | |
|---|---|
| **Time** | `O(n²)` — every pair `(i, j)` with `j < i` is examined once. |
| **Space** | `O(n)` — the `dp` array. |
 
## Notes
 
- **Strictly increasing:** the comparison is `nums[i] > nums[j]`. Using `>=` would allow equal values and solve the non-decreasing variant instead.
- **Empty input:** `n == 0` skips both loops and returns `0`. (LeetCode guarantees `n >= 1`.)
- **Faster alternative:** an `O(n log n)` solution exists — maintain a `tails` array where `tails[k]` is the smallest possible tail of an increasing subsequence of length `k + 1`, and binary-search each element's insertion point. That version gives only the *length*, while this DP table also makes it straightforward to reconstruct the actual subsequence by backtracking through `dp`.
## Constraints
 
- `1 <= nums.length <= 2500`
- `-10^4 <= nums[i] <= 10^4`