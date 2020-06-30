package main.chess.structure;

import main.chess.controller.Controller;

import java.util.HashMap;

/**
 * Created by Rahul on 7/19/2017.
 * <p>
 * This class represents a chess board. A chess board is composed
 * of an array of Squares which can house pieces. These pieces move
 * around the board from square to square. The board, along with
 * the set of pieces define the chess.structure of what is needed to
 * play a game of chess.
 *
 * TODO: KEEP TRACK OF MAPS OR GENERATE THEM??
 *
 * @see Square
 * @see Piece
 */
public class Board {

    /**
     * Properties of a Board:
     * A board is composed of an 8 x 8 array of Squares.
     * Pieces occupy squares on the board
     * whitePieceSet is a list of all white colored pieces on the board.
     * blackPieceSet is a list of all black colored pieces on the board.
     * TODO: {The game field links the board object to a game.}
     * TODO: IS LINKING NECESSARY? SEE CASTLING
     * @see Controller
     * @see Square
     * @see Piece
     */

    private Square[][] board = new Square[8][8];
    private PieceSet whitePieceSet = new PieceSet();
    private PieceSet blackPieceSet = new PieceSet();

    private MoveLog log = new MoveLog();

    /**
     * A constructor for a board. It takes no arguments.
     * The setUpBoard() method is called on construction.
     *
     * @see Board#InitializeBoard()
     */
    public Board() {InitializeBoard();}

    /**
     * Initializes the squares on the board and places the pieces.
     *
     * @see Board#setUpSquares()
     * @see Board#setPieces()
     */
    public void InitializeBoard() {
        setUpSquares();
        setPieces();
    }

    /**
     * Initializes the square on the board by creating
     * appropriate square objects and placing adding them to the
     * board array.
     *
     * @see Board#board
     */
    public void setUpSquares() {
        for (int row = 0; row <= 7; row++) {
            for (int column = 0; column <= 7; column++) {
                if ((row + column) % 2 == 0) {
                    Square sq = new Square(row, column, Color.BLACK);
                    board[row][column] = sq;
                } else {
                    Square sq = new Square(row, column, Color.WHITE);
                    board[row][column] = sq;
                }
            }
        }
    }

    /**
     * Sets square on board with the given square.
     *
     * @param s the square that will replace the old square.
     */
    public void setSquare(Square s) {
        getBoard()[s.getRow()][s.getCol()] = s;
    }

    /**
     * Creates appropriate pieces for each color and places them
     * in both the appropriate squares on the board and the list of
     * pieces.
     *
     * @see Board#whitePieceSet
     * @see Board#blackPieceSet
     */
    public void setPieces() {
        Piece wpawn = new Piece(Type.PAWN, Color.WHITE);
        Piece wknight = new Piece(Type.KNIGHT, Color.WHITE);
        Piece wbishop = new Piece(Type.BISHOP, Color.WHITE);
        Piece wrook = new Piece(Type.ROOK, Color.WHITE);
        Piece wqueen = new Piece(Type.QUEEN, Color.WHITE);
        Piece wking = new Piece(Type.KING, Color.WHITE);

        Piece bpawn = new Piece(Type.PAWN, Color.BLACK);
        Piece bknight = new Piece(Type.KNIGHT, Color.BLACK);
        Piece bbishop = new Piece(Type.BISHOP, Color.BLACK);
        Piece brook = new Piece(Type.ROOK, Color.BLACK);
        Piece bqueen = new Piece(Type.QUEEN, Color.BLACK);
        Piece bking = new Piece(Type.KING, Color.BLACK);

        for (int column = 0; column < 8; column++) {
            addPiece(1, column, wpawn.clone());
            addPiece(6, column, bpawn.clone());
        }

        addPiece(0, 0, wrook.clone());
        addPiece(0, 1, wknight.clone());
        addPiece(0, 2, wbishop.clone());
        addPiece(0, 3, wqueen.clone());
        addPiece(0, 4, wking.clone());
        addPiece(0, 5, wbishop.clone());
        addPiece(0, 6, wknight.clone());
        addPiece(0, 7, wrook.clone());

        addPiece(7, 0, brook.clone());
        addPiece(7, 1, bknight.clone());
        addPiece(7, 2, bbishop.clone());
        addPiece(7, 3, bqueen.clone());
        addPiece(7, 4, bking.clone());
        addPiece(7, 5, bbishop.clone());
        addPiece(7, 6, bknight.clone());
        addPiece(7, 7, brook.clone());

    }

    /**
     * Moves the piece occupying the starting row and column
     * on the board to the ending row and column.
     * An IllegalArgumentException is thrown if the square on the starting
     * row and column is not occupied by a piece.
     *
     * @param startRow starting row
     * @param startCol starting column
     * @param endRow   ending row
     * @param endCol   ending column
     * @see Board#addPiece(int, int, Piece)
     * @see Board#removePiece(int, int)
     */
    public void movePiece(int startRow, int startCol, int endRow, int endCol) {
        if(getSquare(endRow, endCol).isOccupied()){
            removePiece(endRow, endCol);
        }
        Piece p = getPiece(startRow, startCol);
        removePiece(startRow, startCol);
        addPiece(endRow, endCol, p);
    }

    /**
     * Moves piece from starting square to ending square.
     *
     * @param start starting square
     * @param end   ending square
     * @see Board#movePiece(int, int, int, int)
     */
    public void movePiece(Square start, Square end) {
        movePiece(start.getRow(), start.getCol(), end.getRow(), end.getCol());
    }

    /**
     * Moves piece according to specifications of the input move.
     *
     * @param m the move to be made
     * @see Move
     * @see Board#movePiece(int, int, int, int)
     */
    public void movePiece(Move m) {
        movePiece(m.getStart(), m.getEnd());
        writeMove(m);
    }

    /**
     *
     * @param m
     */
    public void movePiece(String m){
        Square start = getSquare(m.substring(0, 2));
        Square end = getSquare(m.substring(2));
        movePiece(new Move(start, end));
    }


    /**
     * Adds a given piece to the square on the board with the given coordinates.
     * This method throws an IllegalArgumentException if there is already a piece
     * on the square with the given coordinates.
     *
     * @param row the row coordinate of the square where the piece is to be added.
     * @param col the column coordinate of the square where the piece is to be added.
     * @param p   the piece to be added.
     * @see Square#addPiece(Piece)
     */
    public void addPiece(int row, int col, Piece p) {
        addToSet(p, getSquare(row, col));
        getSquare(row, col).addPiece(p);
    }


    /**
     * Removes the piece that occupies the square on the board with the given coordinates.
     * This method throws an IllegalArgumentException if the square with
     * the given coordinates is not occupied by a piece.
     *
     * @param row the row coordinate of the square whose piece is to be removed.
     * @param col the column coordinate of the square whose piece is to be removed.
     * @see Square#removePiece()
     */
    public void removePiece(int row, int col) {
        removeFromSet(getPiece(row,col), getSquare(row, col));
        getSquare(row, col).removePiece();
    }


    /**
     * Returns the piece which occupies the square on the board with the given coordinates.
     *
     * @param row the row coordinate of the square whose piece is to be retrieved.
     * @param col the column coordinate of the square whose piece is to be retrieved.
     * @return the piece on that square.
     * @see Square#getPiece()
     */
    public Piece getPiece(int row, int col) {
        return getSquare(row, col).getPiece();
    }


    /**
     * Determines whether the square with the given coordinates is occupied
     * by a piece.
     *
     * @param row the row coordinate of the square to be checked.
     * @param col the column coordinate of the square to be checked.
     * @return whether the square with the given coordinates has a piece.
     * @see Square#isOccupied()
     */
    public boolean hasPiece(int row, int col) {
        return getSquare(row, col).isOccupied();
    }


    /**
     * @return the 2-dimensional square array which defines the board.
     */
    public Square[][] getBoard() {
        return this.board;
    }

    /**
     * Retrieves the square on the board with give coordinates.
     *
     * @param row the row coordinate of the square to be retrieved.
     * @param col the column coordinate of the square to be retrieved.
     * @return the square on the board with the given coordinates.
     * @see Board#getBoard()
     */
    public Square getSquare(int row, int col) {
        return getBoard()[row][col];
    }

    /**
     *
     * @param coord the string coordinates of square
     * @return the square with given coordinates
     */
    public Square getSquare(String coord){
        int col = (coord.charAt(0) - 'A');
        int row = Integer.parseInt(String.valueOf(coord.charAt(1))) - 1;
        return getSquare(row, col);
    }

    /**
     * Retrieves the square on the board given a square
     *
     * @param s a square whose equivalent on the board is to be retrieved
     * @return the equivalent square on the board
     */
    public Square getSquare(Square s) {
        return getSquare(s.getRow(), s.getCol());
    }

    /**
     * replaces a piece on the board with another
     * @param row the row of the piece to be replaced
     * @param col the col of the piece to be replaced
     * @param p the piece which will replace the piece on those
     *          coordinates
     * @throws IllegalArgumentException
     */
    public void replacePiece(int row, int col, Piece p){
        if(!hasPiece(row, col)) throw new IllegalArgumentException("Square is not occupied; nothing to replace.");
        removePiece(row, col);
        addPiece(row, col, p);
    }

    /**
     * replaces a piece on the board with another
     * @param s the square that the piece to be replaced occupies
     * @param p the piece which will replace the piece on that square
     * @see #replacePiece(int, int, Piece)
     */
    public void replacePiece(Square s, Piece p){
        replacePiece(s.getRow(), s.getCol(), p);
    }

    /**
     *
     * @param row the row of pawn to be promoted
     * @param col the col of the pawn to be promoted
     * @param type the type of piece the pawn is to be
     *             promoted to
     * @throws IllegalArgumentException
     */
    public void promotePawn(int row, int col, Type type){
        if(!hasPiece(row, col))
            throw new IllegalArgumentException("No piece occupies that location");
        if(getPiece(row, col).getType() != Type.PAWN)
            throw new IllegalArgumentException("Only pawns maybe promoted");
        if(type == Type.KING || type == Type.PAWN)
            throw new IllegalArgumentException("Promoted types maybe only be: Queen, Rook, Bishop or Knight");
        Color promotingColor = getPiece(row, col).getColor();
        Piece promotedPiece = new Piece(type, promotingColor);
        replacePiece(row, col, promotedPiece);
    }

    /**
     *
     * @param s the square which the pawn to be promoted occupies
     * @param type the type of the piece the pawn is to be promoted to
     * @see #promotePawn(int, int, Type)
     */
    public void promotePawn(Square s, Type type){
        promotePawn(s.getRow(), s.getCol(), type);
    }

    /**
     * @param board the board that is to replace the current board.
     */
    public void setBoard(Square[][] board) {
        this.board = board;
    }

    /**
     * Returns a clone of this Board object.
     *
     * @return
     * @TODO: TO BE IMPLEMENTED -- done 7/30/17
     * @TODO: HAD CLONED SEPARATE COPIES TO BOARD AND TO LIST, THIS
     * @TODO CAUSED THE GLITCH WITH THE CHECKS AND LISTS
     */
    public Board clone() {
        Board clone = new Board();

//        for(Move m : log.getMoves()){
//
//        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                clone.setSquare(getSquare(row, col).clone());
                if(hasPiece(row, col)) {
                    clone.addToSet(clone.getPiece(row, col), clone.getSquare(row, col));
                }
            }
        }
        MoveLog cloneLog = log.clone();
        clone.setLog(cloneLog);
        return clone;
    }

    /**
     * @param b board to be compared to
     * @return whether to boards are equal
     */
    public boolean equals(Board b) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (!getSquare(row, col).equals(b.getSquare(row, col))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return square on board which contains the white king.
     */
    public Square getWhiteKing() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (hasPiece(row, col)) {
                    if (getPiece(row, col).getType() == Type.KING &&
                            getPiece(row, col).getColor() == Color.WHITE) {
                        return getSquare(row, col);
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return square on board which contains the black king.
     */
    public Square getBlackKing() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (hasPiece(row, col)) {
                    if (getPiece(row, col).getType() == Type.KING &&
                            getPiece(row, col).getColor() == Color.BLACK) {
                        return getSquare(row, col);
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param start the starting square from where to translate
     * @param t the translation to execute
     * @return the square after the translation
     */
    public Square translateSquare(Square start, Translation t) {
        return getSquare(start.getRow() + t.getY(), start.getCol() + t.getX());
    }

    /**
     * Castles the king on the board.
     * @param kingMove the move of castling king
     * @param rookMove the move of the castling rook
     */
    public void castle(Move kingMove, Move rookMove){
        movePiece(kingMove);
        movePiece(rookMove.getStart(), rookMove.getEnd());
    }

    /**
     * Executes an en passante move on the board, where a pawn captures
     * an enemy pawn diagonally
     * @param m the move of the attack pawn
     */
    public void enPassante(Move m){
        movePiece(m);
        removePiece(m.getStart().getRow(), m.getEnd().getCol());
    }

    /**
     *
     * @param p the piece to be added to the list of all
     *          pieces on the board
     */
    public void addToSet(Piece p, Square s){
        if(p.getColor() == Color.WHITE){
            addToWhiteSet(p, s);
        }else{
            addToBlackSet(p, s);
        }
    }

    /**
     *
     * @param p the white piece to be added to the list of all
     *         white pieces on the board
     */
    public void addToWhiteSet(Piece p, Square s){
        whitePieceSet.add(p, s);
    }

    /**
     *
     * @param p the black piece to be added to the list of all black
     *          pieces on the board
     */
    public void addToBlackSet(Piece p, Square s){
        blackPieceSet.add(p, s);
    }

    /**
     *
     * @param p the piece to be removed from the list of all
     *          pieces on the board
     */
    public void removeFromSet(Piece p, Square s){
        if(p.getColor() == Color.WHITE){
            removeFromWhiteSet(p, s);
        }else{
            removeFromBlackSet(p, s);
        }
    }

    /**
     *
     * @param p the white piece to be removed from the set
     *          of all white pieces on the board
     */
    public void removeFromWhiteSet(Piece p, Square s){
        whitePieceSet.remove(p, s);
    }

    /**
     *
     * @param p the black piece to be removed from the set of
     *         all black pieces on the board
     */
    public void removeFromBlackSet(Piece p, Square s){
        blackPieceSet.remove(p, s);
    }


    /**
     *
     * @return the set of all white pieces on the board
     */
    public PieceSet getWhitePieceSet() {
        return whitePieceSet;
    }

    /**
     *
     * @return the set of all black pieces on the board
     */
    public PieceSet getBlackPieceSet() {
        return blackPieceSet;
    }


    /**
     * Records a move to the game's MoveLog
     * @param m the move to be recorded to the MoveLog
     * @see MoveLog#write(Move)
     */
    public void writeMove(Move m){
        Square start = getSquare(m.getStart());
        Square end = getSquare(m.getEnd());
        Move move = new Move(start, end);
        this.log.write(move);
    }

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
     * @param log
     */
    public void setLog(MoveLog log){this.log = log;}


    /**
     * @return a stylized string version of board
     */
    public String toString() {
        String s = "";
        s += "\n";
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                s += "|";
                s += (board[i][j].isOccupied()) ? (board[i][j].getPiece().getType().getLabel()) : " ";
                s += "|\t";
            }
            s += "\n";
        }
        return s;
    }

}
