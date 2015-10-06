import java.util.Vector;


public class VectorasList implements SearchList {

	Vector<State> theList;

	public State remove() {
		return theList.remove(theList.size()-1);
	}

	public void add(State o) {
		theList.add(o);
	}

	public VectorasList() {
		theList = new Vector<State>();
	}

	public int size() {
		return theList.size();
	}
}
