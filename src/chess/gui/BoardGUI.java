package chess.gui;

import chess.structure.Move;

/**
 * Board GUI Interface, allows multiple GUI implementations to exist *
 */
public interface BoardGUI {

    /**
     * Updates the board with a given move
     * @param m
     */
    void update(Move m);

    void setupNewGame();
}
