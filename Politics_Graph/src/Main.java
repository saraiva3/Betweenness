import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String args[]) {
System.out.print(",");
		BufferedReader br;
		try {

			br = new BufferedReader(new FileReader(args[0]));

			String line;
			Graph graph = new Graph();
			while (true) {
				line = br.readLine();

				if (line.matches("0,0,0"))
					break;

				String[] values = line.split(",");
				graph.addConnection(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
						Double.parseDouble(values[2]));
			}

			br.close();
			br = null;

			Deputy v1 = graph.deputyList;
			Deputy v2 = graph.deputyList;
			Betweenness computeBetwenness = new Betweenness();

			while (v1 != null) {

				graph.computeAllShortestPaths(graph.getVertexByID(v1.id));
				v2 = graph.deputyList;

				while (v2 != null) {
					System.gc();
					if (v2.getFatherList() != null && v2.id != v1.id) {
						
						computeBetwenness.computeAllPathsFromPrev(graph, v2,v1, v2.getFatherList().size());
					}

					v2 = v2.neigh;
				}

				v1 = v1.neigh;

			}
			computeBetwenness.sortListRecordFile(graph.betweenness, args[1]);

		} catch (IOException e) {
			System.out.println(
					"O caminho do arquivo esta incorreto ou o arquivo esta corrompido. Favor conferir o caminho.");
		}
	}
}
