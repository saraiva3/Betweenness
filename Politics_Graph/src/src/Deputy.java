import java.util.List;

public class Deputy implements Comparable<Deputy> {

	public int id;
	public double totalInfluence = Integer.MAX_VALUE;	
	public Connection connecteds;
	public Deputy neigh;
	private List<Deputy> fatherList;

	public Deputy(int id) {		
		this.id = id;
	}
	public void setTotalInfluence(double influence) {
		this.totalInfluence = influence;
	}

	public List<Deputy> getFatherList() {
		return fatherList;
	}
	
	public void setFatherList(List<Deputy> fatherList) {
		this.fatherList = fatherList;
	}
	
	public double getTotalInfluence() {
		return this.totalInfluence;
	}

	public void addToAdjacencyList(Connection connected) {
		connected.next = connecteds;
		connecteds = connected;
	}

	public int compareTo(Deputy deputy) {
		if (this.getTotalInfluence() < deputy.getTotalInfluence())
			return -1;
		else if (this.getTotalInfluence() == deputy.getTotalInfluence())
			return 0;
		return 1;

	}
}
