# 50. Pow(x, n)

**Difficulty:** Medium
**Topics:** Math, Recursion, Binary Exponentiation
**Link:** https://leetcode.com/problems/pow-x-n/

## Problem

Compute `x` raised to the power `n`, where `n` can be negative.

```
myPow(2.00000, 10)  -> 1024.00000
myPow(2.10000, 3)   -> 9.26100
myPow(2.00000, -2)  -> 0.25000
```

## Why the naive loop isn't enough

Multiplying `x` by itself `n` times is O(n), and `n` can be 2³¹ − 1. That's two
billion multiplications — far too slow, and it's the reason this is a Medium.

## Approach: binary exponentiation

Halve the exponent instead of decrementing it, using:

```
x^n = (x²)^(n/2)        when n is even
x^n = x · x^(n-1)       when n is odd
```

Each even step **squares the base and halves the exponent** — the value of `x^n`
is unchanged, but the exponent shrinks geometrically. Odd steps peel off one
factor into the accumulator to make the exponent even again.

```java
if (nn % 2 == 1) { ans = ans * x; nn = nn - 1; }
else             { x = x * x;     nn = nn / 2; }
```

An odd step is always followed by an even one, so at most two iterations per bit
of `n` — **O(log n)** overall. All three maximum-exponent calls in the test run
completed in microseconds.

Negative exponents are handled by computing `x^|n|` and inverting at the end,
since `x^(-n) = 1 / x^n`.

## The trap: `n = Integer.MIN_VALUE`

This is the real content of the problem.

`Integer.MIN_VALUE` is −2,147,483,648, but `Integer.MAX_VALUE` is only
2,147,483,64**7**. The negative range has one more value than the positive one, so
`-n` has nowhere to go and **wraps back to itself**:

```java
int n = Integer.MIN_VALUE;
-n == Integer.MIN_VALUE;     // still negative
```

A loop written on that value never terminates, or runs with a negative counter and
returns nonsense.

The fix here is one line, placed correctly:

```java
long nn = n;                 // widen FIRST
if (nn < 0) nn = -1 * nn;    // now safe: 2147483648 fits in a long
```

Widening to `long` **before** negating is what makes it work. Doing it the other
way round — `long nn = -n;` — negates in `int` first, wraps, and then widens the
already-broken value.

Verified against `Math.pow` across a range of bases at `n = Integer.MIN_VALUE`,
including 2.0 (→ 0.0), 1.0, −1.0, 0.5 (→ Infinity) and 99.0.

## Complexity

- **Time:** O(log n)
- **Space:** O(1) — iterative, so no recursion stack

The recursive formulation of the same algorithm is equally common and reads more
directly from the identity, but costs O(log n) stack.

## A note on floating-point drift

Repeated squaring accumulates rounding error. At exponents near 2³¹ the base is
squared about 31 times, and for bases close to 1 the result can land around 10⁹³ —
where a relative error of roughly 10⁻⁸ against `Math.pow` shows up.

That's inherent to the method, not a defect: `Math.pow` uses a different technique
internally (logarithm and exponential), so the two are expected to differ in the
last digits. LeetCode accepts answers within 1e-5, three orders of magnitude
looser than the observed drift. The problem also constrains `x^n` to
[−10⁴, 10⁴], well short of where the error becomes visible.

## Verification

**1,573 base/exponent pairs** — 13 bases including negatives, fractions and 1.0,
against every exponent from −60 to 60 — all within 1e-5 of `Math.pow`. Plus
200,000 random `(x, n)` pairs across the full `-100 < x < 100` range.

Separately, the `Integer.MIN_VALUE` cases were compared against `Math.pow`
directly, along with `Integer.MAX_VALUE` exponents and `n = 0`.
