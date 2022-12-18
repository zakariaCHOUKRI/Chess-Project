package src.chess.piece;

/**
 * interface dial lpieces li first move dialhom mohimm
 * pawn (dok 2 squares move) o rook o king (castling)
 */
public interface FirstMoveMatters {
    boolean getHasMoved();
    void setHasMoved(boolean hasMoved);
}