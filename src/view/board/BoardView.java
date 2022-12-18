package src.view.board;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import src.controller.BoardController;
import src.square.Square;
import src.view.piece.PieceView;


public class BoardView implements ActiveBoardView {
    private PieceView[] whitePieces;
    //piece view for white pieces
    private PieceView[] blackPieces;
    //piece view for black pieces
    private Spot[][] grid;
    //2 dimensional  array of spots

    public BoardView(PieceView[] whitePieces,PieceView[] blackPieces, Spot[][] grid){
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
        this.grid = grid;
    }

    @Override
    public Parent getBoardGUI(BoardController boardController) {
        //Board
        Pane root = new Pane();
        // create a Pane
        root.setPrefSize(ROWS * SPOT_WIDTH, COLUMNS * SPOT_WIDTH);
        //sets the height and width of the pane
        for (Spot[] row: grid) {
            //loop over rows of the grid
            for (Spot spot: row) {
                // add all spots in the row to the pane
                root.getChildren().add(spot);
            }
        }

        //Pieces
        for (PieceView whitePiece: whitePieces) {
            //adds  views of white  pieces to the pane
            root.getChildren().add(whitePiece);
        }

        for (PieceView blackPiece: blackPieces) {
            //adds  views of black  pieces to the pane
            root.getChildren().add(blackPiece);
        }
        return root;
    }


    @Override
    public void clearHighlights() {
        for (Spot[] spots : grid) {
            for (Spot spot : spots) {
                //disable highlights, warnings and highlight cover to the spot
                spot.clear();
            }
        }
    }

    @Override
    public void highlight(Square square) {
        grid[square.getRow()][square.getCol()].highLight();
    }
    //highlight a specific square (spot)

    @Override
    public void showWarning(Square square) {
        grid[square.getRow()][square.getCol()].warn();
    }
    //show warning at a specific square(spot)

}
