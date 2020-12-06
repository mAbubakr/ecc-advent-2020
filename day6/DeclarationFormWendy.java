import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DeclarationFormWendy {

	public static long calculate1(String group) {
		String concat = group.replaceAll( System.lineSeparator(), "").trim();
		return concat.chars()
				.distinct()
				.count();
	}

	public static long calculate2(String group) {		
		String concat = group.replaceAll( System.lineSeparator(), "").trim();
		
		//play with map merging
		Map<Character, Integer> freqs = new HashMap<>();
		for (char c: concat.toCharArray()) {
			freqs.merge(c, 1, Integer::sum);
		}

		System.out.println("Frequencies:\n" + freqs);
		
		//stream, filter and count
		int groupSize = (int)group.lines().count();
		long count = freqs.entrySet()
			.stream()
			.filter(entry -> entry.getValue().intValue() == groupSize)
			.count();

		System.out.println(count);
		return count;			
	}
	
	public static long run(String input) {
	    String[] groups = input.split("\n\\s*\n");
	    System.out.println("Number of groups: " + groups.length);
	    long sum = 0;
	    
	    for (String group: groups) {
	    	System.out.println("----"); 
	    	System.out.println(group);
	    	sum = sum + calculate2(group);
	    }		
	    
	    return sum;
	}
	
	public static void main(String[] args) throws IOException {
		String input = "abc\r\n"
				+ "\r\n"
				+ "a\r\n"
				+ "b\r\n"
				+ "c\r\n"
				+ "\r\n"
				+ "ab\r\n"
				+ "ac\r\n"
				+ "\r\n"
				+ "a\r\n"
				+ "a\r\n"
				+ "a\r\n"
				+ "a\r\n"
				+ "\r\n"
				+ "b";
		
		assert run(input) == 6;
		
		
		input = Files.readString(Paths.get("./day6/input"));
		System.out.println("Answer is " + run(input));
	}

}
