# 7. Reverse Integer

**Difficulty:** Medium
**Topics:** Math
**Link:** https://leetcode.com/problems/reverse-integer/

## Problem

Reverse the digits of a signed 32-bit integer. If the reversed value falls
outside `[-2³¹, 2³¹ - 1]`, return `0`.

```
 123 ->  321
-123 -> -321
 120 ->   21
1534236469 -> 0   (reversed would be 9646324351, too big)
```

The stated twist: assume the environment cannot store 64-bit integers. In Java
we have `long` anyway, which is what this solution uses.

## Approach

Peel digits off the end with `% 10` and rebuild them onto the front of an
accumulator:

```java
int digit = x % 10;
x /= 10;
reversed = reversed * 10 + digit;
```

Each pass moves everything already collected one place left and drops the new
digit into the ones column. `123` becomes `3`, then `32`, then `321`.

### Negatives need no special handling

Java's `%` keeps the sign of the dividend: `-123 % 10` is `-3`, not `7`. And
integer division truncates toward zero, so `-123 / 10` is `-12`. The digits come
out negative and reassemble into a negative result on their own.

That's worth knowing because it is *not* universal — Python's `-123 % 10` is `7`,
and the same code there would be wrong. Plenty of solutions strip the sign,
reverse the absolute value, and reapply it. In Java that's unnecessary.

### The overflow check

This is the actual problem. `reversed` is a `long`, so it can hold values that
would have wrapped around in an `int`, and the range check happens **inside the
loop**, before the next multiply.

Checking on every iteration rather than once at the end is what keeps the `long`
itself safe. The largest value ever held is bounded by roughly `2³¹ × 10 + 9`
(≈ 2.1 × 10¹⁰), comfortably inside `long`'s ~9.2 × 10¹⁸ range. If the check were
deferred to the end it would still work here, but only because the input has at
most 10 digits — the in-loop version doesn't depend on that.

`Integer.MIN_VALUE` is the case worth checking by hand: `-2147483648` reverses to
`-8463847412`, which trips the lower bound and returns `0`.

## Complexity

- **Time:** O(log₁₀ x) — one iteration per digit, at most 10
- **Space:** O(1)

## Verification

Run against **all 4,294,967,296 32-bit integers**, comparing against a reference
that accumulates the full reversal first and range-checks only at the end — a
different structure, so a mistake in the early-return logic would show up as a
disagreement. Also cross-checked against a string-reversal implementation on all
boundary values and a large random sample. No disagreements.
