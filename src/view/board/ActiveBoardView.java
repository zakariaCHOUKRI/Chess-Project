package src.view.board;

import javafx.scene.Parent;
import src.controller.BoardController;
import src.square.Square;

public interface ActiveBoardView {
    int SPOT_WIDTH = 64;
    //width of a square = 64 px
    int ROWS = 8;
    int COLUMNS = 8;

    Parent getBoardGUI(BoardController boardController);

    /**
     * disable highlight, warning, and highlight cover
     */
    void clearHighlights();

    /**
     * turn on highlight at a specific square
     * @param square where highlight is turned on
     */
    void highlight(Square square);

    /**
     * show warning at a specific square
     * @param square where the warning is shown
     */
    void showWarning(Square square);
}
