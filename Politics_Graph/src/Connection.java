
public class Connection {

	public Deputy one;
	public Deputy two;
	public double influence;
	public Connection next; 

	public Connection(Deputy one, Deputy two, double weight) {
		this.one = one;
		this.two = two;
		this.influence = weight;
	}

	public double getInfluence() {
		return influence;
	}
}
