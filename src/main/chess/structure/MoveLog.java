package main.chess.structure;

import main.chess.game.Game;

import java.util.*;

/**
 * Created by Rahul on 7/31/2017.
 *
 * The MoveLog class represents the list of moves
 * that are played during the game. The list of moves
 * plays a vital role in determining whether some moves
 * like en passante or castling are possible. The list of
 * moves records the history of a game.
 * @see Game
 */
public class MoveLog {
    /**
     * The list of moves that have been played on the game.
     * The list of squares that have been involved in
     * the game.
     */
    private ArrayList<Move[]> moveLog = new ArrayList<>();    
    private ArrayList<Move> moves = new ArrayList<>();
    private ArrayList<Square> squares = new ArrayList<>();

    /**
     * A constructor for MoveLog objects.
     */
    public MoveLog(){

    }

    /**
     *
     * @param moveNumber the move number of the move to be retrieved
     * @return the moveNumber'th move
     */
    public Move[] getMove(int moveNumber){
        return moveLog.get(moveNumber - 1);
    }

    /**
     *
     * @param moveNumber
     * @return
     */
    public Move getWhiteMove(int moveNumber){
        return getMove(moveNumber)[0];
    }

    /**
     *
     * @param moveNumber
     * @return
     */
    public Move getBlackMove(int moveNumber){
        return getMove(moveNumber)[1];
    }

    /**
     *
     * @return
     */
    public Move[] getLastMoveSet(){
        return moveLog.get(moveLog.size() - 1);
    }

    /**
     *
     * @return
     */
    public Move getLastMove(){
        if(moves.size() == 0) return null;
        return moves.get(moves.size() - 1);
    }

    /**
     *
     * @return
     */
    public Move getWhiteLastMove(){
        return getLastMoveSet()[0];
    }

    /**
     *
     * @return
     */
    public Move getBlackLastMove(){
        return getLastMoveSet()[1];
    }

    /**
     *
     * @param move
     * @return
     */
    public boolean containsMove(Move move){
        for(Move m: moves){
            if(m.equals(move)) return true;
        }
        return false;
    }

    /**
     *
     * @param square
     * @return
     */
    public boolean containsSquare(Square square){
        for(Square s: squares){
            if(s.equals(square)) return true;
        }
        return false;
    }

    /**
     *
     * @param move
     */
    public void write(Move move){
        moves.add(move);
        //moveLog.add()
        squares.add(move.getStart());
        squares.add(move.getEnd());
    }

    /**
     *
     * @return
     */
    public ArrayList<Move[]> getMoveLog() {
        return moveLog;
    }

    /**
     *
     * @return
     */
    public ArrayList<Move> getMoves() {
        return moves;
    }

    /**
     *
     * @return
     */
    public ArrayList<Square> getSquares() {
        return squares;
    }

    /**
     *
     * @return the total number of moves made
     */
    public int size(){
        return moves.size();
    }

    /**
     *
     * @return
     */
    public String toString(){
        return null;
    }


    public MoveLog clone(){
        MoveLog clone = new MoveLog();
        for(Move m : moves){
            clone.write(m);
        }
        return clone;
    }


}
