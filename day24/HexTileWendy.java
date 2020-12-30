import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HexTileWendy {

	/**
	 *      -1,1      1,1
	 *  -2,0      0,0     2,0
	 *     -1,-1      1,-1
	 */
	
	//"x,y" -> isBlack
	Map<String, Boolean> tilesToBlack;
	
	public HexTileWendy(List<String> input) {
		tilesToBlack = new HashMap<>();
		for(String line: input) {		
			parse(line);
		}
	}
	
	private void parse(String line) {
		int x=0; 
		int y=0;
		//e, se, sw, w, nw, and ne
		char[] charArr = line.toCharArray();
		for (int i=0; i<charArr.length; i++) {
			if (charArr[i] == 'e') {
				x = x+2;				
			} else if (charArr[i] == 's') {
				i++;
				char next = charArr[i];
				if (next == 'e') { //se
					x = x+1;
					y = y-1;
				} else { //sw
					x = x-1;
					y = y-1;
				}
			} else if (charArr[i] == 'w') {
				x = x-2;
			} else { //n
				i++;
				char next = charArr[i];
				if (next == 'e') { //ne
					x = x+1;
					y = y+1;
				} else { //nw
					x = x-1;
					y = y+1;
				}				
			}
		}
		
		String tile = x + "," + y;
		if (tilesToBlack.containsKey(tile)) {
			boolean isBlack = tilesToBlack.get(tile);
			tilesToBlack.put(tile, !isBlack);
		} else { //did not contain before - originally they're all white, flip to black
			tilesToBlack.put(tile, true);
		}				
	}
	
	private int countBlackNeighbors(int x, int y) {
		int count = 0;
		if (tilesToBlack.getOrDefault((x+1) + "," + (y+1), false)) count++; 
		if (tilesToBlack.getOrDefault((x+2) + "," + (y+0), false)) count++;
		if (tilesToBlack.getOrDefault((x+1) + "," + (y-1), false)) count++;
		if (tilesToBlack.getOrDefault((x-1) + "," + (y-1), false)) count++;
		if (tilesToBlack.getOrDefault((x-2) + "," + (y+0), false)) count++;
		if (tilesToBlack.getOrDefault((x-1) + "," + (y+1), false)) count++;
		return count;
	}
	
	private void fillGaps() {
		Map<String, Boolean> newMap = new HashMap<>();
		
		tilesToBlack.entrySet().stream().forEach(e-> {
			String tile = e.getKey();
			newMap.put(tile, e.getValue());
			String[] coord = tile.split(",");
			int x = Integer.parseInt(coord[0]);
			int y = Integer.parseInt(coord[1]);
			if (!tilesToBlack.containsKey((x+1) + "," + (y+1))) newMap.put((x+1) + "," + (y+1), false); 
			if (!tilesToBlack.containsKey((x+2) + "," + (y+0))) newMap.put((x+2) + "," + (y+0), false); 
			if (!tilesToBlack.containsKey((x+1) + "," + (y-1))) newMap.put((x+1) + "," + (y-1), false); 
			if (!tilesToBlack.containsKey((x-1) + "," + (y-1))) newMap.put((x-1) + "," + (y-1), false); 
			if (!tilesToBlack.containsKey((x-2) + "," + (y+0))) newMap.put((x-2) + "," + (y+0), false); 
			if (!tilesToBlack.containsKey((x-1) + "," + (y+1))) newMap.put((x-1) + "," + (y+1), false); 
			
		});		
		
		tilesToBlack = newMap;
	}
	
	public void sim() {
		fillGaps();
		
		Map<String, Boolean> newMap = new HashMap<>();
		
		tilesToBlack.entrySet().stream().forEach(e-> {
			String tile = e.getKey();
			boolean isBlack = e.getValue();
			String[] coord = tile.split(",");
			int x = Integer.parseInt(coord[0]);
			int y = Integer.parseInt(coord[1]);
			int blackNeighbors = countBlackNeighbors(x, y);			
			if (isBlack) {
			    //Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.	
				if (blackNeighbors == 0 || blackNeighbors > 2 ) {
					newMap.put(tile, false);
				} else {
					newMap.put(tile, true);
				}				
			} else {
			    //Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
				if (blackNeighbors == 2) {
					newMap.put(tile, true);
				} else {
					newMap.put(tile, false);
				}
			}
		});

		tilesToBlack = newMap;

	}
	
	public long countBlack() {
		return tilesToBlack.values().stream().filter(v -> v == Boolean.TRUE).count();
	}
	
	public static void main(String[] args) throws IOException {
		HexTileWendy htw = new HexTileWendy(Files.readAllLines(Paths.get("./day24/test")));
		assert htw.countBlack() == 10;
		htw = new HexTileWendy(Files.readAllLines(Paths.get("./day24/input")));
		System.out.println("Part 1: " + htw.countBlack());		

		htw = new HexTileWendy(Files.readAllLines(Paths.get("./day24/test")));
		for (int i=0; i<100; i++) {
			htw.sim();
			System.out.format("Day %s: %s\n", i+1, htw.countBlack());
		}
		assert htw.countBlack() == 2208;

		htw = new HexTileWendy(Files.readAllLines(Paths.get("./day24/input")));
		System.out.println("Part 1: " + htw.countBlack());		
		for (int i=0; i<100; i++) {
			htw.sim();
			System.out.format("Day %s: %s\n", i+1, htw.countBlack());
		}
		System.out.println("Part 2: " + htw.countBlack());		
		
	}

}
