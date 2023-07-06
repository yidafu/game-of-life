import kotlin.Array

const val PROBALILITY_RANGE: Int = 1000

internal inline fun randomAlive(probability: Int): Boolean {
    return (Math.random() * PROBALILITY_RANGE) <= probability
}

class Flatland(private val width: Int, private val height: Int, private var round: Int = 0, val initProbability: Int = 100) {
    private val cellList: Array<Cell>

    private val neighboursDirection = listOf<Pair<Int, Int>>(
        -1 to -1, -1 to 0,  -1 to 1, // ktlint-disable argument-list-wrapping
        0 to -1,            0 to 1,
        1 to -1,  1 to 0,   1 to 1
    )
    init {
        val size = width * height

        logger.info { "World Size: $width x $height ($size)" }
        cellList = Array(width * height) {
            val col = it % width
            val row = it / height
            Cell(row, col, randomAlive(initProbability))
        }

        cellList.forEach {cell ->
            neighboursDirection.forEach {
                cellAt(cell.row + it.first, cell.col + it.second)
                    ?.let { cell.addNeighbour(it) }
            }
        }
    }

    private fun cellAt(row: Int, col: Int): Cell? {
        if (row < 0 || row >= width || col < 0 || col >= height) {
            return null
        }
        val index = row * width + col
        logger.debug { "$row x $col => $index" }
        return cellList[index]
    }

    fun update() {
        round += 1
        cellList.forEach { it.updateNextState() }
//        println()
//        println(this.toString())
        cellList.forEach { it.updateAliveState() }
    }

    fun toMatrix(): Array<IntArray>  {
        val byteArray: Array<IntArray> = Array(height) {
            IntArray(width)
        }
        cellList.forEach {
            byteArray[it.row][it.col] = if (it.alive) 1 else 0
        }
        return byteArray
    }

    override fun toString(): String {
        val lastIndex = width - 1
        return StringBuilder().apply {
            append("Flatlnag($round):\n")
            cellList.map {
                append(if (it.alive) "1" else "0")
                if (it.col == lastIndex) {
                    append('\n')
                } else {
                    append(' ')
                }
            }
        }.toString()
    }
}
