package main.chess.controller;

import main.chess.structure.*;

import java.util.ArrayList;

/**
 *  Created by Rahul: 06/07/2020
 *
 *  The MoveGenerator class's goal is to create a generate a list of
 *  squares that a piece on a given square can move to. The generated moves
 *  are not validated for checks and other criterion.
 *
 */

public class MoveGenerator {




    /**
     * Retrieves the list of all possible moves (legal and illegal) that can be made
     * from a square by the piece on that square.
     * @param start the square the piece is starting from
     * @param board the board the game is being played on
     * @return the list of possible squares that the piece can move to
     */
    public ArrayList<Square> generateMoves(Board board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        Piece p = start.getPiece();
        if(p.getType() == Type.PAWN) return pawnIterate(board, start);
        Group g = p.getType().getGroup();
        int[] signature = {0,0};
        for (Translation t : g) {
            if(checkTranslation(board, t, start,signature)){
                moves.add(board.translateSquare(start ,t));
            }
        }
        return moves;
    }




    /**
     *
     * @param t the translation being checked
     * @param start the square the piece is starting from
     * @param board the board the game is being played on
     * @param signature the signature of the translation
     * @return whether that translation is valid on that board
     * @see Translation#getSignature()
     */
    public boolean checkTranslation(Board board, Translation t, Square start,int[] signature) {
        if (start.translate(t).inBounds()) {
            Square s = board.translateSquare(start, t);
            if (!t.isSignature(signature) || start.getPiece().getType() == Type.KNIGHT) {
                if (s.isOccupied()) {
                    signature[0] = t.getSignature()[0];
                    signature[1] = t.getSignature()[1];
                    if (s.getPiece().getColor() != start.getPiece().getColor()) {
                        return true;
                    }

                } else {
                    return true;
                }
            }
        }
        return false;
    }



    /**
     *
     * @param start the square the pawn is starting on
     * @param board the board the game is being played on
     * @param startingRow the starting row of the pawn at the
     *                    beginning of the game
     * @param y the direction the pawn is supposed to move in
     *          (1 for white and -1 for black)
     * @return the list of squares the pawn can move to
     * @see MoveGenerator#checkPawnTranslation(Translation, Square, Board, int, int)
     * @see MoveGenerator#getEnPassanteMoves(Board, Square)
     */
    public ArrayList<Square> pawnIterate(Board board, Square start,int startingRow, int y){
        ArrayList<Square> moves = new ArrayList<>();
        for (Translation t : Type.PAWN.getGroup()) {
            if(checkPawnTranslation(t, start, board, y, startingRow)){
                moves.add(board.translateSquare(start, t));
            }
        }

        moves.addAll(getEnPassanteMoves(board, start));

        return moves;
    }

    /**
     * Retrieves the list of all possible moves that can be
     * made by a pawn from the given square on the given board.
     * @param start the square which houses the pawn
     * @param board the board on which the game is being played
     * @return the list of all possible moves for the pawn
     */
    public ArrayList<Square> pawnIterate(Board board, Square start) {
        if(start.getPiece().getColor() == Color.BLACK){
            return pawnIterate(board, start, 6,-1);
        }else{
            return pawnIterate(board, start, 1, 1);
        }
    }





    /**
     * TODO: MAKE THIS PAWN TRANSLATION CHECKING METHOD EASIER TO UNDERSTAND!
     */

    /**
     *
     * @param t the translation being checked
     * @param start the square the pawn is starting from
     * @param board the board the game is being played on
     * @param y the direction the pawn is supposed to move in (1 for white
     *          and -1 for black)
     * @param startingRow the row that the pawn is placed at the beginning of the
     *                    game
     * @return whether that translation is valid for the pawn
     */
    public boolean checkPawnTranslation(Translation t, Square start, Board board, int y, int startingRow){
        if (t.getY() * y < 0) return false; //if move is in opposite direction
        if (start.translate(t).inBounds()) {
            Square s = board.translateSquare(start, t);
            if ((t.getY() == y && t.getX() == -1) || (t.getY() == y && t.getX() == 1)) {
                if (!s.isOccupied()) return false;
                if (s.getPiece().getColor() == start.getPiece().getColor()) return false;
                return true;
            } else if (t.getY() == (2 * y)) {
                if (start.getRow() == startingRow) {
                    if (!s.isOccupied()) {
                        return true;
                    }
                }
            } else {
                if (!s.isOccupied()) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     *
     * @param row the row on which the pawn is on
     * @param y the direction the pawn moves in (1 for white, -1 for black)
     * @return whether the pawn can move en passante
     * @see Board#getLastMove()
     */
    public boolean canEnPassante(int col, int row, int y, Move last){
        if(row != 4 && row != 3) return false;
        if(row == 4 && y != 1) return false;
        if(row == 3 && y != -1) return false;
        //System.out.println("last: " + last);
        //System.out.println("piece: " + last.getStart().getPiece().toString());
        if(last.getEnd().getPiece().getType() != Type.PAWN) return false;
        int deltaCol =  Math.abs(col - last.getStart().getCol());
        if(deltaCol > 1) return false;
        if(deltaCol == 0) return false;
        if(row == 4){
            if(last.getEnd().getPiece().getColor() == Color.WHITE) return false;
            if(last.getEnd() == null) return false;
            if(last.getStart().getRow() != 6 && last.getEnd().getRow() != 4) return false;
        }
        if(row == 3){
            if(last.getEnd().getPiece().getColor() == Color.BLACK) return false;
            if(last.getEnd() == null) return false;
            if(last.getStart().getRow() != 1 && last.getEnd().getRow() != 3) return false;
        }
        return true;
    }

    /**
     *
     * @param start the square which the pawn is on
     * @param board the board the game is being played on
     * @return the square the pawn can en passante to. Null is returned
     * if the pawn cannot en passante.
     * @see MoveGenerator#canEnPassante(int, int, int, Move)
     */
    public ArrayList<Square> getEnPassanteMoves(Board board, Square start){
        ArrayList<Square> enpassantMoves = new ArrayList<>();
        int row = start.getRow();
        int y;
        if(start.getPiece().getColor() == Color.WHITE){
            y = 1;
        }else{
            y = -1;
        }
        Move last = board.getLastMove();
        System.out.println(last + "\n=====" + board);
        if(canEnPassante(start.getCol(), row, y, last)){
            if(row == 4){
                enpassantMoves.add(board.getSquare(5, last.getEnd().getCol()));
            }
            if(row == 3){
                enpassantMoves.add(board.getSquare(2, last.getEnd().getCol()));
            }
        }
        return enpassantMoves;
    }

}
