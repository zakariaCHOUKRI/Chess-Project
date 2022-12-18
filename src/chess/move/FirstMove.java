package src.chess.move;

import src.chess.Board.ActiveBoard;
import src.chess.piece.FirstMoveMatters;
import src.chess.piece.Pawn;
import src.chess.piece.Piece;
import src.square.Square;

/**
 * had l class t9adat bach nchoufo wach lpiece dart lmove lwl la hit kayni des pieces
 * li lfirst move dyalhom mohim
 */
public class FirstMove extends Move {
    FirstMoveMatters piece;

    /**
     * Constructor d FirstMove
     * @param movingPiece lpiece li ghathrk
     * @param board lboard li ghathrk fih
     * @param endPos lposition li ghatmchi liha
     */
    public FirstMove(Piece movingPiece, ActiveBoard board, Square endPos) {
        //hit had lclass katbyn gha wach lpiece fat thrkat wla la f kadir nfss lhaja
        // d move flconstructor ou katcasti lpiece lFirstMoveMatters.
        super(movingPiece, board, endPos);
        piece = (FirstMoveMatters)movingPiece;
    }

    @Override
    public void doMove(boolean isVisual) {
        //bach tzid bli lpiece thrkat
        piece.setHasMoved(true);
        super.doMove(isVisual);
    }

    @Override
    public void undoMove(boolean isVisual) {
        //hadi ghatst3ml f lAI bach hta ila kan hrk piece f
        //deroulement dyalo i9dr irj3ha lbalsstha ou hyd bli rah tmovat
        piece.setHasMoved(false);
        super.undoMove(isVisual);
    }

    //hadi katst3ml f lmoves dlpawn hit lpawn bimkano ythrk jouj d squares
    //walakin ila kan had lmove howa lmove lwl dyalo
    public boolean isPawn(){
        return (piece instanceof Pawn);
    }
}
