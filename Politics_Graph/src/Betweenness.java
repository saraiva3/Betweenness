import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Betweenness {

	private HashMap<Integer, Double> betweenness;

	public Betweenness() {
		betweenness = new HashMap<Integer, Double>();
	}

	public void calculateBetweeness(Graph graph, ArrayList<List<Integer>> allShortestPaths, int v1, int v2) {

		double calculos = 0.0;
		double listSize = allShortestPaths.size();
		for (int i = 0; i < listSize; i++) {
			List<Integer> path = allShortestPaths.get(i);
			Deputy deputies = graph.deputyList;

			while (deputies != null) {
				if (deputies.id != v1 && deputies.id != v2) {
					if (path.contains(deputies.id)) {
						if (betweenness.get(deputies.id) == null) {
							calculos = (double) 1 / listSize;
						} else {
							calculos = betweenness.get(deputies.id) + (double) 1 / listSize;
						}

						betweenness.put(deputies.id, calculos);
					}

				} else {
					if (path.contains(deputies.id)) {
						if (betweenness.get(deputies.id) == null) {
							calculos = 0.0;
						} else {
							calculos = betweenness.get(deputies.id);
						}

						betweenness.put(deputies.id, calculos);
					}
				}
				deputies = deputies.neigh;
			}

		}
	}

	public void sortListRecordFile(Graph graph, String filename) {

		Set<Entry<Integer, Double>> mapEntries = betweenness.entrySet();
		List<Entry<Integer, Double>> aList = new LinkedList<Entry<Integer, Double>>(mapEntries);

		Collections.sort(aList, new Comparator<Entry<Integer, Double>>() {
			@Override
			public int compare(Entry<Integer, Double> deputy1, Entry<Integer, Double> deputy2) {
				if (deputy1.getValue().equals(deputy2.getValue())) {
					// Modificacao de um comparator normal para ordenar pela chave
					// caso os valores sejam iguas
					if (deputy1.getKey() < deputy2.getKey()) {
						return -1;
					} else {
						return 1;
					}
				} else if (deputy1.getValue() < deputy2.getValue()) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		
		Map<Integer, Double> aMap2 = new LinkedHashMap<Integer, Double>();
		for (Entry<Integer, Double> entry : aList) {
			aMap2.put(entry.getKey(), entry.getValue());
		}
		for (Entry<Integer, Double> entry : aMap2.entrySet()) {
			System.out.print(entry.getKey() + ",");
		}

		/*
		 * PrintWriter writer; try { writer = new PrintWriter(filename, "UTF-8"); for
		 * (Entry<Integer, Integer> entry : aMap2.entrySet()) {
		 * writer.println(entry.getKey() + ","); }
		 * 
		 * writer.close(); } catch (FileNotFoundException | UnsupportedEncodingException
		 * e) { e.printStackTrace(); }
		 */
	}

	public void printFrequency(Graph graph) {
		Deputy deputies = graph.deputyList;
		while (deputies != null) {
			if (betweenness.get(deputies.id) != null) {
				System.out.println("ID: " + deputies.id + "Value " + betweenness.get(deputies.id));
			}
			deputies = deputies.neigh;
		}
	}
}
