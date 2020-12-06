<?php
echo "Puzzle 06a\n";
$groups = [];
$current = [];
$file = new SplFileObject("input.txt");
$file->setFlags(SplFileObject::DROP_NEW_LINE);
$count = 0;
while (!$file->eof()) {
    $line = $file->fgets();
	if (empty($line)) {
		$groups[] = [$count, $current];
		$current = [];
		$count = 0;
		continue;
	}
	$count++;
	foreach (str_split($line) as $char) @++$current[$char];
}
$total = 0;
foreach ($groups as $group) {
	foreach ($group[1] as $responses) {
		if ($responses == $group[0]) $total += 1;
	}
}
echo "Total sum is $total\n";