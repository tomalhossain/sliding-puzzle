## Heuristic Search

*Authored by Steven Molitor, Jonathan Che, and Tomal Hossain*

This program investigaes various search techniques explored in the course COSC-241 (Artificial Intelligence) @ Amherst College during Fall 2016. Such algorithms form the backbone of artificial intelligence. Thus, mastering them before moving on to more involved problems is neccessary. The particular problem at hand is that of solving a 4x4 sliding puzzle. As a deterministic, episodic, and fully observable problem, it's relatively feasible to implement the solution to this puzzle. That being said, it is crucial to maintain a history of opened states lest the puzzle becomes increasingly difficult to solve. The heuristic that is used is the sum of the Manhattan distances of each piece of the puzzle from its position in the goal state.

**Search Algorithms Implemented**:
1. Breadth First Search (BFS)
2. Depth First Search (DFS)
3. Depth First Iterative Deepening (DFID)
4. A\* Search (A) - uses heuristic
5. IDA\* Search (IDA) - uses heuristic

To use this program, you must specify three input parameters in the command line:

1. (string) Which one of the five possible search algorithm you wish like to use : BFS/DFS/DFID/A/IDA
2. (int) A depth limit that only applies to DFS or DFID search. In the three other search cases, this parameter is simply ignored.
3. (int) The number of steps the program takes before printing an update.

Here are a couple of sample terminal commands to fire up the program:

```shell
$ java SearchBase A 5 1
```

This command initiates an A* search with a printing:step ratio of 1:1 (the puzzle is printed for every step of the program). The depth limit of 5 is ignored in this case.

```shell
$ java SearchBase DFID 5 2
```

This command initiates a DFID search with printing:step ratio of 1:2 (the puzzle is printed once for every two steps the program takes, i.e. half the number of print statements as the former process). The depth limit of 5 is observed in this case, so the progam will avoid any solutions that require searching deeper than 5 levels.

The start and goal states of the 4x4 puzzle are currently hard-coded into the program as the following:

```java
String start = "2D34/18E5/xC96/BAF7";
String goal = "1234/CDE5/BxF6/A987";
```
