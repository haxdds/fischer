package main.chess.structure;

/**
 * Created by Rahul on 7/19/2017.
 * <p>
 * This class is factory interface, which is a design pattern that allows
 * one to create an object of a class without knowing the details of how they're created,
 * or what their dependencies are.
 * <p>
 * This factory class creates objects of the Group class. The methods associated with
 * this interface create the groups for the types of pieces in a chess game.
 *
 * @see Group
 */
public interface ChessGroupFactory {
    /**
     * @return a Group object which defines the translations available to pawns.
     */
    Group createPawnGroup();

    /**
     * @return a Group object which defines the translations available to knights.
     */
    Group createKnightGroup();

    /**
     * @return a Group object which defines the translations available to bishops.
     */
    Group createBishopGroup();

    /**
     * @return a Group object which defines the translations available to rooks.
     */
    Group createRookGroup();

    /**
     * @return a Group object which defines the translations available to queens.
     */
    Group createQueenGroup();

    /**
     * @return a Group object which defines the translations available to kings.
     */
    Group createKingGroup();

}
