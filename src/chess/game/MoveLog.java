package chess.game;

import chess.structure.Move;
import chess.structure.Square;

import java.util.ArrayList;

/**
 * Created by Rahul on 7/31/2017.
 */
public class MoveLog {
    /**
     *
     */
    private ArrayList<Move[]> moveLog = new ArrayList<>();
    private ArrayList<Move> moves = new ArrayList<>();
    private ArrayList<Square> squares = new ArrayList<>();

    /**
     *
     * @param game
     */
    public MoveLog(Game game){

    }

    /**
     *
     * @param moveNumber
     * @return
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
        moves.add(move.clone());
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
     * @return
     */
    public String toString(){
        return null;
    }


}
