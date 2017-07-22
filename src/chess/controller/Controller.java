package chess.controller;

import chess.controller.logic.MoveHandler;
import chess.gui.GUI;
import chess.structure.Board;
import chess.structure.Move;
import chess.structure.Square;

import java.util.ArrayList;

/**
 * Created by Rahul on 7/20/2017.
 *
 * Controller in Model-View-Controller(MVC) design pattern. The controller
 * is the bridge between the model ( the board on which the game is played )
 * and the view ( the gui on which we see and interact with the board ). The
 * Controller also handles user input and retrieves moves from the game
 * engine.
 * @see Board
 * @see GUI
 * @see Listener
 * @see
 */
public class Controller {

    /**
     *
     */
    private Board board;
    private GUI gui;
    private MoveHandler moveHandler;
    private Move userInput = null;

    public Controller(Board board, GUI gui){
        this.board = board;
        this.gui = gui;
        this.moveHandler = new MoveHandler();
        addListeners();
    }

    /**
     * Retrieves user input from the listener objects in
     * the view/gui, verifies and updates the board.
     * @param square the square where the user clicked
     * @see Listener
     * @see GUI#highlight(ArrayList)
     * @see Move#update(Square)
     * @see Controller#authenticateUserInput()
     */
    public void pushUserInput(Square square) {
        System.out.println("row:" + square.getRow()+ "  col:" + square.getCol());
        if(moveHandler.isCheck(board)){System.out.println("CHECK");}
        System.out.println(moveHandler.getCheckedColor(board));
        if(userInput == null){
            if(!square.isOccupied()) return;
            userInput = new Move(square);
            gui.highlight(moveHandler.getValidMoves(userInput.getStart(), board));
        }else{
            if(userInput.getStart() == null){
                if(!square.isOccupied()) return;
                userInput.update(square);
                gui.highlight(moveHandler.getValidMoves(userInput.getStart(), board));
            }else{
                userInput.update(square);
                authenticateUserInput();
            }
        }
    }

    /**
     * Adds Listener objects to the view/gui to retrieve
     * user input for the game.
     * @see Listener
     * @see GUI
     */
    public void addListeners(){
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                Listener listener = new Listener(board.getSquare(row, col), this);
                gui.getSquare(row, col).addActionListener(listener);
            }
        }
    }

    /**
     * Authenticates the user input move on the model board
     * by using MoveHandler.
     * @see MoveHandler#isValidMove(Move, Board)
     * @see Controller#update(Move)
     * @see GUI#refreshColor()
     * @see Move#reset()
     */
    public void authenticateUserInput(){
        if(moveHandler.isValidMove(userInput, board)){
            update(userInput);
            gui.refreshColor();
            userInput.reset();
            System.out.println(board.toString());
        }else{
            userInput.reset();
            gui.refreshColor();
        }
    }

    /**
     * TODO: IMPLEMENT ENGINE
     */
    public void runEngine(){}

    /**
     * Updates both the model and the view in the MVC pattern
     * @param move the move that is used to update the game
     * @see Controller#updateModel(Move)
     * @see Controller#updateView(Move)
     */
    public void update(Move move){
        updateModel(move);
        updateView(move);
    }

    /**
     * Updates the model board with the verified user input.
     * @param move the move used to update the model/board
     */
    public void updateModel(Move move){
        board.movePiece(move);
    }

    /**
     * Updates the view/GUI with the verified user input.
     * @param move the move used to update the view/gui
     */
    public void updateView(Move move){
        gui.update(move);
    }


}
