# Minesweeper Game

A command-line implementation of the classic Minesweeper game in Kotlin.

## Environment Requirements

- IntelliJ IDEA 2025.1 or later (Community or Ultimate Edition)
- JDK 11 or higher
- Kotlin 1.9.0
- JUnit 4.13.1 for tests

## Project Structure

```
src/
├── core/        # Core game logic
├── model/       # Data models
├── view/        # CLI interface
└── viewmodel/   # Game coordination
test/
├── core/        # Unit tests
└── e2e/         # End-to-end tests
```

## Design Approach

The project follows SOLID principles and clean architecture:

1. **Single Responsibility**: Each manager handles one aspect of the game
2. **Interface Segregation**: Clear interfaces for each manager
3. **Dependency Inversion**: Components depend on abstractions
4. **Clean Code**: Small, focused classes with clear naming

Key Features:
- Grid size limit of 30x30 for optimal playability
- Mine count limited to 35% of total squares
- Recursive revealing of empty squares
- Comprehensive test coverage

## Getting Started

1. **Setup IntelliJ IDEA**:
   - Download and install [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) 2025.1 or later
   - Ensure the Kotlin plugin is installed and enabled

2. **Clone and Open Project**:
   - Clone the repository:
     ```
     git clone https://github.com/yamindroid/minesweeper-kotlin-cli.git
     ```
   - Open IntelliJ IDEA
   - Select `File -> Open` and navigate to the cloned directory
   - Wait for IntelliJ to import and index the project

3. **Running the Game**:
   - Navigate to `src/view/MinesweeperCLI.kt`
   - Right-click on the file and select `Run 'MinesweeperCLI'`
   - The game will start in the IntelliJ's built-in terminal

4. **Running Tests**:
   - Navigate to the `test` directory in the Project view
   - Right-click on the `test` directory
   - Select `Run 'Tests in 'test''`
   - Test results will appear in the Run tool window

## Game Rules

1. Enter grid size (1-30) and number of mines
2. Select squares using coordinates (e.g., A1)
3. Numbers show adjacent mines
4. Empty squares auto-reveal adjacent squares
5. Win by revealing all non-mine squares
6. Lose by revealing a mine

## Game play

### Sucess example
```
Welcome to Minesweeper!

Enter the size of the grid (e.g. 4 for a 4x4 grid): 
4
Enter the number of mines to place on the grid (maximum is 35% of the total squares): 
3

Here is your minefield:
  1 2 3 4
A _ _ _ _
B _ _ _ _
C _ _ _ _
D _ _ _ _

Select a square to reveal (e.g. A1): D4
This square contains 0 adjacent mines. 

Here is your updated minefield:
  1 2 3 4
A _ _ 2 0
B _ _ 2 0
C _ 2 1 0
D _ 1 0 0

Select a square to reveal (e.g. A1): B1
This square contains 3 adjacent mines. 

Here is your updated minefield:
  1 2 3 4
A _ _ 2 0
B 3 _ 2 0
C _ 2 1 0
D _ 1 0 0

Select a square to reveal (e.g. A1): A1
This square contains 2 adjacent mines. 

Here is your updated minefield:
  1 2 3 4
A 2 _ 2 0
B 3 _ 2 0
C _ 2 1 0
D _ 1 0 0

Select a square to reveal (e.g. A1): D1
This square contains 1 adjacent mines. 

Here is your updated minefield:
  1 2 3 4
A 2 _ 2 0
B 3 _ 2 0
C _ 2 1 0
D 1 1 0 0

Congratulations, you have won the game!
Press any key to play again...
```
### Failure example
```
Welcome to Minesweeper!

Enter the size of the grid (e.g. 4 for a 4x4 grid): 
3
Enter the number of mines to place on the grid (maximum is 35% of the total squares): 
3

Here is your minefield:
  1 2 3
A _ _ _
B _ _ _
C _ _ _

Select a square to reveal (e.g. A1): C3
Oh no, you detonated a mine! Game over.
Press any key to play again...
```
