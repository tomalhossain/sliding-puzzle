import java.util.Vector;

public class SearchBase {

	private static int PRINT_HOW_OFTEN = 1;
	public static boolean debug = false;

	public static void main(String[] args)  {

		String search_type = args[0];
		System.out.println("Search Type: " + search_type);

		int depth_limit = Integer.parseInt(args[1]);
		System.out.println("Depth Limit: "+depth_limit);

		PRINT_HOW_OFTEN = Integer.parseInt(args[2]);
		System.out.println("Print output every " + PRINT_HOW_OFTEN + " lines");

		new SearchBase().process(search_type, depth_limit);

	}

	private void process(String search_type, int depth_limit) {

		String start = "123/456/78x";
		String goal = "123/456/x78";
		int size = 3;
		CarryBoolean p = new CarryBoolean();

		switch(search_type){
			case "BFS": breadthFirstSearch(p , depth_limit, new EightPuzzle(start, goal, size));
						break;
			case "DFS": depthFirstSearch(p , depth_limit, new EightPuzzle(start, goal, size));
						break;
			case "DFID": dFID(p , depth_limit, new EightPuzzle(start, goal, size));
						 break;
			case "A": break;
			case "IDA": break;
			default: break;
		}

		System.out.println("The goal was found: "+p.getValue());

	}

	public int search(CarryBoolean done, int limit, StateSpace ssp, SearchList open) {

		System.out.println("Starting search with limit "+ limit);

		int heuristic = 0;
		int slashes = 0;
		String start = ssp.getStart();
		String goal = ssp.getGoal();
		Character slash = new Character('/');
		for (int i = 0; i < start.length(); i++) {
			char c = start.charAt(i);
			if (c == slash) {
				slashes++;
			}
		}
		char[][] manhattanFinderStart = new char[slashes+1][slashes+1];
		char[][] manhattanFinderGoal = new char[slashes+1][slashes+1];

		// The following two lines take the start and goal states and put them into the 2D arrays created above
		manhattanFinderStart = cleanArray(start, manhattanFinderStart, slashes);
		manhattanFinderGoal = cleanArray(goal, manhattanFinderGoal, slashes);

		// The heuristic will be the sum of the manhattan distances of each number in the puzzle from its goal state
		heuristic = calcHeuristic(manhattanFinderStart, manhattanFinderGoal); // calculates the initial heuristic
		System.out.println("Initial heuristic is: "+heuristic);
		open.add(new State(ssp.getStart(), heuristic));

		int count = 0;

		while (!done.getValue()) {

			if (open.size()==0) {
				System.out.println("open list empty at "+count);
				break;
			}

			State current =  open.remove();
			count++;
			if (count % PRINT_HOW_OFTEN == 0) {
				System.out.println("Search limit "+limit+" at Node # "+
						count+" Open list length:"+open.size()+" Current Node "+
						current.getRep()+"  Depth: "+current.getDepth());
			}
			if (ssp.isGoal(current.getRep())) {
				done.set(true);
				System.out.println(count+"> found goal at "+current.getRep()+" at depth"+current.getDepth());
				current.printPath();
				break;
			}

			if (current.getDepth() < limit) {
				Vector<String> kids = ssp.getKids(current.getRep());

				for (String v : kids) {
					if (!current.getPath().contains(v))
						open.add(new State(current,v));
				}
			}
		}
		return limit+1;
	}

	public int breadthFirstSearch(CarryBoolean done, int limit, StateSpace ssp) {

		return search(done, Integer.MAX_VALUE, ssp, new Queue());
	}

	public int depthFirstSearch(CarryBoolean done, int limit, StateSpace ssp) {

		return search(done,limit,ssp,new VectorasList());
	}

	public void dFID(CarryBoolean done, int limit, StateSpace ssp){

		for(int i = 0; i <= limit; i++){
			if(!done.getValue()){
				depthFirstSearch(done, i, ssp);
			}
		}
	}

	public char[][] cleanArray(String status, char[][] target, int slashes) { // converts start and goal state strings into 2D arrays that will be used to calculate the heuristic

		String cleanStatus = "";

		String[] tempStatus = status.split("/");
		for (int i=0; i<tempStatus.length; i++) {
			cleanStatus += tempStatus[i];
		}

		int count = 0;
		for (int j = 0; j < (slashes+1); j++){
			for (int k = 0; k < (slashes+1); k++) {
				target[j][k] = cleanStatus.charAt(count);
				count++;
			}
		}
		
		return target;
	}


	public int bestFirstSearch(CarryBoolean done, int limit, StateSpace ssp) {
		return search(done,limit,ssp,new PQasList());
	}

	public int calcHeuristic(char[][] start, char[][] goal) {
		int heuristic = 0;
		// First we see how many of the outer level arrays we must traverse to get a number from it's start state to goal state.
		// For every one of these outer array traversals (equivalent to a vertical move in the puzzle), we add 1 to the heuristic.
		// Then we also check the index of a number in the inner array in each state (equivalent to a horizontal move in the puzzle).
		// The value of this difference will also be added to the heuristic.
		//The sum of these two numbers gives us the manhattan distance of a particular number in the puzzle.
		outer: for (int i = 0; i < start.length; i++) { // Iterating through outer arrays of start state
			for (int j = 0; j < start.length; j++) { // Iterating through indicies of each outer array of start state
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
					if (l >= 3) { // iterating through goal array without nested for loops
						l = 0;
						k++; // increments index of outer goal array (upon reaching the end of the current inner goal array)
					}
					if (k >= 3) {
						System.err.println("Error determining heuristic: reached end of goal state without finding item from start state");
			      System.exit(1);
					}
				}
			}
		}
		return heuristic;
	}


}
