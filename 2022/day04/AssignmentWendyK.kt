import java.io.File

fun main() {
    val input = "src/main/day4/input.txt"
//    val input = "src/main/day4/sample.txt"
    var countFull = 0
    var countPartial = 0
    File(input).useLines { lines ->
        lines.forEach {
            println(it)

            val r = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()
            val mr = r.matchEntire(it)
            val matches = mr?.groupValues
            if (matches != null) {
                val s1 = Integer.parseInt(matches[1])
                val e1 = Integer.parseInt(matches[2])
                val s2 = Integer.parseInt(matches[3])
                val e2 = Integer.parseInt(matches[4])

                val set1: HashSet<Int> = HashSet.newHashSet(0)
                val set2: HashSet<Int> = HashSet.newHashSet(0)
                for (i in s1..e1) {
                    set1.add(i)
                }
                for (i in s2..e2) {
                    set2.add(i)
                }

                if (set1.containsAll(set2) || set2.containsAll(set1)) {
                    countFull++
                }

                if (!set1.intersect(set2).isEmpty())
                    countPartial++


            }

        }
    }
    println("Number of full pairs = $countFull")
    println("Number of partial pairs = $countPartial")
}