# 268. Missing Number

**Difficulty:** Easy
**Topics:** Array, Hash Table, Math, Bit Manipulation, Sorting
**Link:** https://leetcode.com/problems/missing-number/

## Problem

Given an array of `n` distinct numbers drawn from the range `[0, n]`, exactly one
value in that range is absent. Return it.

```
[3,0,1]               -> 2
[0,1]                 -> 2
[9,6,4,2,3,5,7,0,1]   -> 8
```

The follow-up asks for **O(1) extra space** and **linear time**.

## Approach: XOR everything twice

XOR has two properties that make this work:

- `a ^ a == 0` — a value XORed with itself cancels out
- `a ^ 0 == a` — XOR with zero changes nothing

So XOR every value that *should* be present together with every value that *is*
present. Each number appearing in both sets cancels itself, and the only one left
standing is the one that appeared just once — the missing number.

```java
for (int i = 0; i <= n - 1; i++) x1 = x1 ^ nums[i];   // what's there
for (int i = 1; i <= n; i++)     x2 = x2 ^ i;         // what should be there
return x1 ^ x2;
```

On `[3,0,1]`:

```
x1 = 3 ^ 0 ^ 1 = 2
x2 = 1 ^ 2 ^ 3 = 0
x1 ^ x2 = 2
```

XOR is commutative and associative, so the order values arrive in is irrelevant —
no sorting, no bookkeeping about which ones have been seen.

### Why starting the second loop at 1 is fine

The range is `[0, n]`, so strictly the full set is `0, 1, …, n`. This loop starts
at `1` and skips `0`.

That's harmless: `x ^ 0 == x`, so including zero would change nothing. Starting
at `0` would be more faithful to the problem statement, but the result is
identical either way.

## Complexity

- **Time:** O(n) — two linear passes
- **Space:** O(1) — two `int` accumulators

Meets the follow-up's requirements.

## Alternatives

**Sum formula.** The numbers `0..n` sum to `n(n+1)/2`. Subtract the actual array
sum and the difference is the missing value:

```java
int expected = n * (n + 1) / 2;
int actual = 0;
for (int v : nums) actual += v;
return expected - actual;
```

Same O(n)/O(1), and arguably more obvious. The catch is overflow: `n(n+1)/2`
exceeds `int` once `n` passes about 65,535. Safe here — the constraints cap `n` at
10⁴ — but XOR has no such failure mode at any size, which is a real point in its
favour.

**Sorting**, then scanning for the first index where `nums[i] != i`: O(n log n)
time, and it destroys the input. Strictly worse.

**HashSet** of all values, then probe `0..n`: O(n) time but O(n) space, which
fails the follow-up.

## Verification

Every array size from 1 to 200, with every possible missing value at every
position — 20,300 cases in total, each shuffled — plus 100,000 random arrays and
a run at the 10⁴ constraint limit. All cross-checked against the sum-formula
approach.
