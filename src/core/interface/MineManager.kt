package core.manager

import model.Position
import model.Square

interface MineManager {
    fun placeMines(grid: Array<Array<Square>>)
    fun calculateAdjacentMines(grid: Array<Array<Square>>)
    fun getAdjacentPositions(pos: Position): List<Position>
    fun getMaxMines(): Int
}