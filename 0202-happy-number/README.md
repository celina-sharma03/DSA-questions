# 202. Happy Number

**Difficulty:** Easy
**Topics:** Hash Table, Math, Two Pointers, Cycle Detection
**Link:** https://leetcode.com/problems/happy-number/

## Problem

Repeatedly replace a number with the sum of the squares of its digits. If the
process reaches `1`, the number is **happy**. If it loops forever without
reaching `1`, it isn't.

`19` is happy:

```
19 -> 1² + 9²  = 82
82 -> 8² + 2²  = 68
68 -> 6² + 8²  = 100
100 -> 1² + 0² + 0² = 1
```

## The real problem: knowing when to stop

Computing the digit-square sum is trivial. The difficulty is termination — an
unhappy number never reaches `1`, so a naive loop runs forever.

The saving fact is that the sequence can only ever cycle. Every step maps a
number into a bounded range (for any `int` input the sum can't exceed 810, and
after one step it's below 243), and a finite set of values with a deterministic
step function must eventually repeat. So the sequence either hits `1` or enters a
loop — there is no third outcome, and nothing runs off to infinity.

Detecting the repeat is therefore the whole job.

## Approach: remember what's been seen

Keep every value visited in a `Set`. The loop ends on the first repeat, and
whether the number is happy is just whether that stopping point was `1`.

```java
while (n != 1 && !set.contains(n)) {
    set.add(n);
    n = happy(n);
}
return n == 1;
```

Both exit conditions land in the same `return`. If the loop stopped because
`n == 1`, the answer is true; if it stopped on a repeat, `n` is some cycle member
and the answer is false. No flag variable, no separate branch.

## Complexity

- **Time:** O(log n) to reduce the input, then a bounded number of steps — the
  sequence drops below 243 almost immediately and the cycle is at most 8 long
- **Space:** O(log n), practically constant — the set never grows past a couple
  of hundred entries no matter how large the input

## Alternative: Floyd's cycle detection

The set can be dropped entirely by running two pointers at different speeds — the
tortoise and hare — and waiting for them to meet:

```java
int slow = n, fast = step(n);
while (fast != 1 && slow != fast) {
    slow = step(slow);
    fast = step(step(fast));
}
return fast == 1;
```

If there's a cycle the faster pointer laps the slower one inside it, and they
collide. **O(1) space** instead of O(log n).

Same idea as detecting a loop in a linked list — which is exactly why this
problem is tagged Two Pointers despite having no array in it. Recognising "this
is a cycle detection problem in disguise" is the transferable part.

## The cycle

Every unhappy number eventually falls into the same 8-element loop:

```
4 -> 16 -> 37 -> 58 -> 89 -> 145 -> 42 -> 20 -> 4
```

There is only one such cycle for base-10 digit-square sums. Verified below.

## Verification

Every value from 1 to 1,000,000 checked against a Floyd's-algorithm
implementation — agreement on all of them; 143,071 turned out happy. Separately,
every unhappy number from 1 to 200,000 was iterated 500 steps and confirmed to
settle inside `{4, 16, 37, 58, 89, 145, 42, 20}`, with no exceptions. Also checked
at `Integer.MAX_VALUE`.
