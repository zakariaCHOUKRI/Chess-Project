package src.chess.piece;

import src.chess.Board.ActiveBoard;

import src.chess.Launcher;
import src.chess.move.Move;
import src.square.Square;
import src.square.SquareMap;
import src.view.piece.PieceView;

import java.util.LinkedList;

public abstract class Piece{

    protected boolean isWhite;
    protected boolean isDead;
    protected Square square;
    protected ActiveBoard activeBoard;
    protected PieceView pieceView;

    public Piece(Square square, boolean isWhite) {
        this.square = square;
        this.isWhite = isWhite;
        this.isDead = false;
        pieceView = new PieceView();
        pieceView.setupIcon(Launcher.filePath.getAbsolutePath() + "/ChessPieceImages",this);
    }

    public PieceView getPieceView() {
        return pieceView;
    }

    /**
     * @return katreturni list dial lmoves fihom moves li possibles o lmoves li ghaykhliw lking en danger
     * @param activeBoard lboard fash khdamin
     */
    public LinkedList<Move> getMoves(ActiveBoard activeBoard){
        LinkedList<Move> moves = new LinkedList<>();
        final int[][] moveDirections = moveDirections();
        for (int[] direction: moveDirections) {
            getMovesHelper(direction[0], direction[1], moves, activeBoard);
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
    protected abstract void getMovesHelper(int rowInc, int colInc, LinkedList<Move> moves, ActiveBoard activeBoard);

    /**
     * getter dial moveDirections
     * @return array fih les directions dial lmoves dial lpiece
     */
    protected abstract int[][] moveDirections();

    /**
     * ka3tik ga3 les moves possibles walakin li makay7tosh lking en danger
     * @return list fiha lvalid moves
     * @param activeBoard lboard fash khdamin
     */
    public LinkedList<Move> getValidMoves(ActiveBoard activeBoard) {
        LinkedList<Move> validMoves = new LinkedList<>();
        LinkedList<Move> allMoves = getMoves(activeBoard);

        Piece king = activeBoard.getKing(isWhite);

        // kandiro had loop bach ndiro lmoves invisibly o nchofo shkon fihom li ykhli lking in danger o kanrddohom
        for (Move move: allMoves) {
            move.doMove(false); // false hna kat3ni anna lmove ghaytra invisible
            // kat checki wash lmove ghadi ikhli lking ikon en danger
            if(activeBoard.isSafeMove(king.getSquare(), isWhite)) {
                validMoves.add(move);
            }
            move.undoMove(false); // false hna kat3ni anna l unmove ghaytra invisible
        }

        return validMoves;
    }

    public abstract int getScore();

    public abstract String getID();

    public abstract String getName();

    public boolean isDead() {
        return isDead;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public Square getNewSquare() {
        return square.getSquareWithOffset();
    }

    public Square getSquare() {
        return this.square;
    }

    // dir animation kat7rrk lpiece mn square li kayna fih db l square li 3tinaha
    public void updateSquare(Square square, boolean isVisual) {
        this.square = square;
        if(isVisual) {
            pieceView.updateSquare(square);
        }
    }

    // khli lpiece matb9ash tban mora mat killa
    public void kill(boolean isVisual) {
        this.isDead = true;
        if(isVisual) {
            pieceView.kill();
        }
    }

    // khli lpiece t3awd tban mora mat reviva
    public void revive(boolean isVisual) {
        this.isDead = false;
        if(isVisual) {
            pieceView.revive();
        }
    }

    // kan3tiwha String du type "	P: {2, A, true, false}"
    public static Piece parsePiece(String rawData) {
        rawData = rawData.substring(1); // hadi bach iwli first index how lettre machi tabulation
        String[] data = rawData.split("[\\W]+"); // had regex kayselectionni ghir les caracteres (a-z A-Z 0-9)

        return switch (data[0]) {
            case "P" -> Pawn.parsePawn(data);
            case "R" -> Rook.parseRook(data);
            case "Kn" -> Knight.parseKnight(data);
            case "B" -> Bishop.parseBishop(data);
            case "Q" -> Queen.parseQueen(data);
            case "K" -> King.parseKing(data);
            default -> null;
        };

    }

    public static SquareMap<Move> getMoveMap(LinkedList<Move> moves) {
        SquareMap<Move> sMap = new SquareMap<>(moves.size());
        for (Move m: moves) {
            sMap.add(m.getEndSquare(), m);
        }
        return sMap;
    }
}
