# 67. Add Binary

**Difficulty:** Easy
**Topics:** Math, String, Bit Manipulation, Simulation
**Link:** https://leetcode.com/problems/add-binary/

## Problem

Given two binary strings, return their sum as a binary string.

```
a = "11",   b = "1"    -> "100"
a = "1010", b = "1011" -> "10101"
```

## Why not just convert to a number?

The tempting one-liner:

```java
return Integer.toBinaryString(Integer.parseInt(a, 2) + Integer.parseInt(b, 2));
```

The constraints kill it: each string can be up to **10⁴ characters**. That's a
number with ten thousand binary digits — `int` holds 32, `long` holds 64. It
would overflow by roughly 9,900 digits.

`BigInteger` would work, but at that point the arithmetic is being delegated
rather than implemented, which is not what the problem is asking for.

## Approach

Long addition, exactly as taught on paper, but base 2. Walk both strings from the
right, add the digits plus any carry, write the low bit, carry the high bit.

```java
int sum = bit1 + bit2 + carry;
result.append(sum % 2);   // the bit to write
carry = sum / 2;          // what moves left
```

`sum` can only be 0, 1, 2, or 3, so `sum % 2` and `sum / 2` split it into exactly
the right bit and carry every time — no lookup table, no branching.

### Three details that make it work

**Unequal lengths.** The condition `i >= 0 || j >= 0` keeps going while *either*
string has digits left, and the ternaries substitute `0` for the exhausted one.
That's the same as padding the shorter string with leading zeros, without
building a padded copy.

**The final carry.** `carry == 1` in the loop condition is the one people leave
out. Without it, `"1" + "1"` produces `"0"` — the loop ends when both strings are
consumed and the last carry is silently dropped. The result should be `"100"`.

**Building backwards.** Digits are produced least-significant first, so they're
appended in reverse and the string is flipped once at the end. Appending to a
`StringBuilder` is O(1) amortised; inserting at the front each time would be O(n)
per digit and turn the whole thing quadratic.

## Complexity

- **Time:** O(max(m, n)) — one pass, plus the O(n) reverse
- **Space:** O(max(m, n)) for the output

## Verification

Cross-checked against `BigInteger.add` — the only reference that stays valid at
the full 10⁴-bit input size. Every pair of values from 0 to 1023 (1,048,576
cases) was verified against ordinary integer addition, and random pairs at the
full 10,000-bit limit against `BigInteger`.
