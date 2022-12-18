package src.chess.piece;

import src.chess.Board.ActiveBoard;
import src.chess.move.Move;
import src.square.Square;

import java.util.LinkedList;

public class Knight extends Piece {
    public static final int SCORE = 3; // valeur dialha bnsba l AI
    public static final String ID = "Kn";
    public static final String NAME = "Knight";
    private static final int[][] moveDirections= {{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{-1,2},{1,-2},{-1,-2}};

    public Knight (Square square, boolean isWhite) {
        super(square, isWhite);
    }

    /**
     * kat checki wash lmove li kayn f square de coordonnées (x+colInc, y+rowInc) possible
     * @param rowInc x coordinate of direction
     * @param colInc y coordinate of direction
     * @param moves List fiha lvalid moves
     * @param activeBoard lboard fash khdamin
     */
    protected void getMovesHelper(int colOffset, int rowOffset, LinkedList<Move> moves, ActiveBoard activeBoard) {
        Square temp = this.square.getSquareWithOffset(colOffset, rowOffset);
        if(activeBoard.isInBounds(temp) && !activeBoard.hasFriendlyPieceAtSquare(temp, isWhite)) {
            moves.add(new Move(this, activeBoard, temp));
        }
        // lfr9 bin getMovesHelper dial knight o dial les pieces lokhrin howa annaha mafihash dik loop
        // 7it knight kay9dr in9z les pieces li kaynin f tri9o
        // kayshof ghir wash maghadish iwta 3la chi friendly piece ola ikhrj mn lboard
    }

    @Override
    protected int[][] moveDirections() {
        return moveDirections ;
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
    // katreturni lina Knight object kayn f column 2 row A o isWhite = true
    public static Knight parseKnight(String[] data) {
        Square square = src.square.Square.parseSquare(data[1] + data[2]);
        boolean isWhite = Boolean.parseBoolean(data[3]);

        return new Knight(square, isWhite);
    }
}
