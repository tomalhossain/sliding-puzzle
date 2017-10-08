import java.util.Vector;

public interface StateSpace{

	public String getStart();

	public String getGoal();

	public Vector<String> getKids(String current);

	public boolean isGoal(String rep);
}
