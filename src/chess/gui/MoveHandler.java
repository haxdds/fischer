package chess.gui;

import chess.engine.MoveIterator;
import chess.structure.Board;
import chess.structure.Square;
import chess.transform.Group;
import chess.transform.Translation;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by Rahul on 6/15/2017.
 */
public class MoveHandler {

    BoardGUI boardGUI;
    Board board;
    Move move;
    MoveIterator iterator;

    public MoveHandler(BoardGUI boardGUI, Board board){
        this.boardGUI = boardGUI;
        this.board = board;
        this.iterator = new MoveIterator(boardGUI, board);
        addListeners();
    }

    private void addListeners(){
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                addListener(new Square(row, col));
            }
        }
    }

    private void addListener(Square square){
        boardGUI.getSquare(square).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(square.getRow() + " " + square.getColumn());

                    if (move == null) {
                        move = new Move(square);
                        boardGUI.highlight(iterator.iterateMoves(square));
                        System.out.println("Start");
                    } else {
                        if (move.getStart() == null) {
                            move.setStart(square);
                            boardGUI.highlight(iterator.iterateMoves(square));
                            System.out.println("Start");
                        } else {
                            move.setEnd(square);
                            System.out.println("End");
                            if (isValidMove(move) && !move.getStart().equals(move.getEnd())){
                                    boardGUI.move(move.getStart(), move.getEnd());
                                    board.movePiece(move.getStart(), move.getEnd());
                                    board.printBoard();
                                    boardGUI.refreshColor();
                            } else if(move.getStart().equals(move.getEnd())){
                                boardGUI.refreshColor();
                            }else{
                                System.out.println("INVALID MOVE");
                            }
                            move.reset();
                        }
                    }
                }

        });
    }

    public boolean isValidMove(Move move){
        Group g = board.getPiece(move.getStart()).getType().getGroup();
        Translation t = move.getTranslation();
        return g.contains(t);
    }


}
