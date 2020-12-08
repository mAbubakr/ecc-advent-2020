import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BagsWendy {

    private Set<String> bags = new HashSet<String>();
    private String input;


    public BagsWendy(String input) throws IOException {
    	this.input = input;    	
    }
    
	public void searchUp(String bagColor) {
		Pattern pattern = Pattern.compile(".* " + bagColor + " bags?.*\\.");
		Matcher matcher = pattern.matcher(input);	
	
		matcher.results()
			.forEach(m -> {
				String line = m.group();
				String key = line.split("bags")[0].trim();
				bags.add(key);
				searchUp(key);
				
			});				
	}
	
	public int searchDown(String bagColor) {

		Pattern pattern = Pattern.compile(bagColor + " bags contain .*");
		Matcher matcher = pattern.matcher(input);	
	
		matcher.find();
		String line = matcher.group();
		
		pattern = Pattern.compile("(\\d) (\\w+ \\w+) bags?");
		
		matcher = pattern.matcher(line);
				
		int sum = 1;
		
		while (matcher.find()) {
			int num = Integer.parseInt(matcher.group(1).trim());
			String key = matcher.group(2).trim();
			int children = searchDown(key);
			sum = sum + (num * children);						
		}
		
		return sum;
				
	}
	
	public int getSize() {
		return bags.size();
	}
	
	public static void main(String[] args) throws IOException {
    	String input = Files.readString(Paths.get("./day7/input"));		
		
		BagsWendy bw = new BagsWendy(input);
		
		bw.searchUp("shiny gold");
		System.out.println("Number of top level bags containing shiny gold: " + bw.getSize());
		
		//-1 since we don't wanna count the original shiny gold
		System.out.println("Number of bags inside shiny gold: " + Integer.toString(bw.searchDown("shiny gold")-1));						
	}
	

}
