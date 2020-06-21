package main.chess.structure;

/**
 * Created by Rahul on 7/19/2017.
 * <p>
 * This class is an implementation of the GroupFactory interface. It contains the
 * methods which are used to create Group objects for different pieces.
 * This class is called anonymously in the Type Enum.
 * <p>
 * Dependencies:
 *
 * @see ChessGroupFactory
 * @see Group
 */
public class ChessGroupFactoryImpl implements ChessGroupFactory {

    /**
     * Creates group of translations which define a bishop:
     * right and up diagonal,
     * left and up diagonal,
     * right and down diagonal,
     * left and down diagonal.
     *
     * @return group object for bishops.
     * @see Translation
     * @see Group
     */
    @Override
    public Group createBishopGroup() {
        Group g = new Group();

        Translation t1 = new Translation(1, 1);
        Translation t2 = new Translation(-1, 1);
        Translation t3 = new Translation(1, -1);
        Translation t4 = new Translation(-1, -1);

        g.add(t1);
        iterate(g, t1);

        g.add(t2);
        iterate(g, t2);

        g.add(t3);
        iterate(g, t3);

        g.add(t4);
        iterate(g, t4);

        return g;

    }

    /**
     * Creates group of translations which define a rook:     *
     * up,
     * down,
     * right,
     * left.
     *
     * @return a group object for rooks.
     * @see Translation
     * @see Group
     */
    @Override
    public Group createRookGroup() {
        Group g = new Group();

        Translation t1 = new Translation(0, 1);
        Translation t2 = new Translation(0, -1);
        Translation t3 = new Translation(1, 0);
        Translation t4 = new Translation(-1, 0);

        g.add(t1);
        iterate(g, t1);

        g.add(t2);
        iterate(g, t2);

        g.add(t3);
        iterate(g, t3);

        g.add(t4);
        iterate(g, t4);

        return g;
    }

    /**
     * Creates a group of translations which define knights:     *
     * 2 right and 1 up,
     * 2 right and 2 down,
     * 2 left and 1 up,
     * 2 left and 1 down,
     * <p>
     * 1 right and 2 up,
     * 1 left and 2 up,
     * 1 right and 2 down,
     * 1 left and 2 down.
     *
     * @return a group object for knights.
     * @see Translation
     * @see Group
     */
    @Override
    public Group createKnightGroup() {
        Translation t1 = new Translation(2, 1);
        Translation t2 = new Translation(2, -1);
        Translation t3 = new Translation(-2, 1);
        Translation t4 = new Translation(-2, -1);

        Translation t5 = new Translation(1, 2);
        Translation t6 = new Translation(-1, 2);
        Translation t7 = new Translation(1, -2);
        Translation t8 = new Translation(-1, -2);

        Group g = new Group();

        g.add(t1);
        g.add(t2);
        g.add(t3);
        g.add(t4);
        g.add(t5);
        g.add(t6);
        g.add(t7);
        g.add(t8);

        return g;

    }

    /**
     * Creates a group of translations which define queens:
     * this group shares the groups of bishops and rooks.
     *
     * @return a group object for queens.
     * @see Group
     * @FIXME switching the order of groups creates the weirdest bug. Bug causes some squares
     * @FIXME to not be iterated.
     * @UPDATED: THE BUG WAS CAUSED BY MY CHOICE OF SIGNATURE. COMPONENTS OF 0 HAS 0 SIGNATURE NOW.
     * @see Translation#getSignature()
     */
    @Override
    public Group createQueenGroup() {
        Group g = createRookGroup();
        g.addAll(createBishopGroup().getGroup());
        return g;
    }

    /**
     * Creates group of translations which define kings:
     * 1 up,
     * 1 down,
     * 1 left,
     * 1 right,
     * <p>
     * 1 up and 1 right,
     * 1 up and 1 left,
     * 1 down and 1 right,
     * 1 down and 1 left.
     *
     * @return a group object for kings.
     * @see Translation
     * @see Group
     */

    @Override
    public Group createKingGroup() {
        Translation t1 = new Translation(0, 1);
        Translation t2 = new Translation(0, -1);
        Translation t3 = new Translation(-1, 0);
        Translation t4 = new Translation(1, 0);

        Translation t5 = new Translation(1, 1);
        Translation t6 = new Translation(1, -1);
        Translation t7 = new Translation(-1, 1);
        Translation t8 = new Translation(-1, -1);

        Group g = new Group();

        g.add(t1);
        g.add(t2);
        g.add(t3);
        g.add(t4);
        g.add(t5);
        g.add(t6);
        g.add(t7);
        g.add(t8);

        return g;
    }

    /**
     * Creates group of translations for pawns:
     * 2 up,
     * 1 right and 1 up,
     * 1 left and 1 up,
     * 1 up,
     * <p>
     * 2 down,
     * 1 right and 1 down,
     * 1 left and 1 down,
     * 1 down.
     *
     * @return a group object for pawns.
     * @see Translation
     */

    @Override
    public Group createPawnGroup() {
        Group g = new Group();

        Translation t1 = new Translation(0, 2);
        Translation t2 = new Translation(1, 1);
        Translation t3 = new Translation(-1, 1);
        Translation t4 = new Translation(0, 1);

        Translation t5 = new Translation(0, -2);
        Translation t6 = new Translation(1, -1);
        Translation t7 = new Translation(-1, -1);
        Translation t8 = new Translation(0, -1);

        g.add(t1);
        g.add(t2);
        g.add(t3);
        g.add(t4);
        g.add(t5);
        g.add(t6);
        g.add(t7);
        g.add(t8);

        return g;
    }

    /**
     * Iterates a base translation up to a scale factor of 8
     * and adds them to a group.
     *
     * @param g the group to which the iterated translations are added.
     * @param t the base translation which is to be iterated.
     * @see Group
     * @see Translation#iterate(int)
     */
    public void iterate(Group g, Translation t) {
        for (int k = 2; k <= 8; k++)
            g.add(t.iterate(k));
    }
}
