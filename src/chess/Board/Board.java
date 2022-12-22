package src.chess.Board;

import src.chess.move.Move;
import src.controller.BoardController;

import src.chess.piece.King;
import src.chess.piece.Pawn;
import src.chess.piece.Piece;
import src.square.Square;

import java.util.LinkedList;

public class Board implements ActiveBoard {
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;

    private BoardController controller;


    private Piece[] whitePieces;
    private Piece[] blackPieces;

    private Piece[][] board;

    public Board(Piece[] whitePieces, Piece[] blackPieces, BoardController boardController){
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;

        board = new Piece[ROWS][COLUMNS];
        for (Piece whitePiece:
                whitePieces) {
            this.put(whitePiece.getSquare(), whitePiece);
            // putting each piece in her square
        }

        for (Piece blackPiece:
                blackPieces) {
            this.put(blackPiece.getSquare(), blackPiece);
            // putting each piece in her square
        }

        this.controller = boardController;
    }



    /**
     * get all possible moves of a player
     *
     * @param isWhite whether the player plays black or white
     * @return list of possible moves of a player
     */
    public LinkedList<Move> getAllMoves(boolean isWhite) {
        Piece[] pieces = isWhite ? whitePieces : blackPieces;
        // if iswhite = True is , then the list will consist of white pieces, else black pieces
        LinkedList<Move> moves = new LinkedList<>();

        for (Piece piece: pieces) {
            if (!piece.isDead()) {
                // if the piece is not dead, then add the moves to the list
                moves.addAll(piece.getMoves(this));
            }
        }
        return moves;
    }

    /**
     * get all possible moves excluding castling of a player
     *
     * @param isWhite whether the player plays black or white
     * @return list of possible moves excluding castling of a player
     */
    private LinkedList<Move> getAllMovesNoCastle(boolean isWhite) {
        Piece[] pieces = isWhite ? whitePieces : blackPieces;
        // if iswhite = True , then the list will be constituted of white pieces, else black pieces
        LinkedList<Move> moves = new LinkedList<>();
        for (Piece piece: pieces) {
            if(!piece.isDead()) {
                if (piece instanceof King) {

                    moves.addAll(((King) piece).getMovesNoCastle(this));
                    //adds all moves besides castling move if the piece is a king
                } else {
                    moves.addAll(piece.getMoves(this));
                    //adds all moves for other pieces
                }
            }
        }
        return moves;
    }

    /**
     * get all valid moves of one player
     *
     * @param isWhite whether the player plays black or white
     * @return list of valid moves of one player
     */
    public LinkedList<Move> getAllValidMoves(boolean isWhite) {
        LinkedList<Move> validMoves = new LinkedList<>();

        Piece[] pieces = isWhite ? whitePieces : blackPieces;
        // if iswhite = True  , then the list will consist of white pieces, else black peieces
        for (Piece piece: pieces) {
            if (!piece.isDead()) {
                //check if the piece is dead, if not then we add all valid moves to the list
                validMoves.addAll(piece.getValidMoves(this));
            }
        }
        return validMoves;
    }

    /**
     * get the king
     *
     * @param isWhite whether the King is black or white
     * @return King {@code Piece}
     */
    public Piece getKing(boolean isWhite) {
        Piece[] pieces = isWhite ? whitePieces : blackPieces;
        // if iswhite = True , then the list will consist of white pieces, else black peieces
        for (Piece piece: pieces) {
            if (piece instanceof King) {
                //if piece is a king, then we return it
                return piece;
            }
        }
        return null;
    }

    /**
     * check if the king is endangered
     * @param isWhite whether the player play white or black
     * @return {@code true} if the king is under attack, {@code false} otherwise
     */
    public boolean checkForCheck(boolean isWhite) {
        Piece king = getKing(isWhite);
        // as seen is the class Active board, this method returns whether a move will lead the piece to be
        // eaten, used with the current square of the piece(here the king), this method returns whether the piece
        // (here the king) is endangered
        return !isSafeMove(king.getSquare(), isWhite);
    }

    /**
     * check if the King can make any moves
     *
     * @param isWhite whether the king is black or white
     * @return {@code true} if the king can move, {@code false} otherwise
     */
    public boolean checkIfKingCanMove(boolean isWhite) {
        //getValidMoves is a method used in the ActiveBoard that returns list with all valid moves for a piece
        return getKing(isWhite).getValidMoves(this).size() != 0;
    }

    /**
     * check if a player came to a stalemate
     *
     * @param isWhite whether the player plays black or white
     * @return {@code true} if this player can no longer make any moves, {@code false} otherwise
     */
    public boolean checkForStaleMate(boolean isWhite) {
        // Stalemate is a kind of draw that happens when one side has NO legal moves to make.
        // If the king is NOT in check, but no piece can be moved without putting the king in check,
        // then the game will end with a stalemate draw.
        return !checkIfKingCanMove(isWhite) && getAllValidMoves(isWhite).size() == 0;
        //return whether the king can move and the move is valid
    }

    /**
     * check if a player is checkmate
     *
     * @param isWhite whether the player plays black or white
     * @return {@code true} if this player is checkmate, {@code false} otherwise
     */
    public boolean checkForCheckMate(boolean isWhite) {
        // is equivalent to when the king has no valid and safe moves to do anymore
        return !isSafeMove(getKing(isWhite).getSquare(), isWhite) && getAllValidMoves(isWhite).size() == 0;
        //return whether there are safe and valid moves left fot the king to do
    }

    /**
     * method katchecki wash makaynch chi pawn fl column li 3tinaha (used for AI)
     *
     * @param col lcolumn li bghina ncheckiw
     * @return {@code true} ila makaynsh pawn, {@code false} ila kayn
     */
    public boolean checkNoPawn(int col) {
        for (int i = 0; i < Board.ROWS; i++) {
            //we loop on the rows of the board and for each row
            //we get the piece at the corresponding column (col)
            Piece p = getPiece(new Square(i, col));
            if (p instanceof Pawn) {
                //if the piece is a pawn then the return is false
                return false;
            }
        }
        return true;
    }

    /**
     * calculate score in this board (used for AI)
     * @return total score in board
     */
    public int score() {
        int score = 0;
        //we calculate the score based on the number of pieces left on the board for each player
        for (Piece piece: whitePieces) {
            if (!piece.isDead()) {
                score += piece.getScore();
            }
        }
        for (Piece piece: blackPieces) {
            if (!piece.isDead()) {
                score -= piece.getScore();
            }
        }
        return score;
    }

    /**
     * Returns the Piece at the specified square
     *
     * @param square the specified square
     * @return the Piece at {@code square}
     */
    public Piece getPiece(Square square) {
        if(isInBounds(square)) {
            //we check if the square is not outside the board
            return board[square.getRow()][square.getCol()];
        }
        else
            return null;
    }

    /**
     * Sets the value in the board array at the square to the specified piece
     *
     * @param square the square which is got the piece
     * @param piece which is placed at square
     */
    public void put(Square square, Piece piece) {
        //puts the piece at the square
        board[square.getRow()][square.getCol()] = piece;
    }

    /**
     * Sets the value in the board array to null
     *
     * @param square whose the piece is removed
     */
    public void remove(Square square) {
        // sets the value of a "square" in the board array to null
        board[square.getRow()][square.getCol()] = null;
    }

    /**
     * Moves the piece to its new position and updates the pieces' internal position
     *
     * @param piece piece which is moved
     * @param newSquare new position of piece
     * @param isVisual whether the move is visible
     */
    public void updateSquare(Piece piece, Square newSquare, boolean isVisual) {
        this.remove(piece.getSquare());
        //remove piece from its current square
        this.put(newSquare, piece);
        //place it in the new square
        piece.updateSquare(newSquare, isVisual);
        //update the value of the assigned square to the piece
    }

    /**
     * Removes the piece from the board array and sets the piece's isDead value to true
     *
     * @param piece which is dead
     * @param isVisual whether the move is real or only simulation (visible vs not visible)
     */
    public void kill(Piece piece, boolean isVisual) {
        this.remove(piece.getSquare());
        // sets the value of a "square" in the board array to null
        piece.kill(isVisual);
        //piece isDead attribute is set to True
    }

    /**
     * Adds the piece to the board at its last position and sets its isDead value to false
     *
     * @param piece which is revived
     * @param isVisual  whether the revival is real or only simulation (visible vs not visible)
     */
    public void revive(Piece piece, boolean isVisual) {
        this.put(piece.getSquare(), piece);
        //puts back the piece in the last square it was in
        piece.revive(isVisual);
        //piece isDead attribute is set to False
    }

    /**
     * Checks to see if there is a piece at the specified square
     *
     * @param square the square which is checked
     * @return Returns true if the square has a piece
     */
    public boolean hasPieceAtSquare(Square square) {
        Piece piece = this.getPiece(square);
        //get the piece at the specified square
        return piece != null;
        // return whether it is null or not
    }

    /**
     * Checks to see if there is a piece at the specified square that is the opposing color
     *
     * @param square the square which is checked
     * @param isWhite whether the piece at {@code square} is white or black
     * @return Returns true if the square in the board has a piece  and the piece is the opposite color
     */
    public boolean hasHostilePieceAtSquare(Square square, boolean isWhite) {
        Piece piece = this.getPiece(square);
        //get the piece at the specified square
        return piece != null && piece.isWhite() != isWhite;
        // return whether the piece is not null and if it is of the opposite color
    }

    /**
     * Checks to see if there is a piece at the specified square that is the same color
     *
     * @param square the square which is checked
     * @param isWhite whether the piece at {@code square} is white or black
     * @return Returns true if the square in the board array is not null and the piece is the same color
     */
    public boolean hasFriendlyPieceAtSquare(Square square, boolean isWhite) {
        Piece piece = this.getPiece(square);
        //get the piece at the specified square
        return piece != null && piece.isWhite() == isWhite;
        // return whether the piece is not null and if it is of the same color

    }

    /**
     * Checks to see if the square is within the bounds of the size of the board
     *
     * @param square the square which is checked
     * @return whether the square is in the Board
     */
    public boolean isInBounds(Square square) {

        int column = square.getCol();
        //get column of the square
        int row = square.getRow();
        //get row of the square

        return !(column < 0 || column >= COLUMNS || row < 0 || row >= ROWS);
        //return whether the square is inside the board
    }

    /**
     * Checks to make sure that there is no piece at the specified square and that no opponent piece is attacking the square
     *
     * @param square the square which is checked
     * @param isWhite whether the piece is black or white
     * @return true if the square is not under attack and contains no piece
     */
    public boolean isCleanMove(Square square, Boolean isWhite) {

        return !hasPieceAtSquare(square) && isSafeMove(square, isWhite);
        // returns whether there is a piece at the square and if it s a safe move
    }

    /**
     * Checks to see if any piece of the opposite color is attacking the specified square
     *
     * @param square the square which is checked
     * @param isWhite whether the piece is black or white
     * @return true if the square is not under attack
     */
    public boolean isSafeMove(Square square, Boolean isWhite) {
        LinkedList<Move> opponentMoves = getAllMovesNoCastle(!isWhite);
        // lists of all moves(except castling) of the opposite color
        for (Move move: opponentMoves) {
            if (move.getEndSquare().equals(square)) {
                // if one of the end squares of the moves in the list can be this square,
                // then it s NOT a safe move
                return false;
            }
        }
        return true;
    }



    public void print() {
        //prints the board (|K||Q|...)
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + " |");
            for (int j = 0; j < 8; j++) {
                Square square = new Square(i, j);
                Piece piece = this.getPiece(square);
                if (piece != null) {
                    System.out.printf("%2s|", piece.isWhite() ? piece.getID().toUpperCase() : piece.getID().toLowerCase());
                    //white pieces are  represented  with uppercase initials , while black ones with lowercase initials
                } else {
                    System.out.print("  |");
                }
            }
            System.out.println();
        }
        System.out.print("   ");

        for (int i = 65; i < 65 + 8; i++) {
            System.out.printf(" %c ", i);
        }
        System.out.println();
    }
}