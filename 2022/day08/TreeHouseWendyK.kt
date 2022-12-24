import java.io.File

fun isVisibleFromTop(x: Int, y: Int): Boolean {
    if (y==0) return true
    val value = grid!![y][x].digitToInt()
    for (row in y-1 downTo 0) {
//        println("Testing $x, $y... at $x, $row")
        if (value <= grid!![row][x].digitToInt()) return false
    }
    return true
}

fun isVisibleFromBottom(x: Int, y: Int) : Boolean {
    if (y==sizeOfGrid-1) return true

    val value = grid!![y][x].digitToInt()
    for (row in y+1 until sizeOfGrid) {
        if (value <= grid!![row][x].digitToInt()) return false
    }
    return true
}

fun isVisibleFromLeft(x: Int, y: Int): Boolean {
    if (x==0) return true

    val value = grid!![y][x].digitToInt()
    for (col in x-1 downTo 0) {
        if (value <= grid!![y][col].digitToInt()) return false
    }
    return true
}

fun isVisibleFromRight(x: Int, y: Int): Boolean {
    if (x==sizeOfGrid-1) return true

    val value = grid!![y][x].digitToInt()
    for (col in x+1 until sizeOfGrid) {
        if (value <= grid!![y][col].digitToInt()) return false
    }
    return true
}


fun scoreFromTop(x: Int, y: Int): Int {
    if (y==0) return 0
    var score=0
    val value = grid!![y][x].digitToInt()
    for (row in y-1 downTo 0) {
//        println("Testing $x, $y... at $x, $row")
        score++
        if (value <= grid!![row][x].digitToInt()) break
    }
    return score
}

fun scoreFromBottom(x: Int, y: Int) : Int {
    if (y==sizeOfGrid-1) return 0
    var score=0
    val value = grid!![y][x].digitToInt()
    for (row in y+1 until sizeOfGrid) {
        score++
        if (value <= grid!![row][x].digitToInt()) break
    }
    return score
}

fun scoreFromLeft(x: Int, y: Int): Int {
    if (x==0) return 0
    var score=0
    val value = grid!![y][x].digitToInt()
    for (col in x-1 downTo 0) {
        score++
        if (value <= grid!![y][col].digitToInt()) break
    }
    return score
}

fun scoreFromRight(x: Int, y: Int): Int {
    if (x==sizeOfGrid-1) return 0
    var score=0
    val value = grid!![y][x].digitToInt()
    for (col in x+1 until sizeOfGrid) {
        score++
        if (value <= grid!![y][col].digitToInt()) break
    }
    return score
}

var sizeOfGrid=0
var grid: List<List<Char>>? = null // [y][x]

fun main() {
    val input = "src/main/day8/input.txt"
//    val input = "src/main/day8/sample.txt"

    File(input).useLines {
        sizeOfGrid=it.first().length
    }

    println("size is $sizeOfGrid")

    grid = File(input).readText().replace("\\s".toRegex(),"").toList().chunked(sizeOfGrid)
    var numTrees=0

    for (y in 0 until sizeOfGrid) {
        for (x in 0 until sizeOfGrid) {
            print(grid!![y][x])
            if (isVisibleFromTop(x, y) || isVisibleFromBottom(x, y) || isVisibleFromLeft(x, y) || isVisibleFromRight(x, y)) numTrees++
        }
        println()
    }

    println("Num of visible trees is $numTrees")
//    val x = 1
//    val y = 2
//    println("at $x, $y it is ${grid[y-1][x-1]}")

    var bestScore=0
    for (y in 0 until sizeOfGrid) {
        for (x in 0 until sizeOfGrid) {
            print(grid!![y][x])
            val score = scoreFromTop(x, y) * scoreFromBottom(x, y) * scoreFromLeft(x, y) * scoreFromRight(x, y)
            if (score > bestScore) bestScore = score
        }
        println()
    }

    println("Best score is $bestScore")
}