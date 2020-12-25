import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonsterMessagesWendy {

	private Map<String, Term> symbolToTerms;
	
	private void part1() throws IOException {
		List<String> input = Files.readAllLines(Paths.get("./day19/input"));

		//Parse everything
		
		//rules
		System.out.println("Parsing rules...");
		symbolToTerms = new HashMap<String, Term>();
		
		input.stream().takeWhile(e -> !e.isBlank()).forEach(e -> {
			System.out.println(e);	

			//parse the tokens
			Pattern pattern = Pattern.compile("(\\d+): (.*)");
			Matcher matcher = pattern.matcher(e);
			matcher.matches();		
			String term = matcher.group(1);
			String expression = matcher.group(2);
			symbolToTerms.put(term, new Term(term, expression));		
			
		});

		String p = this.symbolToTerms.get("0").resolve();
		
		Pattern pattern = Pattern.compile(p);
		
		//messages
		System.out.println("Parsing messages...");

		int numMatches = input.stream().dropWhile(e -> !e.isBlank()).mapToInt(e -> {
			if (e.isBlank()) return 0;
			Matcher matcher = pattern.matcher(e);
//			System.out.println(e + " " + matcher.matches());			
			if (matcher.matches()) {
				System.out.println("Matched: " + e);
				return 1;
			} else {
				return 0;
			}				
		}).sum();
		
		System.out.println("Part 1 answer is " + numMatches);		
	}
	
	private void part2() throws IOException {
		List<String> input = Files.readAllLines(Paths.get("./day19/input"));

		//Parse everything
		
		//rules
		System.out.println("Parsing rules...");
		symbolToTerms = new HashMap<String, Term>();
		
		input.stream().takeWhile(e -> !e.isBlank()).forEach(e -> {
			System.out.println(e);	

			//parse the tokens
			Pattern pattern = Pattern.compile("(\\d+): (.*)");
			Matcher matcher = pattern.matcher(e);
			matcher.matches();		
			String term = matcher.group(1);
			String expression = matcher.group(2);
			symbolToTerms.put(term, new Term(term, expression));		
			
		});

		String r0 = symbolToTerms.get("0").resolve();
		System.out.println("Rule 0 resolves to: " + r0);
		

		String r42 = symbolToTerms.get("42").resolve();
		String r31 = symbolToTerms.get("31").resolve();
		System.out.println("Rule 42 resolves to: " + r42);
		System.out.println("Rule 31 resolves to: " + r31);
		
		/**
		 0: 8 11
		 8: 42 | 42 8
		 11: 42 31 | 42 11 31
		 ====
		 8: 42+
		 11: 42+ 31+ but 42 and 31 must have the same repeating number..
		 -> as long as more 42 than 31..?
		 ====
		 not sure how to do that rule in regex.. manually test combos and add!		 
		 	r42 + r42 + r31 = 173
			r42 + r42 + r42 + r31 = 58
			r42 + r42 + r42 + r42 + r31 = 26
			r42 + r42 + r42 + r42 + r42 + r31 = 19
			r42 + r42 + r42 + r42 + r42 + r42 + r31 = 9
			
			r42 + r42 + r42 + r31 + r31 = 33
			r42 + r42 + r42 + r42 + r31 + r31 = 15
			r42 + r42 + r42 + r42 + r42 + r31 + r31 = 8
			r42 + r42 + r42 + r42 + r42 + r42 + r31 + r31 = 4
			
			r42 + r42 + r42 + r42 + r31 + r31 + r31 = 13
			r42 + r42 + r42 + r42 + r42 + r31 + r31 + r31 = 4
			r42 + r42 + r42 + r42 + r42 + r42 + r31 + r31 + r31 = 2
			
			r42 + r42 + r42 + r42 + r42 + r31 + r31 + r31 + r31 = 2
			r42 + r42 + r42 + r42 + r42 + r42 + r31 + r31 + r31 + r31 = 1
		 */

//		String r0prime = r42 + r42 + "+" + r31 + "+";
		String r0prime = r42 + r42 + r42 + r42 + r42 + r42 + r31 + r31 + r31 + r31 + r31;

		System.out.println("New Rule 0 resolves to: " + r0prime);
		
		Pattern pattern = Pattern.compile(r0prime);
		
		//messages
		System.out.println("Parsing messages...");


		int numMatches = input.stream().dropWhile(e -> !e.isBlank()).mapToInt(e -> {
			if (e.isBlank()) return 0;
			Matcher matcher = pattern.matcher(e);
//			System.out.println(e + " " + matcher.matches());			
			if (matcher.matches()) {
				System.out.println("Matched: " + e);
				return 1;
			} else {
				return 0;
			}				
		}).sum();
		
		System.out.println("Part 2 answer is " + numMatches);		
	}
	
	public MonsterMessagesWendy() throws IOException {
//		part1();
		part2();
	}
	
	private class Term {
		String symbol;
		String expression;
		String[] expressionSymbols;
		
		public Term(String sym, String exp) {
			this.symbol = sym;
			this.expression = exp;
			this.expressionSymbols = this.expression.split(" ");
		}
		
		public String resolve() {
			if (expression.equals("\"a\"") || expression.equals("\"b\"")) return "" + expression.charAt(1);
			
			//else expression contains other terms

			String ans = "(";
			
			for (int i=0; i<expressionSymbols.length; i++) {
				String c = expressionSymbols[i];
				if (c.equals(" ")) {
					ans += c;
					continue;
				}
				if (c.equals("|")) {
					ans += c;
					continue;
				}
//				System.out.println("looking for " + c);
				ans += symbolToTerms.get("" + c).resolve();
			}
			ans += ")";			
			return ans.replaceAll(" ", "");
			
		}
	}
	public static void main(String[] args) throws IOException {
		 new MonsterMessagesWendy(); 

	}


}
