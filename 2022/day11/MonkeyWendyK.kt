import java.io.File
import java.math.BigInteger

private class Monkey(descr: List<String>) {

    val name: Int

    val items: ArrayDeque<BigInteger>

    val operation: Transformation

    enum class Transformation {
        SQUARE, TIMES, PLUS
    }

    val opValue: BigInteger

    fun transform(old: BigInteger): BigInteger {
        var temp = old
        when(operation) {
            Transformation.SQUARE -> temp = temp.times(old)
            Transformation.TIMES -> temp = temp.times(opValue)
            Transformation.PLUS -> temp = temp.plus(opValue)
        }
//part 1
//        return temp.floorDiv(3)
//part 2

        return temp.mod(lcm)
    }

    val divisor: BigInteger
    val trueMonkey: Int
    val falseMonkey: Int
    var numTest = 0

    fun passTest(value: BigInteger): Boolean {
        numTest++
        return value.mod(divisor) == BigInteger.ZERO
    }

    fun addItem(value: BigInteger) = items.add(value)

    init {
        //name
        var mr = """Monkey (\d+):""".toRegex().matchEntire(descr[0].trim())
        if (mr?.groupValues != null) {
            name = Integer.parseInt(mr.groupValues[1])
        } else throw RuntimeException("not possible")

        //items
        mr = """Starting items: (.*)""".toRegex().matchEntire(descr[1].trim())
        if (mr?.groupValues != null) {
            items = ArrayDeque<BigInteger>(elements = mr!!.groupValues[1].replace("""\s""".toRegex(), "").split(",").map {
                it.toBigInteger()
            })
        } else throw RuntimeException("not possible")

        //operation
        mr = """Operation: new = old (.) (.*)""".toRegex().matchEntire(descr[2].trim())
        if (mr?.groupValues != null) {
            val op = mr!!.groupValues[1]
            val change = mr.groupValues[2]
            if (op == "*" && change == "old") {
                //double
                operation = Transformation.SQUARE
                opValue = BigInteger.ZERO //never used
            } else if (op == "*") { //multiply by const
                operation = Transformation.TIMES
                opValue = change.toBigInteger()
            } else if ( op == "+") { //add by const
                operation = Transformation.PLUS
                opValue = change.toBigInteger()
            } else throw RuntimeException("not possible")

        } else throw RuntimeException("not possible")

        //test
        mr = """Test: divisible by (.*)""".toRegex().matchEntire(descr[3].trim())
        if (mr?.groupValues != null) {
            divisor = mr!!.groupValues[1].toBigInteger()
        } else throw RuntimeException("not possible")

        mr = """If true: throw to monkey (.*)""".toRegex().matchEntire(descr[4].trim())
        if (mr?.groupValues != null) {
            trueMonkey = mr!!.groupValues[1].toInt()
        } else throw RuntimeException("not possible")

        mr = """If false: throw to monkey (.*)""".toRegex().matchEntire(descr[5].trim())
        if (mr?.groupValues != null) {
            falseMonkey = mr!!.groupValues[1].toInt()
        } else throw RuntimeException("not possible")
    }

    override fun toString(): String {
        return "" + name + "\n" + items.toString() + "\n" + operation.name + ", " + opValue + ", " + divisor + "\n" + trueMonkey + ", " + falseMonkey + "\n"
    }
}

var lcm = BigInteger.ONE

fun main() {

    val input = "src/main/day11/input.txt"
//    val input = "src/main/day11/sample.txt"

    val monkeys = File(input).readLines().chunked(7) {
        Monkey(it)
    }


    monkeys.forEach {
        println(it)
        //for part 2
        lcm = lcm.times(it.divisor)
    }

//part 1
//    val numRound = 20
//part 2
    val numRound = 10000
    for (i in 1..numRound) {
        monkeys.forEach { m ->
            m.items.forEach { item ->
                val newVal = m.transform(item)

                if (m.passTest(newVal)) {
                    monkeys[m.trueMonkey].addItem(newVal)
                } else {
                    monkeys[m.falseMonkey].addItem(newVal)
                }

            }
            m.items.clear()
        }

        if (i == 1 || i == 20 || i % 1000 == 0) {
            println("Round $i")
            monkeys.forEach {
                println("     Monkey ${it.name} (tested ${it.numTest}): ${it.items}")
            }
        }
    }

    val testList = ArrayList<Int>()
    monkeys.forEach {
        println("Number of tests by Monkey ${it.name}: ${it.numTest}")
        testList.add(it.numTest)
    }
    val sortedList = testList.sortedDescending()
    println(sortedList[0].toBigInteger().times(sortedList[1].toBigInteger()))
}