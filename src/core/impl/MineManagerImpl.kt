package core.manager

import model.Position
import model.Square
import kotlin.random.Random

class MineManagerImpl(private val size: Int, private val numberOfMines: Int) : MineManager {
    override fun placeMines(grid: Array<Array<Square>>) {
        var minesPlaced = 0
        while (minesPlaced < numberOfMines) {
            val row = Random.nextInt(size)
            val col = Random.nextInt(size)
            if (!grid[row][col].hasMine) {
                grid[row][col].hasMine = true
                minesPlaced++
            }
        }
    }

    override fun calculateAdjacentMines(grid: Array<Array<Square>>) {
        for (row in 0 until size) {
            for (col in 0 until size) {
                if (!grid[row][col].hasMine) {
                    grid[row][col].adjacentMines = countAdjacentMines(Position(row, col), grid)
                }
            }
        }
    }

    fun countAdjacentMines(pos: Position, grid: Array<Array<Square>>): Int = getAdjacentPositions(pos)
        .count { grid[it.row][it.col].hasMine }

    override fun getAdjacentPositions(pos: Position): List<Position> {
        val positions = mutableListOf<Position>()
        // Check all 8 neighboring squares
        for (i in -1..1) {
            for (j in -1..1) {
                val newRow = pos.row + i
                val newCol = pos.col + j
                // Skip if out of bounds or current position
                if (newRow in 0 until size &&
                    newCol in 0 until size &&
                    !(i == 0 && j == 0)
                ) {
                    positions.add(Position(newRow, newCol))
                }
            }
        }
        return positions
    }

    override fun getMaxMines(): Int = (size * size * 0.35).toInt()
}