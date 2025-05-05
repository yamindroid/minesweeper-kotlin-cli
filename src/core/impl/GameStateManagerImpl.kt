package core.manager

import model.GameState

class GameStateManagerImpl : GameStateManager {
    private var gameState: GameState = GameState.PLAYING

    override fun getGameState() = gameState

    override fun setGameLost() {
        gameState = GameState.LOST
    }

    override fun setGameWon() {
        gameState = GameState.WON
    }

    override fun isPlaying() = gameState == GameState.PLAYING
}