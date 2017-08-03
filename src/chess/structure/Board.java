package chess.structure;

import chess.controller.Controller;

import java.util.ArrayList;
import java.util.HashMap;

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
     * TODO: {The game field links the board object to a game.}
     * TODO: IS LINKING NECESSARY? SEE CASTLING
     * @see Controller
     * @see Square
     * @see Piece
     */
    private Controller controller;
    private Square[][] board = new Square[8][8];
    private ArrayList<Piece> pieceList = new ArrayList<>();
    private ArrayList<Piece> whitePieceList = new ArrayList<>();
    private ArrayList<Piece> blackPieceList = new ArrayList<>();
    private HashMap<Piece, Square> pieceMap  = new HashMap<>();

    /**
     * A constructor for a board. It takes no arguments.
     * The setUpBoard() method is called on construction.     *
     *
     * @see Board#setUpBoard()
     */
    public Board() {setUpBoard();}

    /**
     * A constructor for a board which is linked to a game.
     * @param controller the game which the board is a part of
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
     * @see Board#whitePieceList
     * @see Board#blackPieceList
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

        if(isCastlingMove(m)){
           castle(m);
        }else if(isEnPassanteMove(m)){
            enPassante(m);
        }else{
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
        addToList(p);
        addToMap(p, getSquare(row, col));
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
        removeFromList(getPiece(row,col));
        removeFromMap(getPiece(row, col), getSquare(row, col));
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
     * @TODO: TO BE IMPLEMENTED -- done 7/30/17
     */
    public Board clone() {
        Board clone = new Board();
        clone.setUpSquares();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                clone.setSquare(getSquare(row, col).clone());
            }
        }
        cloneListAndMap(clone);
        return clone;
    }

    /**
     * Clones and sets the maps and lists of this board to another board.
     * @param clone the board to whose lists and maps of pieces will be set to
     *              the cloned maps and lists of this board.
     */
    public void cloneListAndMap(Board clone){
        ArrayList<Piece> clonelist = new ArrayList<>();
        ArrayList<Piece> clonewhitelist = new ArrayList<>();
        ArrayList<Piece> cloneblacklist = new ArrayList<>();
        HashMap<Piece, Square> clonemap = new HashMap<>();
        for(Piece p: getPieceList()){
            Piece clonepiece = p.clone();
            if(clonepiece.getColor() == Color.WHITE){
                clonewhitelist.add(clonepiece);
            }else{
                cloneblacklist.add(clonepiece);
            }
            clonelist.add(clonepiece);
            clonemap.put(clonepiece, mapPiece(p).clone());
        }
        clone.setPieceList(clonelist);
        clone.setBlackPieceList(cloneblacklist);
        clone.setWhitePieceList(clonewhitelist);
        clone.setPieceMap(clonemap);
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

    /**
     * Castles the king on the board.
     * @param m the move of castling king
     */
    public void castle(Move m){
        movePiece(m.getStart(), m.getEnd());
        Move rookMove = getCastlingRookMove(m);
        movePiece(rookMove.getStart(), rookMove.getEnd());
    }

    /**
     * Executes an en passante move on the board, where a pawn captures
     * an enemy pawn diagonally
     * @param m the move of the attack pawn
     */
    public void enPassante(Move m){
        movePiece(m.getStart(), m.getEnd());
        removePiece(m.getStart().getRow(), m.getEnd().getCol());
    }

    /**
     *
     * @param p the piece to be added to the list of all
     *          pieces on the board
     */
    public void addToList(Piece p){
        pieceList.add(p);
        if(p.getColor() == Color.WHITE){
            addToWhiteList(p);
        }else{
            addToBlackList(p);
        }
    }

    /**
     *
     * @param p the white piece to be added to the list of all
     *         white pieces on the board
     */
    public void addToWhiteList(Piece p){
        whitePieceList.add(p);
    }

    /**
     *
     * @param p the black piece to be added to the list of all black
     *          pieces on the board
     */
    public void addToBlackList(Piece p){
        blackPieceList.add(p);
    }

    /**
     *
     * @param p the piece to be removed from the list of all
     *          pieces on the board
     */
    public void removeFromList(Piece p){
        pieceList.remove(p);
        if(p.getColor() == Color.WHITE){
            removeFromWhiteList(p);
        }else{
            removeFromBlackList(p);
        }
    }

    /**
     *
     * @param p the white piece to be removed from the list
     *          of all white pieces on the board
     */
    public void removeFromWhiteList(Piece p){
        whitePieceList.remove(p);
    }

    /**
     *
     * @param p the black piece to be removed from the list of
     *         all black pieces on the board
     */
    public void removeFromBlackList(Piece p){
        blackPieceList.remove(p);
    }

    /**
     *
     * @param p the piece to be added to the map
     * @param s the square on the board that the piece
     *          is on.
     */
    public void addToMap(Piece p, Square s){
        pieceMap.put(p, s);
    }

    /**
     *
     * @param p the piece to be removed from the map
     * @param s the square on the board from which it
     *          is to be removed
     */
    public void removeFromMap(Piece p, Square s){
        pieceMap.remove(p, s);
    }

    /**
     *
     * @param p the piece to be mapped
     * @return the square which the piece is on
     */
    public Square mapPiece(Piece p){
        return pieceMap.get(p);
    }

    /**
     *
     * @return the list of all pieces on the board
     */
    public ArrayList<Piece> getPieceList() {
        return pieceList;
    }

    /**
     *
     * @return the list of all white pieces on the board
     */
    public ArrayList<Piece> getWhitePieceList() {
        return whitePieceList;
    }

    /**
     *
     * @return the list of all black pieces on the board
     */
    public ArrayList<Piece> getBlackPieceList() {
        return blackPieceList;
    }

    /**
     *
     * @return the hashMap which maps pieces to squares on the board
     */
    public HashMap<Piece, Square> getPieceMap() {
        return pieceMap;
    }

    /**
     *
     * @param pieceList the list of all pieces on the board
     */
    public void setPieceList(ArrayList<Piece> pieceList) {
        this.pieceList = pieceList;
    }

    /**
     *
     * @param whitePieceList the list of white pieces on the board
     */
    public void setWhitePieceList(ArrayList<Piece> whitePieceList) {
        this.whitePieceList = whitePieceList;
    }

    /**
     *
     * @param blackPieceList the list of black pieces on the board
     */
    public void setBlackPieceList(ArrayList<Piece> blackPieceList) {
        this.blackPieceList = blackPieceList;
    }

    /**
     *
     * @param pieceMap the hashMap which maps pieces to squares on the board
     */
    public void setPieceMap(HashMap<Piece, Square> pieceMap) {
        this.pieceMap = pieceMap;
    }

    /**
     *
     * @param controller the controller which will regulate the board
     */
    public void setController(Controller controller) {
        this.controller = controller;

    }

    /**
     *
     * @return the controller which regulates the board
     * @throws NullPointerException if controller is null
     */
    public Controller getController(){
        if(this.controller == null) throw new NullPointerException("NULL CONTROLLER");
        return this.controller;
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
     * checks whether a given move is an en passante move
     * @param m the moved to be checked
     * @return whether that move is an en passante move
     */
    public boolean isEnPassanteMove(Move m){
        if(m.getStart().getPiece().getType() != Type.PAWN) return false;
        if(m.getStart().getPiece().getColor() == Color.WHITE){
            if(m.getStart().getRow() != 4) return false;
            if(m.getStart().getCol() == m.getEnd().getCol()) return false;
            return true;
        }else{
            if(m.getStart().getRow() != 3) return false;
            if(m.getStart().getCol() == m.getEnd().getCol()) return false;
            return true;
        }
    }

    /**
     * @return a stylized string version of board
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
