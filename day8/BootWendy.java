import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



public class BootWendy {

	
	public void run1() throws IOException {

//		Program p = new Program(Files.readAllLines(Paths.get("./day8/test")).toArray(new String[0]));		
		Program p = new Program(Files.readAllLines(Paths.get("./day8/input")).toArray(new String[0]));
		System.out.println("Program hasInfLoop=" + p.hasInfLoop() + " and val=" + p.getValue());

	}		
		
	public void run2() throws IOException {

//		String[] code = Files.readAllLines(Paths.get("./day8/test")).toArray(new String[0]);
		String[] code = Files.readAllLines(Paths.get("./day8/input")).toArray(new String[0]);
		
		//super dumb bruteforcing!!
		for (int i=0; i<code.length; ) {
			String line = code[i];			
			System.out.println("Mutating line " + line);			
			String[] commands = line.split(" ");
			String op = commands[0];
			int num = Integer.parseInt(commands[1]);
			if (op.equals("jmp")) {
				String[] mutated = code.clone();
				mutated[i] = "nop " + num;
				Program p = new Program(mutated);
				if (!p.hasInfLoop()) {
					System.out.println("Found it, value=" + p.getValue());
					break;
				}
			} else if (op.equals("nop")) {
				String[] mutated = code.clone();
				mutated[i] = "jmp " + num;
				Program p = new Program(mutated);
				if (!p.hasInfLoop()) {
					System.out.println("Found it, value=" + p.getValue());
					break;
				}				
			} 
			i++;

		}						
		

	}		

	private class Program {
	
		private String[] code;
		private int[] lineExecCount;
		private int val = 0;
		boolean hasInfLoop = false;
		
		public Program(String[] code) {
			this.code = code;
			this.lineExecCount = new int[code.length];
			findInfLoop();
		}
		
		public boolean hasInfLoop() {
			return hasInfLoop;
		}
		
		public int getValue() {
			return val;
		}
		
		public void findInfLoop() {
			for (int i=0; i<code.length; ) {
				if (lineExecCount[i] != 0) {
					System.out.println("Repeating at line " + i + " with val " + val);
					hasInfLoop = true;
					return;
				}
				lineExecCount[i] = lineExecCount[i] + 1;
				String line = code[i];
				System.out.println("Executing " + line);			
				String[] commands = line.split(" ");
				String op = commands[0];
				int num = Integer.parseInt(commands[1]);
				if (op.equals("acc")) {
					val = val + num;
					i++;
				} else if (op.equals("jmp")) {
					i = i + num;
				} else {
					//nop or anything else
					i++;
				}
			}				
		}
	
	}
	
	
	public static void main(String[] args) throws IOException {
		BootWendy bw = new BootWendy();
		bw.run1();
		bw.run2();
	}

}
