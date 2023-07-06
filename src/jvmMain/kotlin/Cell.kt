/*
每個細胞有兩種狀態 - 存活或死亡，每個細胞與以自身為中心的周圍八格細胞產生互動（如圖，黑色為存活，白色為死亡）
當前細胞為存活狀態時，當周圍的存活細胞低於2個時（不包含2個），該細胞變成死亡狀態。（模擬生命數量稀少）
當前細胞為存活狀態時，當周圍有2個或3個存活細胞時，該細胞保持原樣。
當前細胞為存活狀態時，當周圍有超過3個存活細胞時，該細胞變成死亡狀態。（模擬生命數量過多）
當前細胞為死亡狀態時，當周圍有3個存活細胞時，該細胞變成存活狀態。（模擬繁殖）
 */
data class Cell( val row: Int, val col: Int, var alive: Boolean, var nextState: Boolean = false, private val neighbours: ArrayList<Cell> = ArrayList(8)) {
    init {

        logger.debug { "init cell row: $row col: $col, alive: $alive" }
    }
    private val aliveNeighbours
        get() = neighbours.fold(0) { acc, i -> if (i.alive) acc + 1 else acc }

    fun updateNextState() {
        val nextCount = aliveNeighbours
        nextState = when (nextCount) {
            // 當周圍的存活細胞为1时，該細胞變成死亡狀態
            1 -> false
            // 當周圍有2個存活細胞時，該細胞保持原樣
            2 -> alive
            // 當周圍有3個存活細胞時，該細胞變成存活狀態
            3 -> true
            // 當周圍有超過3個存活細胞時，該細胞變成死亡狀態
            else -> false
        }
        logger.debug { "$this ==> alive count $nextCount, nextState $nextState" }
    }

    fun addNeighbour(cell: Cell) {
        logger.debug { "$this add neighbour $cell" }
        neighbours.add(cell)
    }

    fun updateAliveState() {
        alive = nextState
        logger.debug { "updated alive $alive" }
    }

    override fun toString(): String {
        return "Cell{row: $row, col: $col, alive: $alive}"
    }
}
