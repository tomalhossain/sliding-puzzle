import java.util.PriorityQueue;
import java.util.Vector;

public class VectorPQ {

	Vector<PQasList> VectorPQList; 
	
	public State remove () { 

		// This while loop will prevent the attempt to expand null pointers. So, after all the children of a given state have been inspected, 
		// the empty PQasList within an element of the open list will be deleted. Thus, this function will only return a state that exists within
		// a non-empty PQasList. 

		while (VectorPQList.lastElement().peek() == null) {
			VectorPQList.removeElementAt(VectorPQList.size()-1);
		}	
		return VectorPQList.lastElement().remove();
	}

	public void removeElementAt (int index) {
		VectorPQList.removeElementAt(index);
	}

	public PQasList lastElement () { 
		return VectorPQList.lastElement();
	}

	public void add (PQasList o) { 
		VectorPQList.add(o);	
	}

	public VectorPQ () {
		VectorPQList = new Vector <PQasList>(); 
	}
	
	public int size () {
		// This function totals each of the states within each of the PQasList's of the VectorPQ open list. 
		int count=0; 
		for (int i=0; i < VectorPQList.size(); i++) {
			count += VectorPQList.elementAt(i).size(); 	
		}
		return count; 
	}
}