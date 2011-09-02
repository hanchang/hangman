Hangman
=======

Build
-----

Run
---

Background
----------

I basically figured that the best bang for buck would come from analyzing the dictionary for character frequency.


Algorithm
---------

### Preprocessing

The dictionary file is read line by line and parsed into strings. These strings are inserted into a map that hashes the number of characters to a set of strings of that length.
    Dictionary : { string_length => set(strings of string_length) }

### Strategy Execution

Given a mystery word with n characters, retrieve the set of strings in the dictionary that are of length n into a set P (which denotes the set of possible words the mystery word could be).
    PossibleWordsSet : { Set of words from dictionary that could possibly be the mystery word given the current information }

Construct a character frequency analysis of all the words in P, calling this table F where F[x] => the number of times the character x appears in all the words in P.
    FrequencyTable : { character => number of times character occurs in all strings of set P }

Choose the character in F that appears the most often as the next guess; if there is a tie, just pick randomly.
Update F by removing the letter since it can never be guessed again.

On the next guess, check if the letter previously guessed was in the mystery word or not.

If the previous letter guessed exists in the mystery word, construct a pattern of the mystery word with all the known letters in the mystery word.
Update the set of words in P to only contain the words which matches the pattern.
Recalculate the character frequency analysis of the updated version of P.
Find the most frequent character and choose it as the next guess.

If the previous letter guessed did not exist in the mystery word, remove all words in P containing that letter.
Recalculate the character frequency analysis of the updated version of P.
Find the most frequent character and choose it as the next guess.

When there is only one word remaining in P, guess that word.

Algorithms Used
---------------

I had originally planned on using regular expressions to handle the pattern matching but feared that it would be too slow.
After doing a quick Google search I found Johann Burkard's StringSearch library (http://johannburkard.de/software/stringsearch/)
which contains a Backward Nondeterministic Dawg (Directed Acyclic Word Graph) Matching algorithm, shortened as BNDM.
There is a version containing wildcards which perfectly fits the matching of the mystery word status against the set of possible words.

Runtime Analysis
----------------
