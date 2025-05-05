package core.manager

import model.Position
import model.Square

interface SquareRevealManager {
    fun revealSquareRecursively(position: Position, grid: Array<Array<Square>>, mineManager: MineManager)
}