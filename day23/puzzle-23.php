<?php
$start_time = microtime(true); 
class Cup {
	public $value = "";
	public $previous = null;
	public $next = null;

	public function __construct($v) {
		$this->value = $v;
    }

    function add($newcup) {
    	$newcup->next = $this->next;
    	$newcup->previous = $this;
    	$newcup->next->previous = $newcup;
    	$this->next = $newcup;
    }

	function erase() {
		$n = $this->next;
		$p = $this->previous;
		$this->previous->next = $n;
		$this->next->previous = $p;
	}
}

class Game {
	public $cups;
	public $currentCup;
	public $minimumValue;
	public $maximumValue;

	function checkForDupes() {
		// $tracker = array();
		// foreach ($this->cups as $key => $cup) {
		// 	if ($cup != null) {
		// 		echo "cup in list: '". $key ."', goes to '".$cup->next->value."'\n";
		// 		if (array_key_exists($cup->value, $tracker)) {
		// 			return "Found a duplicate '" . $cup->value . "'\n";
		// 		}
		// 		$tracker[$cup->value] = true;
		// 	} else {
		// 		echo "cup in list: '". $key ."', goes to null\n";
		// 	}
		// }
	}

	function loadA ($input) {
		$this->cups = array();
		$this->minimumValue = -1;
		$this->maximumValue = -1;
		$inputs = str_split($input);
		for ($i=0; $i  < sizeof($inputs); $i++) { 
			$num = intval($inputs[$i]);
			if (($this->minimumValue == -1) || ($this->minimumValue > $num)) {
				$this->minimumValue = $num;
			}
			if (($this->maximumValue == -1) || ($this->maximumValue < $num)) {
				$this->maximumValue = $num;
			}
			$newcup = new Cup($inputs[$i]);
			if (sizeof($this->cups) != 0)  {
				$p = end($this->cups);
				$p->next = $newcup;
				$newcup->previous = $p;
			}
			$this->cups[$input[$i]] = $newcup;
		}
		$firstCup = reset($this->cups);
		$newcup->next = $firstCup;
		$firstCup->previous = $newcup;
		// print_r($this->cups);
		// exit();
	}
	function loadB($input, $desiredSize) {
		$this->loadA($input);
		$currentSize = sizeof($this->cups);
		$destinationNode = end($this->cups);
		for ($i = $currentSize+1; $i <= $desiredSize; $i++) {
			$newCup = new Cup($i);
			$destinationNode->add($newCup);
			$this->cups[$i] = $newCup;
			$destinationNode = $newCup;
		}
		$this->maximumValue = $desiredSize;
	}

	function getList($current = "") {
		// foreach ($this->cups as $key => $value) {
		// 	echo "at '$key', next is '" . $value->next->value . "'\n";
		// }
		if (empty($current)) {
			$currentNode = reset($this->cups);
			while ($currentNode->next == null) {
				$currentNode = next($this->cups);
			}
			$current = $currentNode->value;
		}
		// echo "size of list: " . sizeof($this->cups) . "\n";
		$pos = $this->cups[$current];
		$startValue = $pos->value;
		// echo "startValue: $startValue\n";
		$returnValue = "";
		$break = 0;
		while (1==1) {
			if ($pos->value == $current) {
				$returnValue .= " (" . $pos->value .")";
			} else {
				$returnValue .= " " . $pos->value;
			}
			$pos = $pos->next;
			if ($pos->value == $startValue) break;
			if ($break++ > 20) exit();
		}
		return $returnValue;
	}

 	function toString ($endId = "1") {
 		$return = "";
		$end = $this->cups[$endId];
		$cup = $end->next;
		while ($cup->value != $endId) {
			$return .= $cup->value;
			$cup = $cup->next;
		}
		return $return;
	}

	function findStars($idx = "1") {
		$end = $this->cups[$idx];
		$first = $end->next;
		$second = $first->next;
		return ($first->value * $second->value);
	}

	function play($rounds = 10) {
		// echo "minimumValue: $this->minimumValue\n";
		// echo "maximumValue: $this->maximumValue\n";
		$this->currentCup = reset($this->cups);
		// echo "play $rounds rounds\n";
		for ($i=0; $i < $rounds; $i++) {
			if ($i % 1000000 == 0) {
				if ($i > 0) echo $i . "\n";
			}
			// echo "-- move " . ($i + 1) . " --\n";
			$currentValue = intval($this->currentCup->value);
			// echo "cups: '" . $this->getList($this->currentCup->value) . "'\n";
			$consider = array();
			$grab = $this->currentCup->next;
			for ($j=0; $j < 3; $j++) { 
				array_unshift($consider, $grab->value);
				$temp = $grab->next;
				$grab->erase();
				$this->cups[$grab->value] = null;
				$grab = $temp;
			}
			$destinationValue = $currentValue - 1;
			if ($destinationValue <= 0) {
				$destinationValue = $this->maximumValue;
			}
			while (in_array($destinationValue, $consider)) {
				$destinationValue -= 1;
				if ($destinationValue <= 0) {
					$destinationValue = $this->maximumValue;
				}
			}
			$destinationNode = $this->cups[$destinationValue];
			// echo "destination: $destinationNode->value\n";
			foreach ($consider as $value) {
				$newCup = new Cup($value);
				$destinationNode->add($newCup);
				$this->cups[$value] = $newCup;
			}
			$this->currentCup = $this->currentCup->next;
		}
	}
}

$inputtest = "389125467";
$input = "186524973";
$testafter10 = "92658374";
$testafter100 = "67384529";
// $rounds = 100;

echo str_repeat("-=", 20) . "\n";
$game = new Game();
$game->loadA($input);
// echo "Starting list is '" . $game->getList() . "'\n";
$game->play(100);
$end_time = microtime(true); 
$execution_time = ($end_time - $start_time);   
echo "Puzzle 23a: '" . $game->toString() . "' (in $execution_time seconds)\n";
$start_time = microtime(true); 
$game = new Game();
$game->loadB($input, 1000000);
$game->play(10000000);
$end_time = microtime(true); 
$execution_time = ($end_time - $start_time);   
echo "Puzzle 23b: '" . $game->findStars() . "' (in $execution_time seconds)\n";
