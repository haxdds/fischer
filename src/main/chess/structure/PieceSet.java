package main.chess.structure;

import java.util.*;

public class PieceSet implements Iterable<Piece>{

    /**
     *
     */
    private HashSet<Piece> pieces;
    private HashMap<Piece, Square> pieceMap  = new HashMap<>();

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
    public void add(Piece p, Square s){
        addToMap(p, s);
        pieces.add(p);
    }

    /**
     *
     * @param p
     */
    public void remove(Piece p, Square s){
        pieces.remove(p);
        removeFromMap(p, s);
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
     * @param p the piece to be added to the map
     * @param s the square on the board that the piece
     *          is on.
     */
    public void addToMap(Piece p, Square s){
        pieceMap.put(p, s);
    }

    /**
     *
     * @param p the piece to be removed from the map
     * @param s the square on the board from which it
     *          is to be removed
     */
    public void removeFromMap(Piece p, Square s){
        pieceMap.remove(p, s);
    }

    /**
     *
     * @return
     */
    public Collection<Square> getOccupiedSquares(){
        return pieceMap.values();
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
