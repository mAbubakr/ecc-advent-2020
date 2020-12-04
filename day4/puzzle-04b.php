<?php

class PassportMessages
{
	public const BirthYearInvalid = 'invalid birth year';
	public const IssueYearInvalid = 'invalid issue year';
	public const ExpirationYearInvalid = 'invalid expiration year';
	public const HeightInvalid = 'invalid height';
	public const HeightInvalidCM = 'invalid height in cm';
	public const HeightInvalidIN = 'invalid height in inches';
	public const HairColourInvalid = 'invalid hair colour';
	public const HairColourInvalidNoHash = 'invalid hair colour, no hash';
	public const HairColourInvalidChar = 'invalid character in hair colour';
	public const EyeColourInvalid = 'invalid eye colour';
	public const PassportIDInvalidLength = 'invalid passport ID length';
	public const PassportIDInvalidChar = 'invalid character in passport ID';
	public const Valid = "all good";
	public const ParamsMissing= "missing parameters";
}

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
		if (array_key_exists('byr', $this->tokens) &&
			array_key_exists('iyr', $this->tokens) &&
			array_key_exists('eyr', $this->tokens) &&
			array_key_exists('hgt', $this->tokens) &&
			array_key_exists('hcl', $this->tokens) &&
			array_key_exists('ecl', $this->tokens) &&
			array_key_exists('pid', $this->tokens)) {
			// byr (Birth Year) - four digits; at least 1920 and at most 2002.
			if (!$this->isNumberInRange($this->tokens['byr'], 1920, 2002)) return PassportMessages::BirthYearInvalid;
			// iyr (Issue Year) - four digits; at least 2010 and at most 2020.
			if (!$this->isNumberInRange($this->tokens['iyr'], 2010, 2020)) return PassportMessages::IssueYearInvalid;
			// eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
			if (!$this->isNumberInRange($this->tokens['eyr'], 2020, 2030)) return PassportMessages::ExpirationYearInvalid;
			// hgt (Height) - a number followed by either cm or in:
			// 	If cm, the number must be at least 150 and at most 193.
			// 	If in, the number must be at least 59 and at most 76.
			if (strpos($this->tokens['hgt'],"cm") != false ) {
				if (!$this->isNumberInRange(substr($this->tokens['hgt'], 0, -2), 150, 193)) return PassportMessages::HeightInvalidCM;
			}
			elseif (strpos($this->tokens['hgt'],"in") != false ) {
				if (!$this->isNumberInRange(substr($this->tokens['hgt'], 0, -2), 59, 76)) return PassportMessages::HeightInvalidIN;
			}
			else {
				return PassportMessages::HeightInvalid;
			}
			// hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
			if (strlen($this->tokens['hcl']) == 7) {
				if ($this->tokens['hcl'][0] != '#') return PassportMessages::HairColourInvalidNoHash;
				if (!ctype_alnum(substr($this->tokens['hcl'], 1))) return PassportMessages::HairColourInvalidChar;
			}
			else {
				return PassportMessages::HairColourInvalid;
			}
			// ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
			if (strlen($this->tokens['ecl']) == 3) {
				if (!in_array($this->tokens['ecl'], $this->validEyeColours)) return PassportMessages::EyeColourInvalid;
			}
			else {
				return PassportMessages::EyeColourInvalid;
			}
			// pid (Passport ID) - a nine-digit number, including leading zeroes.
			if (strlen($this->tokens['pid']) == 9) {
				if (!ctype_digit($this->tokens['pid'])) return PassportMessages::PassportIDInvalidChar;
			}
			else {
				return PassportMessages::PassportIDInvalidLength;
			}
			return PassportMessages::Valid;
		}
		else {
			return PassportMessages::ParamsMissing;
		}
	}
	function isNumberInRange($value, $min, $max)
	{
		return ($min <= $value) && ($value <= $max);
	}
}

echo "Puzzle 04b:\n";
$file = file_get_contents('./input.txt');
$inputs = explode("\n\n", $file);
$countValid = 0;
foreach ($inputs as $input) {
	$passport = new Passport($input);
	$valid = $passport->isValid();
	if (PassportMessages::Valid == $valid) {
		$countValid++;	
	}
	else
	{
		// echo "Passport is invalid: $valid\n";
		// echo "Input was: $input\n";
		// echo "===============================================================\n";
	}
}
echo "There were $countValid valid passports.\n";