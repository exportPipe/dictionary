# dictionary
Completed 3-way dictionary

Implemented a dictionary in three ways:
1. Sorted array: entries are found in a sorted array (a-z)
2. Hash map: entries are found with a generated hash key
3. Binary tree: entries are found in a binary search tree

How the API works:
possible console input:
1. "create" - create dictionary (std: sorted array, if you want you can use "create HashDict" or "create BinaryTreeDict"
2. "read" - open file chooser, you can select the text doc (each line must contain the word with translation)
    - you can use dtengl.txt for testing purposes (german - english)
3. "s _theword_" - outputs the translation for the searched word
4. "i _theword_ _thetranslation_" - insert a new word with its translation
5. "r _theword_" - removes the entry
6. "exit" - end process
