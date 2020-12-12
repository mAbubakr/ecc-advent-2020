import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NavigateWendy {

	public enum Direction { N, E, S, W }
	
	public void run1() throws IOException {

//		Chart c = new Chart(Files.readAllLines(Paths.get("./day12/test")).toArray(new String[0]), Direction.E);
		Chart c = new Chart(Files.readAllLines(Paths.get("./day12/input")).toArray(new String[0]), Direction.E);
		c.move();
		System.out.println("Chart's x=" + c.getX() + ", y=" + c.getY() + ", distance=" + c.getDistance());
	}	

	public void run2() throws IOException {

//		Chart c = new Chart(Files.readAllLines(Paths.get("./day12/test")).toArray(new String[0]), Direction.E);
		Chart c = new Chart(Files.readAllLines(Paths.get("./day12/input")).toArray(new String[0]), Direction.E);
		c.move2();
		System.out.println("Chart's x=" + c.getX() + ", y=" + c.getY() + ", distance=" + c.getDistance());
	}	

	private class Chart {
		
		private String[] instr;
		private int x = 0; //assume ship starts at 0,0
		private int y = 0; 
		private int waypoint_x = 10; //hard-code waypoint at 10, 1
		private int waypoint_y = 1;
		
		private Direction orientation;
		
		public Chart(String[] instr, Direction orientation) {
			this.instr = instr;
			this.orientation = orientation;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public int getDistance() {
			return Math.abs(x) + Math.abs(y);
		}		
		

//	    Action N means to move north by the given value.
//	    Action S means to move south by the given value.
//	    Action E means to move east by the given value.
//	    Action W means to move west by the given value.
//	    Action L means to turn left the given number of degrees.
//	    Action R means to turn right the given number of degrees.
//	    Action F means to move forward by the given value in the direction the ship is currently facing.
		public void move() {
			for (int i=0; i<instr.length; i++ ) {
				String line = instr[i];
				System.out.println("Executing " + line);	
				char dir = line.charAt(0);
				int num = Integer.parseInt(line.substring(1));
				
				switch (dir) {
					case 'N' -> { y = y + num; }
					case 'S' -> { y = y - num; }
					case 'E' -> { x = x + num; } 
					case 'W' -> { x = x - num; }
					case 'R' -> { 
						switch (orientation) {
							case E -> { 
								switch (num) {
									case 90 -> { orientation = Direction.S; }
									case 180 -> { orientation = Direction.W; }
									case 270 -> { orientation = Direction.N; }
									default -> { System.err.println("Unexpeced degree: " + num); }
								}
							}
							case S -> {
								switch (num) {
									case 90 -> { orientation = Direction.W; }
									case 180 -> { orientation = Direction.N; }
									case 270 -> { orientation = Direction.E; }
									default -> { System.err.println("Unexpeced degree: " + num); }
								}							
							}
							case W -> {
								switch (num) {
									case 90 -> { orientation = Direction.N; }
									case 180 -> { orientation = Direction.E; }
									case 270 -> { orientation = Direction.S; }
									default -> { System.err.println("Unexpeced degree: " + num); }
								}							
							}
							case N -> {
								switch (num) {
									case 90 -> { orientation = Direction.E; }
									case 180 -> { orientation = Direction.S; }
									case 270 -> { orientation = Direction.W; }
									default -> { System.err.println("Unexpeced degree: " + num); }
								}							
							}							
						}
					}
					case 'L' -> { 
						switch (orientation) {
							case E -> { 
								switch (num) {
									case 90 -> { orientation = Direction.N; }
									case 180 -> { orientation = Direction.W; }
									case 270 -> { orientation = Direction.S; }
									default -> { System.err.println("Unexpeced degree: " + num); }
								}
							}
							case S -> {
								switch (num) {
									case 90 -> { orientation = Direction.E; }
									case 180 -> { orientation = Direction.N; }
									case 270 -> { orientation = Direction.W; }
									default -> { System.err.println("Unexpeced degree: " + num); }
								}							
							}
							case W -> {
								switch (num) {
									case 90 -> { orientation = Direction.S; }
									case 180 -> { orientation = Direction.E; }
									case 270 -> { orientation = Direction.N; }
									default -> { System.err.println("Unexpeced degree: " + num); }
								}							
							}
							case N -> {
								switch (num) {
									case 90 -> { orientation = Direction.W; }
									case 180 -> { orientation = Direction.S; }
									case 270 -> { orientation = Direction.E; }
									default -> { System.err.println("Unexpeced degree: " + num); }
								}							
							}							
						}
					}
					case 'F' -> { 
						switch (orientation) {
							case N -> { y = y + num; }
							case S -> { y = y - num; }
							case E -> { x = x + num; } 
							case W -> { x = x - num; }
						}						
					} 
				};
				
			}				
		}
		

//	    Action N means to move the waypoint north by the given value.
//	    Action S means to move the waypoint south by the given value.
//	    Action E means to move the waypoint east by the given value.
//	    Action W means to move the waypoint west by the given value.
//	    Action L means to rotate the waypoint around the ship left (counter-clockwise) the given number of degrees.
//	    Action R means to rotate the waypoint around the ship right (clockwise) the given number of degrees.
//	    Action F means to move forward to the waypoint a number of times equal to the given value.
		
		public void move2() {
			for (int i=0; i<instr.length; i++ ) {
				String line = instr[i];
				System.out.println("waypoint_x=" + waypoint_x + ", waypoint_y=" + waypoint_y);						
				System.out.println("Executing " + line);		
				char dir = line.charAt(0);
				int num = Integer.parseInt(line.substring(1));
				
				switch (dir) {
					case 'N' -> { waypoint_y = waypoint_y + num; }
					case 'S' -> { waypoint_y = waypoint_y - num; }
					case 'E' -> { waypoint_x = waypoint_x + num; } 
					case 'W' -> { waypoint_x = waypoint_x - num; }
					case 'R' -> { 
						int new_x = 0;
						int new_y = 0;
						switch (num) {
							case 90 -> { new_y = -waypoint_x; new_x = waypoint_y;}
							case 180 -> { new_x = -waypoint_x; new_y = -waypoint_y;}
							case 270 -> { new_y = waypoint_x; new_x = -waypoint_y;}
							default -> { System.err.println("Unexpeced degree: " + num); }
						}
						waypoint_x = new_x;
						waypoint_y = new_y;
					}
					case 'L' -> { 
						int new_x = 0;
						int new_y = 0;
						switch (num) {
							case 90 -> { new_y = waypoint_x; new_x = -waypoint_y;}
							case 180 -> { new_x = -waypoint_x; new_y = -waypoint_y;}
							case 270 -> { new_y = -waypoint_x; new_x = waypoint_y; }
							default -> { System.err.println("Unexpeced degree: " + num); }
						}
						waypoint_x = new_x;
						waypoint_y = new_y;
					}
					case 'F' -> { 
						for (int k=0; k<num; k++) {
							x = x + waypoint_x;
							y = y + waypoint_y;
						}
					} 
				};

			}				
		}		
	
	}
	
	public static void main(String[] args) throws IOException {
		NavigateWendy nw = new NavigateWendy();
//		nw.run1();
		nw.run2();
	}

}
