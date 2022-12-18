package src.chess.move;

import src.chess.Board.ActiveBoard;
import src.chess.piece.Piece;
import src.square.Square;

/**
 * Castling hiya lhala lwahida fach jouj d les peices itkdo imoviw mra whda
 * taykon mabin rook ou lking bach ykon had lmove possible khass lking ou rook
 * ma3mrhoum mathrko ou hta piece mabinathoum ou lblassa li ghaymchi liha lking
 * maghatrj3ouch endangered
 */
public class Castling extends FirstMove {
    private Square startRook;
    private Piece rook;
    private Square endRook;

    /**
     * Constructor for the CastleMove object
     *
     * @param king fcastle move
     * @param rook fcastle move
     * @param endKing square  dlKing mora castling
     * @param endRook square dRook mora castling
     * @param activeBoard presentation board
     */
    public Castling(Piece king, Piece rook, Square endKing, Square endRook, ActiveBoard activeBoard) {
        super(king, activeBoard, endKing);
        this.startRook = rook.getNewSquare();
        this.rook = rook;
        this.endRook = endRook;
    }

    @Override
    public void doMove(boolean isVisual) {
        super.doMove(isVisual);
        super.activeBoard.updateSquare(rook, endRook, isVisual);
    }

    @Override
    public void undoMove(boolean isVisual) {
        super.undoMove(isVisual);
        super.activeBoard.updateSquare(rook, startRook, isVisual);
    }
}
