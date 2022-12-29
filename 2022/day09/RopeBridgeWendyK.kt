import java.io.File

fun printStatus() {
    println("H at $headX, $headY")
    println("T at $tailX, $tailY")
    println("~~~~~~")
    println(visitedSet)
    println("======")
}

fun checkAndAdjustTail() {

    if ( Math.abs(headX-tailX) <=1 && Math.abs(headY-tailY) <=1 ){
        //same x same y or touching
        //nothing to do
    } else if (headX == tailX && headY != tailY) {
        //same x diff y
        if (headY > tailY) {
            tailY++
        } else {
            tailY--
        }
    } else if (headX != tailX && headY == tailY) {
        //diff x same y
        if (headX > tailX) {
            tailX++
        } else {
            tailX--
        }
    } else {
        //diff x diff y
        if (headY > tailY) {
            tailY++
        } else {
            tailY--
        }
        if (headX > tailX) {
            tailX++
        } else {
            tailX--
        }
    }

    visitedSet.add(Pair(tailX, tailY))
}

private var headY=0
private var headX=0
private var tailY=0
private var tailX=0

private var visitedSet = HashSet<Pair<Int, Int>>()

enum class Direction(val dir: String) { R("R"), L("L"), U("U"), D("D")}

fun doActions(dir: Direction, numSteps: Int) {
    println("$dir and $numSteps")
    for (s in 1..numSteps) {
        when (dir) {
           Direction.R -> { headX++; checkAndAdjustTail() }
           Direction.L -> { headX--; checkAndAdjustTail() }
           Direction.U -> { headY++; checkAndAdjustTail() }
           Direction.D -> { headY--; checkAndAdjustTail() }
        }
    }
    printStatus()
}

fun main() {
//    visitedSet.add(Pair(tailX, tailY))
    printStatus()

    val input = "src/main/day9/input.txt"
//    val input = "src/main/day9/sample.txt"

    File(input).useLines { lines ->
        lines.forEach {
            val instr = it.split(" ")
            doActions(Direction.valueOf(instr[0]), instr[1].toInt())
        }
    }

    println("Number of visited positions = ${visitedSet.size}")
}