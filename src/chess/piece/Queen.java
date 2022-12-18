package src.chess.piece;

import src.chess.Board.ActiveBoard;
import src.chess.move.Move;
import src.square.Square;

import java.util.LinkedList;

public class Queen extends Piece {

    public static final int SCORE = 9; // valeur dialha bnsba l AI
    public static final String ID = "Q";
    public static final String NAME = "Queen";
    private static final int[][] moveDirections = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};

    public Queen (Square square, boolean isWhite) {
        super(square, isWhite);
    }

    /**
     * kat checki wash lmove li kayn f square de coordonnées (x+colInc, y+rowInc) possible
     * @param rowInc x coordinate of direction
     * @param colInc y coordinate of direction
     * @param moves List fiha lvalid moves
     * @param activeBoard lboard fash khdamin
     */
    protected void getMovesHelper(int rowInc, int colInc, LinkedList<Move> moves, ActiveBoard activeBoard) {
        Square temp = this.square.getSquareWithOffset(rowInc, colInc);
        // kanb9aw nshofo wash square li m offsetti kayn dakhl lboard
        while(activeBoard.isInBounds(temp)) {
            if(activeBoard.hasFriendlyPieceAtSquare(temp, isWhite)) {
                break; // ila kant friendly piece fhadak square kankhrjo mn loop o makan9lbosh ba9i lmoves li mnb3d
            } else if(activeBoard.hasHostilePieceAtSquare(temp, isWhite)) {
                moves.add(new Move(this, activeBoard, temp.getSquareWithOffset()));
                break; // ila kant enemy piece fhadak square kankhrjo mn loop o makan9lbosh ba9i lmoves li mnb3d
                // wlkn kanzido lmove li ghanklo bih had l enemy piece m3a lmoves
            }
            // ila makant ta piece 7abssa kanb9aw nzido fl moves tanwslo l lkher dial lboard
            moves.add(new Move(this, activeBoard, temp.getSquareWithOffset()));
            // kandiro had l offset bash z3ma ndozo l square li mnb3d flmoves
            temp = temp.getSquareWithOffset(rowInc, colInc);
        }
    }



    @Override
    protected int[][] moveDirections() {
        return moveDirections;
    }

    @Override
    public int getScore() {
        return SCORE;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    // method kan3tiwha array du type [P,2,A,true,false]
    // katreturni lina Queen object kayn f column 2 row A o isWhite = true
    public static Queen parseQueen(String[] data) {
        Square square = src.square.Square.parseSquare(data[1] + data[2]);
        boolean isWhite = Boolean.parseBoolean(data[3]);

        return new Queen(square, isWhite);
    }
}
