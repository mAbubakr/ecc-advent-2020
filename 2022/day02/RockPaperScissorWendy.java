package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RockPaperScissorWendy {

	
	 /*
	  * the score for the outcome of the round (0 if you lost, 
	  * 3 if the round was a draw, and 6 if you won)
	  */
	public static int matchScore(String opp, String you) {
		if ( (opp.equals("A") && you.equals("X")) ||
				 (opp.equals("B") && you.equals("Y")) ||
				 (opp.equals("C") && you.equals("Z"))) {
			//draw
			return 3;
		} else if ( (opp.equals("C") && you.equals("X")) ||
				(opp.equals("A") && you.equals("Y")) ||
				(opp.equals("B") && you.equals("Z")) ) {
			//win
			return 6;
		} else {
			//lose
			return 0;
		}
	}

	public static int shapeScore(String you) {
		switch (you) {
		case "X":
			return 1;
		case "Y":
			return 2;
		case "Z":
			return 3;
		default:
			return 0;
		}
	}
	
	public static String remap(String opp, String result) {
		switch (opp) {
		case "A":
			switch (result) {
			case "X": return "Z";
			case "Y": return "X";
			case "Z": return "Y";
			default: return "";
			}
		case "B":
			switch (result) {
			case "X": return "X";
			case "Y": return "Y";
			case "Z": return "Z";
			default: return "";
			}
			
		case "C":
			switch (result) {
			case "X": return "Y";
			case "Y": return "Z";
			case "Z": return "X";
			default: return "";
			}
			
		default:
			return "";
		}
	}
	
	/*
	 * A for Rock, B for Paper, and C for Scissors
	 * X for Rock, Y for Paper, and Z for Scissors
	 * 
	 * shape you selected (1 for Rock, 2 for Paper, and 3 for Scissors) plus 
     *   X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win.
	 */
	public static void main(String[] args) {
		try {
			List<String> rounds = Files.readAllLines(Paths.get("src/main/day2/input.txt"));
	
			int total = 0;
			for(String round: rounds) {
				String[] chars = round.split(" ");
				String opp = chars[0];
				//part 1
//				String you = chars[1];
				//part 2
				String you = remap(opp, chars[1]);
				System.out.println(opp + " vs " + you);
				int score = shapeScore(you) + matchScore(opp, you);
				System.out.println(score);
				total += score;
			}
			System.out.println(total);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
