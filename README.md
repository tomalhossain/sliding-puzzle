# Heuristic Search

## A program written for the class CS241 that can perform several types of state space searches

*By Steven Molitor, Jonathan Che, and Tomal Hossain*

The program solves a 4x4 sliding puzzle.

**Search Algorithms Implemented**:
1. Breadth First Search
2. Depth First Search
3. Depth First Iterative Deepening
4. A\* Search (uses heuristic)
5. IDA\* Search (uses heuristic)

The heuristic that is used is the sum of the Manhattan distances of each piece of the puzzle from its position in the goal state.

To use this program, you must specify the search algorithm you would like to use, the depth limit (which will be ignored if you are not using either the depth first search algorithm or the depth first iterative deepening algorithm), and the number of steps you would like the program to take before printing an update.  

**How To Specify The Algorithm You Would Like To Use**:

| Algorithm | String to use in terminal command |
| --------- | --------------------------------- |
| Breadth First Search | BFS |
| Depth First Search | DFS |
| Depth First Iterative Deepening | DFID |
| A\* | A |
| IDA\* | IDA |

An example terminal command to use the program would be:
```shell
$ java SearchBase A 5 1
```
The program would use the A\* search algorithm, ignore the depth limit, and print an update after every step.
