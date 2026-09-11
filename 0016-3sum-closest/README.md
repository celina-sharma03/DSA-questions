# 3Sum Closest

## Problem

Given an integer array `nums` of length `n` and an integer `target`, find three integers in `nums` such that their sum is **closest to `target`**.

Return the sum of the three integers.

You may assume that there is exactly one solution.

### Example

**Input:**

```text
nums = [-1, 2, 1, -4]
target = 1
```

**Output:**

```text
2
```

### Explanation

The possible 3-element sums include:

```text
(-1 + 2 + 1) = 2
(-1 + 2 - 4) = -3
(-1 + 1 - 4) = -4
(2 + 1 - 4) = -1
```

The sum `2` is closest to the target `1`.

---

## Approach

The solution uses **sorting + one fixed element + two pointers**.

### 1. Sort the Array

First, sort the array:

```java
Arrays.sort(nums);
```

Sorting allows us to efficiently use the two-pointer technique.

For example:

```text
[-4, -1, 1, 2]
```

### 2. Fix One Element

Use a loop to fix the first element of the triplet:

```java
for(int i = 0; i < n - 2; i++)
```

For every `i`, we need to find two more elements whose sum makes the total as close as possible to `target`.

### 3. Use Two Pointers

Initialize:

```java
int j = i + 1;
int k = n - 1;
```

Here:

* `i` → fixed element
* `j` → left pointer
* `k` → right pointer

Calculate:

```java
int sum = nums[i] + nums[j] + nums[k];
```

### 4. Check the Closest Sum

Keep track of the best sum found so far.

```java
if(Math.abs(sum - target) < Math.abs(ans - target)){
    ans = sum;
}
```

If the current sum is closer to `target`, update `ans`.

### 5. Move the Pointers

Because the array is sorted:

If:

```text
sum < target
```

we need a larger sum, so move the left pointer:

```java
j++;
```

If:

```text
sum > target
```

we need a smaller sum, so move the right pointer:

```java
k--;
```

If:

```text
sum == target
```

we have found the exact answer, so we can immediately return it.

---

## Why Does the Two-Pointer Approach Work?

After sorting the array, the sum changes predictably when we move the pointers.

For example:

```text
[-4, -1, 1, 2, 5]
       j        k
```

If the current sum is smaller than the target, moving `j` to the right gives us a larger value and increases the sum.

If the current sum is larger than the target, moving `k` to the left gives us a smaller value and decreases the sum.

This allows us to find the closest sum in linear time for every fixed `i`.

---

## Example Walkthrough

Consider:

```text
nums = [-1, 2, 1, -4]
target = 1
```

After sorting:

```text
[-4, -1, 1, 2]
```

Fix `-4`:

```text
-4 + (-1) + 2 = -3
```

This is not close enough, so move the left pointer.

Next:

```text
-4 + 1 + 2 = -1
```

Then fix `-1`:

```text
-1 + 1 + 2 = 2
```

The difference from the target is:

```text
|2 - 1| = 1
```

So the closest sum is:

```text
2
```

---

## Complexity Analysis

Let `n` be the length of the array.

### Time Complexity

Sorting takes:

```text
O(n log n)
```

The outer loop runs `n` times, and for each element the two pointers traverse the remaining array in `O(n)`.

Therefore:

```text
O(n²)
```

Overall:

```text
O(n²)
```

### Space Complexity

The algorithm uses constant extra space apart from the sorting implementation:

```text
O(1)
```

---

## Code

```java
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);

        int n = nums.length;
        int ans = nums[0] + nums[1] + nums[2];

        for (int i = 0; i < n - 2; i++) {
            int j = i + 1;
            int k = n - 1;

            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];

                if (Math.abs(sum - target) < Math.abs(ans - target)) {
                    ans = sum;
                }

                if (sum < target) {
                    j++;
                }
                else if (sum > target) {
                    k--;
                }
                else {
                    return sum;
                }
            }
        }

        return ans;
    }
}
```

## Key Takeaway

**3Sum Closest = Sort + Fix 1 Element + Two Pointers**

The main idea is to reduce the problem to finding the best **2Sum for every fixed element**, while sorting lets us move the pointers intelligently instead of checking every possible triplet.
