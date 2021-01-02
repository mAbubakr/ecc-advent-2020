import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CCWendy {



	public static char[][][][] growCube(char[][][][] wzyx) {
		int wDim = wzyx.length;
		int zDim = wzyx[0].length;
		int yDim = wzyx[0][0].length;
		int xDim = wzyx[0][0][0].length;
//		System.out.format("Incoming dimension = %s x %s x %s\n", xDim, yDim, zDim);
		
		int newWDim = wDim + 2;
		int newZDim = zDim + 2;
		int newYDim = yDim + 2;		
		int newXDim = xDim + 2;		

		char[][][][] newWzyx = new char[newWDim][newZDim][newYDim][newXDim];
		
		//fill the new Ws at the beginning and end
		for (int z=0; z<newZDim; z++) {
			for (int y=0; y<newYDim; y++) {
				Arrays.fill(newWzyx[0][z][y], '.');
				Arrays.fill(newWzyx[newWDim-1][z][y], '.');
			}
		}
		
		//loop through incoming array		
		for (int w=0; w<wDim; w++) {
			//w in old -> w+1 in new
			//fill the new Zs in existing Ws at beginning and end			
			for (int y=0; y<newYDim; y++) {
				Arrays.fill(newWzyx[w+1][0][y], '.');
				Arrays.fill(newWzyx[w+1][newZDim-1][y], '.');
			}
			
			for (int z=0; z<zDim; z++) {
				//z in old -> z+1 in new
				
				//fill the new Ys at beginning and ned
				Arrays.fill(newWzyx[w+1][z+1][0], '.');
				Arrays.fill(newWzyx[w+1][z+1][newYDim-1], '.');			
				
				//loop through y
				for (int y=0; y<yDim; y++) {
					char[] xList = wzyx[w][z][y];
					//y in old -> y+1 in new
					
					//fill the new X at beginning and end
					newWzyx[w+1][z+1][y+1][0] = '.';
					newWzyx[w+1][z+1][y+1][newXDim-1] = '.';
					
					System.arraycopy(xList, 0, newWzyx[w+1][z+1][y+1], 1, xList.length);
				}
			}						
		}						
		
		return newWzyx;		
	}	
	
	public static char getState(char[][][][] wzyx, int w, int z, int y, int x) {
		int wDim = wzyx.length;
		int zDim = wzyx[0].length;
		int yDim = wzyx[0][0].length;
		int xDim = wzyx[0][0][0].length;
		
		if (x < 0 || x >= xDim || y < 0 || y >= yDim || z < 0 || z >= zDim || w < 0 || w >= wDim) return '.';
		
		return wzyx[w][z][y][x];
	}
	
	public static int findNumActiveNeighbors(char[][][][] wzyx, int x, int y, int z, int w) {		
		int sum=0;
		
		for (int wCount=w-1; wCount<=w+1; wCount++) {
			for (int zCount=z-1; zCount<=z+1; zCount++) {
				for (int yCount=y-1; yCount<=y+1; yCount++) {
					for (int xCount=x-1; xCount<=x+1; xCount++) {
						if (wCount == w && zCount==z && yCount==y && xCount==x) continue;
						if (getState(wzyx, wCount, zCount, yCount, xCount) == '#') sum += 1;
					}
				}
			}
			
		}
		
//		System.out.format("Active neighbors of %s,%s,%s is %s\n", x, y, z, sum);
		return sum;
	}
	
	public static char[][][][] cloneCube(char[][][][] wzyx) {
		char[][][][] newWzyx = new char[wzyx.length][wzyx[0].length][wzyx[0][0].length][wzyx[0][0][0].length];
		for (int w=0; w<wzyx.length; w++) {
			for (int z=0; z<wzyx[w].length; z++) {					
				for (int y=0; y<wzyx[w][z].length; y++) {
					for (int x=0; x<wzyx[w][z][y].length; x++) {
						newWzyx[w][z][y][x] = wzyx[w][z][y][x];	
					}
				}				
			}			
		}
		return newWzyx;
	}
	
	//todo
	public static char[][][][] simNext(char[][][][] wzyx) {
		char[][][][] prevWzyx = growCube(wzyx);
		char[][][][] newWzyx = cloneCube(prevWzyx);
		
//		System.out.println("After growing...");
//		printCube(newWzyx);
		for (int w=0; w<prevWzyx.length; w++) {
			for (int z=0; z<prevWzyx[w].length; z++) {
				char[][] yx = prevWzyx[w][z];
				for (int y=0; y<yx.length; y++) {
					char[] xList = yx[y];
					for (int x=0; x<xList.length; x++) {
						char currentState = xList[x];
//						System.out.format("Current is %s, %s, %s and state is %s\n", x, y, z, currentState);
						int numActiveNeighbors = findNumActiveNeighbors(prevWzyx, x, y, z, w);
						if (currentState == '#') {
							if (numActiveNeighbors == 2 || numActiveNeighbors == 3) {
								//remain active
							} else {
								//turn inactive
								newWzyx[w][z][y][x] = '.';
							}
						} else {
							if (numActiveNeighbors == 3) {
								//turn active
								newWzyx[w][z][y][x] = '#';
							}
							//else remain inactive
						}
					}
				}
					
			}	
			
		}
		return newWzyx;
	}
		
	public static void printCube(char[][][][] wzyx) {
		for (int w=0; w<wzyx.length; w++) {
			for (int z=0; z<wzyx[w].length; z++) {			
				System.out.format("====Z=%s, W=%s======\n",z, w);			
				for (int y=0; y<wzyx[w][z].length; y++) {
					System.out.println(wzyx[w][z][y]);
				}				
			}			
		}
	}	

	public static int countActive(char[][][][] wzyx) {
		int num=0;
		for (int w=0; w<wzyx.length; w++) {
			for (int z=0; z<wzyx[w].length; z++) {					
				for (int y=0; y<wzyx[w][z].length; y++) {
					for (int x=0; x<wzyx[w][z][y].length; x++) {
						if (wzyx[w][z][y][x] == '#') num++;
					}
				}				
			}			
		}
		return num;
	}
	
	public static void main(String[] args) throws IOException {
		List<String> rows = Files.readAllLines(Paths.get("./day17/input"));
		
		int wDim = 1;
		int zDim = 1;
		int yDim = rows.size();
		int xDim = rows.get(0).length();
				
		var wzyx = new char[wDim][zDim][yDim][xDim];
		
		for (int y=0; y<yDim; y++) {
			wzyx[0][0][y] = rows.get(y).toCharArray();
		}
		
		System.out.println("Original");
		printCube(wzyx);		
		
		var prevWzyx = wzyx; 
		for (int i=0; i<6; i++) {
			System.out.format("====Sim%s====\n",i);			
			var nextWzyx = simNext(prevWzyx);
			printCube(nextWzyx);
			prevWzyx = nextWzyx;
		}		
		
		System.out.println("Count: " + countActive(prevWzyx));
	}	
}
