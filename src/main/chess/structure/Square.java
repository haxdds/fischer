package main.chess.structure;

/**
 * Created by Rahul on 7/19/2017.
 * <p>
 * This class represents the squares which compose a chess board. The squares
 * act as the possible positions which a piece may occupy in a game. Squares
 * have colors and may also house a piece.
 *
 * @see Board
 * @see Color
 * @see Piece
 */
public class Square {
    /**
     * Properties of a square:
     * row: vertical coordinate of the square on the board.
     * col: (column) horizontal coordinate of the square on the board.
     * color: the color of the square.
     * piece: the piece that occupies the square if the square is occupied.
     * occupied: state boolean of whether the square is occupied by a piece.
     *
     * @see Color
     * @see Piece
     */
    private int row;
    private int col;
    private Color color;
    private Piece piece;
    private boolean occupied = false;

    /**
     * A constructor for Square objects.
     *
     * @param row   the row coordinate of the square.
     * @param col   the column coordinate of the square.
     * @param color the color of the square.
     * @see Color
     */
    public Square(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    /**
     * Adds a piece to the square if it is already not occupied.
     *
     * @param p the piece to be added.
     * @see Piece
     */
    public void addPiece(Piece p) {
        if (isOccupied()) {
            throw new IllegalArgumentException("Square is already occupied");
        }
        this.piece = p;
        this.occupied = true;
    }

    /**
     * @return returns the piece occupying the square if there is a piece
     * @see Piece
     */
    public Piece getPiece() {
        if (!isOccupied()) {
            throw new IllegalArgumentException("Square at "  + this.toString() + " is not occupied");
        }
        return this.piece;
    }

    /**
     * removes piece occupying square if there is a piece.
     */

    public void removePiece() {
        if (!isOccupied()) {
            throw new IllegalArgumentException("Square is not occupied.");
        }
        this.piece = null;
    }

    /**
     * @return a new square object which has the same properties as this square.
     */
    public Square clone() {
        Square clone = new Square(getRow(), getCol(), getColor());
        if (isOccupied()) {
            clone.addPiece(getPiece().clone());
        }
        return clone;
    }

    /**
     * Creates and returns a new square object whose coordinates are translated by
     * a given Translation. The new square has the same color as the old square, but
     * not the same piece state.
     *
     * @param t the translation to be applied
     * @return the new square after translation
     */
    public Square translate(Translation t) {
        return new Square(getRow() + t.getY(), getCol() + t.getX(), getColor());
    }

    /**
     * Determines if the row and column coordinates of the square are legal
     * Square coordinates must be between 0 and 7, inclusive.
     *
     * @return state boolean of whether the square is within legal bounds.
     */
    public boolean inBounds() {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    /**
     * @return state boolean of whether the square is occupied by a piece.
     * @see Piece
     */
    public boolean isOccupied() {
        return piece != null;
    }

    /**
     * @return the row coordinate.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the column coordinate.
     */
    public int getCol() {
        return col;
    }

    /**
     * @return the color of the square.
     * @see Color
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @param s the square being compared to
     * @return whether the calling square and the input square are equal
     */
    public boolean equals(Square s){
         if(getRow() != s.getRow() || getCol() != s.getCol()) return false;
         if(isOccupied() != s.isOccupied()) return false;
         if(isOccupied()) {
             if (!getPiece().equals(s.getPiece())) return false;
         }
         return true;
    }

    /**
     *
     * @param s the square being compared to
     * @return whether the two squares have the same
     * row and column coordinates.
     */
    public boolean equalCoordinate(Square s){
        if(getRow() == s.getRow() && getCol() == s.getCol()) return true;
        return false;
    }

    /**
     *
     * @return a stylized string version of the square object
     */
    public String toString(){
        String s = null;
        switch(col){
            case 0:
                s = "A";
                break;
            case 1:
                s = "B";
                break;
            case 2:
                s = "C";
                break;
            case 3:
                s = "D";
                break;
            case 4:
                s = "E";
                break;
            case 5:
                s = "F";
                break;
            case 6:
                s = "G";
                break;
            case 7:
                s = "H";
                break;

        }
        return  s + "" + (row + 1);
    }


}
