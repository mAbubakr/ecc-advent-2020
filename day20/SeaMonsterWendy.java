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
		 
		 List<Tile> corners = findCorners();
		 long product = 1;
		 for (Tile t: corners) {
			 t.printTile();
			 product = product * t.tileNum;
		 }
		 System.out.println("Part 1: " + product); 
		 
		 //set one corner as the top left and stitch from there
		 Tile topLeft = null;
		 for (Tile t: corners) {
			 if (t.findRightNeighbor() != null && t.findBottomNeighbor() != null) {
				 topLeft = t;
				 break;
			 }				 
		 }
		 
		 int mapDim = (int)Math.sqrt(tiles.size());
		 Tile[][] map = new Tile[mapDim][mapDim];
		 map[0][0] = topLeft;
		 for (int y=0; y<mapDim-1; y++) {
			 for (int x=0; x<mapDim-1; x++) {
				 Tile r = map[y][x].findRightNeighbor();
				 map[y][x+1] = r;
				 Tile b = map[y][x].findBottomNeighbor();
				 map[y+1][x] = b;
			 }
		 }
		 //fill the final tile
		 Tile r = map[mapDim-1][mapDim-2].findRightNeighbor();
		 map[mapDim-1][mapDim-1] = r;
		 
		 //print the map tile numbers of sanity
		 for (int y=0; y<mapDim; y++) {
			 for (int x=0; x<mapDim; x++) {
				 System.out.print(map[y][x].tileNum + " ");
			 }
			 System.out.println("");
		 }
		 
		 //remove the borders of each tile		 
		 //stitch together into one big tile
		 int unit = map[0][0].dim - 2; //num of char to copy per tile
		 int finalDim = mapDim * unit;
		 char[][] finalMatrix = new char[finalDim][finalDim];
		 for (int y=0; y<mapDim; y++) {
			 int startY = y * unit;
			 for (int x=0; x<mapDim; x++) {
				 int startX = x * unit;
				 char[][] matrix = map[y][x].matrix;
				 for (int i=0; i<unit; i++) {
					 for (int j=0; j<unit; j++) {
						 finalMatrix[startY + i][startX + j] = matrix[i+1][j+1];
					 }
				 }
			 }
		 }
		 
		 //create a tile with the stitched map
		 Tile whole = new Tile(0, finalMatrix);
		 
		 //search each orientation
		 Tile temp = whole;
		 for (;;) {
			 if (findSeaMonster(temp)) break;
			 temp = whole.rotate90();
			 if (findSeaMonster(temp)) break;
			 temp = whole.rotate180();
			 if (findSeaMonster(temp)) break;
			 temp = whole.rotate270();
			 if (findSeaMonster(temp)) break;
			 
			 temp = whole.flipHorizontally();
			 if (findSeaMonster(temp)) break;
			 temp = temp.rotate90();
			 if (findSeaMonster(temp)) break;
			 temp = temp.rotate180();
			 if (findSeaMonster(temp)) break;
			 temp = whole.rotate270();
			 if (findSeaMonster(temp)) break;

			 temp = whole.flipVertically();
			 if (findSeaMonster(temp)) break;
			 temp = temp.rotate90();
			 if (findSeaMonster(temp)) break;
			 temp = temp.rotate180();
			 if (findSeaMonster(temp)) break;
			 temp = whole.rotate270();
			 if (findSeaMonster(temp)) break;
			 
		 }

		 temp.printTile();
		 
		 System.out.println("Part 2: " + countRoughWater(temp));
		 
		 	 
	}

	private int countRoughWater(Tile t) {
		int count=0;
		int dim = t.dim;
		char[][] matrix = t.matrix;
		for (int y = 0; y < dim; y++) {
			for (int x = 0; x < dim; x++) {
				if (matrix[y][x] == '#') count++;
			}
		}
		return count;		
	}
	
    /*
     *                   # 
       #    ##    ##    ###
        #  #  #  #  #  #   
     */
//	 matrix[y][x+18]
//	 matrix[y+1][x]
//	 matrix[y+1][x+5]
//	 matrix[y+1][x+6]				 
//	 matrix[y+1][x+11]
//	 matrix[y+1][x+12]				 
//	 matrix[y+1][x+17]
//	 matrix[y+1][x+18]				 
//	 matrix[y+1][x+19]
//	 matrix[y+2][x+1]
//	 matrix[y+2][x+4]
//	 matrix[y+2][x+7]						 
//	 matrix[y+2][x+10]
//	 matrix[y+2][x+13]
//	 matrix[y+2][x+16]						 

	
	/**
	 * Search for sea monsters and replace the char with 0 for identified monsters
	 * 
	 * @param t
	 * @return true if sea monster found
	 */
	private boolean findSeaMonster(Tile t) {
		boolean found = false;
		int dim = t.dim;
		char[][] matrix = t.matrix;
		for (int y = 0; y < dim - 2; y++) {
			for (int x = 0; x < dim - 19; x++) {
				if (matrix[y][x + 18] == '#' && matrix[y + 1][x] == '#' && matrix[y + 1][x + 5] == '#'
						&& matrix[y + 1][x + 6] == '#' && matrix[y + 1][x + 11] == '#' && matrix[y + 1][x + 12] == '#'
						&& matrix[y + 1][x + 17] == '#' && matrix[y + 1][x + 18] == '#' && matrix[y + 1][x + 19] == '#'
						&& matrix[y + 2][x + 1] == '#' && matrix[y + 2][x + 4] == '#' && matrix[y + 2][x + 7] == '#'
						&& matrix[y + 2][x + 10] == '#' && matrix[y + 2][x + 13] == '#'
						&& matrix[y + 2][x + 16] == '#') {
					found = true;
					matrix[y][x + 18] = '0';
					matrix[y + 1][x] = '0';
					matrix[y + 1][x + 5] = '0';
					matrix[y + 1][x + 6] = '0';
					matrix[y + 1][x + 11] = '0';
					matrix[y + 1][x + 12] = '0';
					matrix[y + 1][x + 17] = '0';
					matrix[y + 1][x + 18] = '0';
					matrix[y + 1][x + 19] = '0';
					matrix[y + 2][x + 1] = '0';
					matrix[y + 2][x + 4] = '0';
					matrix[y + 2][x + 7] = '0';
					matrix[y + 2][x + 10] = '0';
					matrix[y + 2][x + 13] = '0';
					matrix[y + 2][x + 16] = '0';

				}

			}
		}
		return found;
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
			if (isMatch(t, other)) { matches++; t.addNeighbor(other); continue; }
			if (isMatch(t, other.rotate90())) { matches++; t.addNeighbor(other); continue; }
			if (isMatch(t, other.rotate180())) { matches++; t.addNeighbor(other); continue; }			
			if (isMatch(t, other.rotate270())) { matches++; t.addNeighbor(other); continue; }			
			Tile otherFlipped = other.flipHorizontally();
			if (isMatch(t, otherFlipped)) { matches++; t.addNeighbor(other); continue; }
			if (isMatch(t, otherFlipped.rotate90())) { matches++; t.addNeighbor(other); continue; }
			if (isMatch(t, otherFlipped.rotate180())) { matches++; t.addNeighbor(other); continue; }			
			if (isMatch(t, otherFlipped.rotate270())) { matches++; t.addNeighbor(other); continue; }			
			otherFlipped = other.flipVertically();
			if (isMatch(t, otherFlipped)) { matches++; t.addNeighbor(other); continue; }
			if (isMatch(t, otherFlipped.rotate90())) { matches++; t.addNeighbor(other); continue; }
			if (isMatch(t, otherFlipped.rotate180())) { matches++; t.addNeighbor(other); continue; }			
			if (isMatch(t, otherFlipped.rotate270())) { matches++; t.addNeighbor(other); continue; }					
		}
		return matches;
	}
	
	private List<Tile> findCorners() {
		List<Tile> corners = new ArrayList<Tile>();
		for(Tile t: tiles.values()) {
			if (getMatches(t) == 2)	corners.add(t);			
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
		public int dim;
		public char[][] matrix;
		public List<Tile> neighbors = new ArrayList<Tile>();
		
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
		
		public void addNeighbor(Tile t) {
			neighbors.add(t);
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
		
		public Tile findRightNeighbor() {
			for (Tile other: neighbors) {
				if (Arrays.compare(this.getRight(), other.getLeft()) == 0) { return other; }
				Tile otherFlipped = other.rotate90();
				if (Arrays.compare(this.getRight(), otherFlipped.getLeft()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = other.rotate180();
				if (Arrays.compare(this.getRight(), otherFlipped.getLeft()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = other.rotate270();
				if (Arrays.compare(this.getRight(), otherFlipped.getLeft()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }				
				otherFlipped = other.flipHorizontally();				
				if (Arrays.compare(this.getRight(), otherFlipped.getLeft()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = otherFlipped.rotate90();
				if (Arrays.compare(this.getRight(), otherFlipped.getLeft()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = otherFlipped.rotate180();
				if (Arrays.compare(this.getRight(), otherFlipped.getLeft()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = otherFlipped.rotate270();
				if (Arrays.compare(this.getRight(), otherFlipped.getLeft()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }				
				otherFlipped = other.flipVertically();
				if (Arrays.compare(this.getRight(), otherFlipped.getLeft()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = otherFlipped.rotate90();
				if (Arrays.compare(this.getRight(), otherFlipped.getLeft()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = otherFlipped.rotate180();
				if (Arrays.compare(this.getRight(), otherFlipped.getLeft()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = otherFlipped.rotate270();
				if (Arrays.compare(this.getRight(), otherFlipped.getLeft()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
			}
			return null;
		}
		
		public Tile findBottomNeighbor() {
			for (Tile other: neighbors) {
				if (Arrays.compare(this.getBottom(), other.getTop()) == 0) { return other; }
				Tile otherFlipped = other.rotate90();
				if (Arrays.compare(this.getBottom(), otherFlipped.getTop()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = other.rotate180();
				if (Arrays.compare(this.getBottom(), otherFlipped.getTop()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = other.rotate270();
				if (Arrays.compare(this.getBottom(), otherFlipped.getTop()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }				
				otherFlipped = other.flipHorizontally();				
				if (Arrays.compare(this.getBottom(), otherFlipped.getTop()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = otherFlipped.rotate90();
				if (Arrays.compare(this.getBottom(), otherFlipped.getTop()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = otherFlipped.rotate180();
				if (Arrays.compare(this.getBottom(), otherFlipped.getTop()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = otherFlipped.rotate270();
				if (Arrays.compare(this.getBottom(), otherFlipped.getTop()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }				
				otherFlipped = other.flipVertically();
				if (Arrays.compare(this.getBottom(), otherFlipped.getTop()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = otherFlipped.rotate90();
				if (Arrays.compare(this.getBottom(), otherFlipped.getTop()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = otherFlipped.rotate180();
				if (Arrays.compare(this.getBottom(), otherFlipped.getTop()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
				otherFlipped = otherFlipped.rotate270();
				if (Arrays.compare(this.getBottom(), otherFlipped.getTop()) == 0) { otherFlipped.neighbors = other.neighbors; return otherFlipped; }
			}
			return null;
		}		
	}
	
	public static void main(String[] args) throws IOException {
		
		new SeaMonsterWendy();

	}


	
}
