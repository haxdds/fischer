package chess.structure;

/**
 * Created by Rahul on 7/19/2017.
 * <p>
 * This class represents the different types of pieces that are involved in a chess game.
 * These pieces include: Pawns, Knights, Bishops, Rooks, Queens, Kings.
 * <p>
 * Dependencies:
 *
 * @see Group
 */
public enum Type {

    /**
     * The pieces involved in a classic game of chess and their properties.
     * Pawns, Knights, Bishops, Rooks, Queens, and Kings.
     * The assigned characters are the typical letters used to denote that piece
     * in chess move logs. The assigned values are the typical values associated with each piece.
     *
     * @see Group
     * @see ChessGroupFactory
     * @see ChessGroupFactoryImpl
     */

    PAWN('p', 1, new ChessGroupFactoryImpl().createPawnGroup()),
    KNIGHT('N', 3, new ChessGroupFactoryImpl().createKnightGroup()),
    BISHOP('B', 3, new ChessGroupFactoryImpl().createBishopGroup()),
    ROOK('R', 5, new ChessGroupFactoryImpl().createRookGroup()),
    QUEEN('Q', 9, new ChessGroupFactoryImpl().createQueenGroup()),
    KING('K', -1, new ChessGroupFactoryImpl().createKingGroup());


    /**
     * The characteristics of a type of piece in chess:
     * signature: a character which represents the piece in the console form of the game.
     * value: the typical value given to the piece.
     * group: the group of translations which the piece is allowed to move under.
     *
     * @see Group
     */
    char label;
    int value;
    Group group;

    /**
     * A constructor for Type objects.
     *
     * @param label the character signature of the type.
     * @param value the value of the type.
     * @param group the group assoiated with the type.
     * @see Group
     */
    Type(char label, int value, Group group) {
        this.label = label;
        this.value = value;
        this.group = group;
    }

    /**
     * @return signature field of the Type.
     */

    public char getLabel() {
        return label;
    }

    /**
     * @return value of the Type.
     */

    public int getValue() {
        return value;
    }

    /**
     * @return translation group of the Type.
     * @see Group
     */

    public Group getGroup() {
        return group;
    }
}
