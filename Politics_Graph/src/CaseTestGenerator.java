import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

public class CaseTestGenerator {

	public static void main(String args[]) {
		int size = 100;
		boolean dense = false;
		generateCaseTest(size, dense);

	}

	public static void generateCaseTest(int size, boolean dense) {

		int column1[], column2[], column3[];
		if (dense) {
			column1 = new int[size * 2];
			column2 = new int[size * 2];
			column3 = new int[size * 2];
			int j = 0;
			for (int i = 0; i < size * 2; i++) {
				int randomNum = ThreadLocalRandom.current().nextInt(1, 99 + 1);
				if (i < size) {
					column1[i] = i;
					column2[i] = i + 1;
					column3[i] = randomNum;
				} else {

					column1[i] = j;
					column2[i] = j + 2;
					column3[i] = randomNum;
					j++;
				}
			}

		} else {
			column1 = new int[size];
			column2 = new int[size];
			column3 = new int[size];

			for (int i = 0; i < size; i++) {
				int randomNum = ThreadLocalRandom.current().nextInt(1, 99 + 1);
				column1[i] = i;
				column2[i] = i + 1;
				column3[i] = randomNum;
			}

		}

		PrintWriter writer;
		try {
			writer = new PrintWriter(size + ".txt", "UTF-8");
			if (dense) {

				for (int i = 0; i < size * 2; i++) {
					writer.println(column1[i] + "," + column2[i] + "," + column3[i]);
				}

			} else {
				for (int i = 0; i < size; i++) {
					writer.println(column1[i] + "," + column2[i] + "," + column3[i]);
				}
			}

			writer.print("0,0,0");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
