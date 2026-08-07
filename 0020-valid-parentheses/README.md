# 20. Valid Parentheses

**Difficulty:** Easy
**Topics:** String, Stack
**Link:** https://leetcode.com/problems/valid-parentheses/

## Problem

Given a string of just `()`, `{}`, and `[]`, decide whether every bracket is
closed by the right type, in the right order.

`()[]{}` is valid. `([)]` is not — the brackets overlap instead of nesting.

## Approach

Brackets have to close in reverse order of opening, which is exactly
last-in-first-out. That's a stack.

The variant used here is worth noticing: **on an opening bracket, push the
closing bracket we expect to see**, rather than pushing the opener itself.

```java
if (c == '(') st.push(')');
```

Then when a closing bracket arrives, the check is a plain equality test against
whatever is on top:

```java
else if (st.isEmpty() || st.pop() != c) return false;
```

The usual alternative pushes openers and needs a lookup table to map each closer
back to its opener. Pushing the expectation instead removes that table entirely —
the comparison is already in the right form.

Three conditions cover every failure:

| Situation | Caught by | Example |
|---|---|---|
| Closer with nothing open | `st.isEmpty()` | `)` |
| Closer of the wrong type | `st.pop() != c` | `(]`, `([)]` |
| Openers never closed | `return st.isEmpty()` at the end | `(((` |

That last line is easy to forget. Without it `(((` would run to completion
without ever failing a check and wrongly return `true`.

## Complexity

- **Time:** O(n) — each character is pushed and popped at most once
- **Space:** O(n) — worst case is all openers, e.g. `((((((`

## A note on `Stack`

`java.util.Stack` works and is what most tutorials use, but it's a legacy class:
it extends `Vector`, so every operation is synchronized and pays for a lock you
never need in single-threaded code.

The modern equivalent is `ArrayDeque`:

```java
Deque<Character> st = new ArrayDeque<>();
st.push(')');       // same API
st.pop();
st.isEmpty();
```

Same methods, same complexity, measurably faster. Not worth changing for a
LeetCode submission, but `ArrayDeque` is the right default in real code — and
knowing why `Stack` is discouraged is the kind of thing that comes up in
interviews.
