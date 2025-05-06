import model.GameState
import model.Position
import model.SquareState
import viewmodel.MinesweeperViewModel

class MinesweeperCLI() {
    lateinit var viewModel: MinesweeperViewModel

    private fun startGame() {
        println("Welcome to Minesweeper!")

        while (true) {
            // Prompt the user for grid size and mine count
            val size = promptGridSize()
            val mines = promptMineCount()

            try {
                viewModel = MinesweeperViewModel(size, mines)
                playGame(viewModel)

                // Allow the user to play again after the game ends
                println("Press any key to play again...")
                readLine()

            } catch (e: IllegalArgumentException) {
                // Handle invalid input for grid size or mine count
                println(e.message)
            }
        }
    }

    private fun promptGridSize(): Int {
        while (true) {
            println("\nEnter the size of the grid (e.g. 4 for a 4x4 grid): ")
            val size = readLine()?.toIntOrNull() ?: continue
            if (!isValidGridSize(size)) {
                println("Grid size must be between 1 and $MAX_GRID_SIZE")
                continue
            }
            return size
        }
    }

    private fun isValidGridSize(size: Int) = size in 1..MAX_GRID_SIZE

    private fun promptMineCount(): Int {
        while (true) {
            println("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ")
            val mines = readLine()?.toIntOrNull() ?: continue
            return mines
        }
    }

    private fun playGame(viewModel: MinesweeperViewModel) {
        var isFirstTimeDisplay = true
        val size = viewModel.getBoardSize()

        while (viewModel.isGamePlaying()) { // Continue the game loop while the game is active
            // Display "updated" only after the first time the grid is shown
            val updated = if (isFirstTimeDisplay) "" else " updated"
            isFirstTimeDisplay = false

            displayMinefield(updated)

            print("Select a square to reveal (e.g. A1): ")
            val input = readLine()?.uppercase() ?: continue

            // Validate that the input follows the format of a letter followed by one or two digits (e.g., A1)
            val regex = Regex("^[A-Za-z][0-9]{1,2}\$")
            if (!regex.matches(input)) {
                println("Invalid input! Please use a format like A1 (letter + number).")
                continue
            }

            val row = input[0] - 'A'
            val col = input.substring(1).toIntOrNull()?.minus(1) ?: continue

            if (row !in 0 until size || col !in 0 until size) {
                println("Invalid input! Please try again.")
                continue
            }

            val position = Position(row, col)
            val isSquareRevealed = viewModel.revealSquare(position)

            if (isSquareRevealed) {
                val adjacentMines = viewModel.getSquareAdjacentMines(position)
                println("This square contains $adjacentMines adjacent mines.")
            }

            displayFinalResult(updated) {
                return@displayFinalResult
            }
        }
    }

    private fun displayFinalResult(updated: String, elseAction: () -> Unit) {
        when (viewModel.getGameState()) {
            GameState.LOST -> println("Oh no, you detonated a mine! Game over.")
            GameState.WON -> {
                displayMinefield(updated)
                println("Congratulations, you have won the game!")
            }

            else -> elseAction()
        }
    }

    private fun displayMinefield(updated: String) {
        println("\nHere is your${updated} minefield:")
        displayGrid()
    }

    private fun displayGrid() {
        val sb = StringBuilder()
        val size = viewModel.getBoardSize()
        // Add column headers (e.g., 1 2 3 ...)
        sb.append("  ")
        for (i in 1..size) {
            sb.append("$i ")
        }
        sb.append("\n")

        // Add rows with their corresponding letters and square states
        for (row in 0 until size) {
            sb.append("${('A' + row)} ") // Row label (e.g., A, B, C...)
            for (column in 0 until size) {
                val square = viewModel.getSquare(row, column)
                val symbol = when {
                    square.state == SquareState.HIDDEN -> "_"
                    else -> square.adjacentMines.toString()
                }
                sb.append("$symbol ")
            }
            sb.append("\n")
        }
        println(sb.toString())
    }

    companion object {
        const val MAX_GRID_SIZE = 10

        @JvmStatic
        fun main(args: Array<String>) {
            val minesweeperCLI = MinesweeperCLI()
            minesweeperCLI.startGame()
        }
    }
}