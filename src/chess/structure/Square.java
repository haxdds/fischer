package chess.structure;

import chess.transform.Translation;

/**
 * Created by Rahul on 6/12/2017.
 */
public class Square {
    private Color color;
    private Piece piece;
    private int row;
    private int column;

    public Square(Color color, int row, int column){
        this.color = color;
        this.row = row;
        this.column = column;
    }

    public Square(int row, int column){
        this.row = row;
        this.column = column;
    }


    public void addPiece(Piece piece){
        this.piece = piece;
    }

    public void removePiece(){
        this.piece = null;
    }

    public Piece getPiece(){return piece;}

    public boolean hasPiece(){
        return piece!=null;
    }

    public Color getColor(){
        return this.color;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Square add(Translation t){
        return new Square(this.getRow() + t.getY(), this.getColumn() + t.getX());
    }

    public boolean inBounds(){
        return getRow() <= 7 && getRow() >= 0 && getColumn() <= 7 && getColumn() >= 0;
    }

    public boolean equals(Square sq){
        return row == sq.getRow() && column == sq.getColumn();
    }

}


