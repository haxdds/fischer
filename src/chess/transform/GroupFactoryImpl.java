package chess.transform;

/**
 * Created by Rahul on 6/21/2017.
 */
public class GroupFactoryImpl implements GroupFactory {
    @Override
    public Group createBishopGroup() {
        Group g = new Group();

        Translation t1 = new Translation(1,1);
        Translation t2 = new Translation(-1,1);
        Translation t3 = new Translation(1,-1);
        Translation t4 = new Translation(-1,-1);

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

    @Override
    public Group createRookGroup() {
        Group g = new Group();

        Translation t1 = new Translation(0,1);
        Translation t2 = new Translation(0,-1);
        Translation t3 = new Translation(1,0);
        Translation t4 = new Translation(-1,0);

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

    @Override
    public Group createKnightGroup() {
        Translation t1 = new Translation(2,1);
        Translation t2 = new Translation(2,-1);
        Translation t3 = new Translation(-2,1);
        Translation t4 = new Translation(-2,-1);

        Translation t5 = new Translation(1,2);
        Translation t6 = new Translation(-1,2);
        Translation t7 = new Translation(1,-2);
        Translation t8 = new Translation(-1,-2);

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

    @Override
    public Group createQueenGroup() {
        Group g = createBishopGroup();
        g.addAll(createRookGroup().getGroup());
        return g;
    }

    @Override
    public Group createKingGroup() {
        Translation t1 = new Translation(0,1);
        Translation t2 = new Translation(0,-1);
        Translation t3 = new Translation(-1,0);
        Translation t4 = new Translation(1,0);

        Translation t5 = new Translation(1,1);
        Translation t6 = new Translation(1,-1);
        Translation t7 = new Translation(-1,1);
        Translation t8 = new Translation(-1,-1);

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

    @Override
    public Group createPawnGroup() {
        Group g = new Group();

        Translation t1 = new Translation(0,2);
        Translation t2 = new Translation(1,1);
        Translation t3 = new Translation(-1,1);
        Translation t4 = new Translation(0,1);

        Translation t5 = new Translation(0,-2);
        Translation t6 = new Translation(1,-1);
        Translation t7 = new Translation(-1,-1);
        Translation t8 = new Translation(0,-1);

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


    public void iterate(Group g, Translation t){
        for(int k = 2; k <= 8; k++)
            g.add(t.iterate(k));
    }
}
