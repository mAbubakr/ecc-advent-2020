<?php

class Passport
{
	private $tokens = array();
	private $validEyeColours = ['amb', 'blu', 'brn', 'gry', 'grn', 'hzl', 'oth'];

	function __construct($param)
	{
		$fields = preg_split('/\s+/', $param);
		foreach ($fields as $field) {
			$pair  = explode(':', $field);
			$this->tokens{$pair[0]} = $pair[1];	
		}
	}

	public function isValid() 
	{
		return (array_key_exists('byr', $this->tokens) &&
			array_key_exists('iyr', $this->tokens) &&
			array_key_exists('eyr', $this->tokens) &&
			array_key_exists('hgt', $this->tokens) &&
			array_key_exists('hcl', $this->tokens) &&
			array_key_exists('ecl', $this->tokens) &&
			array_key_exists('pid', $this->tokens)); 
	}
}

echo "Puzzle 04a:\n";
$file = file_get_contents('./input.txt');
$inputs = explode("\n\n", $file);
$countValid = 0;
foreach ($inputs as $input) {
	$passport = new Passport($input);
	if ($passport->isValid()) $countValid++;
}
echo "There were $countValid valid passports.\n";