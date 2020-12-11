<?php
$numbers = [];
function getInput($filename)
{
	global $numbers;
	$input = [];
	$file = new SplFileObject($filename);
	$file->setFlags(SplFileObject::DROP_NEW_LINE);
	while (!$file->eof()) {
	    $line = $file->fgets();
	    if (empty($line)) continue;
	    $numbers[] = $line;
	}
}

function isValid($target, $list)
{
	for ($i = 0; $i < sizeof($list); $i++) {
		for ($j = $i + 1; $j < sizeof($list); $j++) {
			if ($list[$i] + $list[$j] == $target) return true;
		}
	}
	return false;
}

function findInvalid($preamble) 
{
	global $numbers;
	for ($i = 0; $i < (sizeof($numbers) - $preamble); $i++) {
		$offset = $preamble + $i;
		if (!isValid($numbers[$offset], array_slice($numbers, $i, $preamble))) return $numbers[$offset];
	}
	return -1;
}

function calculate($array) {
	return min($array) + max($array);
}

function findWeakness($target)
{
	global $numbers;
	$i = 0;
	for ($i = 0; $i < (sizeof($numbers) - 1); $i++) {
		$runningTotal = $numbers[$i];
		for ($j = $i+1; $j < sizeof($numbers); $j++) {
			$runningTotal += $numbers[$j];
			if ($runningTotal == $target) return calculate(array_slice($numbers, $i, $j - $i + 1));
			if ($runningTotal > $target) break; 
		}
	}
}

$preamble = 25;
getInput('input');
echo "Puzzle 09a\n";
$result = findInvalid($preamble);
echo "The next invalid number is '$result'.\n";
echo "**********************************************************\n";
echo "Puzzle 09b\n";
$result = findWeakness($result);
echo "The weakness is '$result'.\n";