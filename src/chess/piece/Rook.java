package src.chess.piece;

import src.chess.Board.ActiveBoard;
import src.chess.move.FirstMove;
import src.chess.move.Move;
import src.square.Square;

import java.util.LinkedList;

public class Rook extends Piece implements FirstMoveMatters {
    public static final int SCORE = 5; // valeur dialha bnsba l AI
    public static final String ID = "R";
    public static final String NAME = "Rook";
    private static final int[][] moveDirections =  {{1,0},{-1,0},{0,1},{0,-1}};

    public boolean hasMoved;

    public Rook(Square square, boolean isWhite, boolean hasMoved) {
        super(square, isWhite);
        this.hasMoved = hasMoved;
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
            } else if (activeBoard.hasHostilePieceAtSquare(temp, isWhite)) {
                moves.add(setupMove(temp.getSquareWithOffset(),activeBoard)); // kankhdmo setupMove blast new Move fles classes li 3ndhom firstMoveMatters
                break; // ila kant enemy piece fhadak square kankhrjo mn loop o makan9lbosh ba9i lmoves li mnb3d
                       // wlkn kanzido lmove li ghanklo bih had l enemy piece m3a lmoves
            }
            // ila makant ta piece 7abssa kanb9aw nzido fl moves tanwslo l lkher dial lboard
            moves.add(setupMove(temp.getSquareWithOffset(), activeBoard));
            // kandiro had l offset bash z3ma ndozo l square li mnb3d flmoves
            temp = temp.getSquareWithOffset(rowInc, colInc);
        }
    }

    // katchof wash deja mova lking kay créer Move 3adi sinon kaycréer FirstMove
    private Move setupMove(Square square, ActiveBoard activeBoard) {
        if(hasMoved) {
            return new Move(this, activeBoard, square);
        } else {
            return new FirstMove(this, activeBoard, square);
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

    @Override
    public boolean getHasMoved() {
        return hasMoved;
    }

    @Override
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    // method kan3tiwha array du type [P,2,A,true,false]
    // katreturni lina Rook object kayn f column 2 row A o isWhite = true o hasMoved = false
    public static Rook parseRook(String[] data) {
        Square square = Square.parseSquare(data[1] + data[2]);
        boolean isWhite = Boolean.parseBoolean(data[3]);
        boolean hasMoved = Boolean.parseBoolean(data[4]);

        return new Rook(square, isWhite, hasMoved);
    }
}
