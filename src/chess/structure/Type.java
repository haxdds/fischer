package chess.structure;

import chess.transform.Group;
import chess.transform.GroupFactoryImpl;

public enum Type{
    PAWN(1, 'p', new GroupFactoryImpl().createPawnGroup()),
    KNIGHT(3, 'k', new GroupFactoryImpl().createKnightGroup()),
    BISHOP(3, 'b', new GroupFactoryImpl().createBishopGroup()),
    ROOK(5, 'r', new GroupFactoryImpl().createRookGroup()),
    QUEEN(9, 'q', new GroupFactoryImpl().createQueenGroup()),
    KING(0, 'W', new GroupFactoryImpl().createKingGroup());

    private int value;
    private char name;
    private Group group;

    Type(int value, char name, Group group){
        this.value = value;
        this.name = name;
        this.group = group;
    }

    public int getValue(){return value;}
    public char getName(){return name;}
    public Group getGroup(){return group;}
}
