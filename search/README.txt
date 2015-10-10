Programming Assignment #1 - Heuristic Search
CS241: Artificial Intelligence
2015-10-09

Group Members - 
1. Steven Molitor
2. Jonathan Che
3. Tomal Hossain

This program implements a heuristic that is the sum of the Manhattan distances of each item in the puzzle (from their current state to start state).  The initial heuristic is calculated by adding up all of these Manhattan distances, and then the heuristic of any later state is determined by incrementing or decrementing the current heuristic based on whether or not the item in the puzzle being moved at the current step is getting closer or farther from its goal state.

*Notes*

- The class EightPuzzle was not renamed, but it currently is working for a 15-puzzle input size. In order for it to work for the 