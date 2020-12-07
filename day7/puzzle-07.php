<?php
$bags = [];
$file = new SplFileObject("input");
$file->setFlags(SplFileObject::DROP_NEW_LINE);
while (!$file->eof()) {
    $line = $file->fgets();
    $tokens = preg_split('/ bags contain /', $line, -1);
    $innerBags = [];
	$inners = preg_replace('/ bags?\.$/', '', $tokens[1]);
    if ('no other' == $inners) {
	    $bags[$tokens[0]] = null;
    } else {
	    foreach (preg_split('/\|/', preg_replace('/ bags?[\.,] ?/', '|', $inners, -1), -1) as $bag) {
		    preg_match('/(\d+) (.*)/', $bag, $matches);
	    	$innerBags[$matches[2]] = $matches[1];
	    }
	    $bags[$tokens[0]] = $innerBags;
    }
}

function lookForTarget($options, $target)
{
	global $bags;
	if ($options == null) return false;
	if (array_key_exists($target, $options)) return true;
	foreach ($options as $bag => $count) {
		if (lookForTarget($bags[$bag], $target)) return true;
	}
	return false;
}

function howManyInside($options)
{
	global $bags;
	$currentCount = 1;
	if ($options == null) return $currentCount;
	foreach ($options as $bag => $count) {
		$returnCount = howManyInside($bags[$bag]);
		$currentCount += ($returnCount * $count);
	}
	return $currentCount;	
}

$target = 'shiny gold';
$containers = [];
echo "Puzzle 07a\n";
foreach ($bags as $colour => $inners) {
	if (lookForTarget($inners, $target)) $containers[] = $colour;
}
echo "Found '$target' in " . sizeof($containers) . " outer bags.\n";
echo "**********************************************************\n";
echo "Puzzle 07b\n";
echo "There must be " . (howManyInside($bags[$target]) - 1) . " bags inside '$target'\n";