import java.io.File

var currNode: TreeNode? = null
var rootNode: TreeNode? = null
var sum1 = 0// running total for part 1 - at most 100000, populated by running getTotal on the rootNode

var candidatePath: String? = null
var candidateTotal: Int? = null

class TreeNode(path: String, parent: TreeNode?) {
    val children = HashSet<TreeNode>(0)
    val parent = parent
    val path = path
    var directTotal=0


    fun getTotal(candidateThreshold: Int=-1):Int {
        var temp = directTotal
        children.forEach {
            temp += it.getTotal(candidateThreshold)
        }
        if (temp <= 100000) sum1 += temp
        if (candidateThreshold != -1) {
            println("Threshold is $candidateThreshold and current candidate is $candidatePath with $candidateTotal")
            if (temp >= candidateThreshold) {
                if (candidateTotal == null || candidateTotal!! >= temp) {
                    candidatePath = path
                    candidateTotal = temp
                }
            }
        }


        return temp
    }
}

fun parseCD(line: String) {
//    println(line)
    val r = """\$ cd (.+)""".toRegex()
    val mr = r.matchEntire(line)
    val path = mr!!.groupValues[1]
    if (path == "..") {
        currNode = currNode?.parent
    } else if (path == "/") {
        var aNode = TreeNode(path, null)
        currNode = aNode
        rootNode = aNode
    } else {
        currNode = currNode?.children?.find { it.path == path}
    }
}

fun parseDir(line: String) {
//    println(line)
    val path = line.split("dir ")[1]
    //assume ls isn't run twice in the same directory, so every dir means a new node, so dumb...
    currNode?.children?.add(TreeNode(path, currNode))
}

fun parseSize(line: String) {
//    println(line)
    val filesize = Integer.parseInt(line.split(" ")[0])
    if (currNode != null) {
        currNode!!.directTotal += filesize
    }
}

fun main() {
    val input = "src/main/day7/input.txt"
//    val input = "src/main/day7/sample.txt"

    File(input).useLines { lines ->
        lines.forEach {
            if (it.startsWith("$ cd")) {
                parseCD(it)
            } else if (it.startsWith("$ ls")) {
                //nothing to do
            } else if (it.startsWith("dir")) {
                parseDir(it)
            } else {
                parseSize(it)
            }
        }
    }

    val used = rootNode?.getTotal()!!
    val unused = 70000000 - used
    val needUnused = 30000000 - unused
    println("We have 70000000 total, current used space is $used, so we have $unused, and we still need $needUnused")
    rootNode?.getTotal(needUnused)

    println(sum1)
    println("Best candidate to delete is $candidatePath with $candidateTotal")
}