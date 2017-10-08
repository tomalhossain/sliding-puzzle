import java.util.Vector;


public class Queue implements SearchList {

	Vector<State> theList;

	public State remove() {
		return theList.remove(0);
	}

	public void add(State o) {
		theList.add(o);
	}

	public Queue() {
		theList = new Vector<State>();
	}

	public int size() {	
		return theList.size();
	}	
}
