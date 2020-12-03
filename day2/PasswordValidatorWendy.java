import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidatorWendy {

	
	public static void main(String[] args) {
		List<String> entries = null;
		try {
			entries = Files.readAllLines(Paths.get("./day2/input"));
			
			int validCount1 = 0;
			int validCount2 = 0;
			
			for (String entry:entries) {				
				//parse the tokens
				Pattern pattern = Pattern.compile("(\\d+)-(\\d+) (\\w): (\\w+)");				
				Matcher matcher = pattern.matcher(entry);
				matcher.find();							
				int min = Integer.valueOf(matcher.group(1));
				int max = Integer.valueOf(matcher.group(2));
				char character = matcher.group(3).charAt(0); 				
				String pw = matcher.group(4);
				
				System.out.println("checking " + pw + " against pattern " + min + ", " + max + ", " + character);
				
				//part 1
				long numMatches = pw.chars().filter(ch -> ch == character).count();				
				if (numMatches >= min && numMatches <= max) validCount1++;
				
				//part 2
				if ( (pw.charAt(min-1) == character || 	pw.charAt(max-1) == character) 
						&& (pw.charAt(min-1) != pw.charAt(max-1)) ) {
					validCount2++;
				}				
			}
			
			System.out.println("validCount1=" + validCount1);
			System.out.println("validCount2=" + validCount2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
