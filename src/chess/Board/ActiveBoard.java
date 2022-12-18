package src.chess.Board;

import src.chess.move.Move;
import src.chess.piece.Piece;
import src.square.Square;

import java.util.LinkedList;

public interface ActiveBoard {

    /**
     * katrje3 liste dyal  all possible moves dyal player(white or black)
     *
     * @param isWhite wach player white wla black
     * @return list of possible moves of a player
     */
    LinkedList<Move> getAllMoves(boolean isWhite);

    /**
     * katrje3 liste dyal l moves li valid( possible en terme d execution )
     *
     * @param isWhite wach player white wla black
     * @return list dyal valid moves of a player
     */
    LinkedList<Move> getAllValidMoves(boolean isWhite);

    /**
     * katchuf wach had square li anmchi lih khawi wla 3amer ou wach ghadi ikhli l piece tkal
     *
     * @param square the square which is checked
     * @param isWhite wach player white wla black
     * @return True ila kan square khawi ou machi under attack
     */
    boolean isCleanMove(Square square, Boolean isWhite);

    /**
     * wach l move ghadi ikhli l piece tkal wla la
     *
     * @param square the square which is checked
     * @param isWhite wach player white wla black
     * @return True ila kan square not under attack
     */
    boolean isSafeMove(Square square, Boolean isWhite);

    /**
     * wach l move ghadi ikhli l piece tkhrej mn l board
     *
     * @param square the square which is checked
     * @return wach square f lboard wla bra
     */
    boolean isInBounds(Square square);

    /**
     * wach square fih piece men nefs l color dyal player
     *
     * @param square the square which is checked
     * @param isWhite wach player li f suqare white wla black
     * @return True if there is a piece in the square and with the same color
     */
    boolean hasFriendlyPieceAtSquare(Square square, boolean isWhite);

    /**
     * wach square fih piece men nefs l color opposee dyal player
     *
     * @param square the square which is checked
     * @param isWhite wach player li f suqare white wla black
     * @return True if there is a piece in the square and with the opposite color
     */
    boolean hasHostilePieceAtSquare(Square square, boolean isWhite);

    /**
     * wach square fih piece (kifma kanet )
     *
     * @param square the square which is checked
     * @return True if there is a piece in the square
     */
    boolean hasPieceAtSquare(Square square);

    /**
     * katrje3 piece finma kant kbel matkal ou kathyed liha dead (from true to false)
     *  @param piece which is revived
     * @param isVisual wach had l opperation ghadi tban f lboard wla hi simulation
     */
    void revive(Piece piece, boolean isVisual);

    /**
     * katyhed piace men square dyalha ou attribute dead ki wli True
     * @param piece which is dead
     * @param isVisual wach had l opperation ghadi tban f lboard wla hi simulation
     *
     */
    void kill(Piece piece, boolean isVisual);

    /**
     * kathet l piece f new square ou lpiece ki tassigna liha dak square
     *  @param piece piece which is moved
     * @param newSquare new square of piece
     * @param isVisual wach had l opperation ghadi tban f lboard wla hi simulation
     */
    void updateSquare(Piece piece, Square newSquare, boolean isVisual);

    /**
     * katrje3 lik l piece li kayna f dak square
     *
     * @param square the specified square
     * @return the Piece at {@code square}
     */
    Piece getPiece(Square square);

    /**
     * katrje3 score dyal lgame
     * @return score at board
     */
    int score();

    /**
     * check if the file( column) is open
     *
     * @param col the file which is checked
     * @return {@code true} if the file is open, {@code false} otherwise
     */

    boolean checkNoPawn(int col);

    /**
     * wach l king  is checkmate
     *
     * @param isWhite whether the player plays black or white
     * @return True if the player is checkmate, False otherwise
     */
    boolean checkForCheckMate(boolean isWhite);

    /**
     * wach player f stalemate
     *
     * @param isWhite whether the player plays black or white
     * @return True if the player can no longer make any moves, False otherwise
     */
    boolean checkForStaleMate(boolean isWhite);

    /**
     * katrje3 l king
     *
     * @param isWhite whether the King is black or white
     * @return King {@code Piece}
     */
    Piece getKing(boolean isWhite);

    /**
     * wach l king endangered
     * @param isWhite whether the player play white or black
     * @return True if the player is endangered
     */
    boolean checkForCheck(boolean isWhite);
}
