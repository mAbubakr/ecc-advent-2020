<?php 
class Energy
{
	public $occupied;

	function countNeighbours($x, $y ,$z, $w = 0) {
		$count = 0;
		for ($xi=-1; $xi < 2; $xi++) { 
			for ($yi=-1; $yi < 2; $yi++) { 
				for ($zi=-1; $zi < 2; $zi++) { 
					for ($wi=-1; $wi < 2; $wi++) { 
						if (($xi != 0) || ($yi != 0) || ($zi != 0) || ($wi != 0)) {
							if (isset($this->occupied[($x+$xi).",".($y+$yi).",".($z+$zi).",".($w+$wi)])) {
								$count++;
							}
						}
					}
				}
			}
		}
		return $count;
	}

	function process4d() {
		$newOccupied = array();
		for ($x=-14;$x < 14; $x++) {
			for ($y=-14; $y < 14; $y++) { 
				for ($z=-14; $z < 14; $z++) { 
					for ($w=-14; $w < 14; $w++) { 
						$count = $this->countNeighbours($x,$y,$z,$w);
						if (isset($this->occupied[($x).",".($y).",".($z).",".($w)])) {
							if ($count > 1 && $count < 4) $newOccupied[($x).",".($y).",".($z).",".($w)] = 1;
						} else {
							if ($count == 3) $newOccupied[($x).",".($y).",".($z).",".($w)] = 1;
						}
					}
				}
			}
		}
		$this->occupied = $newOccupied;
	}

	function process3d() {
		$newOccupied = array();
		for ($x=-14;$x < 14; $x++) {
			for ($y=-14; $y < 14; $y++) { 
				for ($z=-14; $z < 14; $z++) { 
					$count = $this->countNeighbours($x,$y,$z);
					if (isset($this->occupied[($x).",".($y).",".($z).",0"])) {
						if ($count > 1 && $count < 4) $newOccupied[($x).",".($y).",".($z).",0"] = 1;
					} else {
						if ($count == 3) $newOccupied[($x).",".($y).",".($z).",0"] = 1;
					}
				}
			}
		}
		$this->occupied = $newOccupied;
	}

	function load($filename = "input-test") {
		$file = new SplFileObject($filename);
		$file->setFlags(SplFileObject::DROP_NEW_LINE);
		$this->occupied = array();
		$x = 0;
		while (!$file->eof()) {
			// if (++$count > 50) exit();
			$line = $file->fgets();
			if (empty($line)) continue;
			$y = 0;
			foreach (str_split($line) as $spot) {
				if ($spot == "#") {
					$this->occupied["$x,$y,0,0"] = 1;
				}
				$y++;
			}
			$x++;
		}
	}

	function countOccupied() {
		return sizeof($this->occupied);
	}
}
$filename = "input";
$cyclemax = 6;

$energy = new Energy();
$energy->load($filename);
for ($i=0; $i < $cyclemax; $i++) { 
	$energy->process3d();
}
echo "Puzzle 17a: " . $energy->countOccupied() . "\n";

$energy->load($filename);
for ($i=0; $i < $cyclemax; $i++) { 
	$energy->process4d();
}
echo "Puzzle 17b: " . $energy->countOccupied() . "\n";