<?php
class Calculate
{
	function go($filename, $type = "A") {
		$file = new SplFileObject($filename);
		$file->setFlags(SplFileObject::DROP_NEW_LINE);
		$total = 0;
		while (!$file->eof()) {
			$line = $file->fgets();
			if (empty($line)) continue;
			$answer = $this->parse($line, $type);
			$total += $answer;
		}
		return $total;
	}
	function parse($pass, $type = "A") {
		global $break, $level;
		while (($paran = strpos($pass, "(")) !== false) {
			$matchingParan = $this->findMatching(substr($pass, $paran+1)) + $paran;
			$nextchunk = substr($pass, $paran+1, $matchingParan-$paran);
			$before = substr($pass, 0, $paran);
			$after = substr($pass, $matchingParan+2);
			$pass = $before . $this->parse($nextchunk, $type) . $after;
		}
		if ($type == "B") return $this->calculate2($pass);
		return $this->calculate($pass);
	}

	function findMatching($str) {
		$idx = 0;
		$p = 0;
		foreach (str_split($str) as $v) {
			if ($v == ")" && $p == 0) return $idx;
			if ($v == "(") $p++;
			if ($v == ")") $p--;
			$idx++;
		}
	}

	function calculate($tokens) {
		if (!is_array($tokens)) $tokens = explode(" ", $tokens);
		while (sizeof($tokens) > 1) {
			$x = array_shift($tokens);
			$op = array_shift($tokens);
			$y = array_shift($tokens);
			$e = $x . " " . $op . " " . $y;
			eval( '$val = (' . $e . ');' );
			array_unshift($tokens, $val);		
		}
		return $tokens[0];
	}

	function calculate2($tokens) {
		if (!is_array($tokens)) $tokens = explode(" ", $tokens);
		$plus = array_search("+", $tokens, true);
		while ($plus != false) {
			$x = $tokens[$plus-1];
			$op = $tokens[$plus];
			$y = $tokens[$plus+1];
			$e = $x . " " . $op . " " . $y;
			eval( '$val = (' . $e . ');' );
			array_splice($tokens, $plus-1, 3, $val);
			$plus = array_search("+", $tokens, true);
		}
		return $this->calculate($tokens);
	}
}

$inputfile = "input";
$calc = new Calculate();
$total = $calc->go($inputfile);
echo "Puzzle 18a: $total\n";
$total = $calc->go($inputfile, "B");
echo "Puzzle 18b: $total\n";