package chess.engine;

import chess.structure.Board;
import chess.structure.Color;
import chess.structure.Move;

/**
 * Created by Rahul on 7/27/2017.
 */
public class Engine {

    public Move findBestMove(Board board, Color color, int searchDepth){
        return null;
    }

    public int evaluateBoard(Board board, Color color){
        return -1;
    }

    public Node createTree(Board board, Color color, int searchDepth){
        return null;
    }

    public Node search(Node root){
        return null;
    }

    /**
     *
     * Lots of valuation functions which look for
     *
     * Break into stages:
     * -Opening
     * development -- knights and bishops
     * center control
     * pawn structure
     *
     * negative
     * unsafe king
     *
     * -Middle Game
     * POSITIVE FACTORS:
     * safe king/castled king
     * double bishops
     * pins
     * center control
     * knights control center
     * diagonal bishops
     *
     * NEGATIVE FACTORS:
     * doubled pawns
     * knights on the rim
     *
     *
     *
     *
     * -End Game
     *
     */






}
