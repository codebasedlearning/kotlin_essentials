[© 2025, A.Voß, FH Aachen, codebasedlearning.dev](mailto:info@codebasedlearning.dev)

AI (ChatGPT 5) provides many solutions

```
    // straightforward
    (0..n).filter { it % 2 == 0 }
    
    // better
    if (n < 0) emptyList() else (0..n step 2).toList()
    
    // lazy
    if (n < 0) emptySequence() else generateSequence(0) { it + 2 }.take(n / 2 + 1)
    
    // instead of modulo
    private fun Int.isEvenFancy(): Boolean = (this and 1) == 0 // works for negatives too
```

Final AI comments

```
- Replace the loop with step 2.
- Remove side-effect logging from the pure function; add a progress callback if needed.
- Fix evenness for negatives (and 1 == 0).
- Pre-size the list for O(1) growth.
Future-you will thank present-you—and the CPU will send a fruit basket. 🍏
```
