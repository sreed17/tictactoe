package tictactoe.models;

public class Board {
  private int N; // size of the board
  private int grid[][]; // Grid representing the board state
  private int[] cellSelected = null; // Coordinates of the selected cell
  private int nZeros; // Number of empty cells
  private int trace; // Sum of diagonal elements
  private int antiTrace; // Sum of anti-diagonal elements
  private int[] rowSums; // Array containing sum of elements in each row
  private int[] columnSums; // Array containing sum of elements in each column

  /**
   * Initialize the board with the specified size
   * 
   * @param N Size of the board (N x N)
   */
  public Board(int N) {
    this.N = N;
    resetGrid();
  }

  /**
   * Initialize the board with default size (3 x 3)
   */
  public Board() {
    this(3);
  }

  /**
   * Reset the board to its initial state
   */
  public void resetBoard() {
    resetGrid();
    this.cellSelected = null;
  }

  /**
   * Check whether any cell is selected currently.
   * 
   * @return True if any cell is selected, False otherwise
   */
  public boolean isSelectionEmpty() {
    return this.cellSelected == null;
  }

  /**
   * Select a cell on the board
   * 
   * @param x The x-coordinate (row index) of the cell
   * @param y The y-coordinate (column index) of the cell
   * @throws IndexOutofBoundsException If the cell index is outside the grid
   */
  public void select(int x, int y) {
    if (x >= N || y >= N)
      throw new IndexOutOfBoundsException("Referenced cell index outside the grid");
    if (cellSelected == null) {
      cellSelected = new int[2];
    }
    cellSelected[0] = x;
    cellSelected[1] = y;
  }

  /**
   * Deselect the selected cell
   */
  public void clearSelection() {
    cellSelected = null;
  }

  /**
   * Checks if the selected cell is empty
   * 
   * @return True if the cell is empty, false otherwise
   * @throws IllegalArgumentException If no cell is selected
   */
  public boolean isCellEmpty() {
    throwNoCellSelectedError();
    return this.grid[cellSelected[0]][cellSelected[1]] == 0;
  }

  /**
   * Write a value to the selected cell
   * 
   * @param isMaximumValue True if maximum value should be written, False for minimum value
   * @throws IllegalArgumentException If no cell is selected
   */
  public void writeToCell(boolean isMaximumValue) {
    // A non-empty(zero valued) cell can only have two valid values (1 (maximum) or -1(min));
    throwNoCellSelectedError();
    if (isCellEmpty()) {
      nZeros -= 1;
    }
    int newValue = isMaximumValue ? 1 : -1;
    int previousVal = this.readFromCell();
    boolean isDirty = previousVal != newValue;
    if (isDirty) {
      this.grid[cellSelected[0]][cellSelected[1]] = newValue;
      this.updateParameters();
    }
  }

  /**
   * Clear the selected cell
   * 
   * @throws IllegalArgumentException If no cell is selected
   */
  public void clearCell() {
    throwNoCellSelectedError();
    if (!isCellEmpty()) {
      nZeros += 1;
      this.grid[cellSelected[0]][cellSelected[1]] = 0;
      this.updateParameters();
    }
  }

  /**
   * Reads the value from the selected cell.
   * 
   * @return The value of the selected cell
   * @throws IllegalArgumentException If no cell is selected
   */
  public int readFromCell() {
    throwNoCellSelectedError();
    return this.grid[cellSelected[0]][cellSelected[1]];
  }

  /**
   * Gets the number of empty cells on the board.
   * 
   * @return The number of empty cells
   */
  public int getEmptyCellCount() {
    return nZeros;
  }

  /**
   * Gets the sum of elements along the main diagonal (trace).
   * 
   * @return The sum of elements along the main diagonal
   */
  public int getTrace() {
    return trace;
  }

  /**
   * Gets the sum of elements along the anti-diagonal (anti-trace).
   * 
   * @return The sum of elements along the anti-diagonal
   */
  public int getAntiTrace() {
    return antiTrace;
  }

  /**
   * Gets the sum of elements in each column.
   * 
   * @return An array containing the sum of elements in each column
   */
  public int[] getColumnSums() {
    return columnSums;
  }

  /**
   * Gets the sum of elements in the specified column.
   * 
   * @param index The index of the column
   * @return The sum of elements in the specified column
   */
  public int getColumnSum(int index) {
    return columnSums[index];
  }

  /**
   * Gets the sum of elements in each row.
   * 
   * @return An array containing the sum of elements in each row
   */
  public int[] getRowSums() {
    return rowSums;
  }

  /**
   * Gets the sum of elements in the specified row.
   * 
   * @param index The index of the row
   * @return The sum of elements in the specified row
   */
  public int getRowSum(int index) {
    return rowSums[index];
  }

  /**
   * Get the size of the board (N x N)
   * 
   * @return Return the size of the board (N)
   */
  public int getBoardSize() {
    return N;
  }

  // PRIVATE:

  /**
   * Reset the grid and grid-related parameters
   */
  private void resetGrid() {
    this.grid = new int[this.N][this.N];
    this.nZeros = N * N;
    this.trace = 0;
    this.antiTrace = 0;
    this.rowSums = new int[N];
    this.columnSums = new int[N];
  }

  /**
   * Check if a cell is selected else throw error
   * 
   * @throws IllegalArgumentException
   */
  private void throwNoCellSelectedError() {
    if (cellSelected == null)
      throw new IllegalArgumentException("No cell selected");
  }

  /**
   * Updates the board parameters (trace, antiTrace, rowSums, colSums)
   * 
   */

  private void updateParameters() {
    this.trace = 0;
    this.antiTrace = 0;
    this.rowSums = new int[this.N];
    this.columnSums = new int[this.N];
    for (int x = 0; x < this.N; x++) {
      int rowSum = 0;
      int colSum = 0;
      for (int y = 0; y < this.N; y++) {
        rowSum += this.grid[x][y];
        colSum += this.grid[y][x];
        if (x == y) {
          this.trace += this.grid[x][x];
        }
        if (x == N - (y + 1)) {
          this.antiTrace += this.grid[x][y];
        }
      }
      this.rowSums[x] = rowSum;
      this.columnSums[x] = colSum;
    }
  }



}
