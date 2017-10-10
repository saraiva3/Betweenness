import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {

	public Deputy deputyList;
	public ArrayList<HashMap<Integer, Integer>> adjacencyList;
	private ArrayList<List<Integer>> allShortestPaths;

	public Graph() {
		adjacencyList = new ArrayList<HashMap<Integer, Integer>>();	
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

		Connection connected = new Connection(first, second, 100 - weight);
		first.addToAdjacencyList(connected);

		connected = new Connection(second, first, 100 - weight);
		second.addToAdjacencyList(connected);
	}

	public ArrayList<List<Integer>> getAllShortestPathsTo(Deputy v2) {
		
		this.allShortestPaths = new ArrayList<List<Integer>>();
		
		List<Integer> shortestPath = new ArrayList<Integer>();
		
		computeAllPathsFromPrev(shortestPath, v2);
		
		return allShortestPaths;
	}

	private void computeAllPathsFromPrev(List<Integer> pathCalculated, Deputy v2) {
		List<Deputy> previousList = v2.getFatherList();
		if (previousList == null) {
			pathCalculated.add(v2.id);
			Collections.reverse(pathCalculated);
			allShortestPaths.add(pathCalculated);
		} else {
			List<Integer> otherPaths = new ArrayList<Integer>(pathCalculated);

			otherPaths.add(v2.id);

			for(int i =0; i < previousList.size(); i++) {				
				computeAllPathsFromPrev(otherPaths,  previousList.get(i));
			}			
		}		
	}
	
	
	public void graphReset(int id) {
		Deputy deputies = deputyList;

		while (deputies != null) {		
			deputies.setFather(null);
			deputies.setFatherList(null);
			if (deputies.id == id) {
				deputies.setTotalInfluence(0.0);
			} else {
				deputies.setTotalInfluence(Integer.MAX_VALUE);
			}
			deputies = deputies.neigh;
		}

	}

	public void computeAllShortestPaths(Deputy v1, Graph graph) {

		graphReset(v1.id);
		PriorityQueue<Deputy> vertexQueue = new PriorityQueue<Deputy>();
		vertexQueue.add(v1);
		List<Deputy> prev = null;

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
					neighboor.setFather(actual);
					neighboor.setFatherList(prev);

					vertexQueue.add(neighboor);

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
		}
	}

}
