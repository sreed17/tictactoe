package tictactoe.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

  JButton[][] buttons;
  JLabel statusLabel;

  public GamePanel(int N, GameController gameController) {
    buttons = new JButton[N][N];
    statusLabel = new JLabel("Playing");
    GamePanel thisInstance = this;
    JPanel grid = new JPanel();
    grid.setLayout(new GridLayout(N, N));
    for (int x = 0; x < N; x++) {
      for (int y = 0; y < N; y++) {
        final int finalX = x;
        final int finalY = y;
        buttons[x][y] = new JButton();
        buttons[x][y].setPreferredSize(new Dimension(100, 100));
        buttons[x][y].addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            gameController.actionOccured(finalX, finalY, thisInstance);
          }

        });
        grid.add(buttons[x][y]);

      }
    }
    add(grid);
    add(statusLabel);
    setLayout(new GridLayout(2, 1));
  }

  public void changeStatus(String statusStr) {
    statusLabel.setText(statusStr);
  }

  public void updateButton(int x, int y, String ch) {
    buttons[x][y].setText(ch);
    buttons[x][y].setEnabled(false);
  }

}
