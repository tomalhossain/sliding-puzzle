import java.util.Vector;



public class EightPuzzle implements StateSpace {

	int size = 4;
	String start;
	String goal;

	public EightPuzzle(String start, String goal, int size) {
		this.start = start;
		this.goal = goal;
		this.size = size;
	}

	public Vector<String> getKids(String current) {
		Vector<String> kids = new Vector<String>();

		doOne(kids,-1,current);
		doOne(kids,1,current);
		doOne(kids,size+1,current);
		doOne(kids,-(size+1),current);
		return kids;
	}

	private void doOne(Vector<String> kids, int dist, String current) {

		int hole = current.indexOf('x');
		int other = hole + dist;
		if (other < current.length() && other >= 0)
		{
			char otherC = current.charAt(other);
			if (otherC != '/') {
				char[] chars = current.toCharArray();
				chars[hole] = chars[other];
				chars[other] = 'x';
				kids.add(new String(chars));
			}
		}


	}

	public String getGoal() {
		return goal;
	}

	public String getStart() {
		return start;
	}

	public boolean isGoal(String rep) {
		return rep.equals(getGoal());
	}

	public int getSize() {
		return size;
	}
}
