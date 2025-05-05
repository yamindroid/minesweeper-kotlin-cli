package viewmodel

import model.GameBoard
import model.GameState
import model.Position

class MinesweeperViewModel(size: Int, numberOfMines: Int) {
    private val gameBoard: GameBoard = GameBoard(size, numberOfMines)

    fun getBoardSize() = gameBoard.getBoardSize()

    fun revealSquare(position: Position) = gameBoard.revealSquare(position)

    fun getSquare(x: Int, y: Int) = gameBoard.getSquare(x, y)

    fun getSquareAdjacentMines(position: Position) = gameBoard.getSquareAdjacentMines(position)

    fun getGameState() = gameBoard.getGameState()

    fun isGamePlaying() = getGameState() == GameState.PLAYING
}