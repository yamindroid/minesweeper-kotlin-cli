package core.manager

import model.GameState

interface GameStateManager {
    fun getGameState(): GameState
    fun setGameLost()
    fun setGameWon()
    fun isPlaying(): Boolean
}