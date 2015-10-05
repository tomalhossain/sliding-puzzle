import java.util.Scanner;
import java.util.Vector;

public class SearchBase {

	private static int PRINT_HOW_OFTEN = 1;
	public static boolean debug = false;


	public static void main(String[] args)  {

		int depth_limit = Integer.parseInt(args[0]);
		System.out.println("Depth Limit "+depth_limit);
		new SearchBase().process(depth_limit);

	}

	private void process(int depth_limit) {

		String start = "216/4x8/753";
		String goal = "123/8x4/765";
		int size = 3;


		CarryBoolean p = new CarryBoolean();
		int NEXT_DEPTH = bestFirstSearch(p, depth_limit, new EightPuzzle(start, goal, size));

		System.out.println("The goal was found: "+p.getValue());
		System.out.println("Suggested next depth is "+NEXT_DEPTH);

		System.out.println("Continue?");
		new Scanner(System.in).next();

		p = new CarryBoolean();
		NEXT_DEPTH = vectorSearch(p , depth_limit, new EightPuzzle(start, goal,size));

		System.out.println("The goal was found: "+p.getValue());
		System.out.println("Suggested next depth is "+NEXT_DEPTH);


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

		manhattanFinderStart = cleanArray(start, manhattanFinderStart);
		manhattanFinderGoal = cleanArray(goal, manhattanFinderGoal);

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

			if (current.getDepth() <= limit) {
				Vector<String> kids = ssp.getKids(current.getRep());

				for (String v : kids) {
					if (!current.getPath().contains(v))
						open.add(new State(current,v));
				}
			}
		}
		return limit+1;
	}


	public int vectorSearch(CarryBoolean done, int limit, StateSpace ssp) {

		return search(done,limit,ssp,new VectorasList());
	}


	public int bestFirstSearch(CarryBoolean done, int limit, StateSpace ssp) {

		return search(done,limit,ssp,new PQasList());
	}

	public char[][] cleanArray(String status, char[][] target) {

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


}
