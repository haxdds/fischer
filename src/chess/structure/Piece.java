package chess.structure;

import java.util.Random;

/**
 * Created by Rahul on 7/19/2017.
 * <p>
 * This class represents the pieces involved in a chess game.
 * Pieces are the most dynamic objects in a chess game, their
 * motion on a board is what define a chess game.
 * <p>
 * Dependencies:
 *
 * @see Type
 * @see Color
 */
public class Piece {

    /**
     * These are the characteristics which define a piece:
     * Type defines the character signature, value, and translations associated
     * with the Piece object.
     * Color defines the color of the piece.
     *
     * @see Type
     * @see Color
     */
    private Type type;
    private Color color;



    /**
     * A constructor for Piece objects.
     *
     * @param type  the type associated with the piece
     * @param color the color of the piece
     * @see Type
     * @see Color
     *
     */
    public Piece(Type type, Color color) {
        this.type = type;
        this.color = color;

    }

    /**
     * @return the type of the piece
     * @see Type
     */
    public Type getType() {
        return type;
    }

    /**
     * @return the color of the pice
     * @see Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Decides whether two pieces are the same piece in color and type.
     *
     * @param p the piece being compared to.
     * @return boolean of whether pieces are equal,
     */
    public boolean equals(Piece p) {
        return p.getColor() == this.getColor() && p.getType() == this.getType();
    }

    /**
     *
     * @return a new Piece object which shares the properties
     * of this Piece object.
     */
    public Piece clone() { return new Piece(type, color); }

    /**
     *
     * @return string representation of piece
     */
    public String toString(){return "" + getColor() + " " + getType();}



}
