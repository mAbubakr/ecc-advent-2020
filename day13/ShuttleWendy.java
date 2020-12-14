import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ShuttleWendy {

	public void run1() throws IOException {
		List<String> input = Files.readAllLines(Paths.get("./day13/input"));
		int startTime = Integer.parseInt(input.get(0));
		int[] buses = Arrays.stream(input.get(1)
				.replaceAll(",x", "")
				.split(","))
				.mapToInt(Integer::valueOf)
				.sorted().toArray();
		System.out.println(startTime);
		System.out.println(Arrays.toString(buses));
		int foundTime = -1;
		int foundBus = -1;
		int tryTime = startTime;
		while (foundTime == -1) {
			for (int busId: buses) {
				if (tryTime % busId == 0) {
					foundTime = tryTime;
					foundBus = busId;
					break;
				}
			}
			tryTime++;
		}
		
		//Multiplying the bus ID by the number of minutes you'd need to wait 
		int ans = foundBus * (foundTime-startTime);		
		System.out.println("Bus=" + foundBus + ", time=" + foundTime + ",ans=" + ans);
	}	
	
	//Shameless cut and paste from https://www.geeksforgeeks.org/chinese-remainder-theorem-set-2-implementation/	
	// Returns modulo inverse of a  
    // with respect to m using extended 
    // Euclid Algorithm. Refer below post for details: 
    // https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/ 
    static long inv(long a, long m) 
    { 
    	long m0 = m, t, q; 
        long x0 = 0, x1 = 1; 
      
        if (m == 1) 
        return 0; 
      
        // Apply extended Euclid Algorithm 
        while (a > 1) 
        { 
            // q is quotient 
            q = a / m; 
      
            t = m; 
      
            // m is remainder now, process 
            // same as euclid's algo 
            m = a % m;a = t; 
      
            t = x0; 
      
            x0 = x1 - q * x0; 
      
            x1 = t; 
        } 
      
        // Make x1 positive 
        if (x1 < 0) 
        x1 += m0; 
      
        return x1; 
    } 
      
    // k is size of num[] and rem[]. 
    // Returns the smallest number 
    // x such that: 
    // x % num[0] = rem[0], 
    // x % num[1] = rem[1], 
    // .................. 
    // x % num[k-2] = rem[k-1] 
    // Assumption: Numbers in num[] are pairwise  
    // coprime (gcd for every pair is 1) 
    static long findMinX(int num[], int rem[], int k) 
    { 
        // Compute product of all numbers 
        long prod = 1; 
        for (int i = 0; i < k; i++) 
            prod *= num[i]; 
      
        // Initialize result 
        long result = 0; 
      
        // Apply above formula 
        for (int i = 0; i < k; i++) 
        { 
        	long pp = prod / num[i]; 
            result += rem[i] * inv(pp, num[i]) * pp; 
        } 
      
        return result % prod; 
    } 
	
	public void run2() throws IOException {
		List<String> input = Files.readAllLines(Paths.get("./day13/input"));
		String[] buses = input.get(1).split(",");
		int[] busId = new int[buses.length];
		int[] busRemainder = new int[buses.length];
		for (int i=0; i<buses.length; i++) {			
			if (buses[i].equals("x")) {
				busId[i] = 1;
				busRemainder[i] = 0;
			} else {
				busId[i] = Integer.parseInt(buses[i]);
				//because we the time is t+busId, not t-busId.. take the other half of the modulo
				busRemainder[i] = busId[i]-i;
			}

		}
		
		System.out.println(Arrays.toString(busId));
		System.out.println(Arrays.toString(busRemainder));

	    System.out.println("x is " + findMinX(busId, busRemainder, busId.length)); 
	     
		

	}	
	
	public static void main(String[] args) throws IOException {
		ShuttleWendy sw = new ShuttleWendy();
//		sw.run1();
		sw.run2();
	}

}
