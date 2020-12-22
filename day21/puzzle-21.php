<?php
$inputfile = "input";
$file = new SplFileObject($inputfile);
$file->setFlags(SplFileObject::DROP_NEW_LINE);
$total = 0;
$items = array();
$counts = array();
$allAllergens = array();
$allIngredients = array();
while (!$file->eof()) {
	$line = $file->fgets();
	if (empty($line)) continue;
	// mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
	preg_match_all("/(.*) \(contains (.*)\)/", $line, $matches);
	$item = array();
	$ingredients = explode(" ", $matches[1][0]);
	$allergens = explode(", ", $matches[2][0]);
	$item["ingredients"] = $ingredients;
	$item["allergens"] = $allergens;
	$allAllergens = array_unique(array_merge($allAllergens, $allergens));
	$allIngredients = array_unique(array_merge($allIngredients, $ingredients));
	$items[] = $item;

}
$possible = array();
foreach ($allIngredients as $ingredient) {
	$possible[$ingredient] = $allAllergens;
}
foreach ($items as $item) {
	foreach ($item["ingredients"] as $ing) {
		if (isset($counts[$ing])) $counts[$ing]++;
		else $counts[$ing] = 1;
	}
	foreach ($item["allergens"] as $allergen) {
		foreach ($allIngredients as $ingredient) {
			if (!in_array($ingredient, $item["ingredients"])) {
				if (($key = array_search($allergen, $possible[$ingredient])) !== false) {
 				   unset($possible[$ingredient][$key]);
				}
			}
		}

	}
}
$answer = 0;
foreach ($allIngredients as $ingredient) {
	if (!$possible[$ingredient]) $answer += $counts[$ingredient]; 
}
echo "Puzzle 21a: $answer\n";

foreach ($possible as $key => $value) {
	if (sizeof($value) == 0) {
		unset($possible[$key]);
	}
}
$listbyvalue = array();
while(sizeof($possible) > 0) {
	foreach ($possible as $key => $value) {
		if (sizeof($value) == 1) {
			$val = reset($value);
			$firstKey =  key($value);
			$listbyvalue[$val] = $key;
			foreach($possible as $k2 => $v2) {
				if (array_key_exists($firstKey, $v2)) {
    				unset($v2[$firstKey]);
    				$possible[$k2] = $v2;
				}
			}
			unset($possible[$key]);
		}
	}
}
ksort($listbyvalue);
echo "Puzzle 21b: " . implode(',', $listbyvalue) . "\n";