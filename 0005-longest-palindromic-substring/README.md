# 5. Longest Palindromic Substring

**Difficulty:** Medium
**Topics:** String, Dynamic Programming
**Link:** https://leetcode.com/problems/longest-palindromic-substring/

## Problem

Given a string `s`, return the **longest palindromic substring** in `s`.

A palindrome reads the same forward and backward.

```text
s = "babad"

Output = "bab"
```

`"aba"` is also a valid answer.

## Approach: Expand Around Center

Every palindrome has a **center**.

The center can be:

* One character → odd-length palindrome
* Between two characters → even-length palindrome

For every index, expand in both ways while the characters match.

```text
"racecar"
   ↑
 center
```

For each position:

```java
expand(i, i);       // odd-length
expand(i, i + 1);   // even-length
```

Keep track of the longest palindrome found so far.

## Example

For:

```text
s = "cbbd"
```

Expanding around the center between the two `b`s gives:

```text
"bb"
```

So the answer is:

```text
"bb"
```

## Java Solution

```java
class Solution {
    private int start = 0;
    private int maxLen = 0;

    public String longestPalindrome(String s) {

        for (int i = 0; i < s.length(); i++) {
            expand(s, i, i);       // Odd length
            expand(s, i, i + 1);   // Even length
        }

        return s.substring(start, start + maxLen);
    }

    private void expand(String s, int left, int right) {

        while (left >= 0 && right < s.length()
                && s.charAt(left) == s.charAt(right)) {

            if (right - left + 1 > maxLen) {
                start = left;
                maxLen = right - left + 1;
            }

            left--;
            right++;
        }
    }
}
```

## Complexity

|                      | Time  | Space |
| -------------------- | ----- | ----- |
| Expand Around Center | O(n²) | O(1)  |

There are `n` possible centers, and expanding around each center can take up to `O(n)` time.

## Key Takeaway

Instead of checking every possible substring, **treat each character and each gap between characters as a potential center and expand outward**.

The important idea is:

```text
center → expand left + right → keep the longest palindrome
```
