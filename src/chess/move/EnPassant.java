package src.chess.move;

import src.chess.Board.ActiveBoard;
import src.chess.piece.Piece;
import src.square.Square;
/**
 * En-passant hiya fach chi pawn y9dr yakl lpawn dlenemy dayli. ila kan had
 * lkhr yalah awl mra ghaythrk ou thrk bjouj morba3at ou ja fjnbo
 */
public class EnPassant extends Move{
    /**
     * Constructor d EnPassant
     *
     * @param movingPiece  lpiece li ghathrk
     * @param capturedPiece lpiece li ghatcaptura
     * @param boardActions lboard li ghaytbl
     * @param endPos       lbalsa li ghatmchi liha lpiece flkhr
     */
    public EnPassant(Piece movingPiece,Piece capturedPiece, ActiveBoard boardActions, Square endPos) {
        super(movingPiece,boardActions,endPos);
        //tayzid lpawn li tkla l captured pieces
        this.setCapturedPiece(capturedPiece);
    }
}
