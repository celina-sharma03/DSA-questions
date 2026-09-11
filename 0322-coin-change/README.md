# 322. Coin Change

**Difficulty:** Medium
**Topics:** Array, Dynamic Programming
**Link:** https://leetcode.com/problems/coin-change/

## Problem

Given an array of coin denominations and an integer `amount`, return the **fewest number of coins** needed to make that amount. Each coin can be used unlimited times.

```text
coins = [1, 2, 5], amount = 11

5 + 5 + 1 = 11
Answer = 3
```

If the amount cannot be formed, return `-1`.

## Approach: Dynamic Programming

Define:

```text
dp[i] = minimum number of coins needed to make amount i
```

Start with:

```text
dp[0] = 0
```

For every amount `i`, try every coin. If `coin <= i`, then using that coin means we first need to make `i - coin`:

```text
dp[i] = min(dp[i], dp[i - coin] + 1)
```

The `+1` represents the current coin.

Initialize every `dp[i]` with `amount + 1`, which acts as an impossible value. If `dp[amount]` is still `amount + 1` at the end, return `-1`.

## Example

For `coins = [1, 2, 5]` and `amount = 5`:

```text
dp[0] = 0
dp[1] = 1
dp[2] = 1
dp[3] = 2
dp[4] = 2
dp[5] = 1
```

So the answer is `1` because we can use a single `5` coin.

## Why Not Greedy?

Always choosing the largest coin does not guarantee the minimum.

```text
coins = [1, 3, 4], amount = 6

Greedy: 4 + 1 + 1 = 3 coins
Optimal: 3 + 3 = 2 coins
```

Therefore, we need DP to consider all possible choices.

## Java Solution

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);

        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
}
```

## Complexity

|    | Time                     | Space     |
| -- | ------------------------ | --------- |
| DP | O(amount × coins.length) | O(amount) |

## Key Takeaway

Instead of trying to find the answer directly for `amount`, solve every smaller amount first and reuse those results.

The core transition is:

```text
dp[i] = min(dp[i], dp[i - coin] + 1)
```
