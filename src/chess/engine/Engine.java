package chess.engine;

import chess.structure.Board;
import chess.structure.Color;
import chess.structure.Move;

/**
 * Created by Rahul on 7/27/2017.
 *
 * A chess engine.
 *
 */
public class Engine {
    /**
     *
     * TODO: TO BE IMPLEMENTED
     */


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
     * The engine will look for different things on the board
     * and either add or subtract from the total "score" of that
     * board configuration based on many factors.
     * Each factor like center control, pawn structure, etc
     * can be represented by a valuation function. Calling each
     * valuation function on the board will add to its score.
     * Having the valuations modularize will make it simple to
     * add and remove considerations for evaluating the board.
     *
     * There will be two types of valuation functions:
     * ones that look for positive factors and ones that
     * look for negative factors.
     *
     * Different valuation functions may be used at different
     * stages at the game. The motivation behind this the
     * fact that different things matter at different stages
     * of the game.
     *
     *
     * STAGE ONE: THE OPENING
     * POSITIVE FACTORS:
     * development -- knights and bishops
     * center control
     * pawn structure
     *
     * NEGATIVE FACTORS:
     *
     *
     * STAGE TWO:
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
     * STAGE THREE:
     *
     * POSITIVE FACTORS:
     *
     * NEGATIVE FACTORS:
     *
     */






}
