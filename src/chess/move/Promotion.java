package src.chess.move;

import src.chess.Board.ActiveBoard;
import src.chess.Launcher;
import src.chess.piece.Pawn;
import src.chess.piece.Piece;
import src.square.Square;

/**
 * Promotion hiya fach taywsl lpawn lkhr dyak lboard
 * fa blimkan dyal lpawn irj3 queen wla  knight wla rook  wla bishop
 * Promotion extends the Move object and stores a promotion move
 */
public class Promotion extends Move {
    private Piece upgradePiece;
    private Pawn movingPiece;

    /**
     * Constructor d Promotion
     * @param movingPiece lpawn li wsl lend dlboard
     * @param activeBoard lboard li ghaytbdl fih
     * @param endPos lsquare li ghaymchi lih
     * @param upgradePiece lpiece li ghayrj3lliha
     */
    public Promotion(Piece movingPiece, ActiveBoard activeBoard, Square endPos, Piece upgradePiece) {
        super(movingPiece, activeBoard, endPos);
        this.upgradePiece = upgradePiece;
        this.movingPiece = (Pawn)movingPiece;
    }

    /**
     * bach ydir lmove d promotion
     * @param isVisual wach yban lmove flboard  wla flcas li mataybanch howa
     *                 fach kaykoun lAI la3b
     */
    public void doMove(boolean isVisual) {
        movingPiece.upgrade(upgradePiece, isVisual);
        super.doMove(isVisual);
    }

    /**
     * bach yhd lmove li kan dar katkhss lAI
     * @param isVisual wach yban f lboard wla la
     */
    public void undoMove(boolean isVisual) {
        movingPiece.downgrade(isVisual);
        super.undoMove(isVisual);
    }

    @Override
    /**
     * atybdl smiya d lpiece lpiece li rj3liha
     */
    public String toString() {
        return upgradePiece.getName();
    }

}
