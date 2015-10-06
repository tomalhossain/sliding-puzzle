import java.util.Vector;


public class VectorAsList implements SearchList {

	Vector<State> theList;

	public State remove() {
		return theList.remove(theList.size()-1);
	}

	public void add(State o) {
		theList.add(o);
	}

	public VectorAsList() {
		theList = new Vector<State>();
	}

	public int size() {	
		return theList.size();
	}	
}
