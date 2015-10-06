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

		String start = "123/456/78x";
		String goal = "123/456/x78";
		int size = 3;

		CarryBoolean p = new CarryBoolean();
		/*
		int NEXT_DEPTH = bestFirstSearch(p , depth_limit, new EightPuzzle(start, goal,size));

		System.out.println("The goal was found: "+p.getValue());
		System.out.println("Suggested next depth is "+NEXT_DEPTH);

		System.out.println("Continue?");
		new Scanner(System.in).next();

		p = new CarryBoolean();
		*/
		
		//depthFirstSearch(p , depth_limit, new EightPuzzle(start, goal,size));
		dFID(p , depth_limit, new EightPuzzle(start, goal,size));

		System.out.println("The goal was found: "+p.getValue());
		//System.out.println("Suggested next depth is "+NEXT_DEPTH);


	}

	public int search(CarryBoolean done, int limit, StateSpace ssp, SearchList open) {

		System.out.println("Starting search with limit "+ limit);

		open.add(new State(ssp.getStart()));

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


}
