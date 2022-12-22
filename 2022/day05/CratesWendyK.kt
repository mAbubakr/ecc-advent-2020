import java.io.File
import java.util.*

var stacks: Array<ArrayDeque<String>>? = null

fun parseOriginal(line: String) {
    val r = """\[(.|\s)\]""".toRegex()
    val mr = r.findAll(line)
    val size = mr.count()
    if (stacks == null) {
        stacks = Array(size) {
                i -> ArrayDeque<String>()
        }
    }

    for (i in 0..size-1) {
        val v = mr.elementAt(i).groupValues[1]
        val aStack = stacks!![i]
        if (v.isNotBlank()) {
            println("Adding $v")
            aStack.addLast(v)
        } else {
            println("Skipping $v")
        }
    }
}

fun parseOperation9000(line: String) {
    println("Ops on $line")
    val r = """move (\d+) from (\d+) to (\d+)""".toRegex()
    val mr = r.matchEntire(line)
    if (mr != null && mr.groupValues != null) {
        val times = Integer.parseInt(mr.groupValues[1])
        val from = Integer.parseInt(mr.groupValues[2]) - 1
        val to = Integer.parseInt(mr.groupValues[3]) - 1
        for (i in 0..times-1) {
            val crate = stacks!![from].pop()
            stacks!![to].push(crate)
        }
    }

    for (i in 0..stacks!!.size-1) {
        println(stacks!![i].peek())
    }

}

fun parseOperation9001(line: String) {
    println("Ops on $line")
    val r = """move (\d+) from (\d+) to (\d+)""".toRegex()
    val mr = r.matchEntire(line)
    if (mr != null && mr.groupValues != null) {
        val times = Integer.parseInt(mr.groupValues[1])
        val from = Integer.parseInt(mr.groupValues[2]) - 1
        val to = Integer.parseInt(mr.groupValues[3]) - 1

        val tempStack = ArrayDeque<String>()
        for (i in 0..times-1) {
            val crate = stacks!![from].pop()
            tempStack.push(crate)
        }
        for (i in 0..tempStack.size-1) {
            stacks!![to].push(tempStack.pop())
        }
    }

    for (i in 0..stacks!!.size-1) {
        println(stacks!![i].peek())
    }

}


fun main() {
    val input = "src/main/day5/input.txt"
//    val input = "src/main/day5/sample.txt"
    var parseMode = 0

    File(input).useLines { lines ->
        lines.forEach {

            if (it.isBlank()) {
                //switch to operations
                parseMode = 1

                for (i in 0..stacks!!.size-1) {
                    println(stacks!![i].peek())
                }

            } else if (parseMode == 0) {
                parseOriginal(it)
            } else {
//                parseOperation9000(it)
                parseOperation9001(it)
            }
        }
    }

    for (i in 0..stacks!!.size-1) {
        print(stacks!![i].peek())
    }

}