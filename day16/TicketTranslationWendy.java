import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class TicketTranslationWendy {
	
	public static int ticketScanningError = 0;
	//too lazy to pass this around anymore, public it is!
	public static List<String> fieldNames = new ArrayList<>();
	

	public static void main(String[] args) throws IOException {
		List<String> input = Files.readAllLines(Paths.get("./day16/input"));

		//Parse everything
		
		//field definition
		List<int[]> fieldRange = new ArrayList<>();			
		input.stream().takeWhile(e -> !e.startsWith("your")).forEach(e -> {
			System.out.println(e);	
			if (e.isBlank()) return;
			//parse the tokens
			Pattern pattern = Pattern.compile("(.*): (\\d+)-(\\d+) or (\\d+)-(\\d+)");				
			Matcher matcher = pattern.matcher(e);
			matcher.find();		
			String name = matcher.group(1);
			fieldNames.add(name);
			int min1 = Integer.valueOf(matcher.group(2));
			int max1 = Integer.valueOf(matcher.group(3));
			int min2 = Integer.valueOf(matcher.group(4));
			int max2 = Integer.valueOf(matcher.group(5));
			fieldRange.add(new int[] {min1, max1, min2, max2});
			System.out.format("name=%s, min1=%s, max1=%s, min2=%s, max2=%s\n", name, min1, max1, min2, max2);
			
		});
		System.out.format("Number of fields = %s\n", fieldRange.size());
		System.out.println("-------");
		//my ticket
		List<Integer> myTicket = new ArrayList<>();
		input.stream().dropWhile(e -> !e.startsWith("your")).takeWhile(e -> !e.startsWith("nearby")).forEach(e -> {
			if (e.startsWith("your") || e.isBlank()) return;
					
			Arrays.stream(e.split(",")).forEach(val -> {
				myTicket.add(Integer.parseInt(val));
			});
			System.out.println(e);	
		});
		System.out.println("-------");
		//nearby tickets
		List<String> validTickets = new ArrayList<String>();
		input.stream().dropWhile(e -> !e.startsWith("nearby")).forEach(e -> {
			if (e.startsWith("nearby") || e.isBlank()) return;

//			System.out.println(e);	
			if (isTicketValid(fieldRange, e)) validTickets.add(e);	
	
		});
		System.out.format("Ticket scanning error is %s\n", ticketScanningError);
		

		//collect all the numbers for each position together into a string for checking against fields
		String[] cols = new String[fieldRange.size()];
		
		for (int i=0; i< validTickets.size(); i++) {
			String ticket = validTickets.get(i);
			String[] fields = ticket.split(",");
			for (int j=0; j<fields.length; j++) {
				String prev = cols[j];
				if (prev == null || prev.isEmpty()) {
					cols[j] = fields[j];
				} else {
					cols[j] = prev + "," + fields[j];
				}				
			}			
		}
		
		//find all valid fields for each position
		TreeSet<Position> positionsSet = new TreeSet<Position>();		
		for (int i=0; i< cols.length; i++) {
//			System.out.println("Searching for position " + i);
			TreeSet<Integer> validFields = findValidFields(fieldRange, cols[i]);
			positionsSet.add(new Position(i, validFields));
		}
		
		
		//save the position info to use later
		List<Position> positionsList = new ArrayList<Position>();
		positionsList.addAll(positionsSet);
		
		//go through the sorted position objects (sorted by size of valid fields), remove the smallest one by one
		for (int i=0; i<cols.length; i++) {
			Position f = positionsSet.first();
			int field = f.getField();
			System.out.println(f.toString());
			positionsSet.remove(f);
			positionsSet.stream().forEach(p -> p.removeValidField(field));			
		}
		
		System.out.println("========");
		
		//stream magic!
		positionsList.stream()
			.filter(p -> p.getFieldName().startsWith("departure"))
			.forEach(p -> System.out.println(p.toString())); //sanity
		
		long product = positionsList.stream()
				.filter(p -> p.getFieldName().startsWith("departure"))
				.mapToLong(p -> myTicket.get(p.getField()))
				.reduce(1, (a,b) -> a*b);
		
		System.out.println("Answer is " + product);
	}
	
	
	//It is stupid that I need a class to deduce the field for each position.. but oh well 
	public static class Position implements Comparable<Position> {
		TreeSet<Integer> validFields;
		int index;
		int field = -1;
		
		public Position(int index, TreeSet<Integer> validFields) {
			this.index = index;
			this.validFields = validFields;
			if (this.validFields.size() == 1) {				
				field = validFields.first();
			}
		}
		
		public void removeValidField(int i) {
			validFields.remove(i);
			if (validFields.size() == 1) {
				field = validFields.first();				
			}
		}
		
		public int getField() {
			return field;
		}
		
		public int getValidFieldSize() {
			return validFields.size();
		}

		// negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
		public int compareTo(Position o) {
			return Integer.compare(getValidFieldSize(), o.getValidFieldSize());
		}		
		
		public String getFieldName() {
			return fieldNames.get(field);
		}
		
		
		public String toString() {
			return String.format("Position %s is Field \"%s\"", index, getFieldName());
		}
	}
	
	//helpers to deal with fields and ticket positions
	public static TreeSet<Integer> findValidFields(List<int[]> fieldRange, String col) {
		
		TreeSet<Integer> validFields = new TreeSet<Integer>();
		//for each fieldRange, check all vals in col string
		for (int i=0; i<fieldRange.size(); i++) {
			List<int[]> fieldRangeI = fieldRange.subList(i,  i+1);
			String[] vals = col.split(",");
			
			int countValid = 0;
			for (int j=0; j<vals.length; j++) {
				int val = Integer.parseInt(vals[j]);
				if (!isFieldValid(fieldRangeI, val)) break; //non-match
				countValid++;
			}
			if (countValid == vals.length) {
				validFields.add(i);
			}
			
		}
		return validFields;
	}
	
	public static boolean isFieldValid(List<int[]> fieldRange, int val) {
		for(int[] aFieldRange: fieldRange) {
			if (val >= aFieldRange[0] && val <= aFieldRange[1]) return true;
			if (val >= aFieldRange[2] && val <= aFieldRange[3]) return true;
		}
		ticketScanningError = ticketScanningError + val;
		return false;
	}
	
	public static boolean isTicketValid(List<int[]> fieldRange, String ticket) {
		String[] fields = ticket.split(",");
		for (String field: fields) {
			if (!isFieldValid(fieldRange, Integer.parseInt(field))) return false;
		}
		return true;
	}	

}
