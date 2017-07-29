package chess.controller.logic;

import java.util.ArrayList;
import java.util.HashSet;

import chess.Game;
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
 * TODO: MAKE CASTLING CODE NEATER
 * TODO: ADD PAWN ON PAWN EN PASSANTE
 * @see Board
 * @see Move
 */
public class MoveHandler {
    /**
     * The game which the movehandler regulates
     */
    private Game game;

    /**
     * A constructor for MoveHandler objects.
     * @param game the game which is to be regulated
     */
    public MoveHandler(Game game){
        this.game = game;
    }

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
                        if (s.getPiece().getColor() != p.getColor()) {
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

    /**
     * A verified list of moves that can be made by the piece occupying
     * the given square. This takes into consideration checks when determining
     * the legality of moves.
     * @param start the starting square of the piece
     * @param moves the list of unverified moves
     * @param board the board on which the moves are being played
     * @return the verified list of moves
     */
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
                    }else if(concurrentCheck(board)){
                        removeList.add(s);
                    }
                }
            }
        }
        if(start.getPiece().getType() == Type.KING){
            adjacentKingsVerification(start, board, moves, removeList);
            //possible castling
            verifiedMoves.addAll(getVerifiedCastling(start,board));
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
     * @see MoveHandler#getCheckedColors(Board)
     */
    public boolean isCheck(Board board) {
        return getCheckedColors(board).size() != 0;
    }

    /**
     * Returns the color of the checked player
     * @param board the board to be *checked*
     * @return List of the colors of the players in check. Empty List if no
     * player is in check.
     */
    public ArrayList<Color> getCheckedColors(Board board) {
        Square blackKing = board.getBlackKing();
        Square whiteKing = board.getWhiteKing();
        HashSet<Color> checked = new HashSet<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board.getSquare(row, col).isOccupied()) {
                    Color c = board.getPiece(row, col).getColor();
                    ArrayList<Square> moves = iterateMoves(board.getSquare(row, col), board);
                    for (Square s : moves) {
                        if (s.isOccupied()) {
                            if (s.equals(whiteKing)) {
                                if(c == Color.BLACK) {
                                    checked.add(Color.WHITE);
                                }else{
                                    return null;
                                }
                            } else if (s.equals(blackKing)) {
                                if(c == Color.WHITE) {
                                    checked.add(Color.BLACK);
                                }else{
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<>(checked);
    }

    /**
     *
     * @param board
     * @return
     */
    public Color getCheckedColor(Board board){return getCheckedColors(board).get(0);}

    /**
     *
     * @param board
     * @return
     */
    public boolean concurrentCheck(Board board){return getCheckedColors(board).size() == 2;}


    /**
     * Retrieves the list of all possible moves that can be
     * made by a pawn from the given square on the given board.
     * @param start the square which houses the pawn
     * @param board the board on which the game is being played
     * @return the list of all possible moves for the pawn
     */
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

    /**
     * Determines whether a side on this board is in checkmate.
     * @param board the board to be *checked*
     * @return whether there is a checkmate on the board
     */
    public boolean isCheckMate(Board board){
        if(!isCheck(board)) return false;
        Color c = getCheckedColor(board);
        for(int row = 0; row < 7; row++){
            for(int col = 0; col < 7; col++){
                if(board.hasPiece(row, col)) {
                    if(board.getPiece(row, col).getColor() != c) continue;
                    if (getValidMoves(board.getSquare(row, col), board).size() != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param s1
     * @param s2
     * @return
     */
    public boolean areWithinRange(Square s1, Square s2){
        int deltaRow = Math.abs(s1.getRow() - s2.getRow());
        int deltaCol = Math.abs(s1.getCol() - s2.getCol());
        return (deltaRow == 1 && deltaCol == 1) || (deltaCol == 0 && deltaRow == 1) || (deltaCol == 1 && deltaRow == 0);
    }

    /**
     *
     * @param start
     * @param board
     * @param moves
     * @param removeList
     */
    public void adjacentKingsVerification(Square start, Board board, ArrayList<Square> moves, ArrayList<Square> removeList){
        Color c = start.getPiece().getColor();
        if(c == Color.WHITE){
            Square king = board.getBlackKing();
            for(Square s: moves){
                if(areWithinRange(s,king)){
                    removeList.add(s);
                }
            }
        }else{
            Square king = board.getWhiteKing();
            for(Square s: moves) {
                if (areWithinRange(s,king)) {
                    removeList.add(s);
                }
            }
        }
    }
    //TODO: MAKE CASTLING CODE NEATER
    /**
     *
     * @param square the square from which the king will castle
     * @return the list of possible castling squares for that king
     */
    public ArrayList<Square> getCastleSquares(Square square){
        Board board = game.getModel();
        if(!square.isOccupied()) throw new IllegalArgumentException("Square must have king.");
        if(square.getPiece().getType() != Type.KING) throw new IllegalArgumentException("Square must have king.");
        Piece king = square.getPiece();
        ArrayList<Square> castle = new ArrayList<>();
        if(king.getColor() == Color.WHITE){
            if(game.hasString("E1")){
                return castle;
            }else{
                if(!game.hasString("H1")
                        && !board.hasPiece(0,6)
                        && !board.hasPiece(0, 5)){
                    castle.add(board.getSquare(0,6));
                    System.out.println("H1----");
                }
                if(!game.hasString("A1")
                        && !board.getSquare(0,1).isOccupied()
                        && !board.getSquare(0, 2).isOccupied()
                        && !board.getSquare(0,3).isOccupied()){
                    castle.add(board.getSquare(0,2));
                }
            }
        }else{
            if(game.hasString("E8")){
                return castle;
            }else{
                if(!game.hasString("H8")
                        && !board.getSquare(7,6).isOccupied()
                        && !board.getSquare(7, 5).isOccupied()){
                    castle.add(board.getSquare(7,6));
                }
                if(!game.hasString("A8")
                        && !board.getSquare(7,1).isOccupied()
                        && !board.getSquare(7, 2).isOccupied()
                        && !board.getSquare(7,3).isOccupied()){
                    castle.add(board.getSquare(7,2));
                }
            }
        }
        return castle;
    }

    /**
     *
     * @param start the king's starting square for castling
     * @param board the board on which to castle
     * @return the list of verified castling squares for that king
     */
    public ArrayList<Square> getVerifiedCastling(Square start, Board board) {
        ArrayList<Square> moves = getCastleSquares(start);
        Color kingColor = start.getPiece().getColor();
        Board clone = board.clone();
        //if King is in check, castling is not possible. return empty list.
        if(isCheck(board)) {
            if (getCheckedColor(board) == kingColor) {
                return new ArrayList<Square>();
            }
        }
        if (kingColor == Color.WHITE) {
            Square right = board.getSquare(0, 6);
            Square left = board.getSquare(0, 2);
            if (moves.contains(right)) {
                clone.movePiece(0, 4, 0, 6);
                clone.movePiece(0, 7, 0, 5);
                if(isCheck(clone)) {
                    if (getCheckedColor(clone) == kingColor) {
                        moves.remove(right);
                        System.out.println("vro");
                    }
                }
            } else if (moves.contains(left)) {
                clone = board.clone();
                clone.movePiece(0, 4, 0, 2);
                clone.movePiece(0, 0, 0, 3);
                if(isCheck(clone)) {
                    if (getCheckedColor(clone) == kingColor) {
                        moves.remove(left);
                    }
                }
            }
        }else{
            Square right = board.getSquare(7, 6);
            Square left = board.getSquare(7, 2);
            if (moves.contains(right)) {
                clone.movePiece(7, 4, 7, 6);
                clone.movePiece(7, 7, 7, 5);
                if(isCheck(clone)) {
                    if (getCheckedColor(clone) == kingColor) {
                        moves.remove(right);
                    }
                }
            } else if (moves.contains(left)) {
                clone = board.clone();
                clone.movePiece(7, 4, 7, 2);
                clone.movePiece(7, 0, 7, 3);
                if(isCheck(clone)) {
                    if (getCheckedColor(clone) == kingColor) {
                        moves.remove(left);
                    }
                }
            }
        }
        return moves;
    }




}

