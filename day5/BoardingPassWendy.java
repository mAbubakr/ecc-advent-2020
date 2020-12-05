import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BoardingPassWendy {

	private static int MAX_ROWS;
	private static int MAX_COLS;
	
	private int[][] seatMap;
	
	public BoardingPassWendy(int rows, int cols) {
		MAX_ROWS = rows;
		MAX_COLS = cols;
		seatMap = new int[rows+1][cols+1];
	}
	
	public void run() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./day5/input"));
		List<Seat> seats = new ArrayList<Seat>();
		lines.stream().forEach(l -> {
			Seat s = new Seat(l);
			seats.add(s);
			seatMap[s.row][s.col] = s.id;
		});
		
		Optional<Seat> maxSeat = seats.stream().max(Comparator.comparingInt(Seat::getID));
		Optional<Seat> minSeat = seats.stream().min(Comparator.comparingInt(Seat::getID));

		System.out.println("Max ID is " + maxSeat.get().id);
		
		for (int r=0; r<=MAX_ROWS; r++) {
			for (int c=0; c<=MAX_COLS; c++) {
				if (seatMap[r][c] == 0) {				
					int id = r * 8 + c;
					if (id > minSeat.get().id && id < maxSeat.get().id) {
						System.out.println(r + ", " + c + ", " + id + " is mine!");						
					}
				}
			}
		}
		
	}
	

	private class Seat  {
		private int row;
		private int col;
		private int id;
		
 		public Seat(String seq) {
 			row = findIndex(seq.substring(0, seq.length() - 3), 0, BoardingPassWendy.MAX_ROWS);
 			col = findIndex(seq.substring(seq.length() - 3, seq.length()), 0, BoardingPassWendy.MAX_COLS);
 			id = row * 8 + col;
// 			System.out.println(row + ", " + col + ", " + id);
 			
 		}
 		
 		private int findIndex(String seq, int min, int max) {
// 			System.out.println(seq + ", " + min + ", " + max);
 			char c = seq.charAt(0);
 			return switch(c) {
	 			case 'F', 'L' -> {
	 				if (seq.length() == 1) yield min;
	 				else yield findIndex(seq.substring(1), min, min + (max - min) / 2); 
	 			}
	 			case 'B', 'R' -> { 
	 				if (seq.length() == 1) yield max;
	 				else yield findIndex(seq.substring(1), min + (max - min) / 2 + 1, max); 
	 			}
	 			default -> { 
	 				System.out.println("Wrong encoding character " + c); 
	 				yield -999;
	 			}
 			};
 		}
 		
 		public int getID() {
 			return id;
 		} 	
 		
 		
	}
	
	
	public void test() {
		Seat s = new Seat("FBFBBFFRLR");
		assert s.row == 44;
		assert s.col == 5;
		assert s.id == 357;		
		
		s = new Seat("BFFFBBFRRR");
		assert s.row == 70;
		assert s.col == 7;
		assert s.id == 567;
		

		s = new Seat("FFFBBBFRRR");
		assert s.row == 14;
		assert s.col == 7;
		assert s.id == 119;

		s = new Seat("BBFFBBFRLL");
		assert s.row == 102;
		assert s.col == 4;
		assert s.id == 820;

	}	
	
	public static void main(String[] args) throws IOException {
		BoardingPassWendy bpw = new BoardingPassWendy(127, 7);
		bpw.test();
		bpw.run();
	}
	
}
