package src.chess.piece;

import src.chess.Board.ActiveBoard;
import src.chess.Board.Board;
import src.chess.move.Castling;
import src.chess.move.FirstMove;
import src.chess.move.Move;
import src.square.Square;

import java.util.LinkedList;

public class King extends Piece implements FirstMoveMatters {
    public static final int SCORE = 400; // valeur dialha bnsba l AI
    public static final String ID = "K";
    public static final String NAME = "King";
    private static final int[][] moveDirections = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};

    public boolean hasMoved;


    public King(Square square, boolean isWhite, boolean hasMoved) {
        super(square, isWhite);
        this.hasMoved = hasMoved;
    }

    /**
     * @return katreturni list dial lmoves li possible bima fihom lcastling
     * @param activeBoard lboard fash khdamin
     */
    @Override
    public LinkedList<Move> getMoves(ActiveBoard activeBoard) {
        LinkedList<Move> moves = new LinkedList<>();

        moves.addAll(getMovesNoCastle(activeBoard));
        moves.addAll(getMovesCastle(activeBoard));

        return moves;
    }

    /**
     * @return katreturni list dial lmoves fihom moves li possibles mn ghir lcastling
     * @param activeBoard lboard fash khdamin
     */
    public LinkedList<Move> getMovesNoCastle(ActiveBoard activeBoard) {
        LinkedList<Move> moves = new LinkedList<>();
        final int[][] moveDirections = moveDirections();
        for (int[] direction: moveDirections) {
            getMovesHelper(direction[0],direction[1],moves,activeBoard );
        }
        return moves;
    }

    /**
     * kat checki wash lmove li kayn f square de coordonnées (x+colInc, y+rowInc) possible
     * @param rowInc x coordinate of direction
     * @param colInc y coordinate of direction
     * @param moves List fiha lvalid moves
     * @param activeBoard lboard fash khdamin
     */
    protected void getMovesHelper(int rowOffset, int colOffset, LinkedList<Move> moves, ActiveBoard activeBoard) {
        Square temp = this.square.getSquareWithOffset(colOffset, rowOffset);
        if(activeBoard.isInBounds(temp) && !activeBoard.hasFriendlyPieceAtSquare(temp, isWhite)) {
            moves.add(setupMove(temp, activeBoard)); // kankhdmo setupMove blast new Move fles classes li 3ndhom firstMoveMatters
        }

        // lfr9 bin getMovesHelper dial King o dial les pieces lokhrin howa annaha mafihash dik loop
        // 7it King kayt7rrk ghir l awal square 7dah mn jami3 l itijahat ol9nat
        // kayshof ghir wash maghadish iwta 3la chi friendly piece ola ikhrj mn lboard
    }

    @Override
    protected int[][] moveDirections() {
        return moveDirections;
    }

    /**
     * @return katreturni list dial lmoves dial lcastling
     * @param activeBoard lboard fash khdamin
     */
    public LinkedList<Move> getMovesCastle(ActiveBoard activeBoard) {
        LinkedList<Move> moves = new LinkedList<>();

        // ila kant hasMoved true kaymshi nichan l return wi returni moves khawya
        // nafs l7aja la kan lking aslan in danger
        // is safeMove(chiSquare) katchof wash ta shi enemy piece ma3ndha chi move kay endi f chiSquare
        if(!hasMoved && activeBoard.isSafeMove(this.square, isWhite)) {

            Piece leftPiece = activeBoard.getPiece(new Square(square.getRow(), 0));
            if(leftPiece instanceof Rook) { // kanchofo wash lpiece li flisser de classe Rook
                if (!((Rook) leftPiece).hasMoved) { // ila kant de classe Rook kanshoufo wash deja t7rkat
                    boolean canCastle = true;
                    for (int i = square.getCol() - 1; i > 0 && canCastle; i--) {
                        if (!activeBoard.isCleanMove(new Square(square.getRow(), i), isWhite)) {
                            // isCleanMove katchof wash square li 3tinaha mafih ta chi piece o wash ta enemy piece ma dayrah en danger
                            canCastle = false;
                        }
                    }
                    if(canCastle) {
                        moves.add(new Castling(this, leftPiece, square.getSquareWithOffset(0, -2), square.getSquareWithOffset(0, -1), activeBoard));
                    }
                }
            }

            // meme processus bnsba l Rook li flimen
            Piece rightPiece = activeBoard.getPiece(new Square(square.getRow(), Board.COLUMNS - 1));
            if(rightPiece instanceof Rook) {
                if (!((Rook) rightPiece).hasMoved) {
                    boolean canCastle = true;
                    for (int i = square.getCol() + 1; i < Board.COLUMNS - 1 && canCastle; i++) {
                        if (!activeBoard.isCleanMove(new Square(square.getRow(), i), isWhite)) {
                            canCastle = false;
                        }
                    }
                    if(canCastle) {
                        moves.add(new Castling(this, rightPiece, square.getSquareWithOffset(0, 2), square.getSquareWithOffset(0, 1), activeBoard));
                    }
                }
            }
        }

        return moves;
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
    // katreturni lina King object kayn f column 2 row A o isWhite = true o hasMoved = false
    public static King parseKing(String[] data) {
        Square square = src.square.Square.parseSquare(data[1] + data[2]);
        boolean isWhite = Boolean.parseBoolean(data[3]);
        boolean hasMoved = Boolean.parseBoolean(data[4]);

        return new King(square, isWhite, hasMoved);
    }
}
