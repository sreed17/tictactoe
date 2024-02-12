package tictactoe.models;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Game {
  public static final int FINISHED = 1;
  public static final int NOT_FINISHED = 0;
  public static final int FIRST_PLAYER_WIN = 1;
  public static final int SECOND_PLAYER_WIN = -1;
  public static final int DRAW = 0;
  public static final int MAX_HISTORY_LEN = Integer.MAX_VALUE;
  private Board board; // Instance of board
  private boolean isMaximizingPlayerTurn = true; // Whether the its first player's turn
  private boolean finished = false; // Whether game is finished
  private int outcome; // Final outcome, 1 if first player wins, 0 for draw and -1 if second player-
                       // wins
  private LinkedList<int[]> history = new LinkedList<>();
  // private int[] previousState = {1, 0, 0, 0, 0}; // isMaximplayersturn, finished,
  // outcome, x, y

  /**
   * Initialize the Game Instance
   * 
   * @param board
   */
  public Game(Board board) {
    this.board = board;
  }

  /**
   * Reset the game to intial state
   */
  public void resetGame() {
    this.board.resetBoard();
    this.clearHistory();
    this.isMaximizingPlayerTurn = true;
    this.finished = false;
    this.outcome = 0;
  }

  /**
   * Select the current player who turn is up
   * 
   * @param isMaximizingPlayer True if its first player's turn, False otherwise
   */
  public void setPlayerTurn(boolean isMaximizingPlayer) {
    this.isMaximizingPlayerTurn = isMaximizingPlayer;
  }

  /**
   * Mark a cell in the grid by the current player.
   * 
   * @param x The row index of the cell in the board
   * @param y The column index of the cell in the board
   * @throws IllegalArgumentException if game is finished or the cell is empty
   */
  public void play(int x, int y) {
    if (this.finished) {
      throw new IllegalArgumentException("The game already finished");
    }
    board.select(x, y);
    if (!board.isCellEmpty()) {
      board.clearSelection();
      throw new IllegalArgumentException("The cell is not empty");
    }

    addToHistory(x, y);
    board.writeToCell(isMaximizingPlayerTurn);
    computeOutcome();
    board.clearSelection();
    setPlayerTurn(!isMaximizingPlayerTurn);
  }

  /**
   * Undo the previous move played if any
   */
  public void undoPreviousMove() {
    try {
      int[] previousState = history.removeLast();
      int x = previousState[3];
      int y = previousState[4];
      board.select(x, y);
      board.clearCell();
      computeOutcome();
      board.clearSelection();
      isMaximizingPlayerTurn = previousState[0] == 1 ? true : false;
    } catch (NoSuchElementException e) {
      System.out.println("Game resetted: " + e.getMessage());
      resetGame();
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * Check whether if its Player one's turn
   * 
   * @return
   */
  public boolean isMaximizingPlayerTurn() {
    return isMaximizingPlayerTurn;
  }

  /**
   * Get the outcome of the game at the time of calling
   * 
   * @return [<finished>,<outcome>] where finished = (0 (not finished)| 1 (finished)), outcome = (-1
   *         (seconda player won) | 0 (draw or not finished) | 1 (first player won))
   */
  public int[] getGameOutcome() {
    int[] localOutcome = new int[2];
    localOutcome[0] = finished ? FINISHED : NOT_FINISHED;
    localOutcome[1] = this.outcome;
    return localOutcome;
  }

  public Board getBoard() {
    return board;
  }

  /**
   * Compute the outcome if winning condition occurs or draw other wise unless the game is not
   * finished
   */
  private void computeOutcome() {

    int trace = board.getTrace();
    if (checkAndSetTerminalStateFromSum(trace))
      return;
    int antiTrace = board.getAntiTrace();
    if (checkAndSetTerminalStateFromSum(antiTrace))
      return;
    // check for winning row and column
    for (int i = 0; i < board.getBoardSize(); i++) {
      if (checkAndSetTerminalStateFromSum(board.getRowSum(i)))
        return;
      if (checkAndSetTerminalStateFromSum(board.getColumnSum(i)))
        return;
    }
    if (checkForDraw())
      return;
    finished = false;
    outcome = 0;
  }

  /**
   * Set the terminal state of the Game.
   * 
   * @param outcome The outcome of the game
   */
  private void setTerminalState(int outcome) {
    this.finished = true;
    this.outcome = outcome;
  }

  /**
   * Check whether terminal state is reached and then set terminal state.
   * 
   * @param sum The sum of row, column, diagonal or anti-diagonal elements
   * @return True if terminal state is reached, False otherwise
   */
  private boolean checkAndSetTerminalStateFromSum(int sum) {
    if (Math.abs(sum) == this.board.getBoardSize()) {
      int outcome = sum > 0 ? FIRST_PLAYER_WIN : SECOND_PLAYER_WIN;
      setTerminalState(outcome);
      return true;
    }
    return false;
  }

  /**
   * Check for draw condition and sets the terminal state in case of draw
   * 
   * @return True if its a draw, False otherwise
   */
  private boolean checkForDraw() {
    if (board.getEmptyCellCount() == 0) {
      setTerminalState(DRAW);
      return true;
    }
    return false;
  }

  /**
   * Adds the move to history
   * 
   * @param x The row index of current move
   * @param y The column index of current move
   */
  private void addToHistory(int x, int y) {
    int len = history.size();
    int[] historyEntry =
        {isMaximizingPlayerTurn ? 1 : 0, finished ? FINISHED : NOT_FINISHED, outcome, x, y};
    history.add(historyEntry);
    if (len + 1 > MAX_HISTORY_LEN) {
      history.removeFirst();
    }
  }

  /**
   * Reset the history
   */
  private void clearHistory() {
    history = new LinkedList<>();
  }
}
