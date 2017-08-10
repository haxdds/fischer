package chess.structure;

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
    private int x;
    private int y;
    /**
     * The signature of a translation defines the direction of motion of the translation.
     * i.e. a translation where (x,y) = (2,2) moves in the same direction as
     * a translation defined by (x,y) = (5,5); both moves upwards and rightwards in the
     * positive y and x directions.
     * so a signature of [1,1] is defined for both these translations.
     * <p>
     * Signatures are defined by a 1-dimensional array of length 2, where the zeroth
     * component defines the x signature and the first component defines the y signature.
     * @see Translation#getSignature()
     */
    private int[] signature = new int[2];

    /**
     * A constructor for Translation objects.
     *
     * @param x x/column component of the translation.
     * @param y y/row component of the translation
     */
    public Translation(int x, int y) {
        this.x = x;
        this.y = y;

        setSignature();
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
     * @return signature of translation
     */

    public int[] getSignature() {
        return signature;
    }

    /**
     * Signatures are defined by the direction of motion of each component of
     * the translation. Components that are less than 0 are given a signature of -1,
     * components that are greater than 0 are given a signature of 1, and components
     * of 0 are given a signature of 0.
     * These conventions were chosen out of convenience.
     * <p>
     * This method is called on construction of the translation object.
     */

    public void setSignature() {
        if (x < 0) {
            signature[0] = -1;
        } else if(x > 0){
            signature[0] = 1;
        }else{
            signature[0] = 0;
        }
        if (y < 0) {
            signature[1] = -1;
        } else if(y > 0){
            signature[1] = 1;
        }else{
            signature[1] = 0;
        }
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

    /**
     *
     * TODO: WHY DOESN'T THIS WORK INSIDE OF FUNCTION {@link chess.controller.MoveHandler#iterateMoves(Square, Board)}
     * @param t the translation whose signature is to be checked
     * @return whether the signatures of both translations are equal
     * @see #signature
     */
    public boolean equalSignatures(Translation t){
        return this.getSignature()[0] == t.getSignature()[0] && this.getSignature()[1] == t.getSignature()[1];
    }

    /**
     *
     * @param a the signature being compared to
     * @return whether the signature of this translation is equal
     * to the input signature
     * @see #signature
     */
    public boolean isSignature(int[] a){
        if(a == null) return false;
        return this.getSignature()[0] == a[0] && this.getSignature()[1] == a[1];
    }



}
