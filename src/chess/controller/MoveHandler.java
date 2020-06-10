package chess.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    private Controller controller;
    private MoveGenerator generator;
    private MoveValidator validator;


    /**
     * A constructor for MoveHandler objects.
     * @param controller the controller which uses this move handler.
     */
    public MoveHandler(Controller controller){
        this.controller = controller;
        generator = new MoveGenerator();
        validator = new MoveValidator();
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
     * @see MoveGenerator#generateMoves(Square, Board)
     * @see MoveHandler#verifyMoves(Square, ArrayList, Board)
     */
    public ArrayList<Square> getValidMoves(Square start, Board board) {
        ArrayList<Square> moves = generator.generateMoves(start, board);
        return verifyMoves(start, moves, board);
    }



    //TODO: MAKE THIS CODE MODULAR!! -- Done 7/30/17

    /**
     * A verified list of moves that can be made by the piece occupying
     * the given square. This takes into consideration checks when determining
     * the legality of moves.
     * @param start the starting square of the piece
     * @param moves the list of unverified moves
     * @param board the board on which the moves are being played
     * @return the verified list of moves
     * @see MoveHandler#verifyForChecks(Square, Board, ArrayList)
     * @see MoveHandler#verifyKingMoves(Square, Board, ArrayList)
     */
    public ArrayList<Square> verifyMoves(Square start, ArrayList<Square> moves, Board board) {
        verifyForChecks(start, board, moves);
        verifyKingMoves(start, board, moves);
        return moves;
    }

    /**
     * Filters the list of squares the piece can move to based on checks
     * @param start the square the piece is starting on
     * @param board the board the game is being played on
     * @param moves the list of squares the piece can move to
     * @see MoveHandler#verifyForCheck(Board, Move)
     */
    public void verifyForChecks(Square start, Board board, ArrayList<Square> moves){
        ArrayList<Square> removeList = new ArrayList<>();
        for (Square s : moves) {
            Board clone = board.clone();
            Move move = new Move(start, s);
            if(!verifyForCheck(clone, move)){
                removeList.add(s);
            }
        }
        moves.removeAll(removeList);
    }

    /**
     * Verifies whether a move is consistent with check legality
     * @param board the board on which the move is being made
     * @param move the move being made
     * @return whether the move is legal with respect to checks.
     * @see MoveHandler#verifyCurrentCheck(Board, Move)
     * @see MoveHandler#verifyFutureChecks(Board, Move)
     */
    public boolean verifyForCheck(Board board, Move move){
        if (isCheck(board)) {
            if(!verifyCurrentCheck(board, move)) {
                return false;
            }
        } else {
            if(!verifyFutureChecks(board, move)){
                return false;
            }
        }
        return true;
    }
    /**
     * Filters the list of squares a king can move to based on checks
     * @param start the square the king is starting on
     * @param board the board the game is being played on
     * @param moves the list of squares the king can move to
     * @see MoveHandler#adjacentKingsVerification(Square, Board, ArrayList)
     * @see MoveHandler#getVerifiedCastling(Square, Board)
     */
    public void verifyKingMoves(Square start, Board board, ArrayList<Square> moves){
        if (start.getPiece().getType() == Type.KING) {
            adjacentKingsVerification(start, board, moves);
            moves.addAll(getVerifiedCastling(start, board));
        }else{
            return;
        }
    }


    /**
     * Verifies that the move deals with the current check on the board.
     * That is the move removes the check from the board.
     * @param board the board on which the move is being made
     * @param move the move to be verified
     * @return whether that move deals with the check
     */
    public boolean verifyCurrentCheck(Board board, Move move){
        Board clone = board.clone();
        Color c = getCheckedColor(clone);
        Color pieceColor = move.getStart().getPiece().getColor();

        if(c == pieceColor) {
//            if(move.getEnd().isOccupied()) {
//                if (move.getStart().getPiece().getType() == Type.PAWN
//                        && move.getEnd().getPiece().getType() == Type.QUEEN) {
//                    System.out.println("BEFORE:");
//                    System.out.println(clone.toString());
//                    clone.movePiece(move);
//                    System.out.println("AFTER:");
//                    System.out.println(clone.toString());
//                    System.out.println("is Check: " + isCheck(clone));
//
//                }
//            }else{
//                clone.movePiece(move);
//            }
            clone.movePiece(move);
            if (isCheck(clone)) {
                if (getCheckedColor(clone) == pieceColor) {
                    return false;
                } else if(concurrentCheck(clone)){
                    return false;
                }
                else {
                    return true;
                }
            } else {
                return true;
            }
        }else{
            return true;
        }
    }

    /**
     * Verifies that the move being made is consistent with checks.
     * That is the move doesn't create a check to one's own side.
     * @param board the board on which the move is being made
     * @param move the move to be verified
     * @return whether that move is consistent with future checks
     */
    public boolean verifyFutureChecks(Board board, Move move){
        Board clone = board.clone();
        clone.movePiece(move);
        if (isCheck(clone)) {
            Color checkedColor = getCheckedColor(clone);
            if (checkedColor == move.getStart().getPiece().getColor()) {
                return false;
            }else if(concurrentCheck(clone)){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
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
     * TODO: WTF IS UP WITH THIS METHOD
     * TODO: FIXED THIS SHIT FUCK YEAH 7-31-17
     * TODO: LOL FIXED IT FOR REAL THIS TIME {@link Board#clone()}
     * Returns the color of the checked player
     * @param board the board to be *checked*
     * @return List of the colors of the players in check. Empty List if no
     * player is in check.
     */
    public ArrayList<Color> getCheckedColors(Board board) {
        Square blackKing = board.getBlackKing();
        Square whiteKing = board.getWhiteKing();
        HashSet<Color> checked = new HashSet<>();
        for(Piece p: board.getWhitePieceSet()){
            if(p.getType() == Type.KING) continue;
            if(generator.generateMoves(board.mapPiece(p), board).contains(blackKing)){
                checked.add(Color.BLACK);
            }
        }
        for(Piece p: board.getBlackPieceSet()){
            if(p.getType() == Type.KING) continue;
            if(generator.generateMoves(board.mapPiece(p), board).contains(whiteKing)){
                checked.add(Color.WHITE);
            }
        }
        return new ArrayList<>(checked);
    }

    /**
     *
     * @param board the board being *checked* for checks
     * @return the color in check. Returns the first color
     * to be identified {@code checkedColors.get(0)}
     * @see MoveHandler#getCheckedColors(Board)
     */
    public Color getCheckedColor(Board board){
        ArrayList<Color> checkedColors = getCheckedColors(board);
        return (checkedColors.size() == 0) ? null : checkedColors.get(0);
    }

    /**
     *
     * @param board the board being *checked*
     * @return whether both players are in check simultaneously
     * @see MoveHandler#getCheckedColors(Board)
     */
    public boolean concurrentCheck(Board board){return getCheckedColors(board).size() == 2;}


    /**
     * Determines whether a side on this board is in checkmate.
     * @param board the board to be *checked*
     * @return whether there is a checkmate on the board
     */
    public boolean isCheckMate(Board board){
        return getCheckMatedColor(board) != null;
    }

    /**
     * The loser of the game on a board
     * @param board the board to be *checked*
     * @return the color of the side that is checkmated. Returns null
     * if no side is checkmated.
     */
    public Color getCheckMatedColor(Board board){
        if(!isCheck(board)) return null;
        Color c = getCheckedColor(board);
        for(Piece p : board.getPieceSet()){
            if(p.getColor() != c) continue;
            if(getValidMoves(board.mapPiece(p), board).size() != 0){
                return null;
            }
        }
        return c;
    }

    /**
     *
     * @param s1 square being compared
     * @param s2 the other square being compared to
     * @return whether the given squares are 1 unit apart
     */
    public boolean areWithinRange(Square s1, Square s2){
        int deltaRow = Math.abs(s1.getRow() - s2.getRow());
        int deltaCol = Math.abs(s1.getCol() - s2.getCol());
        return (deltaRow == 1 && deltaCol == 1) || (deltaCol == 0 && deltaRow == 1) || (deltaCol == 1 && deltaRow == 0);
    }

    /**
     * filters a list of squares that can be moved to by the king
     * for squares that are adjacent to the opponent king.
     * @param start the square the king is on
     * @param board the board the game is being played on
     * @param moves the list of squares that can be moved to
     */
    public void adjacentKingsVerification(Square start, Board board, ArrayList<Square> moves){
        if(start.getPiece().getColor() == Color.WHITE){
            removeAdjacentSquares(moves, board.getBlackKing());
        }else{
            removeAdjacentSquares(moves, board.getWhiteKing());
        }
    }

    /**
     * removes all squares from a list that are 1 unit from the key square
     * @param squares the list of squares being checked
     * @param key the square being checked against
     * @see MoveHandler#areWithinRange(Square, Square)
     */
    public void removeAdjacentSquares(ArrayList<Square> squares, Square key){
        ArrayList<Square> removeList = new ArrayList<>();
        for(Square s: squares){
            if(areWithinRange(s,key)){
                removeList.add(s);
            }
        }
    }

    //TODO: MAKE CASTLING CODE NEATER -- progress 7/31/17
    /**
     *
     * @param square the square from which the king will castle
     * @return the list of possible castling squares for that king
     * @see MoveHandler#addCastlingSquares(int, ArrayList)
     */
    public ArrayList<Square> getCastleSquares(Square square){
        if(!square.isOccupied()) throw new IllegalArgumentException("Square must have king.");
        if(square.getPiece().getType() != Type.KING) throw new IllegalArgumentException("Square must have king.");
        Piece king = square.getPiece();
        ArrayList<Square> castle = new ArrayList<>();
        if(king.getColor() == Color.WHITE){
            addCastlingSquares(0, castle);
        }else{
            addCastlingSquares(7, castle);
        }
        return castle;
    }

    /**
     *
     * @param row the row the king is on
     * @param castle the list of squares that the castling squares
     *               will be added to
     */
    public void addCastlingSquares(int row, ArrayList<Square> castle){
        Board board = controller.getBoard();
        Square E = board.getSquare(row, 4);
        Square H = board.getSquare(row, 7);
        Square A = board.getSquare(row, 0);

        if(controller.hasSquare(E)){
            //king has been moved
            return;
        }else{
            if(!controller.hasSquare(H)
                    && !board.getSquare(row,6).isOccupied()
                    && !board.getSquare(row, 5).isOccupied()){
                castle.add(board.getSquare(row,6));
            }
            if(!controller.hasSquare(A)
                    && !board.getSquare(row,1).isOccupied()
                    && !board.getSquare(row, 2).isOccupied()
                    && !board.getSquare(row,3).isOccupied()){
                castle.add(board.getSquare(row,2));
            }
        }
    }

    //TODO: MORE CHECK REQUIREMENTS
    /**
     *
     * @param start the king's starting square for castling
     * @param board the board on which to castle
     * @return the list of verified castling squares for that king
     */
    public ArrayList<Square> getVerifiedCastling(Square start, Board board) {
        ArrayList<Square> moves = getCastleSquares(start);
        Color kingColor = start.getPiece().getColor();
        //if King is in check, castling is not possible. return empty list.
        if(isCheck(board)) {
            if (getCheckedColor(board) == kingColor) {
                return new ArrayList<>();
            }
        }
        if (kingColor == Color.WHITE) {
            verifyCastlingSquares(moves, board, 0);
        }else{
            verifyCastlingSquares(moves, board, 7);
        }
        return moves;
    }

    /**
     * Filters a list of castling moves for checks
     * @param moves the list of squares that can be castled to
     * @param board the board the game is being played on
     * @param row the row the king is on
     */
    public void verifyCastlingSquares(ArrayList<Square> moves, Board board, int row){
        Square right = board.getSquare(row, 6);
        Square left = board.getSquare(row, 2);
        Board clone = board.clone();
        if (moves.contains(right)) {
            clone.movePiece(row, 4, row, 6);
            if(isCheck(clone)) {
                if (getCheckedColor(clone) == board.getPiece(row, 4).getColor()) {
                    moves.remove(right);
                }
            }
            clone = board.clone();
            clone.movePiece(row, 4, row, 5);
            if(isCheck(clone)) {
                if (getCheckedColor(clone) == board.getPiece(row, 4).getColor()) {
                    moves.remove(right);
                }
            }
        } else if (moves.contains(left)) {
            clone = board.clone();
            clone.movePiece(row, 4, row, 2);
            if(isCheck(clone)) {
                if (getCheckedColor(clone) == board.getPiece(row, 4).getColor()) {
                    moves.remove(left);
                }
            }
            clone = board.clone();
            clone.movePiece(row, 4, row, 3);
            if(isCheck(clone)) {
                if (getCheckedColor(clone) == board.getPiece(row, 4).getColor()) {
                    moves.remove(left);
                }
            }
        }
    }

    /**
     * Determines if a move is a castling move
     * @param m the move to be checked
     * @return whether the move is a castling move
     */
    public boolean isCastlingMove(Move m){
        if(m.getStart().getPiece().getType() != Type.KING) return false;
        int deltaRow = Math.abs(m.getStart().getCol() - m.getEnd().getCol());
        if(deltaRow != 2) return false;
        return true;
    }

    /**
     *
     * @param m the move being checked for en passante
     * @return whether the given move is an en passante move
     */
    public boolean isEnPassanteMove(Move m){
        Move lastMove = controller.getLastMove();
        if(lastMove == null) return false;
        if(lastMove.getEnd().getPiece().getType() != Type.PAWN) return false;
        if(lastMove.getEnd().getPiece().getColor() == m.getStart().getPiece().getColor()) return false;
        if(m.getStart().getPiece().getType() != Type.PAWN) return false;
        if(m.getEnd().getCol() != lastMove.getEnd().getCol()) return false;

        if(m.getStart().getPiece().getColor() == Color.WHITE){
            if(lastMove.getStart().getRow() != 6 || lastMove.getEnd().getRow() != 4) return false;
            if(m.getStart().getRow() != 4) return false;
            if(m.getStart().getCol() == m.getEnd().getCol()) return false;
            return true;
        }else{
            if(lastMove.getStart().getRow() != 1 || lastMove.getEnd().getRow() != 3) return false;
            if(m.getStart().getRow() != 3) return false;
            if(m.getStart().getCol() == m.getEnd().getCol()) return false;
            return true;
        }
    }

    /**
     *
     * @param start the square being started on
     * @param moves the list of squares that can be moved to
     *             being checked
     * @return the square that can be moved to by en passante. Null
     * is returned if no moves are en passante.
     * @see MoveHandler#isEnPassanteMove(Move)
     */
    public Square hasEnPassanteMove(Square start, ArrayList<Square> moves){
        for(Square s: moves){
            if(isEnPassanteMove(new Move(start, s))) return s;
        }
        return null;
    }




    /**
     * Retrieves the corresponding rook move for castling for
     * a given king castling move.
     * @param start the starting position of king
     * @param end the ending position of castled king
     * @return the corresponding rook move for castling
     */
    public Move getCastlingRookMove(Square start, Square end){
        if(end.getCol() == 6){
            Square s1 = new Square(start.getRow(), 7, null);
            Square s2 = new Square(start.getRow(), 5, null);
            Move rookMove = new Move(s1, s2);
            return rookMove;
        }else if(end.getCol() == 2){
            Square s1 = new Square(start.getRow(), 0, null);
            Square s2 = new Square(start.getRow(), 3, null);
            Move rookMove = new Move(s1, s2);
            return rookMove;
        }
        return null;
    }

    /**
     *
     * @param m the move which castles the king
     * @return the corresponding rook move for castling
     * @see MoveHandler#getCastlingRookMove(Square, Square)
     */
    public Move getCastlingRookMove(Move m){
        return getCastlingRookMove(m.getStart(), m.getEnd());
    }






}

