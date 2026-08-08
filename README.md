# DSA Questions

My daily LeetCode practice — solutions written in Java, one folder per problem.

Each folder contains the solution plus a short write-up of the approach and its
time/space complexity.

## Progress

**Total solved:** 8

| # | Problem | Difficulty | Topics | Solution |
|---|---------|------------|--------|----------|
| 1 | [Two Sum](https://leetcode.com/problems/two-sum/) | Easy | Array, Hash Table | [Java](./0001-two-sum/Solution.java) |
| 9 | [Palindrome Number](https://leetcode.com/problems/palindrome-number/) | Easy | Math, Two Pointers | [Java](./0009-palindrome-number/Solution.java) |
| 12 | [Integer to Roman](https://leetcode.com/problems/integer-to-roman/) | Medium | Hash Table, Math, String | [Java](./0012-integer-to-roman/Solution.java) |
| 13 | [Roman to Integer](https://leetcode.com/problems/roman-to-integer/) | Easy | Hash Table, Math, String | [Java](./0013-roman-to-integer/Solution.java) |
| 20 | [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) | Easy | String, Stack | [Java](./0020-valid-parentheses/Solution.java) |
| 26 | [Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) | Easy | Array, Two Pointers | [Java](./0026-remove-duplicates-from-sorted-array/Solution.java) |
| 217 | [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/) | Easy | Array, Hash Table, Sorting | [Java](./0217-contains-duplicate/Solution.java) |
| 242 | [Valid Anagram](https://leetcode.com/problems/valid-anagram/) | Easy | Hash Table, String, Sorting | [Java](./0242-valid-anagram/Solution.java) |

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
