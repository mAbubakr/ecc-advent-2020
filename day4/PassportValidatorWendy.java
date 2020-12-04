import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PassportValidatorWendy {

	public static final String[] REQUIRED_FIELDS = new String[] {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};
	public static final String[] OPTIONAL_FIELDS = new String[] {"cid"};
	
	public static boolean validate1(String cred) {
    	String[] fields = cred.split("\s|(\r\n)");
    	Set<String> keySet = new HashSet<String>();
    	Arrays.stream(fields).forEach(field -> keySet.add(field.split(":")[0]));

    	if (keySet.size() == REQUIRED_FIELDS.length) {
    		return Arrays.stream(REQUIRED_FIELDS).allMatch(r -> keySet.contains(r));
    	} else if (keySet.size() == REQUIRED_FIELDS.length + OPTIONAL_FIELDS.length) {
    		return Arrays.stream(REQUIRED_FIELDS).allMatch(r -> keySet.contains(r)) && 
    				Arrays.stream(OPTIONAL_FIELDS).allMatch(o -> keySet.contains(o));
    	} else {
    		return false;
    	}
	}
	
	public static boolean checkByr(String value) {
		return value.matches("\\d\\d\\d\\d") 
		&& Integer.parseInt(value) >= 1920 
		&& Integer.parseInt(value) <= 2002;		
	}
	
	public static boolean checkIyr(String value) {
		return value.matches("\\d\\d\\d\\d") 
				&& Integer.parseInt(value) >= 2010 
				&& Integer.parseInt(value) <= 2020;	
	}	
	
	public static boolean checkEyr(String value) {
		return value.matches("\\d\\d\\d\\d") 
				&& Integer.parseInt(value) >= 2020 
				&& Integer.parseInt(value) <= 2030;
	}		
	
	public static boolean checkHgt(String value) {
		if (value.endsWith("cm")) {
			value = value.substring(0, value.length() - 2);
			return value.matches("\\d+")
				&& Integer.parseInt(value) >= 150
				&& Integer.parseInt(value) <= 193;
		} else if (value.endsWith("in")) {
			value = value.substring(0, value.length() - 2);
			return value.matches("\\d+")
				&& Integer.parseInt(value) >= 59
				&& Integer.parseInt(value) <= 76;    					
		} else {
			return false;
		}		
	}
	
	public static boolean checkHcl(String value) {
		return value.matches("#[0-9a-f]{6}"); 
	}		
	
	public static boolean checkEcl(String value) {
		return value.matches("(amb|blu|brn|gry|grn|hzl|oth)"); 
	}		
	
	public static boolean checkPid(String value) {
		return value.matches("\\d{9}");
	}		
	
	public static boolean validate2(String cred) {
		if (!validate1(cred)) return false;
		
    	String[] fields = cred.split("\s|(\r\n)");
    	for (String field: fields) {
    		String[] pair = field.split(":");
    		if (pair.length != 2) return false;
    		String key = pair[0].trim();
    		String value = pair[1].trim();
    		    			
    		//play with new switch expression!
    		boolean valid = switch (key) {
    			case "byr" -> {	yield checkByr(value); }
    			case "iyr" -> { yield checkIyr(value); }
    			case "eyr" -> { yield checkEyr(value); }
    			case "hgt" -> { yield checkHgt(value); }
    			case "hcl" -> { yield checkHcl(value); }
    			case "ecl" -> { yield checkEcl(value); }
    			case "pid" -> { yield checkPid(value); }    
    			case "cid" -> { yield true; }
    			default -> { yield false; }
    		};
    		
    		if (!valid) return false;
    	}
    	    	
    	return true;
		
	}
	
	public static int validateBatch(String input) {
	    String[] creds = input.split("\n\\s*\n");
	    System.out.println("Number of creds: " + creds.length);
	    int numValid = 0;
	    
	    for (String cred: creds) {
	    	System.out.println("----"); 
	    	System.out.println(cred);
	    	boolean valid = validate2(cred);
	    	System.out.println(valid);
	    	if (valid) numValid++;
	    }		
	    
	    return numValid;
	}
	
	public static void main(String[] args) throws IOException {				
	    assert checkByr("2002");
	    assert !checkByr("2003");
	    assert checkHgt("60in");
	    assert checkHgt("190cm");
	    assert !checkHgt("190in");
	    assert !checkHgt("190");
	    assert checkHcl("#123abc");
	    assert !checkHcl("#123abz");
	    assert !checkHcl("123abc");
	    assert checkEcl("brn");
	    assert !checkEcl("wat");	    
	    assert checkPid("000000001");
	    assert !checkPid("0123456789");
	    
		//invalid
		String input = "eyr:1972 cid:100\r\n"
				+ "hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926\r\n"
				+ "\r\n"
				+ "iyr:2019\r\n"
				+ "hcl:#602927 eyr:1967 hgt:170cm\r\n"
				+ "ecl:grn pid:012533040 byr:1946\r\n"
				+ "\r\n"
				+ "hcl:dab227 iyr:2012\r\n"
				+ "ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277\r\n"
				+ "\r\n"
				+ "hgt:59cm ecl:zzz\r\n"
				+ "eyr:2038 hcl:74454a iyr:2023\r\n"
				+ "pid:3556412378 byr:2007";
		
		assert validateBatch(input) == 0;
		
		//valid
		input = "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980\r\n"
				+ "hcl:#623a2f\r\n"
				+ "\r\n"
				+ "eyr:2029 ecl:blu cid:129 byr:1989\r\n"
				+ "iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm\r\n"
				+ "\r\n"
				+ "hcl:#888785\r\n"
				+ "hgt:164cm byr:2001 iyr:2015 cid:88\r\n"
				+ "pid:545766238 ecl:hzl\r\n"
				+ "eyr:2022\r\n"
				+ "\r\n"
				+ "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719";
		
		assert validateBatch(input) == 4;
		
		input = Files.readString(Paths.get("./day4/input"));
		System.out.println("Answer is " + validateBatch(input));
	}

}
