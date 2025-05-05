package test.core

import core.manager.MineManagerImpl
import core.manager.SquareRevealManagerImpl
import model.Position
import model.Square
import model.SquareState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SquareRevealManagerTest {
    private lateinit var squareRevealManagerImpl: SquareRevealManagerImpl
    private lateinit var mineManagerImpl: MineManagerImpl
    private val boardSize = 3

    @Before
    fun setup() {
        squareRevealManagerImpl = SquareRevealManagerImpl()
        mineManagerImpl = MineManagerImpl(boardSize, 0) // No mines for basic tests
    }

    @Test
    fun `reveal single square changes state to REVEALED`() {
        val grid = createEmptyGrid()
        val position = Position(1, 1)

        squareRevealManagerImpl.revealSquareRecursively(position, grid, mineManagerImpl)

        assertEquals(SquareState.REVEALED, grid[1][1].state)
    }

    @Test
    fun `reveal square with adjacent mines only reveals that square`() {
        val grid = createEmptyGrid()
        grid[1][1].adjacentMines = 1

        squareRevealManagerImpl.revealSquareRecursively(Position(1, 1), grid, mineManagerImpl)

        assertEquals(SquareState.REVEALED, grid[1][1].state)
        assertEquals(SquareState.HIDDEN, grid[0][0].state)
        assertEquals(SquareState.HIDDEN, grid[0][1].state)
    }

    @Test
    fun `reveal empty square reveals adjacent empty squares`() {
        val grid = createEmptyGrid()

        squareRevealManagerImpl.revealSquareRecursively(Position(1, 1), grid, mineManagerImpl)

        // Center and adjacent squares should be revealed
        assertEquals(SquareState.REVEALED, grid[1][1].state)
        assertEquals(SquareState.REVEALED, grid[0][1].state)
        assertEquals(SquareState.REVEALED, grid[1][0].state)
        assertEquals(SquareState.REVEALED, grid[1][2].state)
        assertEquals(SquareState.REVEALED, grid[2][1].state)
    }

    @Test
    fun `reveal already revealed square does nothing`() {
        val grid = createEmptyGrid()
        val position = Position(1, 1)
        grid[1][1].state = SquareState.REVEALED

        squareRevealManagerImpl.revealSquareRecursively(position, grid, mineManagerImpl)

        // Adjacent squares should remain hidden
        assertEquals(SquareState.HIDDEN, grid[0][1].state)
        assertEquals(SquareState.HIDDEN, grid[1][0].state)
    }

    private fun createEmptyGrid(): Array<Array<Square>> {
        return Array(boardSize) { row ->
            Array(boardSize) { col ->
                Square(Position(row, col))
            }
        }
    }
}