package chess.game;

import chess.controller.Controller;
import chess.gui.GUI;
import chess.structure.Board;
import chess.structure.Move;
import chess.structure.Square;

import java.util.ArrayList;

/**
 * Created by Rahul on 7/27/2017.
 */
public class Game {
    /**
     *
     */
    private Board model;
    private Controller controller;
    private GUI view;
    private boolean gameRunning = true;
    private ArrayList<String> moveList = new ArrayList<>();
    private MoveLog log;

    /**
     *
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
     * @return
     */
    public Board getModel() {
        return model;
    }

    /**
     *
     * @param model
     */
    public void setModel(Board model) {
        this.model = model;
    }

    /**
     *
     * @return
     */
    public Controller getController() {
        return controller;
    }

    /**
     *
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     *
     * @return
     */
    public GUI getView() {
        return view;
    }

    /**
     *
     * @param view
     */
    public void setView(GUI view) {
        this.view = view;
    }

    /**
     *
     * @return
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     *
     * @param gameRunning
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getMoveList() {
        return moveList;
    }

    /**
     *
     * @param moveList
     */
    public void setMoveList(ArrayList<String> moveList) {
        this.moveList = moveList;
    }

    /**
     *
     * @param m
     */
    public void writeMove(Move m){
        System.out.println(m.toString());
        this.log.write(m);
    }

    /**
     *
     * @return
     */
    public String toString() {
        String s = "{";
        for (String str : moveList) {
            s += str + ", ";
        }
        s += "}";
        return s;
    }

    /**
     *
     * @param str
     * @return
     */
    public boolean hasString(String str){
        for(String s: moveList){
            String[] split = s.split(" ");
            for(String sp: split){
                if(sp.equals(str)) {
                    System.out.print(sp);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param move the move for which to be searched
     * @return whether that move has been played in this game
     */
    public boolean hasMove(Move move){
        return this.log.containsMove(move);
    }

    /**
     *
     * @param s
     * @return
     */
    public boolean hasSquare(Square s){return this.log.containsSquare(s);}

    /**
     *
     * @return
     */
    public MoveLog getLog() {
        return log;
    }

    public Move getLastMove(){return this.log.getLastMove();}
}
