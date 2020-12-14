<?php
class Chug 
{
	public $filename = 'input';
	public $mask;
	public $store = array();

	public function convertValueUsingMask($value) {
		$dec = str_pad(decbin($value), 36, "0", STR_PAD_LEFT);
		for ($i=0; $i < strlen($dec); $i++) { 
			$m = substr($this->mask, -$i, 1);
			if ($m != "X") $dec = substr_replace($dec, $m, -$i, 1);
		}
		return $dec;
	}

	public function convertAddressUsingMask($value) {
		$dec = str_pad(decbin($value), 36, "0", STR_PAD_LEFT);
		for ($i=0; $i < strlen($this->mask); $i++) { 
			$m = substr($this->mask, -$i, 1);
			if ($m == "X" || $m == "1") {
				$dec = substr_replace($dec, $m, -$i, 1);
			}
		}
		return $dec;
	}

	public function fillAddresses($address, $value, $currentIx) {
		$nextX = strpos($address, 'X', $currentIx+1);
		if (($currentIx >= strlen($address)) || ($nextX == false)) {
			$this->store[substr_replace($address, "0", $currentIx, 1)] = $value;
			$this->store[substr_replace($address, "1", $currentIx, 1)] = $value;
		} else if (substr($address, $currentIx, 1) == 'X') {
			$this->fillAddresses(substr_replace($address, "0", $currentIx, 1), $value, $nextX);
			$this->fillAddresses(substr_replace($address, "1", $currentIx, 1), $value, $nextX);
		} else {
			$this->fillAddresses($address, $value, $currentIx+1);
		}
	}

	public function getSumOfValues() {
		$total = 0;
		foreach ($this->store as $key => $value) $total+=bindec($value);
		return $total;		
	}

	public function run($type = "A") {
		$file = new SplFileObject($this->filename);
		$file->setFlags(SplFileObject::DROP_NEW_LINE);
		while (!$file->eof()) {
			$line = $file->fgets();
			if (empty($line)) continue;
			if (str_starts_with($line, 'mask')) {
				$this->mask = substr($line, 7);
			} else {
				preg_match('/mem\[(.*)\] = (.*)/i', $line, $tok);
				if ($type == "A") {
					$this->store[$tok[1]] = $this->convertValueUsingMask($tok[2]);
				} else {
					$this->fillAddresses($this->convertAddressUsingMask($tok[1]), decbin($tok[2]), 0);
				}
			}
		}
	}
}

$chug = new Chug();
$chug->run("A");
echo "Puzzle 14a answer is " . $chug->getSumOfValues() . "\n";
$chug = new Chug();
$chug->run("B");
echo "Puzzle 14b answer is " . $chug->getSumOfValues() . "\n";