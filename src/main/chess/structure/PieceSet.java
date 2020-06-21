package main.chess.structure;

import java.util.HashSet;
import java.util.Iterator;

public class PieceSet implements Iterable<Piece>{

    /**
     *
     */
    private HashSet<Piece> pieces;

    /**
     *
     * @param pieces
     */
    public PieceSet(HashSet<Piece> pieces){
        this.pieces = pieces;
    }

    /**
     *
     */
    public PieceSet(){this.pieces = new HashSet<Piece>();}

    /**
     *
     * @return
     */
    public PieceSet createWhitePieceSet(){
        PieceSet set = new PieceSet();


        return set;
    }

    /**
     *
     * @param p
     */
    public void add(Piece p){
        pieces.add(p);
    }

    /**
     *
     * @param p
     */
    public void remove(Piece p){
        pieces.remove(p);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean hasPiece(Piece p){
        return pieces.contains(p);
    }

    /**
     *
     * @return
     */
    @Override
    public Iterator<Piece> iterator() {
        return pieces.iterator();
    }
}
