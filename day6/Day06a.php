<?php
echo "Puzzle 06a\n";
$groups = [];
$current = [];
$file = new SplFileObject("input.txt");
$file->setFlags(SplFileObject::DROP_NEW_LINE);
while (!$file->eof()) {
    $line = $file->fgets();
	if (empty($line)) {
		$groups[] = $current;
		$current = [];
		continue;
	}
	foreach (str_split($line) as $char) $current[$char] = 1;
}
$total = 0;
foreach ($groups as $group) {
	$total += array_sum($group);
}
echo "Total sum is $total\n";