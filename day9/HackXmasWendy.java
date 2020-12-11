import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;


public class HackXmasWendy {

	public static long findInvalid(int preamble, Long[] input) throws IOException {
		
		int foundIndex = -1;
		for (int candidate=preamble; candidate<input.length; candidate++) {
			int start = candidate-preamble;
			int end = candidate-1;
			System.out.println("candidate=" + candidate + ", start=" + start + ", end=" + end);
			var yayGenericSet = Set.of(Arrays.copyOfRange(input, start, end));
			boolean valid = false;
			for (int i=start; i<=end; i++) {				
				long target = input[candidate]-input[i];
				System.out.println("    target=" + target + ", wantTotal=" + input[candidate] + ", checking=" + input[i]);
				if (target == input[i]) continue;
				if (yayGenericSet.contains(target)) {
					valid = true;
					break;
				}
			}
			if (!valid) {
				foundIndex = candidate;
				break; 
			}
		}
		return input[foundIndex];

	}		
	
	public static long findMinMaxSumRegion(long target, Long[] input) throws IOException {		
		for (int start=0; start<input.length; start++) {
			long sum =0;
			for (int i=start; i<input.length; i++) {
				sum = sum + input[i];
				if (sum == target) {
					System.out.println("Found indices: " + start + " to " + i);
					var yayGenericSet = Set.of(Arrays.copyOfRange(input, start, i));					
					long answer = Collections.min(yayGenericSet) + Collections.max(yayGenericSet);
					return answer;
				}
			}
		}
		return -1;
	}		
	
	public static void main(String[] args) throws IOException {
		
		Long[] testInput = Files.readAllLines(Paths.get("./day9/test")).stream().map(Long::valueOf).toArray(Long[]::new);		
		long testInvalidVal = findInvalid(5, testInput);
		assert(testInvalidVal == 127);
		assert(findMinMaxSumRegion(testInvalidVal, testInput) == 62);
				
		Long[] input = Files.readAllLines(Paths.get("./day9/input")).stream().map(Long::valueOf).toArray(Long[]::new);	
		long invalidVal = findInvalid(25, input); 
		System.out.println("First invalid value is " + invalidVal);
		System.out.println("MinMaxSumRegion is " + findMinMaxSumRegion(invalidVal, input));
	}

}
