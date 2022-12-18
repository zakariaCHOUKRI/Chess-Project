package src.chess.Board;

public enum Status {
    WHITE(), BLACK(), FREEZE(), CREATIVE();

    public String toString() {
        return this.name();
    }
    // Freeze to end the game ( if one of the players is incheck or stalemate )

}
