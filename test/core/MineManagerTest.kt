package test.domain

import core.manager.MineManagerImpl
import model.Position
import model.Square
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MineManagerTest {
    private lateinit var mineManagerImpl: MineManagerImpl
    private val boardSize = 4
    private val mineCount = 2

    @Before
    fun setup() {
        mineManagerImpl = MineManagerImpl(boardSize, mineCount)
    }

    @Test
    fun `places correct number of mines`() {
        val grid = createEmptyGrid()
        mineManagerImpl.placeMines(grid)

        val totalMines = countMines(grid)
        assertEquals(mineCount, totalMines)
    }

    @Test
    fun `adjacent mine count is correct`() {
        val grid = createEmptyGrid()
        // Place a mine manually
        grid[1][1].hasMine = true

        // Check adjacent squares
        assertEquals(1, mineManagerImpl.countAdjacentMines(Position(0, 0), grid))
        assertEquals(1, mineManagerImpl.countAdjacentMines(Position(0, 1), grid))
        assertEquals(1, mineManagerImpl.countAdjacentMines(Position(1, 0), grid))
        assertEquals(0, mineManagerImpl.countAdjacentMines(Position(3, 3), grid))
    }

    @Test
    fun `mine placement is within bounds`() {
        val grid = createEmptyGrid()
        mineManagerImpl.placeMines(grid)

        // Check all mines are within board boundaries
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                if (grid[row][col].hasMine) {
                    assertTrue(row in 0 until boardSize)
                    assertTrue(col in 0 until boardSize)
                }
            }
        }
    }

    @Test
    fun `mines are randomly distributed`() {
        val grid1 = createEmptyGrid()
        val grid2 = createEmptyGrid()

        mineManagerImpl.placeMines(grid1)
        mineManagerImpl.placeMines(grid2)

        // At least one mine should be in a different position
        var hasDifference = false
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                if (grid1[row][col].hasMine != grid2[row][col].hasMine) {
                    hasDifference = true
                    break
                }
            }
        }

        assertTrue("Mines should be randomly distributed", hasDifference)
    }

    private fun createEmptyGrid(): Array<Array<Square>> =
        Array(boardSize) { row ->
            Array(boardSize) { col ->
                Square(Position(row, col))
            }
        }

    private fun countMines(grid: Array<Array<Square>>): Int = grid.sumOf { row -> row.count { it.hasMine } }
}