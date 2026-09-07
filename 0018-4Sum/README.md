# 18. 4Sum

**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Sorting  
**Link:** https://leetcode.com/problems/4sum/

## Problem

Given an integer array `nums` and an integer `target`, return all unique quadruplets `[a, b, c, d]` such that:

`a + b + c + d = target`

Each quadruplet must contain different indices, and duplicate quadruplets are not allowed.

## Approach: Sorting + Two Pointers

Sort the array first, then fix the first two elements using two loops. For the remaining two elements, use two pointers `k` and `l`.

- If `sum == target` → store the quadruplet and move both pointers.
- If `sum < target` → move `k` forward.
- If `sum > target` → move `l` backward.
- Skip duplicate values at every level to avoid duplicate quadruplets.

`O(n³)` is achieved by using two pointers instead of four nested loops.

## Java Solution

```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);

        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            for (int j = i + 1; j < n; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                int k = j + 1;
                int l = n - 1;

                while (k < l) {
                    double sum = nums[i];
                    sum += nums[j];
                    sum += nums[k];
                    sum += nums[l];

                    if (sum == target) {
                        ans.add(Arrays.asList(
                            nums[i], nums[j], nums[k], nums[l]
                        ));

                        k++;
                        l--;

                        while (k < l && nums[k] == nums[k - 1]) k++;
                        while (k < l && nums[l] == nums[l + 1]) l--;

                    } else if (sum < target) {
                        k++;
                    } else {
                        l--;
                    }
                }
            }
        }

        return ans;
    }
}
```

## Complexity

| | Time | Space |
|---|---|---|
| Sorting + Two Pointers | O(n³) | O(1) |

Ignoring the space required for the output, the algorithm uses constant extra space.

## Key Takeaway

**Fix two numbers, then use two pointers for the remaining two.** Sorting makes pointer movement and duplicate removal possible, reducing the brute-force `O(n⁴)` approach to `O(n³)`.