package chess.structure;

import java.util.HashSet;
import java.util.Iterator;

public class PieceSet implements Iterable<Piece>{

    private HashSet<Piece> pieces;

    public PieceSet(HashSet<Piece> pieces){
        this.pieces = pieces;
    }

    public PieceSet(){this.pieces = new HashSet<Piece>();}

    public void add(Piece p){
        pieces.add(p);
    }

    public void remove(Piece p){
        pieces.remove(p);
    }

    public boolean hasPiece(Piece p){
        return pieces.contains(p);
    }

    @Override
    public Iterator<Piece> iterator() {
        return pieces.iterator();
    }
}
