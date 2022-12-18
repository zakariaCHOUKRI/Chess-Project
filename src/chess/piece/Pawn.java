package src.chess.piece;

//import com.sun.org.apache.xpath.internal.operations.Bool;
import src.chess.Board.ActiveBoard;
import src.chess.Board.Board;
import src.chess.Launcher;
import src.chess.move.EnPassant;
import src.chess.move.FirstMove;
import src.chess.move.Move;
import src.chess.move.Promotion;
import src.controller.BoardController;
import javafx.scene.image.Image;
import src.square.Square;

import java.util.LinkedList;

public class Pawn extends Piece implements FirstMoveMatters {
    public static final int SCORE = 1; // valeur dialha bnsba l AI
    public static final String ID = "P";
    public static final String NAME = "Pawn";

    public boolean hasMoved;

    private Piece upgradePiece;
    private Image oldImage;


    public Pawn(Square square, boolean isWhite, boolean hasMoved) {
        super(square, isWhite);
        this.hasMoved = hasMoved;
    }

    /**
     * ka3tik ga3 les moves possibles walakin li makay7tosh lking en danger
     *
     * @return list fiha lvalid moves
     * @param activeBoard lboard fash khdamin
     */
    @Override
    public LinkedList<Move> getMoves(ActiveBoard activeBoard) {
        LinkedList<Move> moves = new LinkedList<>();

        // if this pawn has been upgraded
        if(upgradePiece != null) {
            moves.addAll(upgradePiece.getMoves(activeBoard));
            for (Move m: moves) {
                m.setMovingPiece(this);
            }
        } else {
            Square temp;

            //Forward one
            temp = this.square.getSquareWithOffset(forward(1), 0);
            if (activeBoard.isInBounds(temp) && !activeBoard.hasPieceAtSquare(temp)) { // kanshofo square wash dakhl lboard o mafih tta piece
                moves.addAll(setupMove(temp.getSquareWithOffset(),activeBoard));
                //Forward two
                if (!hasMoved) {
                    temp = this.square.getSquareWithOffset(forward(2), 0);
                    if (activeBoard.isInBounds(temp) && !activeBoard.hasPieceAtSquare(temp)) { // kanshofo square wash dakhl lboard o mafih tta piece
                        moves.addAll(setupMove(temp.getSquareWithOffset(),activeBoard));
                    }
                }
            }
            //Capture right
            temp = this.square.getSquareWithOffset(forward(1), 1);
            if (activeBoard.isInBounds(temp) && activeBoard.hasHostilePieceAtSquare(temp, isWhite)) { // kanshofo square wash dakhl lboard o darori tkon fih enemy piece
                moves.addAll(setupMove(temp.getSquareWithOffset(),activeBoard));
            }

            //Capture left
            temp = this.square.getSquareWithOffset(forward(1), -1);
            if (activeBoard.isInBounds(temp) && activeBoard.hasHostilePieceAtSquare(temp, isWhite)) { // kanshofo square wash dakhl lboard o darori tkon fih enemy piece
                moves.addAll(setupMove(temp.getSquareWithOffset(),activeBoard));
            }

            // had section dial enPassant
            int row;
            row = isWhite ? 3:4; // hada howa row li kayt7tt fih lpawn li ghanakloh mli ydir 2 steps
            if (square.getRow()==row) { // bash ikon en passant possible khass b3da lpawn ikon flblassa lmonassiba (row 3 si byed sinon row 4)
                int catchableRow, endRow ;
                catchableRow = isWhite ? 1:6; // hada howa row li kaybda fih lpawn 9bl maydir 2 steps move o li ghanakloh b en passant
                endRow = isWhite ? 2:5; // hada howa row li ghadi nt7tto fih mora mandiro en passant
                Move lastMove = BoardController.getLastMove();
                if (lastMove instanceof FirstMove) { // khass darori akhir move tdar flboard instance dial FirstMove (c'est à dire ya imma FirstMove dial Rook ola Pawn ola King)
                    if (((FirstMove) lastMove).isPawn()) { // o 3ad kancheckiw annaho instance dial FirstMove dial Pawn bddebt
                        Square endSquare = lastMove.getEndSquare();
                        Square startSquare = lastMove.getStartPos();
                        if (endSquare.getRow()==row && startSquare.getRow()==catchableRow && Math.abs(square.getCol()- endSquare.getCol())==1){
                            // condition katchouf wash hadak lpawn li t7rk akhir mrra t7tt fnafs row dial pawn dialna
                            // o ja mn catchableRow o difference bin column dial pawn dialna o dialo == 1 (ya3ni kayn ya imma 3llisser ola limen nichan)
                            moves.add(new EnPassant(this, activeBoard.getPiece(endSquare),activeBoard,new Square(endRow, startSquare.getCol())));
                        }
                    }
                }
            }
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
    @Override
    protected void getMovesHelper(int rowInc, int colInc, LinkedList<Move> moves, ActiveBoard activeBoard) {}

    @Override
    protected int[][] moveDirections() {
        return null;
    }

    // katchof wash 3mmr ma mova lpawn o katcréer FirstMove tzido f list dl moves
    // sinon la déja mova o destination hya akhir/awal (3la 7sab lcouleur dialo) row flboard kan addiw lmoves d promotion
    // sinon la makan ta wahd mn had les conditions kanzido move 3adi
    private LinkedList<Move> setupMove(Square square, ActiveBoard activeBoard) {
        LinkedList<Move> moves = new LinkedList<>();
        if(!hasMoved) { // if the first move has been executed
            moves.add(new FirstMove(this, activeBoard, square));
        } else if (square.getRow() == (isWhite ? 0: Board.ROWS - 1)) {
            moves.add(new Promotion(this, activeBoard, square, new Queen(square, isWhite)));
            moves.add(new Promotion(this, activeBoard, square, new Knight(square, isWhite)));
            moves.add(new Promotion(this,activeBoard, square,new Rook(square,isWhite,true)));
            moves.add(new Promotion(this,activeBoard, square, new Bishop(square,isWhite)));
        } else {
            moves.add(new Move(this, activeBoard, square));
        }
        return moves;
    }

    // used by AI
    @Override
    public int getScore() {
        if(upgradePiece != null) {
            return upgradePiece.getScore();
        } else {
            return SCORE;
        }
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

    // dir animation kat7rrk lpiece mn square li kayna fih db l square li 3tinaha
    // hadshi lamakantsh ghaadir upgrade sinon ghir kanbdlo tswira osf bla animation
    @Override
    public void updateSquare(Square square, boolean isVisual) {
        super.updateSquare(square, isVisual);
        if(upgradePiece != null) {
            upgradePiece.updateSquare(square, false);
        }
    }

    // kan3tiwha wahd l3adad dial steps, wila kan lpawn byd kanreturniw had l3adad negatif dialo 7it l'axe badi mn lfo9 sinon lpositif
    public int forward(int steps) {
        steps = Math.abs(steps);
        if(isWhite) {
            return -1 * steps;
        } else {
            return steps;
        }
    }

    // had lmethode kat3ti tswira dial lpiece li khtarina n upgradiw liha lhad lpawn
    public void upgrade(Piece piece, boolean isVisual) {
        this.upgradePiece = piece;
        if(isVisual) {
            oldImage = pieceView.getImage();
            pieceView.setupIcon(Launcher.filePath.getAbsolutePath() + "/ChessPieceImages", piece);
        }
    }

    // used for the AI
    public void downgrade(boolean isVisual) {
        upgradePiece = null;
        if(isVisual) {
            pieceView.setImage(oldImage);
            oldImage = null;
        }
    }

    // method kan3tiwha array du type [P,2,A,true,false]
    // katreturni lina Pawn object kayn f column 2 row A o isWhite = true o hasMoved = false
    public static Pawn parsePawn(String[] data) {
        Square square = src.square.Square.parseSquare(data[1] + data[2]);
        boolean isWhite = Boolean.parseBoolean(data[3]);
        boolean hasMoved = Boolean.parseBoolean(data[4]);

        return new Pawn(square, isWhite, hasMoved);
    }
}
