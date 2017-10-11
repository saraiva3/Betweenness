
public class Connection {

	
	public Deputy two;
	public double influence;
	public Connection next; 

	public Connection(Deputy two, double weight) {		
		this.two = two;
		this.influence = weight;
	}

	public double getInfluence() {
		return influence;
	}
}
