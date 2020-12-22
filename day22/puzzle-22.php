<?php
$start_time = microtime(true); 
$inputfile = "input";
$file = new SplFileObject($inputfile);
$file->setFlags(SplFileObject::DROP_NEW_LINE);
$deck1 = array();
$deck2 = array();
$loading = 0;
while (!$file->eof()) {
	$line = $file->fgets();
	if (empty($line)) continue;
	if (str_contains($line, "Player 1")) {
		$loading = 1;
	} else if (str_contains($line, "Player 2")) {
		$loading = 2;
	} else {
		if ($loading == 1) {
			array_push($deck1, intval($line));
		} else {
			array_push($deck2, intval($line));
		}
	}
}

function game1($deck1, $deck2) {
	global $winningdeck;
	$game = 1;
	$round = 1;
	while (sizeof($deck1) > 0 && sizeof($deck2) > 0) {
		$roundWinner = 0;
		$play1 = array_shift($deck1);
		$play2 = array_shift($deck2);
		$roundWinner = 2;
		if ($play1 > $play2) $roundWinner = 1;
		if ($roundWinner == 1) {
			array_push($deck1, $play1);
			array_push($deck1, $play2);
		} else {
			array_push($deck2, $play2);
			array_push($deck2, $play1);
		}
		$round++;
	}
	if (sizeof($deck2) > 0) {
		$winningdeck = $deck2;
		return 2;
	}
	$winningdeck = $deck1;
	return 1;
}

function recursiveCombat($deck1, $deck2, $game) {
	global $winningdeck;
	$pastRounds = array();
	$round = 1;
	// echo "\n=== Game $game ===\n";
	while (sizeof($deck1) > 0 && sizeof($deck2) > 0) {
		$roundWinner = 0;
		// echo "-- Round $round (Game $game) --\n";
		// echo "Player 1's deck: " . implode(", ", $deck1) . "\n";
		// echo "Player 2's deck: " . implode(", ", $deck2) . "\n";
		$roundString = "deck1-".implode("|", $deck1)."-deck2-".implode("|", $deck2)."\n";
		if (array_key_exists($roundString, $pastRounds)) {
			// echo "The winner of game $game is player 1! (RECURSION)\n";
			$winningdeck = $deck1;
			return 1;
		}
		$pastRounds[$roundString] = 1;
		$play1 = array_shift($deck1);
		$play2 = array_shift($deck2);
		// echo "Player 1 plays: $play1\n";
		// echo "Player 2 plays: $play2\n";
		if ($play1 <= sizeof($deck1) && $play2 <= sizeof($deck2)) {
			// echo "Playing a sub-game to determine the winner...\n";
			$roundWinner = recursiveCombat(array_slice($deck1, 0, $play1), array_slice($deck2, 0, $play2), $game+1);
		} else {
			$roundWinner = 2;
			if ($play1 > $play2) $roundWinner = 1;
		}
		if ($roundWinner == 1) {
			array_push($deck1, $play1);
			array_push($deck1, $play2);
		} else {
			array_push($deck2, $play2);
			array_push($deck2, $play1);
		}
		// echo "Player $roundWinner wins round $round of game $game!\n";
		$round++;
	}
	if (sizeof($deck2) > 0) {
		// echo "The winner of game $game is player 2!\n";
		$winningdeck = $deck2;
		return 2;
	}
	// echo "The winner of game $game is player 1!\n";
	$winningdeck = $deck1;
	return 1;
}

function getAnswer($deck) {
	$multiplier = 1;
	$answer = 0;
	for ($i=sizeof($deck)-1; $i >= 0; $i--) { 
		$answer += $multiplier++ * $deck[$i];
	}
	return $answer;
}

$winningdeck;
$game = 1;
$winner = game1($deck1, $deck2);
echo "Puzzle 22a: " . getAnswer($winningdeck) . "\n";

$winner = recursiveCombat($deck1, $deck2, 1);
echo "Puzzle 22b: " . getAnswer($winningdeck) . "\n";
$end_time = microtime(true); 
$execution_time = ($end_time - $start_time);   
echo " Execution time: = ".$execution_time." sec\n"; 