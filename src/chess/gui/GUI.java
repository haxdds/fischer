package chess.gui;

import chess.structure.Board;

/**
 * Created by Rahul on 6/15/2017.
 */
public class GUI {
    private BoardGUI boardGUI;
    private MoveHandler handler;
    private Board board;


    public GUI(Board board){
        boardGUI = new BoardGUI();
        this.board = board;
        handler = new MoveHandler(boardGUI, board);
    }

    public BoardGUI getBoardGUI(){
        return boardGUI;
    }

    public MoveHandler getHandler(){
        return handler;
    }

    public Board getBoard() {
        return board;
    }
}
