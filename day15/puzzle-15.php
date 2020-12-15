<?php
function process($input, $target) {
	$spoken = array();
	$inputs = explode(",", $input); 
	$lastadded = 0;
	for ($i=0; $i < sizeof($inputs); $i++) { 
		$spoken[$inputs[$i]]["last"] = $i+1;
		$spoken[$inputs[$i]]["previous"] = 0;
		$lastadded = $inputs[$i];
	}
	$turn = sizeof($inputs)+1;
	while ($turn <= $target) {
		if (!isset($spoken[$lastadded]) || ($spoken[$lastadded]["previous"] == 0)) {
			$addnext = 0;
			if (!isset($spoken[$addnext])) $spoken[$addnext]["last"] = 0;
			$spoken[$addnext]["previous"] = $spoken[$addnext]["last"];
		} else {
			$addnext = $spoken[$lastadded]["last"] - $spoken[$lastadded]["previous"];
			if (isset($spoken[$addnext])) {
				$spoken[$addnext]["previous"] = $spoken[$addnext]["last"];
			} else {
				$spoken[$addnext]["previous"] = 0;
			}
		}
		$spoken[$addnext]["last"] = $turn;
		$lastadded = $addnext;
		$turn++;
	}
	return $lastadded;
}
$stopat = 2020;
$inputs = ["0,3,6","1,3,2","2,1,3","1,2,3","2,3,1","3,2,1","3,1,2"];
foreach ($inputs as $input) {
	echo "for '$input': Number at '$stopat' is '" . process($input, $stopat) . "'\n";
}
$input = "1,0,18,10,19,6";
echo "Puzzle 15a: Number at '$stopat' is '" . process($input, $stopat) . "'\n";
$stopat = 30000000;
echo "Puzzle 15b: Number at '$stopat' is '" . process($input, $stopat) . "'\n";