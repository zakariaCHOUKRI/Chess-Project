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

    // kayna hna gha 7it kayna fl base class amma definition kayna f ComputerPlayer
    public void calculateNextMove() {}

    public void forwardBoardInput(Square square) {
        // kola mra kanclickiw 3la piece tathighlighta
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
                Move move = squareMap.remove(square); // this gives you the move(s) that will take you to said square (it can be either one move or 2)
                                                      // the only case where it can be 2 is if it's a pawn that's about to be promoted
                                                      // thus the conditions below
                //ila kan mazal bimkan lpiece 3ndha moves li tdir
                while (move != null) { // this allows us to see if there's a second move and add it as well
                    moves.add(move);
                    move = squareMap.remove(square);
                }
                highlightedPiece = null;
                squareMap = null;

                if (moves.size() == 1) { // if there's only one move we execute it
                    //ta excuter lmove li whadid li kayn
                    returnMove(moves.get(0));
                } else if (moves.size() > 1) { // but if there are 2 moves we execute the promotion instead
                    //lcas dpromotion
                    Move move1 = PromotionConfirmationWindow.display("Promotion", "Which piece you would like to replace the pawn with?", moves);
                    // kat executi lmove
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
