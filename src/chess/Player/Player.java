package src.chess.Player;

import src.controller.BoardController;
import src.chess.move.Move;
import src.square.Square;

/**
 * hiya abstract class fiha jami3 hwayj commun mabin humanPlayer ou computerPlayer
 */
public abstract class Player {
    public static final String[] PLAYER_TYPES = {"Human", "Computer"};

    protected boolean isWhite;

    protected BoardController boardController;

    /**
     * Constructor dplayer taychouf wach byd wla khl
     * @param isWhite
     */
    public Player(boolean isWhite) {
        this.isWhite = isWhite;
    }

    /**
     * setter dlboardController
     * @param boardController
     */
    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
    }

    /**
     * returnMove ta excuter lmove f lboardController
     * @param move
     */
    public void returnMove(Move move) {
        boardController.executeNextMove(move);
    }
    //katkhdm f LAI
    public abstract void calculateNextMove();
    public abstract void forwardBoardInput(Square square);
   //katkhdm f LAI
    public abstract void stop();
    public abstract String toString();

    /**
     * kan3tiwha type dlplayer ou lon dyalo ou taycreer lina
     * humanplayer mea lon dyalo wla computerPlayer mea lon dyalo
     * @param playerType yama "Human", "Computer"
     * @param isWhite boolean 1 ila kan byd 0 khl
     */
    public static Player parsePlayer(String playerType, boolean isWhite) {
        return switch (playerType) {
            case "Human" -> new HumanPlayer(isWhite);
            case "Computer" -> new ComputerPlayer(isWhite);
            default -> null;
        };
    }
}
