import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Betweenness {



	public Betweenness() {
		
	}

	/*public void calculateBetweeness(Graph graph, ArrayList<List<Integer>> allShortestPaths, int v1, int v2) {

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
		System.gc();
		return;
	}*/
	public void computeAllPathsFromPrev( Graph graph, Deputy v2, double listSize) {
		
	
		if (v2.getFatherList() == null) {		
			return;			
		} else {
			if (!graph.betweenness.containsKey(v2.id)) {				
				graph.betweenness.put(v2.id, 1.0);
			} else {
				
				
					System.out.println((double)(1/listSize));
					System.out.println("ID" + v2.id);
					graph.betweenness.put(v2.id, graph.betweenness.get(v2.id) + (1/listSize));
				
			}		
			for(int i =0; i < v2.getFatherList().size(); i++) {	
					
				computeAllPathsFromPrev(graph,v2.getFatherList().get(i),v2.getFatherList().size());
			}			
			
		}		
	}
	
	public void sortListRecordFile( HashMap<Integer, Double> betweenness, String filename) {	
		
		List<Entry<Integer, Double>> aList = new LinkedList<Entry<Integer, Double>>(betweenness.entrySet());

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
		for (Entry<Integer, Double> entry : aList) {
			System.out.println(entry.getKey() + " FREQUENCIA  "+ entry.getValue());
		}
		  PrintWriter writer; 
		  try {
			  writer = new PrintWriter(filename, "UTF-8"); 
			  for(int i =0; i<aList.size();i++) {
				  if(i == aList.size()-1) {
					  writer.print(aList.get(i).getKey() );
				  }else
				  writer.print(aList.get(i).getKey() + ",");
			  }		  
			  writer.close(); 
		  } catch (IOException e) { e.printStackTrace(); }
		 
			aList = null;
			System.gc();
	}
	
}
