package src.chess.Player;

import src.chess.move.Move;
import src.chess.move.PromotionConfirmationWindow;
import src.chess.piece.Piece;
import src.square.Square;
import src.square.SquareMap;

import java.util.LinkedList;

public class HumanPlayer extends Player {

    private Piece highlightedPiece;
    private SquareMap<Move> squareMap;

    /**
     * Constructor dHumanPlayer
     * @param isWhite
     */
    public HumanPlayer(boolean isWhite) {
        super(isWhite);
    }

    public void calculateNextMove() {}

    public void forwardBoardInput(Square square) {
        //kola mra kantclickiw 3la piece tathighlighta
        // y3ni ila bghina nclickiw ela chi whda khra khasna ntfiw,lhighlight l9dim

        //hadi katcleari lhighlights likano f lboardcontroller
        boardController.getActiveBoardView().clearHighlights();
        //
        if(highlightedPiece == null) {
            Piece piece = boardController.getActiveBoard().getPiece(square);
            //bach ychouf wach kayna piece f square ou dik lpiece nta3 lplayer cad endha lh9 thrk /endha nfss lon mea lplayer
            if(piece != null && piece.isWhite() == isWhite) {
                boardController.getActiveBoardView().highlight(square);
                highlightedPiece = piece;
                //linkedlist dlmove possible lit9dr tdir lpiece
                LinkedList<Move> moves = piece.getValidMoves(boardController.getActiveBoard());
                squareMap = Piece.getMoveMap(moves);
                //bach nhighlightiw jami3 les moves
                for (Move move : moves) {
                    boardController.getActiveBoardView().highlight(move.getEndSquare());
                }

            }
        } else {
            if (squareMap != null) {

                LinkedList<Move> moves = new LinkedList<>();
                //kayhd square squareMap ou ta3tih lmove
                Move move = squareMap.remove(square);
                //ila kan mazal bimkan lpiece 3ndha moves li tdir
                while (move != null) {
                    moves.add(move);
                    move = squareMap.remove(square);
                }
                highlightedPiece = null;
                squareMap = null;
                if (moves.size() == 1) {
                    //ta excuter lmove li whadid li kayn
                    returnMove(moves.get(0));
                } else if (moves.size() > 1) {
                    //lcas dpromotion
                    Move move1 = PromotionConfirmationWindow.display("Promotion", "Which piece you would like to replace the pawn with?", moves);
                    returnMove(move1);

                } else {
                    //kat3awd nfss lprocessus
                    forwardBoardInput(square);
                }
             } else {
                //kathighlighti square hit may9drch ythrk
                boardController.getActiveBoardView().highlight(square);
            }
        }
    }

    public void stop() {}

    /**
     * toString
     * @return type dlplayer
     */
    public String toString() {
        return "Human";
    }
}
