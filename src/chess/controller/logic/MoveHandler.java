package chess.controller.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chess.structure.*;

/**
 * Created by Rahul on 7/19/2017.
 *
 * This class handles move requests on boards. MoveHandler takes a move and a board
 * that the move is to be made on, and determines whether the given move is legal on
 * that board.
 * TODO: NEW CLASS FOR CHECKS? BOARD HANDLER? MORE MODULAR.
 * MoveHandler can also determine whether the board is in check and which
 * color is in check.
 * @see Board
 * @see Move
 */
public class MoveHandler {
    /**
     * The central purpose of MoveHandler is take a (Move, Board) as input
     * and return whether that move is legal on that board. This is what
     * isValidMove does. The retrieves the list of valid moves from
     * getValidMoves and checks whether the given move is contained
     * in the list of valid moves.
     * @param move the move to be checked
     * @param board the board on which the move is to be checked
     * @return whether the move is legal
     * @see Board
     * @see Move
     * @see MoveHandler#getValidMoves(Square, Board)
     */
    public boolean isValidMove(Move move, Board board) {
        return getValidMoves(move.getStart(), board).contains(move.getEnd());
    }

    /**
     * Retrieves a list of valid moves that can be made by a piece given a
     * starting square on a board. First a list of all possible moves is generated
     * by calling iterateMoves and then verifies and filters the
     * possible moves for checks.
     * @param start the starting square on the board from which valid moves are
     *              searched for
     * @param board the board on which the moves are searched for
     * @return the list of valid moves that can be made
     * @see MoveHandler#iterateMoves(Square, Board)
     * @see MoveHandler#verifyMoves(Square, ArrayList, Board)
     */
    public ArrayList<Square> getValidMoves(Square start, Board board) {
        ArrayList<Square> moves = iterateMoves(start, board);
        return verifyMoves(start, moves, board);
    }

    /**
     * Retrieves the list of all possible moves (legal and illegal) that can be made
     * from a square by the piece on that square.
     * @param start the square from which
     * @param board
     * @return
     */
    public ArrayList<Square> iterateMoves(Square start, Board board) {
        ArrayList<Square> moves = new ArrayList<>();
        Piece p = board.getPiece(start);
        if(p.getType() == Type.PAWN) return pawnIterate(start, board);
        Group g = p.getType().getGroup();
        int[] signature = null;
        for (Translation t : g.getGroup()) {
            if (start.translate(t).inBounds()) {
                Square s = board.translateSquare(start, t);
                if (!t.isSignature(signature) || p.getType() == Type.KNIGHT) {
                    if (s.isOccupied()) {
                        if (s.getPiece().getColor() != p.getColor()){
                            moves.add(s);
                            signature = t.getSignature();
                        } else {
                            signature = t.getSignature();
                        }
                    } else {
                        moves.add(s);
                    }
                }
            }

        }

        return moves;
    }


    //TODO: MAKE THIS CODE MODULAR!!

    public ArrayList<Square> verifyMoves(Square start, ArrayList<Square> moves, Board board) {
        Color pieceColor = board.getPiece(start).getColor();
        ArrayList<Square> removeList = new ArrayList<>();
        ArrayList<Square> verifiedMoves = new ArrayList<>();
        for (Square s : moves) {
            Board clone = board.clone();
            Move move = new Move(start, s);
            if (isCheck(clone)) {
                Color checkedColor = getCheckedColor(clone);
                if (checkedColor == pieceColor) {
                    clone.movePiece(move);
                    if (isCheck(clone)) {
                        if (getCheckedColor(clone) == pieceColor) {
                            removeList.add(s);
                        }
                    }
                } else {
                    //TODO: WTF
                    //TODO: OPPONENT IN CHECK AND YOUR MOVE?!?
                }
            } else {
                    clone.movePiece(move);
                if (isCheck(clone)) {
                    Color checkedColor = getCheckedColor(clone);
                    if (checkedColor == pieceColor) {
                        removeList.add(s);
                    }
                }
            }
        }
        verifiedMoves.addAll(moves);
        verifiedMoves.removeAll(removeList);
        return verifiedMoves;
    }

    /**
     * Determines whether the board is in check. i.e. checkmate is threatened
     * on the next turn by one of the players. It does this by calling
     * getCheckedColor, which returns the color of the checked player, and then
     * checking if that color is null. A null color means there is no checked player.
     * @param board the board to be *checked*
     * @return whether the given board is in check
     * @see MoveHandler#getCheckedColor(Board)
     */
    public boolean isCheck(Board board) {
        return getCheckedColor(board) != null;
    }

    /**
     * Returns the color of the checked player
     * @param board
     * @return
     */
    public Color getCheckedColor(Board board) {
        Square blackKing = board.getBlackKing();
        Square whiteKing = board.getWhiteKing();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board.getSquare(row, col).isOccupied()) {
                    Color c = board.getPiece(row, col).getColor();
                    ArrayList<Square> moves = iterateMoves(board.getSquare(row, col), board);
                    for (Square s : moves) {
                        if (s.isOccupied()) {
                            if (s.equals(whiteKing)) {
                                if(c == Color.BLACK) {
                                    return Color.WHITE;
                                }else{
                                    return null;
                                }
                            } else if (s.equals(blackKing)) {
                                if(c == Color.WHITE) {
                                    return Color.BLACK;
                                }else{
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    public ArrayList<Square> pawnIterate(Square start, Board board){
        Color c = start.getPiece().getColor();
        ArrayList<Square> moves = new ArrayList<>();
        if(c == Color.BLACK){
            for(Translation t: Type.PAWN.getGroup().getGroup()){
                if(t.getY() >  0) continue;
                if(start.translate(t).inBounds()){
                    Square s = board.translateSquare(start,t);
                    if((t.getY() == -1 && t.getX() == -1) || (t.getY() == -1 && t.getX() == 1)){
                        if(!s.isOccupied()) continue;
                        if(s.getPiece().getColor() == c) continue;
                        moves.add(s);
                    }else if(t.getY() == -2){
                        if(start.getRow() == 6){
                            if(!s.isOccupied()) {
                                moves.add(s);
                            }
                        }
                    }else{
                        if(!s.isOccupied()){
                            moves.add(s);
                        }
                    }
                }
            }
        }else{
            for(Translation t: Type.PAWN.getGroup().getGroup()){
                if(t.getY() <  0) continue;
                if(start.translate(t).inBounds()){
                    Square s = board.translateSquare(start,t);
                    if((t.getY() == 1 && t.getX() == -1) || (t.getY() == 1 && t.getX() == 1)){
                        if(!s.isOccupied()) continue;
                        if(s.getPiece().getColor() == c) continue;
                        moves.add(s);
                    }else if(t.getY() == 2){
                        if(start.getRow() == 1){
                            if(!s.isOccupied()) {
                                moves.add(s);
                            }
                        }
                    }else{
                        if(!s.isOccupied()){
                            moves.add(s);
                        }
                    }
                }
            }
        }
        return moves;
    }

}