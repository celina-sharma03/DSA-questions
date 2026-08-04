# DSA Questions

My daily LeetCode practice — solutions written in Java, one folder per problem.

Each folder contains the solution plus a short write-up of the approach and its
time/space complexity.

## Progress

**Total solved:** 4

| # | Problem | Difficulty | Topics | Solution |
|---|---------|------------|--------|----------|
| 1 | [Two Sum](https://leetcode.com/problems/two-sum/) | Easy | Array, Hash Table | [Java](./0001-two-sum/Solution.java) |
| 9 | [Palindrome Number](https://leetcode.com/problems/palindrome-number/) | Easy | Math, Two Pointers | [Java](./0009-palindrome-number/Solution.java) |
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
