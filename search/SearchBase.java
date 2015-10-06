import java.util.Scanner;
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
			case "BFS": break;
			case "DFS": depthFirstSearch(p , depth_limit, new EightPuzzle(start, goal, size));
						break;
			case "DFID": dFID(p , depth_limit, new EightPuzzle(start, goal, size));
						 break;
			case "A": break;
			case "IDA": break;
			default: break;
		}
		
		System.out.println("The goal was found: "+p.getValue());

		
		/*
		int NEXT_DEPTH = bestFirstSearch(p, depth_limit, new EightPuzzle(start, goal, size));

		System.out.println("The goal was found: "+p.getValue());
		System.out.println("Suggested next depth is "+NEXT_DEPTH);

		System.out.println("Continue?");
		new Scanner(System.in).next();

		p = new CarryBoolean();
		*/

	}

	public int search(CarryBoolean done, int limit, StateSpace ssp, SearchList open) {

		System.out.println("Starting search with limit "+ limit);

		public int heuristic = 0;
		public int slashes = 0;
		public String start = ssp.getStart();
		public String goal = ssp.getGoal();
		for (char c : start) {
			if (c = "/") {
				slashes++;
			}
		}
		public char[][] manhattanFinderStart = new char[slashes+1][slashes+1];
		public char[][] manhattanFinderGoal = new char[slashes+1][slashes+1];

		// The following two lines take the start and goal states and put them into the 2D arrays created above
		manhattanFinderStart = cleanArray(start, manhattanFinderStart);
		manhattanFinderGoal = cleanArray(goal, manhattanFinderGoal);

		// The heuristic will be the sum of the manhattan distances of each number in the puzzle from its goal state
		heuristic = calcHeuristic(manhattanFinderStart, manhattanFinderGoal); // calculates the initial heuristic

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
						open.add(new State(current,v));
				}
			}
		}
		return limit+1;
	}


	public int depthFirstSearch(CarryBoolean done, int limit, StateSpace ssp) {

		return search(done,limit,ssp,new VectorAsList());
	}
	
	public void dFID(CarryBoolean done, int limit, StateSpace ssp){
		
		for(int i = 0; i <= limit; i++){
			if(!done.getValue()){
				depthFirstSearch(done, i, ssp);
			}
		}
	
	}


	public int bestFirstSearch(CarryBoolean done, int limit, StateSpace ssp) {

		return search(done,limit,ssp,new PQasList());
	}

	public char[][] cleanArray(String status, char[][] target) { // converts start and goal state strings into 2D arrays that will be used to calculate the heuristic

		int j = 0; // first index of 2D array
		int k = 0; // second index of 2D array
		String cleanStatus = "";

		String[] tempStatus = status.split("/");
		for (i=0; i<tempStatus.length; i++) {
			cleanStatus += tempStatus[i];
		}

		int count = 0;
		for (j; j < (slashes+1; j++){
			for (k; k < (slashes+1); k++) {
				target[j][k] = cleanStatus.charAt(count);
				count++;
			}
		}

		return target;
	}

	public int calcHeuristic(char[][] start, char[][] goal) {
		int heuristic = 0;
		// First we see how many of the outer level arrays we must traverse to get a number from it's start state to goal state.
		// For every one of these outer array traversals (equivalent to a vertical move in the puzzle), we add 1 to the heuristic.
		// Then we also check the index of a number in the inner array in each state (equivalent to a horizontal move in the puzzle).
		// The value of this difference will also be added to the heuristic.
		//The sum of these two numbers gives us the manhattan distance of a particular number in the puzzle.
		for (int i = 0; i < start.length-1; i++) { // Iterating through outer arrays of start state
			for (int j = 0; j < start.length-1; j++) { // Iterating through indicies of each outer array of start state
				boolean found = false;  // will break us out of while loop once we have calculated the heuristic of a number in the puzzle
				//int nextArr = 0; // will allow us to iterate through the 2D goal array without using another set of nested for loops
				int k = 0; // first index of goal 2D array
				int l = 0; // second index of goal 2D array
				while (found == false) {
					if (start[i][j] == goal[k][l]) {  // True if we have found a number in both the start and goal states
						heuristic += Math.abs(k-i)+Math.abs(l-j); // adding the manhattan dist. of this number to the heuristic value
						found = true; // break out of the while loop, then we move on to the next number in the start array
					}
					l++;  // increments index of inner goal array
					if (l >= 3) { // iterating through goal array without nested for loops
						l = 0;
						k++; // increments index of outer goal array (upon reaching the end of the current inner goal array)
					}
					if (k >= 3) {
						throw new Exception("Error determining heuristic: reached end of goal state without finding item from start state");
					}
				}
			}
		}
		return heuristic;
	}


}
