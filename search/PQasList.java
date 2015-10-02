import java.util.PriorityQueue;

public class PQasList implements SearchList {

	PriorityQueue<State> theQ;

	public State remove() {
		return theQ.poll();
	}

	public void add(State o) {
		theQ.add(o);
	}

	public PQasList() {
		theQ = new PriorityQueue<State>();
	}

	public int size() {

		return theQ.size();
	}



}
