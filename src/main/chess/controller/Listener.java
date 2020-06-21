package main.chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.chess.structure.Square;
import main.chess.gui.GUI;

/**
 * Created by Rahul on 7/20/2017.
 *
 * This class handles all user input into the game through the use of
 * the ActionListener interface. The listener objects will be
 * attached to the GUI units which represent the squares on
 * the board(JBUTTON, ETC).
 * @see GUI
 * @see Controller
 */
public class Listener implements ActionListener {

    /**
     * Properties of a Listener object:
     * Square: square on the board that user input
     * is retrieved from.
     * Controller: controller object which user input
     * data is sent to.
     * @see Square
     * @see Controller
     */
    private Square square;
    private Controller controller;

    /**
     * A constructor for Listener objects.
     * @param square the square to the object will retrieve input from
     * @param controller the controller where input data is sent
     * @see Square
     * @see Controller
     */
    public Listener(Square square, Controller controller){
        this.square = square;
        this.controller = controller;
    }

    /**
     * Implemented from the ActionListener Interface
     * @param e an ActionEvent
     * @see ActionEvent
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        controller.pushUserInput(square);
    }

}
