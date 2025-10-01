[© 2025, A.Voß, FH Aachen, codebasedlearning.dev](mailto:info@codebasedlearning.dev)

# Tasks – Unit0x01

---

### 👉 Task 'Halos'

- Work on the task in the prepared file `Halos.kt` (but don't check it in) and 
- start to run the snippet.
- Read a text `line` from the console and convert it to an integer `n`.
- Output `line` and `n` using 'string interpolation.'
- Repeat this until you get an empty text or a negative number.
- Protect the integer conversion with an appropriate exception handling and write the exception to the console.
- Make the code as concise but meaningful as possible (e.g., use the `xOrNull` functions).

---

### 👉 Task 'Pummels'

- Work on the file `Pummels.kt` (but don't check it in).
- Read a number `n` from the console and 
- compute the nth Fibonacci number ([Fibonacci](https://de.wikipedia.org/wiki/Fibonacci-Folge)) iteratively
- and recursively.
- Add a caching system to the recursive calculation. More precisely, a map containing already calculated
  Fibonacci numbers.

---

### 👉 Task 'Rubies'

This task is multipart.
First, implement a solution for Parts I and II and then, as Part III, discuss the problems with the proposed solutions.

Part I
- Work on the file `Rubies.kt`.
- Implement a (base) class called `Figure`.
  - The constructor is given a description, which you store in
  - a corresponding read and write property `description`.
  - Implement a read-only property `area` which returns the area of the figure.
    It is null in this base class, but should be overridden.
  - Finally, give the class a nice `toString` method for appropriate output.
- Derive a class `Rectangle` from `Figure`.
  - In addition to the `description`, the constructor gets `width` and `length`,
  - for which you also create two properties.
  - Customise the output and do not duplicate code, e.g., use the output from the base class.

Part II
- Also derive a class `Square` and decide whether to derive from `Rectangle` or from `Figure`.
  - This constructor only gets a `width` besides the `description`.
  - Again, you customize the output.

Part III
- Why does the solution in `_v1a` not work? `_v1b` is a correct version.
- What are the pros and cons of the `Square` class in `_v2a`?---

---

### 👉 Task 'Shinies'

- Work on the `Shinies.kt` file.
- Write a generic `compress` extension for the list class. It should work like the `joinToString` extension, 
  but unlike that it only gets the delimiter and the transform function as a trailing lambda.
  Prefix and postfix are given by `[` and `]`.
- The `solution` code should work. Here `require` behaves like an `assert` function.

---

### 👉 'AoC'

> The following part contains tasks inspired by those from 'Advent of Code 2023'.
For scientific purposes, I have used only the basic concept and included
self-generated data specific to the task to prevent any legal issues.
However, the data structure remains the same, so you can use your solution
to solve the original puzzle.

> For more information and to enjoy the engaging story, please visit [here](https://adventofcode.com/2023) and
register for next Christmas!

The original 25 tasks always consist of two parts, except for the final one if you have completed all previous tasks.
The first part can usually be completed fast with a straightforward approach, prioritising speed over beauty - also
known as a 'hack'.
The second part often involves a significant increase in complexity as the problem or solution space explodes.
In many cases, it is essential to improve your solution concept, for example by developing or using a clever approach
to reduce the order or number of algorithmic iterations.
Both tasks should be completed as quickly as possible to earn points and improve the ranking on the leaderboard.

Here, we do not have a leaderboard but solving as fast as you can is your first goal.
The second goal is to improve the approach. Therefore, all tasks now consist of the following sections:
- Complete the first part (I) as quickly as possible.
- Same for the second part (II). It is explicitly allowed to simply 'hack' your solution.
- Refactor your solution (III).

### 👉 'Day 01' – Task 'Lemones'

The original story can be found here [Day 01 Trebuchet?!](https://adventofcode.com/2023/day/1).
Btw: The leaderboard winner solves Parts I _and_ II in 00:02:24, and the top 14 of Part I in under 1 min.

- Work on the `Lemones.kt` file.

#### Part I

A text document contains a hidden value on each line that needs to be retrieved. To find this value, combine
the first and last digits of each line to form a two-digit number.
In contrast to the original task, we will refrain from reading the text files and will simply work on the sample data.

For example:
```
3POeQx4
h5g6srvOA70N
f8BMx7BgE8y
rLHp3jcN
```
In this example, the hidden values of these four lines are 34, 50, 88, and 33, which add up to a total of 205.
Find the sum of all hidden values programmatically.

#### Part II

The calculation appears to be incorrect, some digits are spelled out with letters. For example:
```
sixwF1Uone
9xGHzn3one
8eightwoUP
oneightwo2
3sevenine7
Hho6sixQYqWR
```
These values are 61, 91, 82, 12, 37 and 66, in total 349.
Again, find the sum of all hidden 'new' values programmatically.

#### Part III

Refactor your code.

---

End of `Tasks – Unit 0x01`

## Name Source

https://www.fantasynamegenerators.com/candy-names.php
