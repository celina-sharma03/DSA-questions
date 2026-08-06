# 12. Integer to Roman

**Difficulty:** Medium
**Topics:** Hash Table, Math, String, Greedy
**Link:** https://leetcode.com/problems/integer-to-roman/

The inverse of [13. Roman to Integer](../0013-roman-to-integer/README.md).

## Problem

Convert an integer in the range 1–3999 to its Roman numeral representation.

## Approach

Greedy. Keep a table of values paired with their symbols, ordered largest to
smallest, and repeatedly subtract the biggest value that still fits — appending
its symbol each time.

The move that makes this work is putting the six subtractive pairs **into the
table as first-class entries**:

```
1000  900  500  400  100  90  50  40  10   9    5    4    1
 M    CM    D   CD    C   XC   L   XL   X   IX   V   IV    I
```

With `900 → "CM"` sitting in the table between `1000` and `500`, the greedy scan
reaches for it naturally and 900 comes out as `CM`. Without those entries the
same loop would emit `DCCCC`, and you'd need six special cases bolted on to
repair it.

Same insight as #13, mirrored: don't enumerate the exceptions, find the
representation that makes them fall out of the ordinary rule.

Tracing `1994`:

| Step | Value fits | Emit | Remaining |
|------|-----------|------|-----------|
| 1 | 1000 | `M` | 994 |
| 2 | 900 | `CM` | 94 |
| 3 | 90 | `XC` | 4 |
| 4 | 4 | `IV` | 0 |

Result: `MCMXCIV`.

## Why greedy is safe here

Greedy algorithms usually need justification — taking the locally best option
often ruins the global result. It's valid here because the Roman value set is
*canonical*: for every remaining amount there is exactly one correct largest
symbol to emit, and taking it never forces a worse choice later. That's a
property of this specific value table, not of greedy in general.

## Complexity

- **Time:** O(1) — input is capped at 3999, so the loop runs a bounded number of
  times (at most 15 symbols are ever emitted)
- **Space:** O(1) — the tables are fixed size; the output string is bounded too

## Verification

Cross-checked against the #13 solution: every value from 1 to 3999 was encoded
with this function and decoded back with `romanToInt`. All 3999 round-tripped to
themselves, and 14 hand-written cases were checked against known-correct
numerals independently.
