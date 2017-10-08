import java.util.Vector;

public class SearchBase {

	private static int PRINT_HOW_OFTEN = 1;
	public static boolean debug = false;
	public int heuristicFinal;

	public static void main(String[] args)  {

		String search_type = args[0];
		System.out.println("Search Type: " + search_type);

		int depth_limit = Integer.parseInt(args[1]);
		System.out.println("Depth Limit: " + depth_limit);

		PRINT_HOW_OFTEN = Integer.parseInt(args[2]);
		System.out.println("Print output every " + PRINT_HOW_OFTEN + " lines");

		new SearchBase().process(search_type, depth_limit);

	}

	private void process(String search_type, int depth_limit) {

		String start = "2D34/18E5/xC96/BAF7";
		String goal = "1234/CDE5/BxF6/A987";
		int size = 4;

		int IDAStar_f_bound = 0;

		CarryBoolean p = new CarryBoolean();

		switch(search_type){
			case "BFS": breadthFirstSearch(p, new EightPuzzle(start, goal, size));
						break;
			case "DFS": depthFirstSearch(p, depth_limit, new EightPuzzle(start, goal, size));
						break;
			case "DFID": dFID(p, depth_limit, new EightPuzzle(start, goal, size));
						 break;
			case "A": aStarSearch(p, new EightPuzzle(start, goal, size));
					  break;
			case "IDA": IDAStarSearch(p, IDAStar_f_bound, new EightPuzzle (start, goal, size), new VectorPQ());
			default: break;
		}

		System.out.println("The goal was found: "+p.getValue());
		switch(search_type){
			case "BFS": break;
			case "DFS": break;
			case "DFID": break;
			case "A": System.out.println("The final heuristic value is: "+heuristicFinal);
				break;
			case "IDA": System.out.println("The final heuristic value is: "+heuristicFinal);
			  break;
			default: break;
		}

	}

	public int search(CarryBoolean done, int limit, StateSpace ssp, SearchList open) {

		int heuristic = 0;
		int size = ((EightPuzzle)ssp).getSize();

		String start = ssp.getStart();
		String goal = ssp.getGoal();

		char[][] manhattanFinderStart = new char[size][size];
		char[][] manhattanFinderGoal = new char[size][size];

		// The following two lines take the start and goal states and put them into the 2D arrays created above
		manhattanFinderStart = cleanArray(start, manhattanFinderStart, size);
		manhattanFinderGoal = cleanArray(goal, manhattanFinderGoal, size);

		// The heuristic will be the sum of the manhattan distances of each number in the puzzle from its goal state
		heuristic = calcHeuristic(manhattanFinderStart, manhattanFinderGoal); // calculates the initial heuristic
		System.out.println("Initial heuristic is: "+heuristic);
		open.add(new State(ssp.getStart(), heuristic));

		int count = 0;

		while (!done.getValue()) {

			if (open.size()==0) {
				System.out.println("Open list empty at " + count);
				break;
			}

			State current =  open.remove();

			count++;
			if (count % PRINT_HOW_OFTEN == 0) {
				System.out.println("Search limit " + limit + " at Node #" +
						count + " Open list length: " + open.size() + " Current Node: " +
						current.getRep() + "  Depth: " + current.getDepth() + " Heuristic: "
						+ current.getHeuristic());
			}

			if (ssp.isGoal(current.getRep())) {
				done.set(true);
				System.out.println("Count is: " + count + " Found goal at "
						+ current.getRep() + " at depth " + current.getDepth());
				current.printPath();
				heuristicFinal = current.getHeuristic();
				break;
			}

			if (current.getDepth() < limit) {
				Vector<String> kids = ssp.getKids(current.getRep());
				for (String v : kids) {
					if (!current.getPath().contains(v)) { // Calculate heuristic change for each kid here before adding to the open list
						char[][] current2D = new char[size][size]; // current state will be stored in this 2D array
						char[][] kid2D = new char[size][size]; // new kid state will be stored in this 2D array
						current2D = cleanArray(current.getRep(), current2D, size);
						kid2D = cleanArray(v, kid2D, size);
						int newHeuristic = updateHeuristic(current2D, kid2D, manhattanFinderGoal, current.getHeuristic());
						open.add(new State(current, v, newHeuristic));
					}
				}
			}
		}
		return limit+1;
	}

	public int breadthFirstSearch(CarryBoolean done, StateSpace ssp) {

		return search(done, Integer.MAX_VALUE, ssp, new Queue());
	}

	public int depthFirstSearch(CarryBoolean done, int limit, StateSpace ssp) {

		return search(done,limit,ssp,new VectorasList());
	}

	public void dFID(CarryBoolean done, int limit, StateSpace ssp){

		for(int i = 0; i <= limit; i++){
			if(!done.getValue()){
				System.out.println("\n" + "Starting Search with limit " + i + "\n");
				depthFirstSearch(done, i, ssp);
			}
		}
	}

	public int aStarSearch(CarryBoolean done, StateSpace ssp) {

		return search(done, Integer.MAX_VALUE,ssp,new PQasList());
	}

	public void IDAStarSearch (CarryBoolean done, int f_bound, StateSpace ssp, VectorPQ open) {

		System.out.println("Starting IDA* search with f-bound: " + f_bound);

		int heuristic = 0;
		int size = ((EightPuzzle)ssp).getSize();

		String start = ssp.getStart();
		String goal = ssp.getGoal();

		char[][] manhattanFinderStart = new char[size][size];
		char[][] manhattanFinderGoal = new char[size][size];

		// The following two lines take the start and goal states and put them into the 2D arrays created above
		manhattanFinderStart = cleanArray(start, manhattanFinderStart, size);
		manhattanFinderGoal = cleanArray(goal, manhattanFinderGoal, size);

		// The heuristic will be the sum of the manhattan distances of each number in the puzzle from its goal state
		heuristic = calcHeuristic(manhattanFinderStart, manhattanFinderGoal); // calculates the initial heuristic
		System.out.println("Initial heuristic is: "+heuristic);

		State initialState = new State(ssp.getStart(), heuristic);
		PQasList initialPQ = new PQasList();
		// Rather than adding a children states individually directly into a java vector, all children of a given
		// state are added to their own unique PQasList, which is in turn added a java vector (in this case, this open
		// list is the class VectorPQ)
		initialPQ.add(initialState);
		open.add(initialPQ);

		int count = 0;
		// Rejects serves as a java priority queue of all the nodes that exceed the f-bound of a given iteration of the IDA* search.
		PQasList rejects = new PQasList();

		while (!done.getValue()) {
			if (open.size()==0 ) {
				if (rejects.size() != 0) {
					State rejectsMin = rejects.remove();
					IDAStarSearch (done, rejectsMin.getF(), ssp, open);
					return;
				}
				else {
					System.out.println("open list empty at "+count);
					return;
				}
			}

			State current = open.remove();

			count++;
			if (count % PRINT_HOW_OFTEN == 0) {
				System.out.println("F-bound "+f_bound+" at Node # "+
						count+" Open list length:"+open.size()+" Current Node "+
						current.getRep()+"  Depth: "+current.getDepth()+"  Heuristic: "+ current.getHeuristic() +
						" Reject list length: " + rejects.size());
			}
			// This prevents states with f-values greater than the current f-bound from being expanded, i.e. their
			// children will not be added to the open list as a new, distinct PQasList.
			if (current.getF() > f_bound) {
				rejects.add(current);
			}

			else {

				if (ssp.isGoal(current.getRep())) {
					done.set(true);
					System.out.println("Count is: "+count+" found goal at "+current.getRep()+" at depth "+current.getDepth());
					current.printPath();
					heuristicFinal = current.getHeuristic();
					return;
				}
				// All children of a given state are added to a PQasList.
				Vector<String> kids = ssp.getKids(current.getRep());
				PQasList kidsPQ = new PQasList();

				for (String v : kids) {
					if (!current.getPath().contains(v)) { // Calculate heuristic change for each kid here before adding to the open list
						char[][] current2D = new char[size][size]; // current state will be stored in this 2D array
						char[][] kid2D = new char[size][size]; // new kid state will be stored in this 2D array
						current2D = cleanArray(current.getRep(), current2D, size);
						kid2D = cleanArray(v, kid2D, size);
						int newHeuristic = updateHeuristic(current2D, kid2D, manhattanFinderGoal, current.getHeuristic());
						kidsPQ.add(new State(current, v, newHeuristic));
					}
				}
				// The generated priority queue of children is added to the open list (type VectorPQ)
				open.add(kidsPQ);
			}
		}
	}

	public char[][] cleanArray(String status, char[][] target, int size) { // converts start and goal state strings into 2D arrays that will be used to calculate the heuristic

		String cleanStatus = "";

		String[] tempStatus = status.split("/");
		for (int i=0; i<tempStatus.length; i++) {
			cleanStatus += tempStatus[i];
		}

		int count = 0;
		for (int j = 0; j < (size); j++){
			for (int k = 0; k < (size); k++) {
				target[j][k] = cleanStatus.charAt(count);
				count++;
			}
		}

		return target;
	}

	public int calcHeuristic(char[][] start, char[][] goal) {
		int heuristic = 0;
		// First we see how many of the outer level arrays we must traverse to get a number from its start state to goal state.
		// For every one of these outer array traversals (equivalent to a vertical move in the puzzle), we add 1 to the heuristic.
		// Then we also check the index of a number in the inner array in each state (equivalent to a horizontal move in the puzzle).
		// The value of this difference will also be added to the heuristic.
		// The sum of these two numbers gives us the manhattan distance of a particular number in the puzzle.
		outer: for (int i = 0; i < start.length; i++) { // Iterating through outer arrays of start state
			for (int j = 0; j < start.length; j++) { // Iterating through indices of each outer array of start state
				if (start[i][j] == new Character('x')) {  // making sure we don't treat the x's as numbers
					j++;
				}
				if (j == start.length) {
					break outer;
				}
				boolean found = false;  // will break us out of while loop once we have calculated the heuristic of a number in the puzzle
				int k = 0; // first index of goal 2D array
				int l = 0; // second index of goal 2D array
				while (found == false) {
					if (start[i][j] == goal[k][l]) {  // True if we have found a number in both the start and goal states
						heuristic += Math.abs(k-i)+Math.abs(l-j); // adding the manhattan dist. of this number to the heuristic value
						found = true; // break out of the while loop, then we move on to the next number in the start array
					}
					if (found != true) l++; // increments index of inner goal array
					if (l >= start.length) { // iterating through goal array without nested for loops
						l = 0;
						k++; // increments index of outer goal array (upon reaching the end of the current inner goal array)
					}
					if (k >= start.length) {
						System.err.println("Error determining heuristic: reached end of goal state without finding item from start state");
			      System.exit(1);
					}
				}
			}
		}
		return heuristic;
	}


	public int calcManhattan(int x, int y, char moved, char[][] goal) { // calculates the Manhattan distance of a given char, given its current coordinates
		// We start by finding the goal coordinates of the moved character
		int movedXgoal = 0;
		int movedYgoal = 0;
		boolean breaking = false;
		for (int i = 0; i < goal.length; i++) {
			for (int j = 0; j < goal.length; j++) {
				if (goal[i][j] == moved) {
					movedXgoal = i;
					movedYgoal = j;
					breaking = true;
				}
				if (breaking == true) break;
			}
			if (breaking == true) break;
		}

		// We have the goal coordinates now.  Now we just calculate the manhattan distance and return it
		int manhattan = Math.abs(movedXgoal-x)+Math.abs(movedYgoal-y);
		return manhattan;
	}


	// We will update the heuristic by finding the indices of x in the current state, then checking what number is in those incidies in the kid state.
	// We then find the indices of that number in the current state.  Now we have the indices of the number that is moved, both before and after the move.
	// We calculate the manhattan distance of the number from the goal in each state.  If it is greater in the kid state, heuristic++.  Else, heuristic--.
	public int updateHeuristic(char[][] current, char[][] kid, char[][] goal, int heuristic) {
		int xXcurrent = 0; // will hold x coordinate of x in current state
		int xYcurrent = 0; // will hold y coordinate of x in current state
		boolean breaking = false;
		for (int i = 0; i < current.length; i++) { // Iterating through outer arrays of current state
			for (int j = 0; j < current.length; j++) { // Iterating through indices of each outer array of current state
				if (current[i][j] == new Character('x')) {  // looking for x
					xXcurrent = i;
					xYcurrent = j;
					breaking = true;
				}
				if (breaking == true) break;
			}
			if (breaking == true) break;
		}

		// We have the indices of x in the current state now.  Next, we find what number is in that spot in the kid state:
		char moved = kid[xXcurrent][xYcurrent];

		// Now we want to find this number's indices in the current state
		int movedXcurrent = 0;
		int movedYcurrent = 0;

		// There are only 4 places this number could be
		if (xXcurrent-1 >= 0 && current[xXcurrent-1][xYcurrent] == moved) {
			movedXcurrent = xXcurrent-1;
			movedYcurrent = xYcurrent;
		} else if (xYcurrent-1 >= 0 && current[xXcurrent][xYcurrent-1] == moved) {
			movedXcurrent = xXcurrent;
			movedYcurrent = xYcurrent-1;
		} else if (xXcurrent+1 < current.length && current[xXcurrent+1][xYcurrent] == moved) {
			movedXcurrent = xXcurrent+1;
			movedYcurrent = xYcurrent;
		} else {
			movedXcurrent = xXcurrent;
			movedYcurrent = xYcurrent+1;
		}

		// Now that we have the indices of the moved number before and after the move, we want to compare the manhattan distance of the number
		// 	from its spot in the goal state for both the current and kid states.
		int movedManhattanCurrent = calcManhattan(movedXcurrent, movedYcurrent, moved, goal);
		int movedManhattanKid = calcManhattan(xXcurrent, xYcurrent, moved, goal);
		if (movedManhattanKid > movedManhattanCurrent) {
			heuristic++;
		} else {
			heuristic--;
		}

		return heuristic;
	}


}
