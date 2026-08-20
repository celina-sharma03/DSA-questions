# 136. Single Number

**Difficulty:** Easy
**Topics:** Array, Bit Manipulation
**Link:** https://leetcode.com/problems/single-number/

Same cancellation trick as [268. Missing Number](../0268-missing-number/README.md),
reduced to its simplest form.

## Problem

Every element in the array appears **twice** except one, which appears once.
Return that one.

Required: **linear time**, **constant extra space**.

```
[2,2,1]      -> 1
[4,1,2,1,2]  -> 4
[1]          -> 1
```

## Approach

XOR everything together. That's the whole solution.

Two properties do the work:

- `a ^ a == 0` — any value XORed with itself cancels
- `a ^ 0 == a` — XOR with zero is a no-op

Every value that appears twice annihilates itself. Whatever survives is the
element that appeared once.

```java
int XOR = 0;
for (int i = 0; i < nums.length; i++) {
    XOR = XOR ^ nums[i];
}
return XOR;
```

XOR is commutative and associative, so the pairs don't need to be adjacent or
sorted — they cancel wherever they sit. On `[4,1,2,1,2]`:

```
4 ^ 1 ^ 2 ^ 1 ^ 2
= 4 ^ (1 ^ 1) ^ (2 ^ 2)     reorder freely
= 4 ^ 0 ^ 0
= 4
```

## Complexity

- **Time:** O(n) — one pass
- **Space:** O(1) — a single accumulator

Meets both requirements. The obvious alternatives don't: a `HashSet` or frequency
map is O(n) space, and sorting to find the unpaired neighbour is O(n log n) time.

## Where this goes next

The same idea scales to harder variants:

- [137. Single Number II](https://leetcode.com/problems/single-number-ii/) —
  every element appears three times except one. XOR no longer cancels, so you
  count bits mod 3 instead.
- [260. Single Number III](https://leetcode.com/problems/single-number-iii/) —
  **two** elements appear once. XOR of everything gives `a ^ b`; the lowest set
  bit of that is a position where `a` and `b` differ, so partitioning the array
  on that bit separates them and each half reduces to this problem.

That last one is a genuinely elegant use of the trick and a common interview
question.

## Verification

Exhaustive over 40,000 cases - every array size up to 399 with the unique element at
every possible position - plus 200,000 randomly generated and shuffled arrays,
all cross-checked against a frequency-map reference.
