import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SeaMonsterWendy {

	private Map<Integer, Tile> tiles;
	
	public SeaMonsterWendy() throws IOException {
//		 List<String> input = Files.readAllLines(Paths.get("./day20/test"));
		 List<String> input = Files.readAllLines(Paths.get("./day20/input"));		 
		 
		 List<String> currentTileInput = null;
		 int currentTile = -1;
		 tiles = new HashMap<>();
		 for (String line: input) {
			 if (line.startsWith("Tile")) { //new tile
				currentTileInput = new ArrayList<String>();
				currentTile = Integer.parseInt(line.substring(5, line.length()-1));
			 } else if (line.isBlank()) { //end of tile
				 Tile t = new Tile(currentTile, currentTileInput);
				 tiles.put(currentTile, t);
			 } else {
				 currentTileInput.add(line);
			 }
		 }		
		 
//		 test(3079);
//		 test(1259);
		 
		 List<Tile> tiles = findCorners();
		 long product = 1;
		 for (Tile t: tiles) {
			 t.printTile();
			 product = product * t.tileNum;
		 }
		 System.out.println("Part 1: " + product); 
	}
	
	//match by no rotation
	private boolean isMatch(Tile t1, Tile t2) {
		if (Arrays.compare(t1.getBottom(), t2.getTop()) == 0) { return true; }
		if (Arrays.compare(t1.getRight(), t2.getLeft()) == 0) { return true; }
		if (Arrays.compare(t1.getLeft(), t2.getRight()) == 0) { return true; }
		if (Arrays.compare(t1.getTop(), t2.getBottom()) == 0) { return true; }
		return false;
	}
	
	//match by trying to rotate others
	private int getMatches(Tile t) {
		int matches = 0;
		for (Tile other: tiles.values()) {
			if (t.tileNum == other.tileNum) continue; //don't count itself
			if (isMatch(t, other)) { matches++; continue; }
			if (isMatch(t, other.rotate90())) { matches++; continue; }
			if (isMatch(t, other.rotate180())) { matches++; continue; }			
			if (isMatch(t, other.rotate270())) { matches++; continue; }			
			Tile otherFlipped = other.flipHorizontally();
			if (isMatch(t, otherFlipped)) { matches++; continue; }
			if (isMatch(t, otherFlipped.rotate90())) { matches++; continue; }
			if (isMatch(t, otherFlipped.rotate180())) { matches++; continue; }			
			if (isMatch(t, otherFlipped.rotate270())) { matches++; continue; }			
			otherFlipped = other.flipVertically();
			if (isMatch(t, otherFlipped)) { matches++; continue; }
			if (isMatch(t, otherFlipped.rotate90())) { matches++; continue; }
			if (isMatch(t, otherFlipped.rotate180())) { matches++; continue; }			
			if (isMatch(t, otherFlipped.rotate270())) { matches++; continue; }					
		}
		return matches;
	}
	
	private List<Tile> findCorners() {
		List<Tile> corners = new ArrayList<Tile>();
		for(Tile t: tiles.values()) {
			//test each orientation
			if (getMatches(t) > 2) continue;
			if (getMatches(t.rotate90()) > 2) continue;
			if (getMatches(t.rotate180()) > 2) continue;
			if (getMatches(t.rotate270()) > 2) continue;
			Tile tFlipped = t.flipHorizontally();
			if (getMatches(tFlipped) > 2) continue;
			if (getMatches(tFlipped.rotate90()) > 2) continue;
			if (getMatches(tFlipped.rotate180()) > 2) continue;
			if (getMatches(tFlipped.rotate270()) > 2) continue;
			tFlipped = t.flipVertically();
			if (getMatches(tFlipped) > 2) continue;
			if (getMatches(tFlipped.rotate90()) > 2) continue;
			if (getMatches(tFlipped.rotate180()) > 2) continue;
			if (getMatches(tFlipped.rotate270()) > 2) continue;
			
			//else none of its orientation has more than 2 matches, is a corner
			corners.add(t);			
		}		
		return corners;
	}
	
	private void test(int num) {
		System.out.println("==============Test=============");
		System.out.println("Number of tiles: " + tiles.size());
		 for (Tile t: tiles.values()) {
			 t.printTile();
		 }
		 		
		Tile t = tiles.get(num);

		System.out.println("Original");
		t.printTile();

		System.out.println("Top: " + Arrays.toString(t.getTop()));
		System.out.println("Bottom: " + Arrays.toString(t.getBottom()));
		System.out.println("Right: " + Arrays.toString(t.getRight()));
		System.out.println("Left: " + Arrays.toString(t.getLeft()));

		System.out.println("Rotate 90");
		t.rotate90().printTile();

		System.out.println("Rotate 180");
		t.rotate180().printTile();

		System.out.println("Rotate 270");
		t.rotate270().printTile();
		
		System.out.println("Flip horizontally");
		t.flipHorizontally().printTile();		
		
		System.out.println("Flip vertically");
		t.flipVertically().printTile();			
	}

	private class Tile {
		public int tileNum;
		private int dim;
		private char[][] matrix;
		
		public Tile(int tileNum, List<String> input) {
			this.tileNum = tileNum;
			this.dim = input.get(0).length();
			this.matrix = new char[dim][dim];
			int i=0;
			for (String line: input) {
				this.matrix[i] = line.toCharArray();
				i++;
			}			
		}
		
		public Tile(int tileNum, char[][] matrix) {
			this.tileNum = tileNum;
			this.dim = matrix.length;
			this.matrix = matrix;
		}
		
		public void printTile() {
			System.out.format("====Tile %s======\n", tileNum);
			for (int y = 0; y < dim; y++) {
				System.out.println(matrix[y]);
			}
		}
		
		public char[] getTop() {
			return matrix[0].clone();
		}
		
		public char[] getBottom() {
			return matrix[dim-1].clone();
		}
		
		public char[] getRight() {
			char[] right = new char[dim];
			for (int i=0; i<dim; i++) {
				right[i] = matrix[i][dim-1];
			}
			return right;
		}
		
		public char[] getLeft() {
			char[] left = new char[dim];
			for (int i=0; i<dim; i++) {
				left[i] = matrix[i][0];
			}
			return left;			
		}
		
		public Tile flipHorizontally() {
			char[][] newMatrix = new char[dim][dim];
			for (int y=0; y<dim; y++) {
				for (int x=0; x<dim; x++) {
					newMatrix[y][x] = matrix[y][dim-1-x];
				}
			}
			return new Tile(tileNum, newMatrix);			
		}
		
		public Tile flipVertically() {
			char[][] newMatrix = new char[dim][dim];
			for (int y=0; y<dim; y++) {
				newMatrix[y] = matrix[dim-1-y].clone();
			}
			return new Tile(tileNum, newMatrix);		
		}
		
		
		// adapted from
		// https://www.devglan.com/java-programs/java-program-matrix-rotation
		public Tile rotate90() {
			char[][] newMatrix = new char[dim][dim];
			for (int y=0; y<dim; y++) {
				newMatrix[y] = matrix[y].clone();
			}

			// first find the transpose of the matrix.
			for (int i = 0; i < dim; i++) {
				for (int j = i; j < dim; j++) {
					char temp = newMatrix[i][j];
					newMatrix[i][j] = newMatrix[j][i];
					newMatrix[j][i] = temp;
				}
			}
			// reverse each row
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim / 2; j++) {
					char temp = newMatrix[i][j];
					newMatrix[i][j] = newMatrix[i][dim - 1 - j];
					newMatrix[i][dim - 1 - j] = temp;
				}
			}

			return new Tile(tileNum, newMatrix);
		}
		
		public Tile rotate180() {
			return this.rotate90().rotate90();
		}
		
		public Tile rotate270() {
			return this.rotate90().rotate90().rotate90();
		}						
	}
	
	public static void main(String[] args) throws IOException {
		
		new SeaMonsterWendy();

	}


	
}
