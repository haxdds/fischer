package chess.game;

import chess.controller.Controller;
import chess.gui.GUI;
import chess.structure.Board;
import chess.structure.Move;
import chess.structure.Square;
import chess.structure.Piece;


/**
 * Created by Rahul on 7/27/2017.
 *
 * The Game class represents one chess game modelled by an MVC
 * design pattern. It contains all the necessary components to
 * play chess, such as a Board, Players, Pieces and the rules
 * of the chess.
 * @see Board
 * @see Controller
 * @see GUI
 * @see Piece
 */
public class Game {
    /**
     * A Game is a modelled by an MVC design pattern
     * {@see <a href="https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller">MVC</a>}
     * running is a state boolean which determines whether the game is running
     * log records the moves that have been played in the game
     * @see MoveLog
     */
    private Board model;
    private Controller controller;
    private GUI view;
    private boolean running = true;
    private MoveLog log;

    /**
     * A constructor for Game objects. It takes no parameters.
     */
    public Game(){
        this.model = new Board();
        this.view = new GUI();
        this.controller = new Controller(this, model, view);
        this.log = new MoveLog(this);
        model.setController(controller);
    }

    /**
     *
     * @return the board on which the game is being played. i.e. the model
     * in the MVC design pattern.
     */
    public Board getModel() {
        return model;
    }

    /**
     *
     * @return the controller which regulates the game. i.e. the controller
     * in the MVC design pattern
     */
    public Controller getController() {
        return controller;
    }

    /**
     *
     * @return the gui which displays the game. i.e. the view in the
     * MVC design pattern.
     */
    public GUI getView() {
        return view;
    }


    /**
     *
     * @return whether the game is running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     *
     * @param running the state which running should be set to. running determines
     * whether the game is running.
     */
    public void setRunning(boolean running) {
        this.running = running;
    }


    /**
     * Records a move to the game's MoveLog
     * @param m the move to be recorded to the MoveLog
     * @see MoveLog#write(Move)
     */
    public void writeMove(Move m){
        System.out.println(m.toString());
        this.log.write(m);
    }

    /**
     * TODO: Implement
     * @return a stylized string version of the game
     */
    public String toString() {return null;}


    /**
     *
     * @param move the move for which to be searched
     * @return whether that move has been played in this game
     * @see MoveLog#containsMove(Move)
     */
    public boolean hasMove(Move move){
        return this.log.containsMove(move);
    }

    /**
     *
     * @param s the square to be checked for
     * @return whether that square has been involved in any
     * of the moves played during the game.
     * @see MoveLog#containsSquare(Square)
     *
     */
    public boolean hasSquare(Square s){return this.log.containsSquare(s);}

    /**
     *
     * @return the MoveLog which records all moves played
     * during the game
     * @see MoveLog
     */
    public MoveLog getLog() {
        return log;
    }

    /**
     *
     * @return the last move played in the game
     * @see MoveLog#getLastMove()
     */
    public Move getLastMove(){return this.log.getLastMove();}
}
