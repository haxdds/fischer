package chess.structure;

import chess.Game;
import chess.controller.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 7/19/2017.
 * <p>
 * This class represents a chess board. A chess board is composed
 * of an array of Squares which can house pieces. These pieces move
 * around the board from square to square. The board, along with
 * the pieces define the chess.structure of what is needed to
 * play a game of chess.
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
     * TODO: {The Game field links the board object to a game.}
     * TODO: IS LINKING NECESSARY? SEE CASTLING
     * @see Game
     * @see Square
     * @see Piece
     */
    private Controller controller;
    private Square[][] board = new Square[8][8];
    private List<Piece> whitePieceSet = new ArrayList<>();
    private List<Piece> blackPieceSet = new ArrayList<>();

    /**
     * A constructor for a board. It takes no arguments.
     * The setUpBoard() method is called on construction.     *
     *
     * @see Board#setUpBoard()
     */
    public Board() {setUpBoard();}

    /**
     * A constructor for a board which is linked to a game.
     * @param controller the Game which the board is a part of
     */
    public Board(Controller controller) {
        this.controller = controller;
        setUpBoard();
    }

    /**
     * Initializes the squares on the board and places the pieces.
     *
     * @see Board#setUpSquares()
     * @see Board#setPieces()
     */
    public void setUpBoard() {
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
            addPiece(1, column, wpawn);
            addPiece(6, column, bpawn);
            whitePieceSet.add(wpawn);
            blackPieceSet.add(bpawn);
        }

        whitePieceSet.add(wrook);
        whitePieceSet.add(wknight);
        whitePieceSet.add(wbishop);
        whitePieceSet.add(wqueen);
        whitePieceSet.add(wking);
        whitePieceSet.add(wbishop);
        whitePieceSet.add(wknight);
        whitePieceSet.add(wrook);

        blackPieceSet.add(brook);
        blackPieceSet.add(bknight);
        blackPieceSet.add(bbishop);
        blackPieceSet.add(bqueen);
        blackPieceSet.add(bking);
        blackPieceSet.add(bbishop);
        blackPieceSet.add(bknight);
        blackPieceSet.add(brook);


        board[0][0].addPiece(wrook);
        board[0][1].addPiece(wknight);
        board[0][2].addPiece(wbishop);
        board[0][3].addPiece(wqueen);
        board[0][4].addPiece(wking);
        board[0][5].addPiece(wbishop);
        board[0][6].addPiece(wknight);
        board[0][7].addPiece(wrook);

        board[7][0].addPiece(brook);
        board[7][1].addPiece(bknight);
        board[7][2].addPiece(bbishop);
        board[7][3].addPiece(bqueen);
        board[7][4].addPiece(bking);
        board[7][5].addPiece(bbishop);
        board[7][6].addPiece(bknight);
        board[7][7].addPiece(brook);

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
        addPiece(endRow, endCol, getPiece(startRow, startCol));
        removePiece(startRow, startCol);
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
        if(isCastlingMove(m)){
           castle(m);
        }else {
            movePiece(m.getStart(), m.getEnd());
        }
    }

    /**
     * Moves piece backwards according to the specifications of an input move.
     *
     * @param m the move to be undone
     * @see Board#movePiece(Move)
     */
    public void moveBack(Move m) {movePiece(m.getEnd(), m.getStart());}

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
        getSquare(row, col).addPiece(p);
    }

    /**
     * Adds a given piece to the given square on the board
     *
     * @param s the square where the piece is to be added
     * @param p the piece to be added.
     * @see Board#addPiece(int, int, Piece)
     */
    public void addPiece(Square s, Piece p) {
        addPiece(s.getRow(), s.getCol(), p);
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
        getSquare(row, col).removePiece();
    }

    /**
     * Removes the piece that occupies the given square on the board.
     *
     * @param s the square on the board whose piece is to be removed.
     * @see Board#removePiece(int, int)
     */
    public void removePiece(Square s) {
        removePiece(s.getRow(), s.getCol());
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
     * Returns the piece which occupies the given square on the board.
     *
     * @param s the square whose piece is to be retrieved.
     * @return the piece on that square.
     * @see Board#getPiece(int, int)
     */
    public Piece getPiece(Square s) {
        return getPiece(s.getRow(), s.getCol());
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
     * Determines whether a given square is occupied by a piece.
     *
     * @param s the square to be checked
     * @return whether that square has a piece.
     * @see Board#hasPiece(int, int)
     */
    public boolean hasPiece(Square s) {
        return hasPiece(s.getRow(), s.getCol());
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
     * Retrieves the square on the board given a square
     *
     * @param s a square whose equivalent on the board is to be retrieved
     * @return the equivalent square on the board
     */
    public Square getSquare(Square s) {
        return getSquare(s.getRow(), s.getCol());
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
     * @TODO: TO BE IMPLEMENTED
     */
    public Board clone() {
        Board clone = new Board();
        clone.setUpSquares();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                clone.setSquare(getSquare(row, col).clone());
            }
        }
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
                    System.out.println("UNEQUAL" +row + "  " + col);
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
     * Retrieves the corresponding rook move for castling for
     * a given king castling move.
     * @param start the starting position of king
     * @param end the ending position of castled king
     * @return the corresponding rook move for castling
     */
    public Move getCastlingRookMove(Square start, Square end){
        if(end.getCol() == 6){
            Square s1 = getSquare(start.getRow(), 7);
            Square s2 = getSquare(start.getRow(), 5);
            Move rookMove = new Move(s1, s2);
            return rookMove;
        }else if(end.getCol() == 2){
            Square s1 = getSquare(start.getRow(), 0);
            Square s2 = getSquare(start.getRow(), 3);
            Move rookMove = new Move(s1, s2);
            return rookMove;
        }
        return null;
    }

    /**
     *
     * @param m the move which castles the king
     * @return the corresponding rook move for castling
     * @see Board#getCastlingRookMove(Square, Square)
     */
    public Move getCastlingRookMove(Move m){
        return getCastlingRookMove(m.getStart(), m.getEnd());
    }

    public void castle(Move m){
        movePiece(m.getStart(), m.getEnd());
        Move rookMove = getCastlingRookMove(m);
        movePiece(rookMove.getStart(), rookMove.getEnd());
    }

    /**
     * @return stylized string version of board
     */
    public String toString() {
        String s = "";
        s += "\n";
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                s += "|";
                s += (board[i][j].isOccupied()) ? (board[i][j].getPiece().getType().getSignature()) : " ";
                s += "|\t";
            }
            s += "\n";
        }
        return s;
    }


}
