<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Str;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Storage;

class Advent2020Controller extends Controller
{
	private function getInputArray($filename = '01-input.txt')
	{
    	return preg_split("/\r\n|\n|\r/", Storage::get($filename));
	}

    public function Puzzle01a()
    {
    	$pass1 = array();
    	$pass1 = PuzzleController::getInputArray();
    	$pass2 = $pass1;
    	foreach ($pass1 as $input1) {
    		foreach ($pass2 as $input2) {
    			if ($input1 == $input2) {
       				continue;
   				}
    			if (($input1 + $input2) == 2020) {
    				echo $input1 . " + " . $input2 . " = 2020<br>";
    				echo $input1 . " x " . $input2 . " = " . ($input1 * $input2) . "<br>";
    				return;
    			}
    		}
    	}
    }
    public function Puzzle01b()
    {
    	$pass1 = array();
    	$pass1 = PuzzleController::getInputArray();
    	$pass2 = $pass1;
    	$pass3 = $pass1;
    	//for each line
    	foreach ($pass1 as $input1) {
    		foreach ($pass2 as $input2) {
    			if ($input1 == $input2) continue; 
	    		foreach ($pass3 as $input3) {
	    			if (($input1 == $input3) || ($input2 == $input3)) continue; 
    
	    			if (($input1 + $input2 + $input3) == 2020) {
	    				echo $input1 . " + " . $input2 . " + " . $input3 . " = 2020<br>";
	    				echo $input1 . " x " . $input2 . " x " . $input3 . " = " . ($input1 * $input2 * $input3) . "<br>";
	    				return;
	    			}
	    		}
    		}
    	}
    }

}
