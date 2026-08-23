# DSA Questions

My daily LeetCode practice — solutions written in Java, one folder per problem.

Each folder contains the solution plus a short write-up of the approach and its
time/space complexity.

## Progress

**Total solved:** 23

| # | Problem | Difficulty | Topics | Solution |
|---|---------|------------|--------|----------|
| 1 | [Two Sum](https://leetcode.com/problems/two-sum/) | Easy | Array, Hash Table | [Java](./0001-two-sum/Solution.java) |
| 7 | [Reverse Integer](https://leetcode.com/problems/reverse-integer/) | Medium | Math | [Java](./0007-reverse-integer/Solution.java) |
| 9 | [Palindrome Number](https://leetcode.com/problems/palindrome-number/) | Easy | Math, Two Pointers | [Java](./0009-palindrome-number/Solution.java) |
| 12 | [Integer to Roman](https://leetcode.com/problems/integer-to-roman/) | Medium | Hash Table, Math, String | [Java](./0012-integer-to-roman/Solution.java) |
| 13 | [Roman to Integer](https://leetcode.com/problems/roman-to-integer/) | Easy | Hash Table, Math, String | [Java](./0013-roman-to-integer/Solution.java) |
| 20 | [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) | Easy | String, Stack | [Java](./0020-valid-parentheses/Solution.java) |
| 22 | [Generate Parentheses](https://leetcode.com/problems/generate-parentheses/) | Medium | String, Backtracking, Dynamic Programming | [Java](./0022-generate-parentheses/Solution.java) |
| 26 | [Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) | Easy | Array, Two Pointers | [Java](./0026-remove-duplicates-from-sorted-array/Solution.java) |
| 33 | [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) | Medium | Array, Binary Search | [Java](./0033-search-in-rotated-sorted-array/Solution.java) |
| 35 | [Search Insert Position](https://leetcode.com/problems/search-insert-position/) | Easy | Array, Binary Search | [Java](./0035-search-insert-position/Solution.java) |
| 38 | [Count and Say](https://leetcode.com/problems/count-and-say/) | Medium | String | [Java](./0038-count-and-say/Solution.java) |
| 67 | [Add Binary](https://leetcode.com/problems/add-binary/) | Easy | Math, String, Bit Manipulation, Simulation | [Java](./0067-add-binary/Solution.java) |
| 75 | [Sort Colors](https://leetcode.com/problems/sort-colors/) | Medium | Array, Two Pointers, Sorting | [Java](./0075-sort-colors/Solution.java) |
| 118 | [Pascal's Triangle](https://leetcode.com/problems/pascals-triangle/) | Easy | Array, Dynamic Programming | [Java](./0118-pascals-triangle/Solution.java) |
| 119 | [Pascal's Triangle II](https://leetcode.com/problems/pascals-triangle-ii/) | Easy | Array, Dynamic Programming | [Java](./0119-pascals-triangle-ii/Solution.java) |
| 136 | [Single Number](https://leetcode.com/problems/single-number/) | Easy | Array, Bit Manipulation | [Java](./0136-single-number/Solution.java) |
| 167 | [Two Sum II - Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) | Medium | Array, Two Pointers, Binary Search | [Java](./0167-two-sum-ii-input-array-is-sorted/Solution.java) |
| 202 | [Happy Number](https://leetcode.com/problems/happy-number/) | Easy | Hash Table, Math, Two Pointers | [Java](./0202-happy-number/Solution.java) |
| 217 | [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/) | Easy | Array, Hash Table, Sorting | [Java](./0217-contains-duplicate/Solution.java) |
| 242 | [Valid Anagram](https://leetcode.com/problems/valid-anagram/) | Easy | Hash Table, String, Sorting | [Java](./0242-valid-anagram/Solution.java) |
| 268 | [Missing Number](https://leetcode.com/problems/missing-number/) | Easy | Array, Hash Table, Math, Bit Manipulation, Sorting | [Java](./0268-missing-number/Solution.java) |
| 290 | [Word Pattern](https://leetcode.com/problems/word-pattern/) | Easy | Hash Table, String | [Java](./0290-word-pattern/Solution.java) |
| 303 | [Range Sum Query - Immutable](https://leetcode.com/problems/range-sum-query-immutable/) | Easy | Array, Design, Prefix Sum | [Java](./0303-range-sum-query-immutable/NumArray.java) |

## Repo layout

```
0001-two-sum/
├── Solution.java   # the solution
└── README.md       # approach + complexity
```

## Workflow

```powershell
.\new-problem.ps1 -Number 217 -Title "Contains Duplicate" -Difficulty Easy -Topics "Array, Hash Table"
# ...solve it, paste your accepted code into Solution.java...
.\push.ps1
```
