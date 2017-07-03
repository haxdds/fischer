package chess.transform;

/**
 * Created by Rahul on 6/21/2017.
 */
public interface GroupFactory {


    Group createBishopGroup();
    Group createRookGroup();
    Group createKnightGroup();
    Group createQueenGroup();
    Group createKingGroup();
    Group createPawnGroup();




}
