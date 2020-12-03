import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class TobogganPathWendy {

	public static char[][] matrix;
	public static int numRows;
	public static int numCols;
	
	public static int calculateNumTrees(int right, int down) {
		int r = 0;
		int c = 0;
		
		int numTrees = 0;
		while (r < numRows) {
			
			if (matrix[r][c] == '#') {
				numTrees++;
			}
			
			r = r + down;
			c = (c + right) % numCols;
		}
		return numTrees;
	}
	
	public static void main(String[] args) {
		List<String> lines;
		
		try {
			lines = Files.readAllLines(Paths.get("./day3/input"));
					
			numRows = lines.size();
			
			matrix = new char[numRows][];
			
			int i=0;			
			for (String line:lines) {				
				matrix[i] = line.toCharArray();
				i++;		
			}			
			numCols = matrix[0].length;
			
			long answer = calculateNumTrees(1, 1)
				* calculateNumTrees(3, 1)
				* calculateNumTrees(5, 1)
				* calculateNumTrees(7, 1)
				* calculateNumTrees(1, 2);

			
			System.out.println("Answer is " + answer);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
