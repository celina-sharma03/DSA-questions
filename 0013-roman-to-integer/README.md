# 13. Roman to Integer

**Difficulty:** Easy
**Topics:** Hash Table, Math, String
**Link:** https://leetcode.com/problems/roman-to-integer/

## Problem

Convert a Roman numeral string to its integer value.

Numerals normally read largest to smallest and simply add up: `LVIII` is
50 + 5 + 1 + 1 + 1 = 58. The exception is subtraction — a smaller numeral placed
before a larger one is subtracted instead. There are six such pairs: `IV` (4),
`IX` (9), `XL` (40), `XC` (90), `CD` (400), `CM` (900).

## Approach

Map each character to its value, then scan left to right. At each position,
compare the current numeral with the one after it:

- current **<** next → this is the front half of a subtractive pair, so subtract
- otherwise → add

The elegant part is that this needs no list of the six special pairs. "Smaller
before larger" is the *definition* of the subtractive rule, so checking that
condition handles `IV`, `IX`, `XL`, `XC`, `CD`, and `CM` uniformly.

`MCMXCIV` works out as:

| i | char | next | rule | running total |
|---|------|------|------|---------------|
| 0 | M (1000) | C (100) | add | 1000 |
| 1 | C (100) | M (1000) | 100 < 1000, subtract | 900 |
| 2 | M (1000) | X (10) | add | 1900 |
| 3 | X (10) | C (100) | 10 < 100, subtract | 1890 |
| 4 | C (100) | I (1) | add | 1990 |
| 5 | I (1) | V (5) | 1 < 5, subtract | 1989 |
| 6 | V (5) | — | last char, add | **1994** |

The `i < s.length() - 1` guard is what keeps the final character safe: there is
no "next" to compare against, and a lone trailing numeral is always added.

## Complexity

- **Time:** O(n) — one pass over the string
- **Space:** O(1) — the map holds exactly 7 entries regardless of input

## Possible refinement

The map is rebuilt on every call. Hoisting it to a `static final` field, or
replacing it with a `switch`, avoids that and skips `Integer` boxing entirely:

```java
private static int value(char c) {
    switch (c) {
        case 'I': return 1;
        case 'V': return 5;
        case 'X': return 10;
        case 'L': return 50;
        case 'C': return 100;
        case 'D': return 500;
        case 'M': return 1000;
        default:  return 0;
    }
}
```

Same O(n)/O(1) complexity — the algorithm is unchanged, it's purely a constant
factor. Not worth the rewrite here, but it's the kind of detail an interviewer
may prod at once the solution is working.
