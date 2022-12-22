import java.io.File

    /*
	 * Lowercase item types a through z have priorities 1 through 26.
	   Uppercase item types A through Z have priorities 27 through 52.
	 */
    fun getPriority(c: Char): Int {
        return if (Character.isUpperCase(c)) {
            c.code - 38
        } else {
            c.code - 96
        }
    }


fun main() {
    var sum=0
    val input = "src/main/day3/input.txt";
//    val input = "src/main/day3/sample.txt";
    File(input).useLines { lines -> lines.forEach {
//        println(it);
        val length: Int = it.length
        val c1: String = it.substring(0, length / 2)
        val c2: String = it.substring(length / 2, length)
        println("$c1, $c2")
        val m = c1.filter { c ->  c2.contains(c); };
        println(m);
        sum += getPriority(m[0]);
    }}
    println("Part 1 Total = $sum")

    sum=0
    val lines = File(input).readLines().iterator();
    while (lines.hasNext()) {
        val e1 = lines.next();
        val e2 = lines.next();
        val e3 = lines.next();
        var m = e1.filter { c -> e2.contains(c)}
        m = m.filter { c -> e3.contains(c)};
        println(m)
        sum += getPriority(m[0]);
    }
    println("Part 2 Total = $sum")
}
