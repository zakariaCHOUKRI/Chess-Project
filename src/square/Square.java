package src.square;

import src.chess.Board.Board;

/**
 * row      (0 -> 7) kanrepresentiwh par (8 -> 1)
 * o column (0 -> 7) kanrepresentiwh par (A -> E)
 */
public class Square {

    private final int row; // (0 -> 7)
    private final int column; // (0 -> 7)

    /**
     * Constructor for Square class
     *
     * @param row The value for the row
     * @param column The value for the column
     */
    public Square(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * getter for the row attribute
     *
     * @return Returns the row value
     */
    public int getRow() {
        return row;
    }

    /**
     * getter for the column attribute
     *
     * @return Returns the column value
     */
    public int getCol() {
        return column;
    }

    /**
     * @return kayreturni Square object 3ndo nafs row o nafs l column dial l'objet li 3yt 3la lfonction
     */
    public Square getSquareWithOffset() {
        return this;
    }

    /**
     * @param row the row offset
     * @param column the column offset
     * @return kayreturni Square object 3ndo row dial li 3yt + offset o column dial li 3yt + offset
     */
    public Square getSquareWithOffset(int row, int column) {
        return new Square(this.row + row, this.column + column);
    }

    /**
     * Check equality of this square and some object
     *
     * @param obj The Object to be checked
     * @return Returns true if o is a Square object and has the same row and column value
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Square) {
            Square temp = (Square)obj;
            return temp.getRow() == this.getRow() && temp.getCol() == this.getCol();
        }
        return false;
    }

    /**
     * katcreer Square object mn String kan3tiwh liha (chiffre + lettre majuscule)
     *
     * @param string par exemple "1A" ola "3D"
     * @return les exemples li lfo9 ghayreturniw lik Square(row, col) -> (7,0) o (5, 3)
     */
    public static Square parseSquare(String string) {
        int row = 8 - (string.charAt(0) - 48);
        int column = string.charAt(1) - 65;

        if(row < 0 || row >= Board.ROWS || column < 0 || column >= Board.COLUMNS) {
            return null;
        } else {
            return new Square(row, column);
        }
    }

    /**
     * l3kss dial parseSquare kay3yt liha chi square kat3tina string dialo (ex. "1A" ola "3D")
     *
     * @return Returns a string representation of a Square
     */
    public String toString() {
       return String.format("%d%c", Board.ROWS - row, (char)(column + 65));
    }

    /**
     * method kat7sb distance mabin square li ghan3tiw f parametre o lwst dial lboard b norme 1
     * method kaykhdmha l AI
     *
     * @param square square li bghina n7sbo lih distance
     * @return distance mabin square li 3tina olwst dial lboard
     */
    public static int distanceFromMiddle(Square square) {
        return (int)Math.abs(((Board.ROWS - 1) / 2.0) - square.getRow()) + (int)Math.abs(((Board.COLUMNS - 1) / 2.0) - square.getCol());
    }
}
