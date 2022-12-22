package src.chess.move;

import src.chess.Board.ActiveBoard;
import src.chess.piece.Knight;
import src.chess.piece.Pawn;
import src.chess.piece.Piece;
import src.chess.piece.Rook;
import src.square.Square;

/**
 * had lclass hiya li tan9dro ndiro biha move f chess
 */
public class Move implements Comparable<Move> {
    private Piece movingPiece;
    private Square startPos;
    private Square endPos;
    private Piece capturedPiece;
    protected ActiveBoard activeBoard;

    /**
     * Constructor for the move class
     * ila kant chi captured piece kayakhdha automatiquement
     *
     * @param movingPiece lpiece li ghathrk
     * @param boardActions lboard li ghathrk fih
     * @param endPos lbalassa li ghaymmchi liha
     */
    public Move(Piece movingPiece, ActiveBoard boardActions, Square endPos) {
        this.movingPiece = movingPiece;
        this.startPos = movingPiece.getNewSquare();
        this.endPos = endPos;
        this.activeBoard = boardActions;
        this.capturedPiece = boardActions.getPiece(endPos);
    }

    /**
     * doMove hiya li tadir lmove ou hiyali katchouf katkhli lmove yban
     * flboard wla la
     *
     * @param isVisual wach yban lmove flboard  wla flcas li mataybanch howa
     *                 fach kaykoun lAI la3b
     */
    public void doMove(boolean isVisual) {
        if(capturedPiece != null) {
            activeBoard.kill(capturedPiece, isVisual);
        }
        activeBoard.updateSquare(movingPiece, endPos, isVisual);
    }

    /**
     * katyhd lmove hadi khassa blAI
     *
     * @param isVisual wach yban f lboard wla la
     */
    public void undoMove(boolean isVisual) {
        activeBoard.updateSquare(movingPiece, startPos, isVisual);
        if(capturedPiece != null) {
            // ila kan lmove li kant dart dik lpiece k9lt chi piece
            // fatayrj3ha
            activeBoard.revive(capturedPiece, isVisual);
        }
    }

    /**
     *  getter dyal lendSquare
     * @return endPos
     */
    public Square getEndSquare() {
        return endPos;
    }
    /**
     *  getter dyal lstartSquare
     * @return startPos
     */
    public Square getStartPos() {
        return startPos;
    }
    /**
     * katkhss lAI bach
     * @return score dlcaptured piece
     */
    public int getCaptureScore() {
        if(capturedPiece != null) {
            return capturedPiece.getScore();
        }
        return 0;
    }

    /**
     * katkhss lAI
     * @return score dlpiece li thrkat
     */
    public int getMovingScore() {
        return movingPiece.getScore();
    }

    /**
     * katrj3 String form d lmove katkoun fih lId dlpiece li thrkat ou starting square ou ila kant chi
     * capturedSquare ou lsquare li mchat lih
     *
     * @return A String representation  dlmove
     */
    public String toString() {
        StringBuilder moveDescription = new StringBuilder(movingPiece.getID() + " " + startPos.toString() + " -> ");
        if(capturedPiece != null) {
            moveDescription.append(capturedPiece.getID()).append(" ");
        }
        moveDescription.append(endPos.toString());
        return moveDescription.toString();
    }
    /**
     *  setter dyal lpiece li ghathrk
     *  @param movingPiece
     */
    public void setMovingPiece(Piece movingPiece) {
        this.movingPiece = movingPiece;
    }

    /**
     * setter dlcapturedPiece
     * @param capturedPiece
     */
    void setCapturedPiece(Piece capturedPiece){
        this.capturedPiece = capturedPiece;
    }


    /**
     * katcomparer mabin lmoves  (katkhss lAI) katchouf chkoun li hssn mabinathoum bjouj
     * @param move tlmove li ghatcompari bih
     * @return la difference  dyal lbenefit
     */
    public int compareTo(Move move) {
        //1 katchouf wach this(move dyalna) Promotion
        if(this instanceof Promotion) {
            return -1;
        } else if(move instanceof Promotion) {
            //katchouf wach lmove Promotion
            return 1;
        }

        //2 katchouf wach this(move dyalna) Castling
        if(this instanceof Castling) {
            return -1;
        } else if(move instanceof Castling) {
            //katchouf wach lmove Castling
            return 1;
        }

        //3 kanhsbo ladifference dscore mabin lpiecedhad lthis(move) ou lmove lakhour
        // bach nchoufo les cas
        int captureScore = this.getCaptureScore() - move.getCaptureScore();
        if(captureScore != 0) {
            return captureScore * -1;
        }
        // kanchoufo les cas :
        // hadchi dlAI
        //4
        if(this.getMovingScore() == Rook.SCORE && activeBoard.checkNoPawn(this.getEndSquare().getCol())) {
            return -1;
        } else if(move.getMovingScore() == Rook.SCORE && activeBoard.checkNoPawn(move.getEndSquare().getCol())) {
            return 1;
        }

        //5
        if(this.getMovingScore() == Knight.SCORE && (src.square.Square.distanceFromMiddle(this.getEndSquare()) - Square.distanceFromMiddle(this.getStartPos())) < 0) {
            return -1;
        } else if(move.getMovingScore() == Knight.SCORE && (src.square.Square.distanceFromMiddle(move.getEndSquare()) - src.square.Square.distanceFromMiddle(move.getStartPos())) < 0) {
            return 1;
        }

        //6
        if(this.getMovingScore() == Pawn.SCORE) {
            return 1;
        } else if(move.getMovingScore() == Pawn.SCORE) {
            return -1;
        }

        return 0;
    }
}
