import java.io.File

fun printStatus2() {
    println("H at ${positions[HEAD_INDEX].toList()}")
    println("T at ${positions[TAIL_INDEX].toList()}")
    println("~~~~~~")
    println(visitedSet)
    println("======")
}

fun checkAndAdjust(index: Int) {
println("Adjusting index $index")
    var relTailX = positions[index][0]
    var relTailY = positions[index][1]
    var relHeadX = positions[index-1][0]
    var relHeadY = positions[index-1][1]

    println("Head is at $relHeadX,$relHeadY, Tail original at $relTailX,$relTailY")

    if ( Math.abs(relHeadX-relTailX) <=1 && Math.abs(relHeadY-relTailY) <=1 ){
        //same x same y or touching
        //nothing to do
    } else if (relHeadX == relTailX && relHeadY != relTailY) {
        //same x diff y
        if (relHeadY > relTailY) {
            relTailY++
        } else {
            relTailY--
        }
    } else if (relHeadX != relTailX && relHeadY == relTailY) {
        //diff x same y
        if (relHeadX > relTailX) {
            relTailX++
        } else {
            relTailX--
        }
    } else {
        //diff x diff y
        if (relHeadY > relTailY) {
            relTailY++
        } else {
            relTailY--
        }
        if (relHeadX > relTailX) {
            relTailX++
        } else {
            relTailX--
        }
    }

    println("Now at $relTailX and $relTailY")
    positions[index] = intArrayOf(relTailX, relTailY)
}

fun checkAndAdjustTail2() {

    //head already moved, adjust starting at index 1
    for (i in 1..TAIL_INDEX) {
        checkAndAdjust(i)
    }

    visitedSet.add(Pair(positions[TAIL_INDEX][0], positions[TAIL_INDEX][1]))
}

private const val NUM_KNOTS = 10
private const val HEAD_INDEX = 0
private const val TAIL_INDEX = NUM_KNOTS-1

private var positions = MutableList<IntArray>(NUM_KNOTS) { intArrayOf(0, 0) }
private var visitedSet = HashSet<Pair<Int, Int>>()


fun doActions2(dir: Direction, numSteps: Int) {
    println("$dir and $numSteps")
    var x = positions[HEAD_INDEX][0]
    var y = positions[HEAD_INDEX][1]
    for (s in 1..numSteps) {
        when (dir) {
            Direction.R -> x++
            Direction.L -> x--
            Direction.U -> y++
            Direction.D -> y--
        }
        positions[HEAD_INDEX] = intArrayOf(x, y)
        checkAndAdjustTail2()
    }
    printStatus2()
}

fun main() {

    printStatus2()

    val input = "src/main/day9/input.txt"
//    val input = "src/main/day9/sample.txt"
//    val input = "src/main/day9/sample2.txt"

    File(input).useLines { lines ->
        lines.forEach {
            val instr = it.split(" ")
            doActions2(Direction.valueOf(instr[0]), instr[1].toInt())
        }
    }

    println("Number of visited positions = ${visitedSet.size}")
}