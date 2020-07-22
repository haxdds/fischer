package main.chess.structure;

/**
 * Created by Rahul on 7/19/2017.
 * <p>
 * This class represents the abstract concept of the movement of a piece
 * on a chess board.A Translation object represents a motion that isn't
 * tied to any particular starting or ending squares.
 * This is the difference between a Translation object and Move object,
 * which is defined by its starting and ending squares.
 *
 * @see Move
 */
public class Translation {

    /**
     * The components of the translation. i.e. how many rows/columns
     * up or down/left or right the translation represents
     * <p>
     * x = columns
     * y = rows
     */
    private final int x;
    private final int y;

    /**
     * A constructor for Translation objects.
     *
     * @param x x/column component of the translation.
     * @param y y/row component of the translation
     */
    public Translation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return y component of translation
     */
    public int getY() {
        return y;
    }

    /**
     * @return x component of translation
     */
    public int getX() {
        return x;
    }


    /**
     * Creates and returns a translation which is a scaled version of the called translation
     *
     * @param scale the scale factor
     * @return a scaled Translation of the called translation
     */

    public Translation iterate(int scale) {
        return new Translation(this.x * scale, this.y * scale);
    }

}
