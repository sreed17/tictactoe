package tictactoe;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import tictactoe.models.Board;
import tictactoe.models.Game;
import tictactoe.view.GameController;
import tictactoe.view.GamePanel;

public class Main extends JFrame {
  String title = "Tic-Tac-Toe";

  public Main() {

    Board b = new Board();
    Game g = new Game(b);
    GameController controller = new GameController(g);
    GamePanel gp = new GamePanel(b.getBoardSize(), controller);

    add(gp);
    pack();
    setTitle(title);
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
    setFocusable(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new Main();
    });
  }
}
