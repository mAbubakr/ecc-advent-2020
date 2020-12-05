<?php
class Seat
{
	private $row;
	private $column;

	function set($row, $column)
	{
		$this->row = $row;
		$this->column = $column;
	}

	public function decode($param)
	{
		$this->row = $this->getSpot(str_split(substr($param, 0, 7)), 0, 127, "F", "B");
		$this->column = $this->getSpot(str_split(substr($param, 7)), 0, 7, "L", "R");
	}

	function getRow() { return $this->row; }
	function getColumn() { return $this->column; }
	function getId()
	{
		return ($this->row * 8) + $this->column;
	}

	function getSeat()
	{
		return "Row: '" . $this->row . "'" . "Column: '" . $this->column . "'";
	}

	function getSpot($list, $min, $max, $goLow, $goHigh)
	{
		// echo "min: $min, max: $max\n";
		$consider = array_shift($list);
		// echo "consider: $consider\n";
		if (empty($list)) {
			if ($goLow == $consider) return $min;
			return $max;
		}
		if ($goLow == $consider) {
			return $this->getSpot($list, $min, intdiv($max - $min, 2)+$min, $goLow, $goHigh);
		}
		elseif ($goHigh == $consider) {
			return $this->getSpot($list, intdiv($max - $min, 2)+$min+1, $max, $goLow, $goHigh);
		}
	}
}

class Plane
{
	private $grid;

	function __construct() {
		$this->grid = array_fill(0, 128, array_fill(0, 8, false));
	}
	function fillSeat($row, $column) {
		$this->grid[$row][$column] = true;
	}
	function findEmptySeat() {
		// hard set to a number higher than the empty rows shown by showPlane
		for ($rowIndex = 13; $rowIndex < count($this->grid); $rowIndex++) {
			for ($columnIndex = 0; $columnIndex < 8; $columnIndex++) {
				if (!$this->grid[$rowIndex][$columnIndex]) {
					$seat = new Seat();
					$seat->set($rowIndex, $columnIndex);
					return $seat;
				}
			}
		}
	}
	// the 'O' will mark empty seats, easily see where empty middle seat is
	function showPlane() {
		for ($columnIndex = 0; $columnIndex < 8; $columnIndex++) {
			for ($rowIndex = 0; $rowIndex < count($this->grid); $rowIndex++) {
				echo (($this->grid[$rowIndex][$columnIndex]) ? '.' : 'O');
			}
			echo "\n";
		}
	}
}

$plane = new Plane();
echo "Puzzle 05a:\n";
$file = file_get_contents('./input.txt');
$inputs = explode("\n", $file);
$currentHighest = 0;
foreach ($inputs as $input) {
	$seat = new Seat();
	$seat->decode($input);
	if ($seat->getId() > $currentHighest) $currentHighest = $seat->getId();
	$plane->fillSeat($seat->getRow(), $seat->getColumn());
}
echo "Highest Seat was id: $currentHighest.\n";
$plane->showPlane();
$mySeat = $plane->findEmptySeat();
echo "My seat: " . $mySeat->getSeat() . ", id: " . $mySeat->getId() . "\n";
