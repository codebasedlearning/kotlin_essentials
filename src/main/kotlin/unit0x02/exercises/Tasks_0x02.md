[© 2024, A.Voß, FH Aachen, codebasedlearning.dev](mailto:kotlin@codebasedlearning.dev)

# Tasks – Unit0x02

---


### 👉 Task 'Jumpers'

- Work on the task in the prepared file `Jumpers.kt` (but don't check it in).
- Implement 'fibonacci' so that it generates an infinite sequence of Fibonacci numbers, 
  starting from zero and one, using Kotlin's `sequence` and `yield` functions.
- Check your code by uncommenting the print line in `main`.


### 👉 Task 'Ribbles'

- Work on the task in the prepared file `Ribbles.kt` (but don't check it in).
- Implement a _private suspending function_ `downloadFiles` that simulates downloading multiple files concurrently. 
- Each file takes a certain amount of time to download (simulated by `delay`).
  The function is given an input list with these download times (see `main`).
- Provide a message when each download starts and ends. The output may look like this:
```
1 | start download
a | download file no 1 started
a | download file no 2 started
a | download file no 3 started
b | download file no 2 completed after 500ms
b | download file no 1 completed after 1000ms
b | download file no 3 completed after 1300ms
2 | overall download time: 1452
```
- Each file download should be initiated _concurrently_ using the `IO` dispatcher.
- Use (at least) Kotlin's `runBlocking` and `launch` functions.
- Check your code by completing the lines in `main`.


### 👉 Task 'Magmas'
- 
- Work on the task in the prepared file `Magmas.kt` (but don't check it in).
- Complete the function `processNumbers` (and `main`) that generates a sequence of integers from 1 to 100, 
  filters out the odd numbers, squares the remaining numbers, takes the first five of them and prints them out.
  The output may look like this:
```
1 | start processing
a | value 4
a | value 16
a | value 36
a | value 64
a | value 100
```
- This process should delay for 500 milliseconds before processing each number. 
- Use Kotlin coroutine's `flow` and appropriate operators.
- Check your code by completing the lines in `main`.
- Is this a cold flow or a hot flow?


---

End of `Tasks – Unit 0x02`
