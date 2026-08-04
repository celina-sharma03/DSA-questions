# 242. Valid Anagram

**Difficulty:** Easy
**Topics:** Hash Table, String, Sorting
**Link:** https://leetcode.com/problems/valid-anagram/

## Problem

Given two strings `s` and `t`, return `true` if `t` is an anagram of `s` — that
is, if both use exactly the same characters with the same counts, in any order.

## Approach

Two strings are anagrams precisely when their sorted forms are identical.
Sort both into `char[]` and compare with `Arrays.equals`.

The length check comes first as an early exit. Strings of different lengths can
never be anagrams, and bailing out immediately skips two sorts on inputs that
were never going to match.

`Arrays.equals` compares element by element, which is what's wanted here — `==`
would compare array references and always be `false`, and `c1.equals(c2)` does
the same reference comparison, since arrays don't override `equals`. That's a
genuinely easy mistake to make in Java.

## Complexity

- **Time:** O(n log n) — dominated by the two sorts
- **Space:** O(n) — the two char arrays

## Alternative: frequency counting

Since the problem restricts input to lowercase English letters, a fixed array of
26 counters beats sorting:

```java
int[] count = new int[26];
for (int i = 0; i < s.length(); i++) {
    count[s.charAt(i) - 'a']++;
    count[t.charAt(i) - 'a']--;
}
for (int c : count) {
    if (c != 0) return false;
}
return true;
```

Increment for `s`, decrement for `t` in the same loop. If every counter lands
back on zero, the two strings used identical characters.

That's **O(n) time and O(1) space** — the array is 26 slots regardless of input
size. Strictly better than sorting, and the version worth reaching for in an
interview.

The sorting version earns its keep by being harder to get wrong and needing no
assumption about the alphabet.

## Follow-up

LeetCode asks what changes if the input contains Unicode. The `- 'a'` trick
breaks immediately, since it assumes 26 contiguous lowercase letters — swap the
array for a `HashMap<Character, Integer>`. Sorting still works for characters in
the Basic Multilingual Plane, but splits surrogate pairs for anything above it,
so emoji and rarer scripts would need `codePoints()` rather than `char[]`.
