<?php
$instructions = [];
$file = new SplFileObject("input");
$file->setFlags(SplFileObject::DROP_NEW_LINE);
while (!$file->eof()) {
    $line = $file->fgets();
    if (empty($line)) continue;
    preg_match('/(.*) (.*)/', $line, $matches);
    $instructions[] = [
    	'code' => $matches[1],
    	'offset' => intval($matches[2])
    ];
}

function check($instruction, $offset)
{
	global $instructions, $visited, $currentIndex, $accumulator, $debug;
	if (array_key_exists($currentIndex, $visited)) return false;
	$visited[$currentIndex] = true;
	switch ($instruction) {
		case 'acc':
			$accumulator += $offset;
			$currentIndex++;
			break;
		case 'jmp':
			$currentIndex += $offset;
			break;
		case 'nop':
			$currentIndex ++;
			break;
		default:
			echo "invalid $instruction found\n";
			break;
	}
	if ($currentIndex >= sizeof($instructions)) return true;
	return check($instructions[$currentIndex]['code'], $instructions[$currentIndex]['offset']);
}

function findFix()
{
	global $instructions, $currentIndex, $visited, $accumulator, $debug;
	for ($i = 0; $i < sizeof($instructions); $i++) {
		$operation = $instructions[$i]['code'];
		if (('jmp' == $operation) || ('nop' == $operation)) {
			$originalValue = $operation;
			if ('jmp' == $operation) {
				$instructions[$i]['code'] = 'nop';
			}
			else {
				$instructions[$i]['code'] = 'jmp';
			}
			$currentIndex = 0;
			$accumulator = 0;
			$visited = [];
			$result = check($instructions[$currentIndex]['code'], $instructions[$currentIndex]['offset']);
			if ($result) return true;
			$instructions[$i]['code'] = $originalValue;
		}
	}
}

$accumulator = 0;
$currentIndex = 0;
$visited = [];
echo "Puzzle 08a\n";
$result = check($instructions[$currentIndex]['code'], $instructions[$currentIndex]['offset']);
echo "The accumulator was '$accumulator'.\n";
echo "**********************************************************\n";
echo "Puzzle 08b\n";
$accumulator = 0;
$currentIndex = 0;
$visited = [];
$result = findFix();
echo "The accumulator was '$accumulator'.\n";