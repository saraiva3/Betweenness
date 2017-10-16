import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

public class CaseTestGenerator {

	public static void main(String args[]) {
		int size = 600;
		boolean dense = false;
		generateCaseTest(size, dense);

	}

	public static void generateCaseTest(int size, boolean dense) {

		int column1[], column2[];

			column1 = new int[size];
			column2 = new int[size];
		

			for (int i = 0; i < size; i++) {
				
				column1[i] = i;
				column2[i] = i + 1;				
			}

		

		PrintWriter writer;
		try {
		
			if (dense) {
				writer = new PrintWriter(size + "DE.txt", "UTF-8");
				for (int i = 0; i < size; i++) {
					for(int j=i+1;j<size-1;j++) {						
						writer.println(column1[i] + "," + column2[j] + "," + ThreadLocalRandom.current().nextInt(1, 99 + 1));
					}				
				}			
			} else {
				writer = new PrintWriter(size + "ES.txt", "UTF-8");
				for(int j=0; j<size/4 ; j++)
				for (int i = j; i < size; i++) {
					writer.println(column1[j] + "," + column2[i] + "," +ThreadLocalRandom.current().nextInt(1, 99 + 1));
				}
			}

			writer.print("0,0,0");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
