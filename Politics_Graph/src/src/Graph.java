import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {

	public Deputy deputyList;
	public HashMap<Integer, Double> betweenness;

	public Graph() {
		betweenness = new HashMap<Integer, Double>();
	}

	public Deputy getVertexByID(int id) {
		Deputy deputies = deputyList;
		while (deputies != null) {
			if (deputies.id == id) {
				return deputies;
			}
			deputies = deputies.neigh;
		}

		return null;
	}

	public Deputy addVertex(int id) {
		
		Deputy deputy = new Deputy(id);
		deputy.neigh = deputyList;
		deputyList = deputy;

		return deputy;
	}

	public void addConnection(int firstDeputy, int secondDeputy, double weight) {

		Deputy first = getVertexByID(firstDeputy);
		if (first == null) {
			first = addVertex(firstDeputy);
		}

		Deputy second = getVertexByID(secondDeputy);
		if (second == null) {
			second = addVertex(secondDeputy);
		}

		Connection connected = new Connection(second, 100 - weight);
		first.addToAdjacencyList(connected);

		connected = new Connection(first, 100 - weight);
		second.addToAdjacencyList(connected);

		first = null;
		second = null;
		connected = null;
	}

	public void getAllShortestPathsTo(Deputy v2) {
		computeAllPathsFrom(v2);
	
	}

	public void computeAllPathsFrom(Deputy v2) {

		List<Deputy> previousList = v2.getFatherList();
		double calculos = 0;

		while (previousList != null) {
			if (betweenness.get(v2.id) == null) {
				calculos = 1;
			} else {
				calculos = betweenness.get(v2.id) + 1;
			}

			betweenness.put(v2.id, calculos);
			v2 = previousList.get(0);
			previousList = v2.getFatherList();
			
		}
		previousList = null;

		System.gc();
		return;
	}

	public void graphReset(int id) {
		Deputy deputies = deputyList;

		while (deputies != null) {
			deputies.setFatherList(null);
			if (deputies.id == id) {
				deputies.setTotalInfluence(0.0);
			} else {
				deputies.setTotalInfluence(Integer.MAX_VALUE);
			}
			deputies = deputies.neigh;
		}
		deputies = null;
		System.gc();
		return;
	}

	public void computeAllShortestPaths(Deputy v1) {

		graphReset(v1.id);
		PriorityQueue<Deputy> vertexQueue = new PriorityQueue<Deputy>();
		vertexQueue.add(v1);
		List<Deputy> prev = null;
		v1 = null;
		while (!vertexQueue.isEmpty()) {
			Deputy actual = vertexQueue.poll();

			Connection connected = actual.connecteds;
			while (connected != null) {

				Deputy neighboor = connected.two;

				double weight = connected.influence;

				double calculatedDistance = actual.totalInfluence + weight;

				if (calculatedDistance < neighboor.totalInfluence) {

					neighboor.totalInfluence = calculatedDistance;

					prev = new ArrayList<Deputy>();
					prev.add(actual);
					neighboor.setFatherList(prev);

					if (vertexQueue.contains(neighboor)) {
						vertexQueue.remove(neighboor);
						vertexQueue.add(neighboor);
					} else {
						vertexQueue.add(neighboor);
					}

				} else if (calculatedDistance == neighboor.totalInfluence) {

					prev = neighboor.getFatherList();
					if (prev != null) {
						prev.add(actual);
					} else {
						prev = new ArrayList<Deputy>();
						prev.add(actual);
						neighboor.setFatherList(prev);
					}
				}
				connected = connected.next;
			}
			System.gc();
		}

		vertexQueue = null;
		prev = null;
		
	}

}
