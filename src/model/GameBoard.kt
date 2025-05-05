package model

import core.manager.GameStateManagerImpl
import core.manager.GameStateManager
import core.manager.MineManager
import core.manager.SquareRevealManager
import core.manager.MineManagerImpl
import core.manager.SquareRevealManagerImpl

class GameBoard(
    private val size: Int,
    private val numberOfMines: Int,
    private val mineManagerImpl: MineManager = MineManagerImpl(size, numberOfMines),
    private val gameStateManagerImpl: GameStateManager = GameStateManagerImpl(),
    private val squareRevealManagerImpl: SquareRevealManager = SquareRevealManagerImpl()
) {
    private val grid: Array<Array<Square>> = Array(size) { row ->
        Array(size) { col ->
            Square(Position(row, col))
        }
    }

    init {
        require(isValidGridSize(size)) { "Grid size must be between 1 and $MAX_GRID_SIZE" }
        require(isValidNumberOfMines(numberOfMines)) {
            "Number of mines must be between 1 and 35% of total squares"
        }
        mineManagerImpl.placeMines(grid)
        mineManagerImpl.calculateAdjacentMines(grid)
    }

    private fun isValidGridSize(size: Int) = size in 1..MAX_GRID_SIZE

    private fun isValidNumberOfMines(numberOfMines: Int) = numberOfMines in 1..mineManagerImpl.getMaxMines()

    fun getBoardSize(): Int = size

    fun revealSquare(position: Position): Boolean {
        if (!gameStateManagerImpl.isPlaying()) return false

        val square = grid[position.row][position.col]
        if (square.state == SquareState.REVEALED) return false

        if (square.hasMine) {
            gameStateManagerImpl.setGameLost()
            return false
        }

        // Reveal the square and its neighbors recursively
        squareRevealManagerImpl.revealSquareRecursively(position, grid, mineManagerImpl)

        if (hasWon()) {
            gameStateManagerImpl.setGameWon()
        }

        return true
    }

    private fun hasWon(): Boolean {
        for (row in 0 until size) {
            for (col in 0 until size) {
                val square = grid[row][col]
                if (!square.hasMine && square.state != SquareState.REVEALED) {
                    return false
                }
            }
        }
        return true
    }

    fun getGameState() = gameStateManagerImpl.getGameState()

    fun getSquareAdjacentMines(position: Position): Int = grid[position.row][position.col].adjacentMines

    fun getSquare(row: Int, column: Int): Square = grid[row][column]

    companion object {
        const val MAX_GRID_SIZE = 30  // Define a maximum grid size limit to prevent potential memory issues and ensure the game remains playable
    }
}