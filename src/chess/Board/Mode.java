package src.chess.Board;

public enum Mode {
    COMPUTER_VS_COMPUTER(), PLAYER_VS_PLAYER(), COMPUTER_VS_PLAYER();
    //enumeration of the game modes we want to add to our chess project
    //(AI vs AI)
    //(player vs player)
    //(AI vs player)
    public String toString()
    {
        return this.name();
    }
    //returns the mode
}
