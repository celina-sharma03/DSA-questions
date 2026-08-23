# 38. Count and Say

**Difficulty:** Medium
**Topics:** String, Run-Length Encoding
**Link:** https://leetcode.com/problems/count-and-say/

## Problem

The sequence starts at `"1"`, and each term is produced by *reading the previous
term aloud* — describing each run of identical digits as "how many, then which".

```
1
11        one 1
21        two 1s
1211      one 2, one 1
111221    one 1, one 2, two 1s
312211    three 1s, two 2s, one 1
```

Return term `n`.

## Approach

There's no closed form. The only way to reach term `n` is to build every term
before it, so the outer loop runs `n - 1` times and each pass run-length encodes
the current string.

The inner loop walks the string comparing each character to the one before it:

```java
if (currentTerm.charAt(j) == currentTerm.charAt(j - 1)) {
    count++;                                   // run continues
} else {
    nextTerm.append(count).append(currentTerm.charAt(j - 1));   // run ended, emit it
    count = 1;
}
```

### The line outside the loop

```java
nextTerm.append(count).append(currentTerm.charAt(currentTerm.length() - 1));
```

This is the one that's easy to miss. A run is only emitted when a *different*
character appears — so the final run has nothing following it to trigger the
write, and it has to be flushed after the loop ends.

Without it, `"111"` would produce nothing at all rather than `"31"`. It's the
same shape as the trailing `carry` in
[67. Add Binary](../0067-add-binary/README.md): the last piece of state has no
natural trigger and needs handling explicitly.

The inner loop starting at `j = 1` is also what makes single-character terms
work — the body never runs, and the flush line alone produces the correct answer.

## Complexity

Let `L` be the length of term `n`.

- **Time:** O(sum of all term lengths) — every term must be built to reach the next
- **Space:** O(L) for the string being constructed

Terms grow by roughly **1.3× each step**, so `L` is exponential in `n`. Measured
here: term 30 is **4,462 characters**, and the ratio of consecutive lengths came
out at **1.3085** — converging on **Conway's constant, 1.303577…**, which is the
proven growth rate of this sequence.

The constraint stopping at `n = 30` is why the exponential growth stays
manageable; term 60 would run to about a million characters.

## A curiosity that makes for a good test

Starting from `"1"`, **only the digits 1, 2 and 3 ever appear** — no `4` shows up
at any term, ever. It's a proven property of the sequence (part of Conway's work
on it), and it follows from the fact that no run of four identical digits can
occur.

That makes it a strong correctness check: a run-length bug producing a run of four
would immediately emit a `4` and break the property. Confirmed across all 30
terms.

## Verification

The first 10 terms matched known values. Beyond that, each term was checked
against the sequence's *defining relation* rather than against another
implementation: term `n` was read back as (count, digit) pairs and expanded, and
the result compared to term `n-1`. That inversion holds for every `n` from 2 to
30. Also confirmed: no digit outside 1-3, and no run of 4 or more identical
characters, in any term.
