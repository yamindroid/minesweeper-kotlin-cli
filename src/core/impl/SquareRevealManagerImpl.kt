package core.manager

import model.Position
import model.Square
import model.SquareState

class SquareRevealManagerImpl() : SquareRevealManager {
    override fun revealSquareRecursively(position: Position, grid: Array<Array<Square>>, mineManager: MineManager) {
        val square = grid[position.row][position.col]
        // If the square is already revealed or contains a mine, stop further processing
        if (square.state == SquareState.REVEALED || square.hasMine) return

        square.state = SquareState.REVEALED

        // If square has no adjacent mines, reveal all neighboring squares
        if (square.adjacentMines == 0) {
            mineManager.getAdjacentPositions(position).forEach {
                // Recursively reveal the neighboring square
                revealSquareRecursively(it, grid, mineManager)
            }
        }
    }
}