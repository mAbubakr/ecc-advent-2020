<?php
class Life {
	public $grid = array();
	const INPUTFILENAME = 'input';
	const SHOW_GRIDS = false;
	const COORDINATE_OFFSETS = [
			[-1, -1],[-1, 0],[-1, 1],
			[0, -1],[0, 1],
			[1, -1],[1, 0],[1, 1]
		];

	public function createGrid() {
		$file = new SplFileObject(self::INPUTFILENAME);
		$file->setFlags(SplFileObject::DROP_NEW_LINE);
		$i = 0;
		while (!$file->eof()) {
			$line = $file->fgets();
			if (empty($line)) continue;
			$this->grid[$i++] = str_split($line);
		}
	}

	public function areGridsEqual($new) {
		for ($i=0; $i < sizeof($this->grid)-1; $i++) { 
			for ($j=0; $j < sizeof($this->grid[$i])-1; $j++) { 
				if ($this->grid[$i][$j] != $new[$i][$j]) return false;
			}
		}
		return true;
	}

	public function processSpotA($x, $y) {
		$count = $this->countAdjacentCells($x, $y);
		switch($this->grid[$x][$y]) {
			case "L":
				if ($count == 0) return "#";
				return "L";
			case "#":
				if ($count >= 4) return "L";
				return "#";
			default:
				return $this->grid[$x][$y];
		}
	}

	public function processSpotB($x, $y) {
		$count = $this->countSeenCells($x, $y);
		switch($this->grid[$x][$y]) {
			case "L":
				if ($count == 0) return "#";
				return "L";
			case "#":
				if ($count >= 5) return "L";
				return "#";
			default:
				return $this->grid[$x][$y];
		}
	}

	public function runLife($process = "A") {
		$newGrid = array();
		for ($i=0; $i < sizeof($this->grid); $i++) { 
			$newGrid[$i] = array();
			for ($j=0; $j < sizeof($this->grid[$i]); $j++) { 
				if ("A" == $process) {
					$newGrid[$i][$j] = $this->processSpotA($i, $j);
				} else {
					$newGrid[$i][$j] = $this->processSpotB($i, $j);
				}
			}
		}
		if (self::SHOW_GRIDS) echo $this->render($newGrid);
		if ($this->grid == $newGrid) {
			return false;
		}
		$this->grid = $newGrid;
		unset($newGrid);
		return true;
	}

	public function countAdjacentCells($x, $y) {
		$count = 0;
		foreach (self::COORDINATE_OFFSETS as $coordinate) {
			if (isset($this->grid[$x + $coordinate[0]][$y + $coordinate[1]])
				&& $this->grid[$x + $coordinate[0]][$y + $coordinate[1]] == "#") {
				$count++;
			}
		}
		return $count;
	}

	public function checkDirection($x, $y, $incX, $incY) {
		while (isset($this->grid[$x += $incX][$y += $incY]) &&
				$this->grid[$x][$y] != "L") {
			if ($this->grid[$x][$y] == "#") return true;
		}
		return false;
	}

	public function countSeenCells($x, $y) {
		$count = 0;
		foreach (self::COORDINATE_OFFSETS as $coordinate) {
			if ($this->checkDirection($x, $y, $coordinate[0], $coordinate[1])) ++$count;
		}
		return $count;
	}

	public function render($grid) {
		$output = '';
		for ($i=0; $i < sizeof($grid); $i++) { 
			for ($j=0; $j < sizeof($grid[$i]); $j++) { 
				$output .= "[" . $grid[$i][$j] . "]";
			}
			$output .= PHP_EOL;
		}
		return $output;
	}

} // class Life

function run($problem = "A") {
	$life = new Life();
	$life->createGrid();
	$count = 0;
	$notequal = true;
	while ($notequal) {
		++$count;
		$notequal = $life->runLife($problem);
	}
	$sitters = 0;
	array_walk_recursive($life->grid, function($item) use (&$sitters) {
	    if ($item === "#") ++$sitters;
	});

	echo "Sitters stable at '$sitters' after $count runs.\n";
}
echo "Puzzle 11a:\n";
run();
echo "**********************************************************\n";
echo "Puzzle 11b:\n";
run("B");
