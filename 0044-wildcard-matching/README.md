# 44. Wildcard Matching

**Difficulty:** Hard
**Topics:** String, Dynamic Programming, Greedy, Recursion
**Link:** https://leetcode.com/problems/wildcard-matching/

## Problem

Match a string against a pattern where:

- `?` matches exactly one character
- `*` matches **any sequence**, including the empty one

The match must cover the *entire* string, not a substring.

```
s = "aa",     p = "a"       -> false
s = "aa",     p = "*"       -> true
s = "cb",     p = "?a"      -> false
s = "adceb",  p = "*a*b"    -> true
s = "acdcb",  p = "a*c?b"   -> false
```

## Why `*` makes this hard

`?` is trivial — one character, one character. `*` is the difficulty, because its
length isn't determined locally. In `"*a*b"` against `"adceb"`, how much the first
`*` should swallow can't be decided until the rest of the pattern has been tried.

Naive recursion branches on every possible split, which is exponential. The fix is
to note that the same `(position in s, position in p)` pair gets revisited over and
over — so the answer for each pair is worth computing once.

## The DP

`dp[i][j]` = does `s[0..i)` match `p[0..j)`?

| pattern char | rule |
|---|---|
| `*` | `dp[i-1][j] \|\| dp[i][j-1]` |
| `?` or a literal that matches | `dp[i-1][j-1]` |
| literal that doesn't match | `false` |

The `*` case is the whole problem, and it's two options rather than a loop over
every possible length:

- **`dp[i-1][j]`** — the `*` absorbs `s[i-1]`, staying available for more
- **`dp[i][j-1]`** — the `*` matches nothing and the pattern moves on

Every possible expansion is covered by repeatedly applying those two, which is
what collapses an exponential branch into O(1) work per cell.

### The initialisation carries the empty-string case

```java
dp[0] = true;
for (int i = 1; i <= n; i++) {
    if (p.charAt(i - 1) == '*') dp[i] = dp[i - 1];
}
```

An empty string matches a pattern only if that pattern is entirely `*`s. The loop
propagates `true` along a leading run of stars and stops dead at the first
non-star, since `dp[i]` stays `false` there and everything after it reads from it.

This is where most implementations go wrong — `*` matching the *empty* sequence is
easy to forget, and it only surfaces on inputs like `("", "*")` or `("ho", "**ho")`.
Both are in the test set below.

## The 1D space optimisation

`dp[i][j]` reads only from row `i-1` and from `dp[i][j-1]` on the current row, so
the full 2D table is unnecessary — two rows suffice:

```java
boolean[] temp = new boolean[n + 1];
...
temp[j] = dp[j] || temp[j - 1];     // dp = previous row, temp = current row
```

`dp[j]` is the previous row (the star absorbs a character), `temp[j-1]` the
current one (the star matches nothing). Dropping from O(m × n) to **O(n)** space.

`temp` is freshly allocated each row, so `temp[0]` starts `false` — correct, since
a non-empty string can never match an empty pattern.

Same collapse as [119. Pascal's Triangle II](../0119-pascals-triangle-ii/README.md)
and the note on [198. House Robber](../0198-house-robber/README.md): when a DP only
looks back a fixed distance, the table shrinks to a couple of rows.

## Complexity

- **Time:** O(m × n) — one constant-time cell per (string, pattern) position pair
- **Space:** O(n) — two rows instead of the full table

At the 2000 × 2000 constraint limit that's 4 million cells, measured at 11-13 ms.

## The input that separates this from backtracking

```
s = "aaaa…a" (2000 chars)
p = "a*a*a*…a*b"   (40 stars, ending in a literal b that can never match)
```

Naive recursion explores every way of distributing 2000 characters among 40 stars
before discovering none of them work — astronomically many. This DP answers it in
**0 ms**, because each cell is computed once regardless of how many paths reach it.

## Verification

**21,483 string/pattern pairs** — every string over `{a,b}` up to length 5 against
every pattern over `{a,b,?,*}` up to length 4 — checked against brute-force
recursion that tries every expansion of every star. That reference is exponential
and unusable for real input, but it's a direct transcription of the definition, so
it can't be subtly wrong. All matched.

Plus the empty-string and empty-pattern cases in both directions, the
2000 × 2000 constraint limit, an all-stars pattern, and the pathological
backtracking case above.
