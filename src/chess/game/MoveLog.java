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
    public Move[] getLastMove(){
        return moveLog.get(moveLog.size() - 1);
    }

    /**
     *
     * @return
     */
    public Move getWhiteLastMove(){
        return getLastMove()[0];
    }

    /**
     *
     * @return
     */
    public Move getBlackLastMove(){
        return getLastMove()[1];
    }

    /**
     *
     * @param move
     * @return
     */
    public boolean containsMove(Move move){
        return false;
    }

    /**
     *
     * @param square
     * @return
     */
    public boolean containsSquare(Square square){
        return false;
    }

    /**
     *
     * @return
     */
    public String toString(){
        return null;
    }


}
