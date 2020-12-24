import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class OperationOrderWendy {


	public static void main(String[] args) throws IOException {
		assert calculate("1 + 2 * 3 + 4 * 5 + 6") == 71;
		assert calculate("1 + (2 * 3) + (4 * (5 + 6))") == 51;
		assert calculate("2 * 3 + (4 * 5)") == 26;
		assert calculate("5 + (8 * 3 + 9 + 3 * 4 * 3)") == 437;
		assert calculate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))") == 12240;
		assert calculate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2") == 13632;
		
		List<String> rows = Files.readAllLines(Paths.get("./day18/input"));
		
		System.out.format("Part 1 answer is %s\n", rows.stream().mapToLong(eq -> calculate(eq)).sum());
		
		assert calculate2("1 + 2 * 3 + 4 * 5 + 6") == 231;
		assert calculate2("1 + (2 * 3) + (4 * (5 + 6))") == 51;
		assert calculate2("2 * 3 + (4 * 5)") == 46;
		assert calculate2("5 + (8 * 3 + 9 + 3 * 4 * 3)") == 1445;
		assert calculate2("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))") == 669060;
		assert calculate2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2") == 23340;
		
		System.out.format("Part 2 answer is %s\n", rows.stream().mapToLong(eq -> calculate2(eq)).sum());

	}
	
	public static long calculate2(final String str) {
		String eq = str.replaceAll("\s", "");		
	    return new Object() {
	        int pos = -1, ch;

	        void nextChar() {
	            ch = (++pos < eq.length()) ? eq.charAt(pos) : -1;
	        }

	        boolean eat(int charToEat) {
	            while (ch == ' ') nextChar();
	            if (ch == charToEat) {
	                nextChar();
	                return true;
	            }
	            return false;
	        }

	        long parse() {
	            nextChar();
	            long x = parseExpression();
	            if (pos < eq.length()) throw new RuntimeException("Unexpected: " + (char)ch);
	            return x;
	        }

	        // Grammar:
	        // expression = term | expression `*` term 
	        // term = factor | term `+` factor | term `/` factor
	        // factor =  `(` expression `)` | number

	        long parseExpression() {
	        	long x = parseTerm();
	            for (;;) {
	                if      (eat('*')) x *= parseTerm(); // multiplication
	                else return x;
	            }
	        }

	        long parseTerm() {
	        	long x = parseFactor();
	            for (;;) {
	                if      (eat('+')) x += parseFactor(); // addition
	                else return x;
	            }
	        }

	        long parseFactor() {

	            long x;
	            int startPos = this.pos;
	            if (eat('(')) { // parentheses
	                x = parseExpression();
	                eat(')');
	            } else if ((ch >= '0' && ch <= '9') ) { // numbers
	                while ((ch >= '0' && ch <= '9') ) nextChar();
	                x = Long.parseLong(eq.substring(startPos, this.pos));
	            }  else {
	                throw new RuntimeException("Unexpected: " + (char)ch);
	            }

	            return x;
	        }
	    }.parse();
	}	
	
	
	//Adapted from https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
	public static long calculate(final String str) {
		String eq = str.replaceAll("\s", "");
	    return new Object() {
	        int pos = -1, ch;

	        void nextChar() {
	            ch = (++pos < eq.length()) ? eq.charAt(pos) : -1;
	        }

	        boolean eat(int charToEat) {
	            while (ch == ' ') nextChar();
	            if (ch == charToEat) {
	                nextChar();
	                return true;
	            }
	            return false;
	        }

	        long parse() {
	            nextChar();
	            long x = parseExpression();
	            if (pos < eq.length()) throw new RuntimeException("Unexpected: " + (char)ch);
	            return x;
	        }

	        // Grammar:
	        // expression = term | expression `+` term | expression `*` term
	        // term =  `(` expression `)` | number 

	        long parseExpression() {
	        	long x = parseTerm();
	            for (;;) {
	                if      (eat('+')) x += parseTerm(); // addition
	                else if (eat('*')) x *= parseTerm(); // multiply
	                else return x;
	            }
	        }


	        long parseTerm() {
	        	long x;
	            int startPos = this.pos;
	            if (eat('(')) { // parentheses
	                x = parseExpression();
	                eat(')');
	            } else if ((ch >= '0' && ch <= '9')) { // numbers
	                while ((ch >= '0' && ch <= '9')) nextChar();
	                x = Long.parseLong(eq.substring(startPos, this.pos));
	            }  else {
	                throw new RuntimeException("Unexpected: " + (char)ch);
	            }

	            return x;
	        }
	    }.parse();
	}	

}

