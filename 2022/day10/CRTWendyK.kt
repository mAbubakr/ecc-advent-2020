import java.io.File

var registerValue = 1
var cycle = 0
val interestedCycles = intArrayOf(20, 60, 100, 140, 180, 220)
var sum = 0
var crtPosition = 0

fun incrementCycle() {
    //start cycle
    cycle++
    //draw at current position
    val position = crtPosition % 40
    if (position >= registerValue-1 && position <= registerValue+1) {
        print("#")
    } else {
        print(".")
    }
    if (position == 39) println()
    //move position for next time
    crtPosition++

    //during cycle - check value if needed
    if (cycle in interestedCycles) {
        sum += cycle * registerValue
    }
}

fun main() {

    val input = "src/main/day10/input.txt"
//    val input = "src/main/day10/sample.txt"

    File(input).useLines { lines ->
        lines.forEach {
            if (it == "noop") {
                incrementCycle()
            } else {
                incrementCycle()
                incrementCycle()
                //after 2nd cycle, increment value
                val instr = it.split(" ")
                registerValue += instr[1].toInt()
            }

        }
    }

    println("Sum is $sum")

}