<?php
function isValidForRule($v, $r) {
	if (($r[0] <= $v) && ($v <= $r[1])) return true;
	if (($r[2] <= $v) && ($v <= $r[3])) return true;
	return false;
}
$inputfile = "input";
$rules = array();
$yourTicket = "";
$validTickets = array();
$invalidTotal = 0;

$file = new SplFileObject($inputfile);
$file->setFlags(SplFileObject::DROP_NEW_LINE);

while (!$file->eof()) {
	$line = $file->fgets();
	if (empty($line)) continue;
	preg_match_all('/(\d+)/', $line, $tok);
	if (sizeof($tok[0]) > 0){
		if (sizeof($tok[0]) == 4) {
			preg_match('/(^[^:]+)/', $line, $name);
			$rules[$name[0]] = $tok[0];	
		} else {
			if ($yourTicket == "") {
				$yourTicket = $line;
			} else {
				$ticketValid = 1;
				$tokens = explode(',', $line);
				for ($i=0; $i < sizeof($tokens); $i++) { 
					$isValid = 0;
					foreach ($rules as $rule) {
						if (isValidForRule($tokens[$i], $rule)) {
							$isValid = 1;
						}
					}
					if ($isValid == 0) {
						$invalidTotal += $tokens[$i];
						$ticketValid = 0;
					}
				}
				if ($ticketValid == 1) {
					array_push($validTickets, $tokens);
				}
			}
		}
	}
}
echo "Puzzle 16a: $invalidTotal\n";
$fieldnames = array_keys($rules);
$numfields = sizeof($validTickets[0]);
$possibleForField = array();
foreach ($fieldnames as $name) {
	$validFor = array();
	for ($i=0; $i < $numfields; $i++) {
		$numValid = 0; 
		foreach ($validTickets as $ticket) {
			if (isValidForRule($ticket[$i], $rules[$name])) {
				$numValid++;
			}
		}
		if ($numValid == sizeof($validTickets)) {
			array_push($validFor, $i);
		}
	}
	$possibleForField[$name] = $validFor;
}

$positions = array();
while (sizeof($positions) < $numfields) {
	foreach ($possibleForField as $key => $value) {
		if (sizeof($value) == 1) {
			$pos = array_shift($value);
			$positions[$key] = $pos;
			unset($possibleForField[$key]);
			foreach ($possibleForField as $key2 => $value2) {
				if (($exists = array_search($pos, $value2)) !== false) {
	 			   unset($value2[$exists]);
	 			   $possibleForField[$key2] = $value2;
				}
			}
		}
	}
}
$yourValues = explode(",", $yourTicket);
$total = 0;
foreach ($positions as $key => $idx) {
	if (str_starts_with($key, "departure")) {
		if ($total == 0) $total = $yourValues[$idx];
		else $total *= $yourValues[$idx];
	}
}
echo "Puzzle 16b: $total\n";