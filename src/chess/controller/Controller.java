package chess.controller;

import chess.Game;
import chess.controller.logic.MoveHandler;
import chess.gui.GUI;
import chess.structure.*;


import javax.tools.Diagnostic;
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
 * @see Game
 */
public class Controller {

    /**
     * The board and gui that the controller object regulates.
     * The moveHandler object acts as a logic filter for
     * incoming moves from the user.
     * The userInput object handles all move requests from
     * the user.
     * @see MoveHandler
     * @see Move
     */
    private Game game;
    private Board board;
    private GUI gui;
    private MoveHandler moveHandler;
    private Move userInput = new Move();
    private boolean userMove = true;
    private boolean whiteMove = true;


    /**
     * A constructor for a controller object
     * @param game the game which the controller will regulate
     * @param board the board in the game which is being regulated
     * @param gui the gui in the game which is being regulated
     * @see Game
     */
    public Controller(Game game, Board board, GUI gui){
        this.game = game;
        this.board = board;
        this.gui = gui;
        this.moveHandler = new MoveHandler(game);
        addListeners();
    }

    /**
     * Retrieves user input from the listener objects in
     * the view/gui, verifies and updates the board.
     * @param square the square where the user clicked
     * @see Listener
     * @see Move#update(Square)
     * @see Controller#renderUserInput()
     */
    public void pushUserInput(Square square) {
        if(!userMove) return;
        userInput.update(square);
        renderUserInput();
    }

    /**
     * Processes the userInput move object and
     * updates the gui accordingly. If the move
     * is matured {@link Move#isMatured()}
     * @see Move#reset()
     * @see Controller#highlightView()
     * @see Controller#authenticateAndUpdate()
     */
    public void renderUserInput(){
        if(!userInput.getStart().isOccupied()){
            userInput.reset();
            return;
        }
        if(!userInput.isMatured()){
            highlightView();
        }else{
            authenticateAndUpdate();
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
    public void authenticateAndUpdate(){
        if(moveHandler.isValidMove(userInput, board)) {
            System.out.println(userInput.toString());
            game.writeMove(userInput);
            update(userInput);
            refresh();
        }else{
            refresh();
        }
    }

    /**\
     * Resets the userInput Move object and refreshes the GUI
     * @see GUI#refreshColor()
     */
    public void refresh(){
        userInput.reset();
        gui.refreshColor();
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
     * @FIXME INCORRECT ORDER FUCKS IT UP BECAUSE PIECE IS MOVED BEFORE CHECKING
     */
    public void updateModel(Move move){
        if(board.isCastlingMove(move)){
            updateView(board.getCastlingRookMove(move));
        }
        board.movePiece(move);
    }

    /**
     * Updates the view/GUI with the verified user input.
     * @param move the move used to update the view/gui
     */
    public void updateView(Move move){
        gui.update(move);
    }

    /**
     * Highlights the possible square on the gui that
     * the user can move to
     */
    public void highlightView(){
       gui.highlight(moveHandler.getValidMoves(userInput.getStart(), board));
    }

    /**
     *
     * @return whether there is a checkmate on the board
     */
    public boolean isCheckMate(){return moveHandler.isCheckMate(board);}

    /**
     *
     * @return
     */
    public boolean isUserMove(){return userMove;}

    /**
     * Starts userMove by setting it to true.
     */
    public void startUserMove(){userMove = true;}
    /**
     *
     *
     */
    public void endUserMove(){userMove = false;}

    /**
     *
     * @return
     */
    public boolean isWhiteMove() {
        return whiteMove;
    }

    /**
     *
     */
    public void startWhiteMove() {
        this.whiteMove = true;
    }

    /**
     *
     * @return
     */
    public boolean isBlackMove() {
        return whiteMove == false;
    }

    /**
     *
     */
    public void startBlackMove() {
        this.whiteMove = false;
    }




}
