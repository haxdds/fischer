package chess.structure;

import chess.structure.*;

/**
 * Created by Rahul on 7/19/2017.
 * <p>
 * This class represents a Move in a chess game. A move, in contrast to a Translation,
 * is a motion on a chess board which is tied to a starting square and a ending square.
 *
 * @see Translation
 * @see Square
 */
public class Move {
    /**
     * Properties of a Move object:
     * start: the starting square of a move.
     * end: the ending square of a move.
     * matured: state boolean which describes whether the move
     * is ready to be executed. A move is ready to be executed
     * when both the start square and end square are defined.
     *
     * @see Square
     */
    private Square start;
    private Square end;
    private boolean matured = false;

    /**
     * A constructor for a Move object which is not mature.
     *
     * @param start the starting square of the move.
     * @see Square
     */
    public Move(Square start) {
        this.start = start;
    }

    /**
     * A constructor for a Move object which is mature.
     *
     * @param start the starting square of the move.
     * @param end   the ending square of the move.
     * @see Square
     */
    public Move(Square start, Square end) {
        this.start = start;
        this.end = end;
        this.matured = true;
    }

    /**
     * A constructor for a move which not been started. This
     * can be used for utility objects.
     */
    public Move(){}

    /**
     * @return whether the move is ready to be executed, i.e. whether
     * both the start and end squares are defined.
     */
    public boolean isMatured() {
        return getEnd() != null;
    }

    /**
     * Determines whether the called move and the input move are equal.
     * Two moves are equal if their starting and ending squares are equal.
     *
     * @param m the move being compared to
     * @return state boolean of whether moves are equal.
     * @see Square#equals(Object)
     */
    public boolean equals(Move m) {
        return this.getStart().equals(m.getStart()) && this.getEnd().equals(m.getEnd());
    }

    /**
     * @return the starting square of the move.
     * @see Square
     */
    public Square getStart() {
        return start;
    }

    /**
     * @return the ending square of the move.
     * @see Square
     */
    public Square getEnd() {
        return end;
    }

    /**
     * @param start the square to be set as the starting square
     * @see Square
     */
    public void setStart(Square start) {
        this.start = start;
    }

    /**
     * @param end the square to be set as the ending square.
     * @see Square
     */
    public void setEnd(Square end) {
        this.end = end;
        this.matured = true;
    }

    /**
     * Resets the move so that it can be reused. Starting and ending squares
     * are both set to null.
     * @see Square
     */
    public void reset() {
        this.setStart(null);
        this.setEnd(null);
        this.matured = false;
    }

    /**
     * if the move is not yet mature, sets the next available slot for
     * a square(start or end) of the move to the input square.
     * @param s the square which will be added to the move
     * @throws IllegalArgumentException if the move is mature/full
     */
    public void update(Square s){
        if(isMatured()) throw new IllegalArgumentException("Move is already mature");
        if(getStart() == null){
            setStart(s);
            return;
        }
        if(getEnd() == null){
            setEnd(s);
        }
    }

    /**
     *
     * @return a new Move object with the same properties
     */
    public Move clone(){
        Move clone = new Move();
        clone.setStart(getStart().clone());
        clone.setEnd(getEnd().clone());
        return clone;
    }

    /**
     *
     * @return whether the move has been started
     */
    public boolean isStarted(){
        return getStart() != null;
    }

    /**
     * @return string containing move log
     * @TODO FINISH IMPLEMENTING COLUMNS AS LETTERS SHOULD BE IN FORM K F2, N C3, etc.
     */
    public String toString() {
        String s = "";
        if(getStart().isOccupied()){s += getStart().getPiece().getType().getLabel();}
        if(getEnd().isOccupied()) s += " x";
        s += " " + getStart().toString() + " " + getEnd().toString();
        if(getEnd().isOccupied()){s += getEnd().getPiece().getType().getLabel();}
        return s;
    }
}
