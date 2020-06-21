package main.chess.engine;

import main.chess.structure.Board;
import main.chess.structure.Move;

import java.util.ArrayList;

/**
 * Created by Rahul on 7/27/2017.
 */
public class Node {
    /**
     *
     */
    private Board board;
    private ArrayList<Move> moves = new ArrayList<>();
    private int value = 0;
    private int score;
    /**
     *
     */
    private Node parent;
    private ArrayList<Node> children = new ArrayList<>();

    /**
     *
     * @param parent
     * @param move
     */
    public Node(Node parent, Move move){
        this.parent = parent;
        board = parent.getBoard().clone();
        board.movePiece(move);
        this.score = parent.getScore();

    }

    /**
     *
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     *
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
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
     * @param moves
     */
    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    /**
     *
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * @return
     */
    public Node getParent() {
        return parent;
    }

    /**
     *
     * @param parent
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     *
     * @return
     */
    public ArrayList<Node> getChildren() {
        return children;
    }

    /**
     *
     * @param children
     */
    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    /**
     *
     * @param child
     */
    public void addChild(Node child){this.children.add(child);}
}
