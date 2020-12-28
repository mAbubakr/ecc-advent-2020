import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CrabCombatWendy {
	private Deque<Integer> player1;
	private Deque<Integer> player2;
	private int gameCount;
	private Set<String> configurations;
		
	public CrabCombatWendy(Deque<Integer> p1, Deque<Integer> p2, int gameCount) {
		player1 = new ArrayDeque<>(p1);
		player2 = new ArrayDeque<>(p2);
		configurations = new HashSet<String>();
		this.gameCount = gameCount;

		System.out.format("======Game %s=====\n", gameCount);

	}
	
	public void part1() {
		int round=0;
		//Stop when either has no more cards
		while(player1.size() != 0 && player2.size() != 0) {
			System.out.format("======Round %s=====\n", round++);			
			int card1 = player1.remove();
			int card2 = player2.remove();
			if (card1 > card2) {
				player1.add(card1);
				player1.add(card2);
			} else {
				player2.add(card2);
				player2.add(card1);
			}
			System.out.println("Player 1: " + player1);
			System.out.println("Player 2: " + player2);			
		}
		
		Deque<Integer> winningDeck;
		if (player1.size() > player2.size()) {
			winningDeck = player1;
		} else {
			winningDeck = player2;
		}
		Integer[] cardArr = new Integer[winningDeck.size()];
		winningDeck.toArray(cardArr);
		long winningScore = 0; 
		for (int i=0; i<cardArr.length; i++) {
			winningScore += cardArr[i] * (cardArr.length-i);
		}
		System.out.format("Part 1 winning score is %s\n", winningScore);		
	}
	
	/**
	 * 
	 * @return 1 if player 1 wins, 2 if player 2 wins
	 */
	public int part2() {
		int round=0;
		//Stop when either has no more cards
		while(player1.size() != 0 && player2.size() != 0) {								
			System.out.format("======Round %s=====\n", round++);	
			System.out.println("Player 1: " + player1);
			System.out.println("Player 2: " + player2);					
			
			//break infinite recursion
			String roundConfig = player1.toString() + player2.toString();			
			if (configurations.contains(roundConfig)) {
				//same config as before, player 1 wins
				System.out.println("Break infinite recursion - player 1 wins!");
				return 1;
			} else {
				configurations.add(roundConfig);
			}
			
			//draw top cards as normal
			int card1 = player1.remove();
			int card2 = player2.remove();
			System.out.println("Player 1 plays: " + card1);
			System.out.println("Player 2 plays: " + card2);
			
			int winner = -1;
			//if both players have at least as many cards remaining as value of card, recurse
			if (card1 <= player1.size() && card2 <= player2.size()) {
				System.out.println("Recursing!");
				var newP1 = player1.stream().limit(card1).collect(Collectors.toCollection(ArrayDeque::new));
				var newP2 = player2.stream().limit(card2).collect(Collectors.toCollection(ArrayDeque::new));			
				CrabCombatWendy ccw = new CrabCombatWendy(newP1, newP2, gameCount + 1);
				winner = ccw.part2();				
			} else { //else determine winner by higher-value card
				if (card1 > card2) {
					winner = 1;
				} else {
					winner = 2;
				}
			}
			
			if (winner == 1) {
				player1.add(card1);
				player1.add(card2);
			} else {
				player2.add(card2);
				player2.add(card1);				
			}						
		}		
		
	
		if (gameCount == 1) { //calculate score if this is the original game
			Deque<Integer> winningDeck;
			if (player1.size() > player2.size()) {
				winningDeck = player1;
			} else {
				winningDeck = player2;
			}				
			Integer[] cardArr = new Integer[winningDeck.size()];
			winningDeck.toArray(cardArr);
			long winningScore = 0; 
			for (int i=0; i<cardArr.length; i++) {
				winningScore += cardArr[i] * (cardArr.length-i);
			}
			System.out.format("Part 2 winning score is %s\n", winningScore);							
		}		
		
		//return the winner
		if (player1.size() > player2.size()) {
			return 1;
		} else {
			return 2;
		}				
		
	}	
	
	public static void main(String[] args) throws IOException {
		List<String> input = Files.readAllLines(Paths.get("./day22/input"));
		
		Deque<Integer> player1 = new ArrayDeque<>();
		Deque<Integer> player2 = new ArrayDeque<>();		
		
		//Parse
		Iterator<String> iter = input.iterator();
		String line;
		//parse player 1
		while (iter.hasNext() && !(line = iter.next()).isEmpty()) {
			if (line.startsWith("Player")) continue;
			player1.add(Integer.parseInt(line));
		}
		iter.next();
		//parse player 2
		while (iter.hasNext() && !(line = iter.next()).isBlank()) {
			if (line.startsWith("Player")) continue;			
			player2.add(Integer.parseInt(line));
		}
		
		new CrabCombatWendy(player1, player2, 1).part1();
		new CrabCombatWendy(player1, player2, 1).part2();
	}

}
