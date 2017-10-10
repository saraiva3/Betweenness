import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

	public static void main(String args[]) {
		Graph graph;

		for (int i = 1; i <= 5; i++) {	
			try (BufferedReader br = new BufferedReader(new FileReader(
					"C:\\Users\\Lucas\\Desktop\\UFMG- Mestrado\\PAA\\Tp Grafos\\Workspace\\Politics_Graph\\src\\Ex" + i
							+ ".txt"))) {

				String line;
				graph = new Graph();
				
				while (true) {
					line = br.readLine();

					if (line.matches("0,0,0"))
						break;

					String[] values = line.split(",");
					graph.addConnection(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
							Double.parseDouble(values[2]));
				}		

				Betweenness computeBetwenness = new Betweenness();
			
				Deputy v1 = graph.deputyList;
				Deputy v2 = graph.deputyList;
				
				while (v1 != null) {

					graph.computeAllShortestPaths(graph.getVertexByID(v1.id), graph);
					v2 = graph.deputyList;
					while (v2 != null) {		
						
						ArrayList<List<Integer>> allShortestPaths = graph.getAllShortestPathsTo(v2);						
						computeBetwenness.calculateBetweeness(graph, allShortestPaths, v1.id, v2.id);				
						
						v2 = v2.neigh;
					}
					v1 = v1.neigh;
				}

				computeBetwenness.printFrequency(graph);
				computeBetwenness.sortListRecordFile(graph, "Ex1.out");
				
			} catch (IOException e) {
				System.out.println(
						"O caminho do arquivo esta incorreto ou o arquivo esta corrompido. Favor conferir o caminho.");
			}
		}

	}
}
