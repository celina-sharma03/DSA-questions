# 14. Longest Common Prefix

**Difficulty:** Easy  
**Topics:** Array, String, Sorting  
**Link:** https://leetcode.com/problems/longest-common-prefix/

## Problem

Given an array of strings, find the **longest common prefix** shared by all strings.

If there is no common prefix, return `""`.

## Approach: Sorting

Sort the strings lexicographically. After sorting, the **first and last strings** will have the maximum difference, so their common prefix is also the common prefix of the entire array.

Compare characters of the first and last strings until they differ.

## Java Solution

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        Arrays.sort(strs);

        String s1 = strs[0];
        String s2 = strs[strs.length - 1];

        int ind = 0;

        while (ind < s1.length() && ind < s2.length()) {
            if (s1.charAt(ind) == s2.charAt(ind))
                ind++;
            else
                break;
        }

        return s1.substring(0, ind);
    }
}
```

## Complexity

| | Time | Space |
|---|---|---|
| Sorting | O(n log n) | O(n) |

## Key Takeaway

**Sort the strings and compare only the first and last strings** to find the common prefix efficiently.