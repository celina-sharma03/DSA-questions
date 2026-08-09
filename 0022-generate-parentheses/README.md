# 22. Generate Parentheses

**Difficulty:** Medium
**Topics:** String, Backtracking
**Link:** https://leetcode.com/problems/generate-parentheses/

Companion to [20. Valid Parentheses](../0020-valid-parentheses/README.md) — that
one *checks* whether a string is balanced, this one *builds* every balanced
string.

## Problem

Given `n` pairs of parentheses, generate all combinations of well-formed
parentheses.

For `n = 3`:

```
((()))  (()())  (())()  ()(())  ()()()
```

## Approach

Build the string one character at a time, and never make a move that could lead
somewhere invalid. Two counters — how many `(` placed, how many `)` placed —
are enough to decide what's legal at each step:

| Move | Allowed when | Why |
|------|-------------|-----|
| add `(` | `open < total` | can't use more opening brackets than we have pairs |
| add `)` | `closed < open` | can't close a bracket that was never opened |

When the string reaches length `2n`, every pair has been placed and the result is
recorded.

The important property is that **these two rules make invalid strings
unreachable**. There's no validity check anywhere and no filtering at the end —
a partial string that violates the rules is never constructed in the first place.
Compare with the naive approach of generating all 2²ⁿ strings and testing each
with a #20-style validator: for `n = 8` that's 65,536 candidates to produce just
1,430 answers.

That's what "backtracking" means in practice — prune at the decision point, not
after the fact.

### Why no explicit undo?

Most backtracking templates append to a shared `StringBuilder` and then remove
the character after recursing:

```java
sb.append('(');
solve(sb, ...);
sb.deleteCharAt(sb.length() - 1);   // undo
```

This solution passes `curr + "("` instead — a brand new string. The caller's
`curr` is never modified, so there is nothing to undo. Java strings being
immutable is what makes that safe.

The trade-off is allocation: each call builds a fresh string rather than reusing
one buffer. With `n ≤ 8` that's irrelevant, and the version without undo logic is
harder to get wrong — forgetting the undo line is one of the most common
backtracking bugs.

## Complexity

The number of results is the **nth Catalan number**:

| n | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |
|---|---|---|---|---|---|---|---|---|
| results | 1 | 2 | 5 | 14 | 42 | 132 | 429 | 1430 |

- **Time:** O(4ⁿ / √n) — Catalan(n) results, each taking O(n) to assemble
- **Space:** O(n) recursion depth, plus O(4ⁿ / √n · n) held in the output list

The recursion tree has no wasted branches: every leaf reached is a valid answer.

## Verification

For `n = 1..8`: the result count matched the Catalan numbers exactly, every
generated string was confirmed balanced by an independent validator, and no
duplicates were produced. The `n = 3` output was also compared against the five
expected strings.
