import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SeatingSimWendy {

	//--helpers---
	public static void printMap(char[][] map) {
		for (int i=0; i<map.length; i++) {
			System.out.println(Arrays.toString(map[i]));
		}
	}
	
	public static boolean isIdentical(char[][] map1, char[][] map2) {
		for (int i=0; i<map1.length; i++) {
			int cmp = Arrays.compare(map1[i], map2[i]);
			if (cmp < 0 || cmp > 0) return false; 
		}
		return true;
	}
	
	
	public static boolean checkOccupied(char[][] map, int r, int c) {		
		if (r == -1 || r > map.length-1 || c == -1 || c > map[r].length-1) {
			return false;
		} else {
			char s = map[r][c];
			if (s == '#') {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public static boolean checkOccupiedRecurse(char[][] map, int r, int c, int rd, int cd) {
		if (r == -1 || r > map.length-1 || c == -1 || c > map[r].length-1) {
			return false;
		} else {
			char s = map[r][c];
			if (s == '#') {
				return true;
			} if (s == 'L') { //stop at first empty seat
				return false;
			} else {
				return checkOccupiedRecurse(map, r+rd, c+cd, rd, cd);
			}
		}
		
	}
	
	public static int checkNumOccupied(char[][] map, int r, int c) {
		int total = 0;
		if (checkOccupied(map, r-1, c-1)) total++;	//UL	
		if (checkOccupied(map, r-1, c)) total++; //U
		if (checkOccupied(map, r-1, c+1)) total++; //UR
		if (checkOccupied(map, r, c-1)) total++; //L
		if (checkOccupied(map, r, c+1)) total++; //R
		if (checkOccupied(map, r+1, c-1)) total++; //LL
		if (checkOccupied(map, r+1, c)) total++; //D
		if (checkOccupied(map, r+1, c+1)) total++;	//LR	
		return total;
	}
	
	public static int checkNumOccupied2(char[][] map, int r, int c) {
		int total = 0;
		if (checkOccupiedRecurse(map, r-1, c-1, -1,-1)) total++;	//UL	
		if (checkOccupiedRecurse(map, r-1, c, -1, 0)) total++; //U
		if (checkOccupiedRecurse(map, r-1, c+1, -1, 1)) total++; //UR
		if (checkOccupiedRecurse(map, r, c-1, 0, -1)) total++; //L
		if (checkOccupiedRecurse(map, r, c+1, 0, 1)) total++; //R
		if (checkOccupiedRecurse(map, r+1, c-1, 1, -1)) total++; //LL
		if (checkOccupiedRecurse(map, r+1, c, 1, 0)) total++; //D
		if (checkOccupiedRecurse(map, r+1, c+1, 1, 1)) total++;	//LR	
		return total;
	}
	
	public static char[][] simNext(char[][] map) {
		char[][] newMap = new char[map.length][];
		for (int r=0; r<map.length; r++) {
			newMap[r] = map[r].clone();
		}
		
		
		for (int r=0; r<map.length; r++) {
			for (int c=0; c<map[r].length; c++) {
				char s = map[r][c];
				if (s == 'L' && checkNumOccupied(map, r, c) == 0) {
//				    If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
					newMap[r][c] = '#';
				} else if (s == '#' && checkNumOccupied(map, r, c) >=4) {
//				    If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.					
					newMap[r][c] = 'L';
				}
//			    Otherwise, the seat's state does not change.				
			}
		}
		return newMap;
	}
	
	public static char[][] simNext2(char[][] map) {
		char[][] newMap = new char[map.length][];
		for (int r=0; r<map.length; r++) {
			newMap[r] = map[r].clone();
		}
				
		for (int r=0; r<map.length; r++) {
			for (int c=0; c<map[r].length; c++) {
				char s = map[r][c];
				if (s == 'L' && checkNumOccupied2(map, r, c) == 0) {
//				    If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
					newMap[r][c] = '#';
				} else if (s == '#' && checkNumOccupied2(map, r, c) >=5) {
//				    If a seat is occupied (#) and five or more seats adjacent to it are also occupied, the seat becomes empty.					
					newMap[r][c] = 'L';
				}
//			    Otherwise, the seat's state does not change.				
			}
		}
		return newMap;
	}	
	
	public static void main(String[] args) throws IOException {
//		List<String> rows = Files.readAllLines(Paths.get("./day11/test"));
		List<String> rows = Files.readAllLines(Paths.get("./day11/input"));
		int r=0;
		char[][] map = new char[rows.size()][rows.get(0).length()];
		for (String row: rows) {
			map[r] = row.toCharArray();
			r++;
		}
		printMap(map);

		char[][] prevMap = map;
		char[][] nextMap = simNext2(map);
		
		while (!isIdentical(prevMap,nextMap)) {
			System.out.println("------------");			
			printMap(nextMap);
			prevMap = nextMap;
			nextMap = simNext2(prevMap);
		}

		long numOccupied = Arrays.deepToString(nextMap).chars().filter(c->c=='#').count();
		System.out.println(numOccupied);
		
	}

}
