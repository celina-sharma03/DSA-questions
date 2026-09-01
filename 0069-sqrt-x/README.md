# 69. Sqrt(x)

**Difficulty:** Easy  
**Topics:** Math, Binary Search  
**Link:** https://leetcode.com/problems/sqrtx/

## Problem

Given a non-negative integer `x`, return the **integer square root** of `x`. Return the largest integer whose square is less than or equal to `x`.

Example: `x = 8 → 2`

## Approach: Binary Search

Search for the answer between `1` and `x`. For each `mid`, compare `mid * mid` with `x`.

- `mid² > x` → search left
- `mid² < x` → search right
- `mid² == x` → return `mid`

When the search ends, `end` is the integer square root.

`long` is used for multiplication to avoid integer overflow.

## Java Solution

```java
class Solution {
    public int mySqrt(int x) {
        if (x == 0 || x == 1)
            return x;

        int start = 1;
        int end = x;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if ((long) mid * mid > x)
                end = mid - 1;
            else if ((long) mid * mid == x)
                return mid;
            else
                start = mid + 1;
        }

        return end;
    }
}
```

## Complexity

| | Time | Space |
|---|---|---|
| Binary Search | O(log x) | O(1) |

## Key Takeaway

Find the **largest `mid` such that `mid² <= x`** using Binary Search.