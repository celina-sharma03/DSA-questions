# 290. Word Pattern

**Difficulty:** Easy
**Topics:** Hash Table, String
**Link:** https://leetcode.com/problems/word-pattern/

## Problem

Decide whether a string of words follows a letter pattern — each letter must map
to exactly one word, and each word to exactly one letter.

```
"abba", "dog cat cat dog"   -> true
"abba", "dog cat cat fish"  -> false   (a maps to two different words)
"abba", "dog dog dog dog"   -> false   (a and b both map to "dog")
"aaaa", "dog cat cat dog"   -> false
```

## The trap: the mapping must go both ways

Storing `letter -> word` alone is not enough. That catches `"abba"` against
`"dog cat cat fish"`, where `a` would need two different words — but it happily
accepts `"abba"` against `"dog dog dog dog"`, where `a` and `b` both map to
`"dog"` without either letter contradicting itself.

The requirement is a **bijection**: one-to-one in both directions. Most wrong
answers to this problem check only one.

## Approach

Keep the `letter -> word` map, and guard the reverse direction with
`containsValue` before recording anything new:

```java
if (map.containsValue(word[i]) && !key) {
    return false;   // this word is taken by a different letter
}
if (key && !map.get(ch).equals(word[i])) {
    return false;   // this letter already means something else
}
```

The first check is the reverse direction — the word is already spoken for, and
the letter in hand isn't the one that claimed it. The second is the forward
direction. Together they enforce the bijection.

The length guard up front matters too: `split(" ")` can produce a different
number of words than the pattern has letters, and comparing them first avoids
indexing past the end of the array.

## Complexity

- **Time:** O(n × m) — `containsValue` on a `HashMap` is a **linear scan** of the
  values, unlike `containsKey` which is O(1). Running it once per character makes
  the loop quadratic in the number of distinct entries.
- **Space:** O(k), one entry per distinct letter — at most 26.

Fast enough here: the pattern is capped at 300 characters, and the full-size case
measured at 142 microseconds. But the asymmetry between `containsKey` and
`containsValue` is worth knowing, because it is easy to assume both are O(1).

## The O(n) version: two maps

Keeping a second map in the opposite direction makes the reverse lookup O(1) too:

```java
Map<Character, String> c2w = new HashMap<>();
Map<String, Character> w2c = new HashMap<>();

for (int i = 0; i < words.length; i++) {
    char c = pattern.charAt(i);
    String w = words[i];
    if (c2w.containsKey(c) && !c2w.get(c).equals(w)) return false;
    if (w2c.containsKey(w) && w2c.get(w) != c)       return false;
    c2w.put(c, w);
    w2c.put(w, c);
}
return true;
```

The structure also states the bijection more plainly — one map per direction,
one check each.

This "two maps, one per direction" shape is the standard answer to a family of
problems: [205. Isomorphic Strings](https://leetcode.com/problems/isomorphic-strings/)
is the same question with characters instead of words.

## Verification

Every pattern and word sequence up to length 4 drawn from 3 symbols and 3 words —
7,380 pairs — checked against a two-map implementation, plus 20,000 random pairs
including mismatched lengths. All agreed.
