package model

data class Square(
    val position: Position,
    var hasMine: Boolean = false,
    var state: SquareState = SquareState.HIDDEN,
    var adjacentMines: Int = 0
)