package tictactoe.ai;


import org.junit.Assert;
import org.junit.Test;
import tictactoe.models.Board;
import tictactoe.models.Game;

public class MinimaxAiTest {
  Game game;

  public MinimaxAiTest() {
    Board board = new Board(3);
    this.game = new Game(board);
  }

  @Test
  public void predictsTheBestMove() {
    // Test scenarios
    // Scenario 1: Test a scenario where the AI can win
    // In this scenario, the AI has the opportunity to win the game in the next move
    // Setup the board
    game.play(0, 0); // Player X
    game.play(1, 1); // Player O
    game.play(0, 1); // Player X
    game.play(1, 0); // Player O
    // Now it's AI's turn, AI should choose position (0, 2) to win
    int[] bestMove = MinimaxAi.getBestMove(game);
    int[] expectedBestMove = {0, 2};
    Assert.assertArrayEquals(expectedBestMove, bestMove);
  }

  @Test
  public void testMiniMax2() {

    // Scenario 2: Test a scenario where the AI needs to block the opponent
    // In this scenario, the opponent has an opportunity to win in the next move
    // Setup the board
    game.resetGame();
    game.play(0, 0); // Player X
    game.play(1, 1); // Player O
    game.play(2, 2); // Player X
    game.play(1, 0); // Player O
    // Now it's AI's turn, AI should choose position (2, 0) to block the opponent
    int[] bestMove = MinimaxAi.getBestMove(game);
    Assert.assertEquals(2, bestMove[0]);
    Assert.assertEquals(0, bestMove[1]);
  }

  @Test
  public void testMiniMax3() {
    // Scenario 3: Test a scenario where the game ends in a draw
    // In this scenario, the game ends in a draw without any winning moves available
    // Setup the board
    game.resetGame();
    game.play(0, 0); // Player X
    game.play(1, 0); // Player O
    game.play(2, 0); // Player X
    game.play(1, 1); // Player O
    game.play(0, 2); // Player X
    game.play(0, 1); // Player O
    game.play(1, 2); // Player X
    game.play(2, 1); // Player O
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      MinimaxAi.getBestMove(game);

    });
  }
}
