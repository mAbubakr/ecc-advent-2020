import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CCWendy {



	public static char[][][] growCube(char[][][] zyx) {
		int zDim = zyx.length;
		int yDim = zyx[0].length;
		int xDim = zyx[0][0].length;
//		System.out.format("Incoming dimension = %s x %s x %s\n", xDim, yDim, zDim);
		
		int newZDim = zDim + 2;
		int newYDim = yDim + 2;		
		int newXDim = xDim + 2;		

		char[][][] newZyx = new char[newZDim][newYDim][newXDim];
		
		//fill the new Zs at beginning and end
		for (int y=0; y<newYDim; y++) {
			Arrays.fill(newZyx[0][y], '.');
			Arrays.fill(newZyx[newZDim-1][y], '.');
		}
		

		//loop through incoming array
		for (int z=0; z<zDim; z++) {
			//z in old -> z+1 in new
			
			//fill the new Ys at beginning and ned
			Arrays.fill(newZyx[z+1][0], '.');
			Arrays.fill(newZyx[z+1][newYDim-1], '.');			
			
			//loop through y
			for (int y=0; y<yDim; y++) {
				char[] xList = zyx[z][y];
				//y in old -> y+1 in new
				
				//fill the new X at beginning and end
				newZyx[z+1][y+1][0] = '.';
				newZyx[z+1][y+1][newXDim-1] = '.';
				
				System.arraycopy(xList, 0, newZyx[z+1][y+1], 1, xList.length);
			}
		}
			
		
		return newZyx;		
	}	
	
	public static char getState(char[][][] zyx, int z, int y, int x) {
		int zDim = zyx.length;
		int yDim = zyx[0].length;
		int xDim = zyx[0][0].length;
		
		if (x < 0 || x >= xDim || y < 0 || y >= yDim || z < 0 || z >= zDim ) return '.';
		
		return zyx[z][y][x];
	}
	
	public static int findNumActiveNeighbors(char[][][] zyx, int x, int y, int z) {		
		int sum=0;
		
		for (int zCount=z-1; zCount<=z+1; zCount++) {
			for (int yCount=y-1; yCount<=y+1; yCount++) {
				for (int xCount=x-1; xCount<=x+1; xCount++) {
					if (zCount==z && yCount==y && xCount==x) continue;
					if (getState(zyx, zCount, yCount, xCount) == '#') sum += 1;
				}
			}
		}
		
//		System.out.format("Active neighbors of %s,%s,%s is %s\n", x, y, z, sum);
		return sum;
	}
	
	public static char[][][] cloneCube(char[][][] zyx) {
		char[][][] newZyx = new char[zyx.length][zyx[0].length][zyx[0][0].length];
		for (int z=0; z<zyx.length; z++) {					
			for (int y=0; y<zyx[z].length; y++) {
				for (int x=0; x<zyx[z][y].length; x++) {
					newZyx[z][y][x] = zyx[z][y][x];	
				}
			}				
		}
		return newZyx;
	}
	
	public static char[][][] simNext(char[][][] zyx) {
		char[][][] prevZyx = growCube(zyx);
		char[][][] newZyx = cloneCube(prevZyx);
		
//		System.out.println("After growing...");
//		printCube(newZyx);
		for (int z=0; z<prevZyx.length; z++) {
			char[][] yx = prevZyx[z];
			for (int y=0; y<yx.length; y++) {
				char[] xList = yx[y];
				for (int x=0; x<xList.length; x++) {
					char currentState = xList[x];
//					System.out.format("Current is %s, %s, %s and state is %s\n", x, y, z, currentState);
					int numActiveNeighbors = findNumActiveNeighbors(prevZyx, x, y, z);
					if (currentState == '#') {
						if (numActiveNeighbors == 2 || numActiveNeighbors == 3) {
							//remain active
						} else {
							//turn inactive
							newZyx[z][y][x] = '.';
						}
					} else {
						if (numActiveNeighbors == 3) {
							//turn active
							newZyx[z][y][x] = '#';
						}
						//else remain inactive
					}
				}
			}
				
		}	
		return newZyx;
	}
		
	public static void printCube(char[][][] zyx) {
		for (int z=0; z<zyx.length; z++) {			
			System.out.format("====Z=%s======\n",z);			
			for (int y=0; y<zyx[z].length; y++) {
				System.out.println(zyx[z][y]);
			}				
		}
	}	

	public static int countActive(char[][][] zyx) {
		int num=0;
		for (int z=0; z<zyx.length; z++) {					
			for (int y=0; y<zyx[z].length; y++) {
				for (int x=0; x<zyx[z][y].length; x++) {
					if (zyx[z][y][x] == '#') num++;
				}
			}				
		}
		return num;
	}
	
	public static void main(String[] args) throws IOException {
		List<String> rows = Files.readAllLines(Paths.get("./day17/test"));
					
		int zDim = 1;
		int yDim = rows.size();
		int xDim = rows.get(0).length();
				
		var zyx = new char[zDim][yDim][xDim];
		
		for (int y=0; y<yDim; y++) {
			zyx[0][y] = rows.get(y).toCharArray();
		}
		
		System.out.println("Original");
		printCube(zyx);		
		
		var prevZyx = zyx; 
		for (int i=0; i<6; i++) {
			System.out.format("====Sim%s====\n",i);			
			var nextZyx = simNext(prevZyx);
			printCube(nextZyx);
			prevZyx = nextZyx;
		}		
		
		System.out.println("Count: " + countActive(prevZyx));
	}	
}
