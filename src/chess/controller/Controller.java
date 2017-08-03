package chess.controller;

import chess.game.Game;
import chess.gui.GUI;
import chess.structure.*;

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
        this.moveHandler = new MoveHandler(this);
        addListeners();
    }

    public Controller(Game game){
        this.game = game;
        this.board = new Board(this);
        this.gui = new GUI();
        this.moveHandler = new MoveHandler(this);
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
            if(userInput.getStart().equalCoordinate(userInput.getEnd())){
                refresh();
                return;
            }
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
     * @ATTENTION order of writeMove and update shouldn't be
     * changed or bad things will happen.
     * @see MoveHandler#isValidMove(Move, Board)
     * @see Controller#update(Move)
     * @see GUI#refreshColor()
     * @see Move#reset()
     */
    public void authenticateAndUpdate(){
        if(moveHandler.isValidMove(userInput, board)) {
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
        if(moveHandler.isCastlingMove(move)){
            updateView(board.getCastlingRookMove(move));
        }else if(moveHandler.isEnPassanteMove(move)){
            enPassanteUpdate(move);
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
       gui.highlight(userInput.getStart(),
               moveHandler.getValidMoves(userInput.getStart(), board), this);
    }

    /**
     *
     * @param s the square whose piece is to be removed from the view
     */
    public void removePieceFromView(Square s){
        gui.remove(s);
    }

    /**
     *
     * @param m the en passante move to be updated to the board and gui
     */
    public void enPassanteUpdate(Move m){
        Color c = (m.getStart().getColor() == Color.WHITE) ? Color.BLACK : Color.WHITE;
        Square remove = new Square(m.getStart().getRow(), m.getEnd().getCol(), c);
        removePieceFromView(remove);
    }

    /**
     *
     * @return whether there is a checkmate on the board
     */
    public boolean isCheckMate(){return moveHandler.isCheckMate(board);}

    /**
     *
     * @return whether its the user's move
     */
    public boolean isUserMove(){return userMove;}

    /**
     * Starts userMove by setting it to true.
     */
    public void startUserMove(){userMove = true;}
    /**
     *
     * ends the user's Move
     */
    public void endUserMove(){userMove = false;}

    /**
     *
     * @return whether its the white player's move
     */
    public boolean isWhiteMove() {
        return whiteMove;
    }

    /**
     * Start's the white players move
     */
    public void startWhiteMove() {
        this.whiteMove = true;
    }

    /**
     *
     * @return whether its the black player's move. i.e. not the
     * white player's move
     */
    public boolean isBlackMove() {
        return whiteMove == false;
    }

    /**
     *  Starts the move of the black player. i.e. ends the move
     *  the white player.
     */
    public void startBlackMove() {
        this.whiteMove = false;
    }

    /**
     *
     * @return the game which encapsulates the game
     * @see Game
     */
    public Game getGame() {
        return game;
    }

    /**
     *
     * @return the board on which the game is being played and
     * which the controller object regulates
     */
    public Board getBoard() {
        return board;
    }

    /**
     *
     * @return the gui which the board is displayed to
     */
    public GUI getGui() {
        return gui;
    }

    /**
     *
     * @return the moveHandler object which regulates moves on the board
     */
    public MoveHandler getMoveHandler() {
        return moveHandler;
    }

    /**
     *
     * @return the userInput object which handles user inputs
     * from the action listener class.
     */
    public Move getUserInput() {
        return userInput;
    }
}
