# 58. Length of Last Word

**Difficulty:** Easy
**Topics:** String
**Link:** https://leetcode.com/problems/length-of-last-word/

## Problem

Given a string of words and spaces, return the length of the **last** word. A word
is a maximal run of non-space characters.

```
"Hello World"                  -> 5
"   fly me   to   the moon  "  -> 4
"luffy is still joyboy"        -> 6
```

The string is guaranteed to contain at least one word.

## The trap: trailing spaces

The obvious move — count backwards from the end until you hit a space — returns
`0` on `"fly me to the moon  "`, because the very first character from the end is
already a space. Trailing whitespace has to be dealt with before any counting
starts.

## Approach

Strip the ends, find the last space, and measure what follows it:

```java
s = s.trim();
int last = s.lastIndexOf(' ');
return s.length() - last - 1;
```

After `trim()` the string ends on a letter, so the last word runs from just past
the final space to the end. That's `s.length() - (last + 1)` characters.

### The single-word case needs no special handling

If there's only one word, `lastIndexOf(' ')` finds nothing and returns `-1`. The
formula then gives `s.length() - (-1) - 1`, which is `s.length()` — the whole
string, exactly right. The `-1` sentinel slots straight into the arithmetic.

Same shape as seeding `answer = -1` in
[34. Find First and Last Position](../0034-find-first-and-last-pos-in-sorted-array/README.md):
choose a value for "not found" that makes the general formula produce the correct
answer on its own.

### Leading spaces

`trim()` strips both ends, but only the trailing side actually matters here — a
leading space can never be the *last* one unless there's just one word, and then
`trim()` removes it anyway. So this is also correct on `"   leading"` → 7.

## Complexity

- **Time:** O(n) — `trim` and `lastIndexOf` are each a single scan
- **Space:** O(n) worst case — `trim()` returns a new `String` whenever it removes
  anything, and `String` is immutable, so that's a copy of the remainder

## The O(1)-space version

Scan backwards directly — skip the trailing spaces, then count letters until the
next space:

```java
int i = s.length() - 1;
while (i >= 0 && s.charAt(i) == ' ') i--;
int len = 0;
while (i >= 0 && s.charAt(i) != ' ') { len++; i--; }
return len;
```

No new string, and it stops as soon as the last word is measured rather than
touching the whole input. On `"a"` followed by 9,999 spaces both versions do
comparable work, but on a long string whose last word is short, this one reads
only the tail.

Both are fine for an Easy. The `trim()` version is shorter and harder to get wrong;
the scan is the one to write if asked for O(1) space.

## Verification

**32,752 strings** — every arrangement of letters and spaces of length 1 to 14
containing at least one word — checked against the backward-scan version above,
which uses neither `trim()` nor `lastIndexOf()`. All matched.

Plus 20,000 random strings up to the 10⁴-character limit, a single 10,000-letter
word, and a one-letter word followed by 9,999 trailing spaces.
