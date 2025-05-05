package test.domain

import core.manager.GameStateManagerImpl
import model.GameState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameStateManagerTest {
    private lateinit var gameStateManagerImpl: GameStateManagerImpl

    @Before
    fun setup() {
        gameStateManagerImpl = GameStateManagerImpl()
    }

    @Test
    fun `initial state is PLAYING`() {
        assertEquals(GameState.PLAYING, gameStateManagerImpl.getGameState())
        assertTrue(gameStateManagerImpl.isPlaying())
    }

    @Test
    fun `setting game lost changes state`() {
        gameStateManagerImpl.setGameLost()

        assertEquals(GameState.LOST, gameStateManagerImpl.getGameState())
        assertFalse(gameStateManagerImpl.isPlaying())
    }

    @Test
    fun `setting game won changes state`() {
        gameStateManagerImpl.setGameWon()

        assertEquals(GameState.WON, gameStateManagerImpl.getGameState())
        assertFalse(gameStateManagerImpl.isPlaying())
    }

    @Test
    fun `cannot change state after losing`() {
        gameStateManagerImpl.setGameLost()

        assertEquals(GameState.LOST, gameStateManagerImpl.getGameState())
    }

    @Test
    fun `cannot change state after winning`() {
        gameStateManagerImpl.setGameWon()

        assertEquals(GameState.WON, gameStateManagerImpl.getGameState())
    }
}
