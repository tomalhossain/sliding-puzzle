import java.util.Vector;


public class State implements Comparable<State> {

	private String rep;
	private Vector<String> path;
	private int depth;

	public State(String r) {
		rep = r;
		path = new Vector<String>();
		path.add(r);
		depth = 0;

	}
	public State(State s, String n) {
		rep = n;
		path = new Vector<String>(s.path);
		path.add(n);
		depth = s.depth+1;
	}
	public int printPath() {
		int count = 1;
		for (String step : path)
		{
			System.out.println(count+"\n"+convert(step));
			count++;
		}
		return 0;
	}

	public int compareTo(State other) {

		if (!(other instanceof State)) {
			System.exit(3);
			return 0;
		}
		// deeper is larger
		//else return ((State)other).getDepth() -depth;
		//deeper is smaller  
		else return depth - ((State)other).getDepth();

	}

	private String convert(String step) {
		String[] pieces = step.split("/");
		String answer = "";
		for (String p : pieces)
			answer = answer +p +"\n";
		return answer;
	}

	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public Vector<String> getPath() {
		return path;
	}
	public void setPath(Vector<String> path) {
		this.path = path;
	}
	public String getRep() {
		return rep;
	}
	public void setRep(String rep) {
		this.rep = rep;
	}
}
