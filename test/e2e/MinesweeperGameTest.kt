package test.e2e

import model.GameState
import model.Position
import model.SquareState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import viewmodel.MinesweeperViewModel

class MinesweeperGameTest {
    private lateinit var viewModel: MinesweeperViewModel
    private val boardSize = 4
    private val mineCount = 2

    @Before
    fun setup() {
        viewModel = MinesweeperViewModel(boardSize, mineCount)
    }

    @Test
    fun `test complete game winning scenario`() {
        // Initial state check
        assertEquals(GameState.PLAYING, viewModel.getGameState())
        assertEquals(boardSize, viewModel.getBoardSize())

        // Reveal all non-mine squares
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                val position = Position(row, col)
                val square = viewModel.getSquare(row, col)

                if (!square.hasMine) {
                    viewModel.revealSquare(position)
                    assertEquals(SquareState.REVEALED, square.state)
                }
            }
        }

        // Verify win condition
        assertEquals(GameState.WON, viewModel.getGameState())
    }

    @Test
    fun `test complete game losing scenario`() {
        // Find a mine
        var minePosition: Position? = null
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                if (viewModel.getSquare(row, col).hasMine) {
                    minePosition = Position(row, col)
                    break
                }
            }
            if (minePosition != null) break
        }

        // Reveal the mine
        assertNotNull("Should have found a mine", minePosition)
        viewModel.revealSquare(minePosition!!)

        // Verify loss condition
        assertEquals(GameState.LOST, viewModel.getGameState())
    }

    @Test
    fun `test revealing empty square reveals connected empty squares`() {
        // Find an empty square with no adjacent mines
        var emptyPosition: Position? = null
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                val square = viewModel.getSquare(row, col)
                if (!square.hasMine && square.adjacentMines == 0) {
                    emptyPosition = Position(row, col)
                    break
                }
            }
            if (emptyPosition != null) break
        }

        assertNotNull("Should have found an empty square", emptyPosition)

        // Reveal the empty square
        viewModel.revealSquare(emptyPosition!!)

        // Verify connected empty squares are revealed
        var revealedCount = 0
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                if (viewModel.getSquare(row, col).state == SquareState.REVEALED) {
                    revealedCount++
                }
            }
        }

        assertTrue("Multiple squares should be revealed", revealedCount > 1)
    }

    @Test
    fun `test game progression with number hints`() {
        // Find a square with adjacent mines
        var numberPosition: Position? = null
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                val square = viewModel.getSquare(row, col)
                if (!square.hasMine && square.adjacentMines > 0) {
                    numberPosition = Position(row, col)
                    break
                }
            }
            if (numberPosition != null) break
        }

        assertNotNull("Should have found a square with adjacent mines", numberPosition)

        // Reveal the square
        val expectedAdjacentMines = viewModel.getSquare(numberPosition!!.row, numberPosition.col).adjacentMines
        viewModel.revealSquare(numberPosition)

        // Verify the number is correct and game continues
        assertEquals(SquareState.REVEALED, viewModel.getSquare(numberPosition.row, numberPosition.col).state)
        assertEquals(expectedAdjacentMines, viewModel.getSquareAdjacentMines(numberPosition))
        assertEquals(GameState.PLAYING, viewModel.getGameState())
    }
}