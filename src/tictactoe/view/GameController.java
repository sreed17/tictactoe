package tictactoe.view;

import tictactoe.models.Game;

public class GameController {
  Game game;

  public GameController(Game game) {
    this.game = game;
  }

  public void actionOccured(int x, int y, GamePanel p) {
    try {
      game.play(x, y);
      p.updateButton(x, y, game.isMaximizingPlayerTurn() ? "X" : "0");
      int[] outcome = game.getGameOutcome();
      if (outcome[0] == Game.FINISHED) {
        String outcomeStr = "";
        switch (outcome[1]) {
          case Game.FIRST_PLAYER_WIN:
            outcomeStr = "You won";
            break;
          case Game.SECOND_PLAYER_WIN:
            outcomeStr = "You failed";
            break;
          case Game.DRAW:
            outcomeStr = "Draw";
            break;
        }
        p.changeStatus("Finished: " + outcomeStr);
      }
      game.setPlayerTurn(!game.isMaximizingPlayerTurn());
    } catch (Exception e) {
    }
  }

}
