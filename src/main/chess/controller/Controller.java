package main.chess.controller;

import main.chess.game.Game;
import main.chess.gui.GUI;
import main.chess.structure.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    private LinkedList<List<Move>> validMoveHistory = new LinkedList<>();

    private MoveHandler moveHandler = new MoveHandler();;
    private Move userInput = new Move();
    private boolean userMove = true;
    private boolean whiteMove = true;
    private boolean running = true;


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
        addListeners();
        validMoveHistory.add(moveHandler.generateAllValidMoves(board));
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
//
    /**
     * Processes the userInput move object and
     * updates the gui accordingly. If the move
     * is matured {@link Move#isMatured()}
     * @see Move#reset()
     * @see Controller#highlightView()
     * @see Controller#authenticateAndUpdate()
     */
    public void renderUserInput(){
        if(!authenticateMove()){
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
     * Authenticates the userInput two ways:
     * 1) the starting square must contain a piece
     * 2) it must be the correct side's turn
     * @return whether the userInput is valid
     */
    public boolean authenticateMove(){
        if(!userInput.getStart().isOccupied()){
            return false;
        }
        if(isWhiteMove()){
            if(userInput.getStart().getPiece().getColor() != Color.WHITE){
                return false;
            }
        }else{
            if(userInput.getStart().getPiece().getColor() != Color.BLACK) {
                return false;
            }
        }
        return true;
    }


    /**
     * Authenticates the user input move on the model board
     * by using MoveHandler.
     * @ATTENTION order of writeMove and update shouldn't be
     * changed or bad things will happen.
     * @see Controller#isValidMove(Move, Board)
     * @see Controller#update(Move)
     * @see GUI#refreshColor()
     * @see Move#reset()
     */
    public void authenticateAndUpdate(){
        if(isValidMove(userInput, board)) {
            System.out.println("HELLO");
            update(userInput);
            changeTurn();
            processTurn();
            validMoveHistory.add(moveHandler.generateAllValidMoves(board));
        }
        System.out.println("HI");

        refresh();
    }


    public boolean isValidMove(Move m, Board b){
        for(Move mv : validMoveHistory.get(validMoveHistory.size()-1)) {

            if (mv.equals(m)) {
                System.out.println(mv.toString());
            }
        }
        System.out.println(validMoveHistory.get(validMoveHistory.size() - 1).contains(m));
        return validMoveHistory.get(validMoveHistory.size() - 1).contains(m);
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
//        if(moveHandler.isCastlingMove(move)){
//            Move rookMove = moveHandler.getCastlingRookMove(move);
//            updateView(rookMove);
//            board.castle(move, rookMove);
//        }else if(moveHandler.isEnPassanteMove(board, move)){
//            enPassanteUpdate(move);
//            board.enPassante(move);
//        }else {
            board.movePiece(move);
//        }
        System.out.println(board.toString());
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
               getValidMoves(board, userInput.getStart()), this);
    }


    public ArrayList<Square> getValidMoves(Board b, Square s){
        List<Move> validMoves = validMoveHistory.get(validMoveHistory.size()-1);
        return getMovesFromSquare(b, s, validMoves);
    }

    public ArrayList<Square> getMovesFromSquare(Board b, Square s, List<Move> moves){
        ArrayList<Square> end = new ArrayList<>();
        for(Move m : moves){
            if(m.getStart().equals(s)){
                end.add(m.getEnd());
            }
        }
        return end;
    }
//
//    /**
//     *
//     * @param s the square whose piece is to be removed from the view
//     */
//    public void removePieceFromView(Square s){
//        gui.remove(s);
//    }
//
//    /**
//     *
//     * @param m the en passante move to be updated to the board and gui
//     */
//    public void enPassanteUpdate(Move m){
//        Color c = (m.getStart().getColor() == Color.WHITE) ? Color.BLACK : Color.WHITE;
//        Square remove = new Square(m.getStart().getRow(), m.getEnd().getCol(), c);
//        removePieceFromView(remove);
//    }
//
//    /**
//     *
//     * @return whether there is a checkmate on the board
//     */
//    public boolean isCheckMate(){return moveHandler.isCheckMate(board);}
//
//    /**
//     *
//     * @return whether its the user's move
//     */
//    public boolean isUserMove(){return userMove;}
//
//    /**
//     * Starts userMove by setting it to true.
//     */
//    public void startUserMove(){userMove = true;}
//    /**
//     *
//     * ends the user's Move
//     */
//    public void endUserMove(){userMove = false;}
//
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
     */
    public void changeTurn(){
       if(isWhiteMove()) {
           startBlackMove();
       }else{
           startWhiteMove();
       }
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

    /**
     *
     * @return whether a player can promote his pawn.
     */
    public boolean canPromotePawn(){
        return getPromotionSquare() != null;
    }

    /**
     *
     * @return the square where the player can promote. Returns null
     * if the player cannot promote a piece.
     */
    public Square getPromotionSquare(){
        for(int col = 0; col < 8; col++){
            if(getBoard().hasPiece(0, col)){
                Piece p = getBoard().getPiece(0, col);
                if(p.getType() == Type.PAWN && p.getColor() == Color.BLACK){
                    return getBoard().getSquare(0, col);
                }
            }
            if(getBoard().hasPiece(7, col)){
                Piece p = getBoard().getPiece(7, col);
                if(p.getType() == Type.PAWN && p.getColor() == Color.WHITE){
                    return getBoard().getSquare(7, col);
                }
            }
        }
        return null;
    }

    /**
     * Checks if either it is checkmate and the game is over or if
     * a player can promote his pawn to another piece.
     */
    public void processTurn(){
//        if(isCheckMate()){
//            System.out.println("IS CHECKMATE");
//            //lock();
//        }
        if(canPromotePawn()){
            System.out.println("CAN PROMOTE PAWN");
            lock();
            getPromotion();
        }
    }

    /**
     *
     * @param type
     */
    public void promote(Type type){
        if(canPromotePawn()){
            getBoard().promotePawn(getPromotionSquare(), type);
            unlock();
            System.out.println(getBoard().toString());
            gui.removePromotionBox();
        }
    }

    /**
     *
     * @return
     */
    public boolean isRunning(){
        return this.running;
    }

    /**
     *
     */
    public void lock(){
        this.running = false;
    }

    /**
     *
     */
    public void unlock(){
        this.running = true;
    }

    /**
     *
     */
    public void getPromotion(){
        Square s = getPromotionSquare();
        if(s.getPiece().getColor() == Color.WHITE) {
            gui.getPromotion(java.awt.Color.WHITE, this);
        }else{
            gui.getPromotion(java.awt.Color.BLACK, this);
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
}
