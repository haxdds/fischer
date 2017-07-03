package chess.structure;

import java.awt.*;

/**
 * Created by Rahul on 6/12/2017.
 */
public class Piece {
    private Type type;
    private Color color;
    private Square square;

    Piece(Type type, Color color){
        this.type = type;
        this.color = color;
    }

    public Type getType(){
        return type;
    }

    public Color getColor(){
        return color;
    }

    public boolean equals(Piece piece){
        return piece.type == this.type && piece.getColor() == this.getColor();
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }
}

