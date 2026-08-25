# 198. House Robber

**Difficulty:** Medium
**Topics:** Array, Dynamic Programming
**Link:** https://leetcode.com/problems/house-robber/

## Problem

Each house holds some amount of money. Robbing two **adjacent** houses triggers
the alarm. Return the maximum that can be taken.

```
[1,2,3,1]     -> 4    (houses 0 and 2)
[2,7,9,3,1]   -> 12   (houses 0, 2 and 4)
```

## Why greedy fails

The obvious instincts — take the largest house and skip its neighbours, or take
every alternate house — both lose.

On `[2,1,1,2]`, "largest first" grabs a `2`, blocks a neighbour, and ends at 3.
The answer is 4 (both `2`s). On `[1,3,1,3,100]`, strict alternation takes indices
0, 2, 4 for 102, while skipping index 0 gives 3 + 100 = **103**.

Whether a house is worth taking depends on what it *costs you later*, and that
isn't visible locally. This is what makes it DP rather than greedy.

## The recurrence

At house `i` there are exactly two choices:

- **rob it** — then house `i-1` is off limits, so the best total is
  `nums[i] + dp[i-2]`
- **skip it** — the best total is whatever was already achievable at `i-1`, `dp[i-1]`

Take the better:

```java
dp[i] = Math.max(nums[i] + dp[i - 2], dp[i - 1]);
```

`dp[i]` means "the most that can be taken from houses `0..i`" — not "the most,
given that house `i` was robbed". That distinction is what allows `dp[i-1]` to be
reused directly as the skip case.

Base cases:

```java
dp[0] = nums[0];
dp[1] = Math.max(nums[1], nums[0]);   // best of the first two, since both can't be taken
```

Tracing `[2,7,9,3,1]`:

| i | nums[i] | rob: nums[i] + dp[i-2] | skip: dp[i-1] | dp[i] |
|---|---------|------------------------|---------------|-------|
| 0 | 2 | — | — | 2 |
| 1 | 7 | — | — | 7 |
| 2 | 9 | 9 + 2 = **11** | 7 | 11 |
| 3 | 3 | 3 + 7 = 10 | **11** | 11 |
| 4 | 1 | 1 + 11 = **12** | 11 | 12 |

Answer: 12, from houses 0, 2 and 4.

## Complexity

- **Time:** O(n) — one pass, constant work per house
- **Space:** O(n) for the `dp` array

## The O(1)-space version

Each step reads only `dp[i-1]` and `dp[i-2]`, so the array is unnecessary — two
variables suffice:

```java
int prev2 = 0, prev1 = 0;
for (int v : nums) {
    int cur = Math.max(prev1, prev2 + v);
    prev2 = prev1;
    prev1 = cur;
}
return prev1;
```

O(1) space, and the base cases disappear — starting both at `0` makes the first
two iterations produce the right values on their own, so the `n == 1` and
`n == 2` special cases are no longer needed.

Recognising "this DP only looks back a fixed distance, so the table can collapse
to a few variables" is a standard optimisation and worth having as a reflex. Same
shape as the in-place trick in
[119. Pascal's Triangle II](../0119-pascals-triangle-ii/README.md).

## Note on the null check

```java
int n = nums.length;
if (nums == null || n == 0) return 0;
```

The null test can never fire — `nums.length` on the line above already
dereferences the array, so a null input throws `NullPointerException` before the
guard is reached. Reordering the two lines would make it meaningful.

It doesn't matter here: the constraints guarantee `1 <= nums.length <= 100` and a
non-null array. Kept as submitted, but it's a classic ordering mistake and worth
recognising.

## Verification

**87,380 arrays** — every array of length 1 to 8 with values 0 to 3 — checked
against **brute-force enumeration of all non-adjacent subsets**. That matters
more than matching another DP: brute force proves the recurrence finds the true
optimum rather than merely a plausible answer. Plus 20,000 random arrays up to
length 20 also against brute force, and 200,000 up to the 100-house limit against
the O(1)-space formulation.
