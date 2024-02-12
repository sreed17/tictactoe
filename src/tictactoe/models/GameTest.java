package tictactoe.models;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
  private Game game;

  @Before
  public void setUp() {
    // Initialize the Game instance with a new Board
    game = new Game(new Board());
  }

  @Test
  public void testInitialGameStatus() {
    // Test initial game status
    assertTrue(game.isMaximizingPlayerTurn());
    assertFalse(game.getGameOutcome()[0] == Game.FINISHED);
    assertEquals(0, game.getGameOutcome()[1]);
  }

  @Test
  public void testPlayMove() {
    // Make a move and check if game status changes
    game.resetGame();
    game.play(0, 0);
    assertTrue(!game.isMaximizingPlayerTurn());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayOnFinishedGame() {
    // Make moves to finish the game
    game.play(0, 0);
    game.play(1, 1);
    game.play(0, 1);
    game.play(1, 2);
    game.play(0, 2);
    // Try to play after the game has finished
    game.play(2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayOnNonEmptyCell() {
    // Make a move
    game.play(0, 0);
    // Try to play on a non-empty cell
    game.play(0, 0);
  }

  // Add more test cases as needed...
}


