package tictactoe.ai;

import tictactoe.models.Game;

/**
 * Minimax with alphabeta pruning Algorithm to predict the next best move
 */
public class MinimaxAi {
  /**
   * Computes the next best move by minimax algorithm
   * 
   * @param g The current Game instance
   * @return The next best move as int[2] array with [x,y]
   * @throws IllegalArugumentException when the run with a finished game
   */
  public static int[] getBestMove(Game g) {
    int[] bestMove = new int[2];
    boolean isMaximizingPlayerTurn = g.isMaximizingPlayerTurn();
    int bestScore = isMaximizingPlayerTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;

    int alpha = Integer.MIN_VALUE;
    int beta = Integer.MAX_VALUE;

    int N = g.getBoard().getBoardSize();
    for (int x = 0; x < N; x++) {
      for (int y = 0; y < N; y++) {
        g.getBoard().select(x, y);
        if (g.getBoard().isCellEmpty()) {
          // given that the correct player is set
          g.play(x, y);
          // compute the score recursively using minmax
          int score = minimax(g, alpha, beta);
          // update the best score
          if (isMaximizingPlayerTurn) {
            if (score > bestScore) {
              bestScore = score;
              bestMove[0] = x;
              bestMove[1] = y;
            }
            alpha = Math.max(alpha, bestScore);
          } else {
            if (score < bestScore) {
              bestScore = score;
              bestMove[0] = x;
              bestMove[1] = y;
            }
            beta = Math.min(beta, bestScore);
          }
          // revert the changes
          g.undoPreviousMove();
        }
      }
    }

    return bestMove;
  }

  /**
   * Recursive minimax algorithm
   * 
   * @param g The game instance
   * @return The best score of current recursion
   */
  public static int minimax(Game g, int alpha, int beta) {

    // Termination condition
    int[] outcome = g.getGameOutcome();
    if (outcome[0] == Game.FINISHED) {
      return outcome[1];
    }

    // iterate
    boolean isMaximizingPlayer = g.isMaximizingPlayerTurn();
    int N = g.getBoard().getBoardSize();
    for (int x = 0; x < N; x++) {
      for (int y = 0; y < N; y++) {
        g.getBoard().select(x, y);
        if (g.getBoard().isCellEmpty()) {
          g.play(x, y);
          int score = minimax(g, alpha, beta);
          if (isMaximizingPlayer) {
            alpha = Math.max(score, alpha);
          } else {
            beta = Math.min(score, beta);
          }
          g.undoPreviousMove();

          // pruning
          if (beta <= alpha) {
            break;
          }
        }
      }
    }
    return isMaximizingPlayer ? alpha : beta;
  }
}
