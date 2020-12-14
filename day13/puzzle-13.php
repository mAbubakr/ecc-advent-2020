<?php
class Sched {
	const INPUTFILENAME = 'input';
	public $buses = array();
	public $bussesOrdered = array();
	public $data = array();
	public $depart = 0;
	public $remainders = array();

	public function load() {
		$file = file_get_contents(SELF::INPUTFILENAME);
		$lines = explode("\n", $file);
		$this->depart = $lines[0];
		$this->data = explode(",",$lines[1]);
		for ($i=0; $i < sizeof($this->data); $i++) { 
			if ($this->data[$i] == "x") {
				$this->buses[$i] = 1;
				$this->remainders[$i] = 0;
			} else {
				$this->justBuses[] = $this->data[$i];
				$this->buses[$i] = $this->data[$i];
				$this->remainders[$i] = $this->buses[$i]-$i;
			}
		}
		foreach ($this->data as $value) {
			if ($value != 'x') {
			}
		}
		sort($this->justBuses);
	}

	public function findNext() {
		$i=$this->depart;
		while (true) {
			++$i;
			// echo "check $i\n";
			foreach($this->justBuses as $bus) {
				if (($i % $bus) == 0) {
					return $bus * ($i - $this->depart);
				}
			}
		}		
	}

	public function checkNext($count, $index) {
		// echo "check count $count, with index $index\n";
		if ($index >= sizeof($this->data)) return true;
		if (($this->data[$index] == 'x') || ($count % $this->data[$index] == 0)) {
			echo " $index";
			return $this->checkNext($count+1, $index+1);
		}
		// echo "failed at '$index' because '$count' not divisible by " . $this->data[$index];
		return false;
	}

	public function findStraight() {
		$i=100000000000000;
		// $streak = 0;
		// while (true) {
		// 	if (($i % $this->data[0]) == 0) {
		// 		echo "check $i: ";
		// 		if ($this->checkNext($i+1, 1)) return $i;
		// 		echo "\n";
		// 		$i += $this->data[0];
		// 	} else {
		// 		++$i;
		// 	}
		// }
		foreach ($this->bussesOrdered as $value) {
			// echo ":: $value mod " . $this->mods[$value] . "\n";
			echo "( " . $this->mods[$value] . ", $value)\n";
		}

	}



} // class Sched

// PHP program to demonstrate working  
// of Chinise remainder Theorem 
  
// Returns modulo inverse of a with  
// respect to m using extended Euclid  
// Algorithm. Refer below post for details: 
// https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/ 
function inv($a, $m) 
{ 
	$m0 = $m; 
	$x0 = 0; 
	$x1 = 1; 

	if ($m == 1) 
	return 0; 

	// Apply extended Euclid Algorithm 
	while ($a > 1) 
	{ 
		// q is quotient 
		$q = (int)($a / $m); 

		$t = $m; 

		// m is remainder now, process 
		// same as euclid's algo 
		$m = $a % $m; 
		$a = $t; 

		$t = $x0; 

		$x0 = $x1 - $q * $x0; 

		$x1 = $t; 
	} 

	// Make x1 positive 
	if ($x1 < 0) 
	$x1 += $m0; 

	return $x1; 
} 

// k is size of num[] and rem[]. 
// Returns the smallest 
// number x such that: 
// x % num[0] = rem[0], 
// x % num[1] = rem[1], 
// .................. 
// x % num[k-2] = rem[k-1] 
// Assumption: Numbers in num[] 
// are pairwise coprime (gcd for 
// every pair is 1) 
function findMinX($num, $rem, $k) 
{ 
	// Compute product of all numbers 
	$prod = 1; 
	for ($i = 0; $i < $k; $i++) 
		$prod *= $num[$i]; 

	// Initialize result 
	$result = 0; 

	// Apply above formula 
	for ($i = 0; $i < $k; $i++) 
	{ 
		$pp = (int)$prod / $num[$i]; 
		$result += $rem[$i] * inv($pp, 
					$num[$i]) * $pp; 
	}
	return $result % $prod; 
} 

$sched = new Sched();
$sched->load();
echo "Puzzle 13a:\n";
$answer = $sched->findNext();
echo "Answer is '$answer'\n";
echo "**********************************************************\n";
echo "Puzzle 13b:\n";
$answer = findMinX($sched->buses, $sched->remainders, sizeof($sched->buses));
echo "Answer is '$answer'\n";