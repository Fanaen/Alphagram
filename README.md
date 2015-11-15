# Alphagram

Java Library/Desktop Application for building anagrams using alphagrams. Two sentences are anagrams only if their alphagrams are equal.

```
A gentleman = Elegant man = aaeeglmnnt
```

If we want to find an anagram for `A gentleman` with `Man` inside, we could use this tool to compute the resulting alphagram:
```
A gentleman - Man = aeeglnt
```
The last step is to find a word having `aeeglnt` as alphagram, like `Elegant`. The search and combination tools built in Alphagram help to find possibilities.

## Features
- Convert words and sentences in alphagrams
- Subtract alphagrams
- Save alphagram in variable to save time
- Generate alphagram dictionaries (named index in this tool) from word lists
- Search in index for words with a given alphagram
- Build combinations of alphagram found by the previous feature

Note: only French dictionaries had been tested. Fix may be required for other languages.

## Download

- [Alphagram v0.1.0](http://storage.fanaen.fr/Projects/2015.Alphagrams/Alphagram-v0.1.0.zip)

## Dependencies

- [Hunspell2WordList](https://github.com/Fanaen/Hunspell2WordList)

## Contributors

- Elouan Poupard-Cosquer aka Fanaen ([contact@fanaen.fr](mailto:contact@fanaen.fr))
