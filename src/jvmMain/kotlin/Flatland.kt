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
            val x = it % width
            val y = it / width
            logger.debug { "init cell X: $x Y: $y" }
            Cell(x, y, randomAlive(initProbability))
        }

        cellList.forEach {cell ->
            neighboursDirection.forEach {
                cellAt(cell.x + it.first, cell.y + it.second)
                    ?.let { cell.addNeighbour(it) }
            }
        }
    }

    private fun cellAt(x: Int, y: Int): Cell? {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null
        }
        val index = x * width + y
        logger.debug { "$x x $y => $index" }
        return cellList[index]
    }

    fun update() {
        round += 1
        cellList.forEach { it.updateNextState() }

        cellList.forEach { it.updateAliveState() }
    }

    fun toMatrix(): Array<IntArray>  {
        val byteArray: Array<IntArray> = Array(height) {
            IntArray(width)
        }
        cellList.forEach {
            byteArray[it.y][it.x] = if (it.alive) 1 else 0
        }
        return byteArray
    }

    override fun toString(): String {
        val lastIndex = width - 1
        return StringBuilder().apply {
            append("Flatlnag($round):\n")
            cellList.map {
                append(if (it.alive) "1" else "0")
                if (it.x == lastIndex) {
                    append('\n')
                } else {
                    append(' ')
                }
            }
        }.toString()
    }
}
