import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AdapterChainWendy {

	public  void run(int[] input) {
		int device = input[input.length-1] + 3;
		System.out.println("device=" + device);
		int outlet=0;
		
		List<Integer> diffs = new ArrayList<Integer>();
		diffs.add(input[0] - outlet);
		for (int i=0; i<input.length-1; i++) {
			diffs.add(input[i+1] - input[i]);
		}
		diffs.add(3);
		long countOne = diffs.stream().filter(a -> a ==1).count();
		long countThree = diffs.stream().filter(a -> a ==3).count();
		System.out.println(countOne + " and " + countThree);		
	}
	
	private class Graph { 	  
	  
	    private LinkedList<Integer>[] adj; 
	   
	    private long[][] countCache;
	    
	    public Graph(int numVertices) {	    
	        countCache = new long[numVertices][numVertices];
	        adj = new LinkedList[numVertices]; 
	        for (int i = 0; i < numVertices; ++i) {
	            adj[i] = new LinkedList<>(); 
	        }
	    } 
	   
	    public void addEdge(int from, int to) {
	        adj[from].add(to); 
	    } 
	  
	    private long countRecurse(int start, int dest, long pathCount) {	    
	  
	    	if (countCache[start][dest] != 0) return countCache[start][dest];

	        if (start == dest) { 
	            pathCount++; 
	        } else { 
	            Iterator<Integer> i = adj[start].listIterator(); 
	            while (i.hasNext()) { 
	                int n = i.next(); 
	            	System.out.println("From " + start + " to " + n);	                
	                pathCount = pathCount + countRecurse(n, dest, pathCount); 
	            } 
	        } 
	        System.out.println("Count from " + start + " to " + dest + " is " + pathCount);
	        countCache[start][dest] = pathCount;
	        return pathCount; 
	    } 
	  
	    public long countPaths(int start, int dest) {
	        return countRecurse(start, dest, 0);
	    }
	    
	}
    
	public void run2(int[] input) {


		int device = input[input.length-1] + 3;
		
		Graph g = new Graph(device + 1);
		
		Arrays.stream(input)
			.takeWhile(c -> c <=3)
			.forEach(c -> g.addEdge(0, c));

		
		for (int i=0; i<input.length-1; i++) {
			int val = input[i];
			Arrays.stream(input)
				.dropWhile(c -> c <= val)
				.takeWhile(c -> c-val <=3)
				.forEach(c -> g.addEdge(val,  c));					
		}

		Arrays.stream(input)
			.dropWhile(c -> c < device-3)
			.forEach(c -> g.addEdge(c,  device));
		
		System.out.println(g.countPaths(0, device));
	}
	
	public static void main(String[] args) throws IOException {
		AdapterChainWendy acw = new AdapterChainWendy();
		acw.run(Files.readAllLines(Paths.get("./day10/test1")).stream().mapToInt(Integer::valueOf).sorted().toArray());
		acw.run(Files.readAllLines(Paths.get("./day10/test2")).stream().mapToInt(Integer::valueOf).sorted().toArray());
		acw.run(Files.readAllLines(Paths.get("./day10/input")).stream().mapToInt(Integer::valueOf).sorted().toArray());
		
		acw.run2(Files.readAllLines(Paths.get("./day10/test1")).stream().mapToInt(Integer::valueOf).sorted().toArray());
		acw.run2(Files.readAllLines(Paths.get("./day10/test2")).stream().mapToInt(Integer::valueOf).sorted().toArray());
		acw.run2(Files.readAllLines(Paths.get("./day10/input")).stream().mapToInt(Integer::valueOf).sorted().toArray());		
	}

}
