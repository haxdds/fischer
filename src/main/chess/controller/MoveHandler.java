package main.chess.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import main.chess.structure.*;

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
     *
     */
    private MoveGenerator generator;
    private PositionValidator positionValidator;


    /**
     * A constructor for MoveHandler objects.
     */
    public MoveHandler(){
        generator = new MoveGenerator();
        positionValidator = new PositionValidator();
    }

    /**
     *  Generates all the possible piece translations on a given board. 
     *  The side to move is taken into consideration.
     * @param board The board for which we want to generate moves
     * @return A list of all possible unverified translations that can be made
     * on the board
     * @see MoveGenerator#generateMoves(Board, Square) 
     */
    public List<Move> generateAllMoves(Board board){
        // even number of moves played imply white to move else black
        Color toMove = board.getLog().size() % 2 == 0? Color.WHITE : Color.BLACK;
        
        List<Move> allMoves = new ArrayList<>();
        
         Collection<Square> piecePositions = toMove == Color.WHITE ?  board.getWhitePieceSet().getOccupiedSquares() :
                 board.getBlackPieceSet().getOccupiedSquares();
        
        for(Square start : piecePositions) {
            allMoves.addAll(generator.generateMoves(board, start));
        }
        
        return allMoves;
    }

    /**
     * Returns all valid moves that can be made on a given board
     * verified for checks and castling conditions
     * @param board The board for which we want to generate moves
     * @return the list of all valid moves that can be made on that board
     */
    public List<Move> generateAllValidMoves(Board board){
        List<Move> allMoves = generateAllMoves(board);
        for(Move m : allMoves) System.out.println(m);
        // filter out invalid moves
        List<Move> invalidMoves = new ArrayList<>();
        for(Move m : allMoves){
            Board dummy = board.clone();
            dummy.movePiece(m);
            if(!positionValidator.isValidPosition(dummy)){
                invalidMoves.add(m);
            }
        }

        // remove invalid moves
        allMoves.removeAll(invalidMoves);

        // add any valid castling king moves
        allMoves.addAll(generateCastlingMoves(board));

        return allMoves;
    }

    /**
     * Returns a list of possible castling moves that can be
     * made by the king
     * @param board the board on which we want to castle
     * @return the possible castling moves
     */
    public List<Move> generateCastlingMoves(Board board){
        MoveLog log = board.getLog();
        int row = log.size() % 2 == 0? 0 : 7;
        Color enemyColor = row == 0? Color.BLACK : Color.WHITE;
        List<Move> castlingMoves = new ArrayList<>();

        Square king = board.getSquare(row, 4);
        Square AFileRook = board.getSquare(row, 0);
        Square HFileRook = board.getSquare(row, 7);
        Square FFile = board.getSquare(row, 5);
        Square GFile = board.getSquare(row, 6);
        Square DFile = board.getSquare(row, 3);
        Square CFile = board.getSquare(row, 2);

        if(log.containsSquare(king)) return castlingMoves;

        if(!positionValidator.isSafeSquare(board, king, enemyColor)) return castlingMoves;

        if(!log.containsSquare(AFileRook) && !DFile.isOccupied() && positionValidator.isSafeSquare(board, DFile, enemyColor) &&
            !CFile.isOccupied() && positionValidator.isSafeSquare(board, CFile, enemyColor))
                castlingMoves.add(new Move(king, CFile));

        if(!log.containsSquare(HFileRook) && !FFile.isOccupied() && positionValidator.isSafeSquare(board, FFile, enemyColor) &&
                !GFile.isOccupied() && positionValidator.isSafeSquare(board, GFile, enemyColor))
            castlingMoves.add(new Move(king, GFile));

        return castlingMoves;
    }

    /**
     * Returns whether the given move is a castling move
     * @param m the move we want to examine
     * @return whether the given move is a castling move
     */
    public boolean isCastlingMove(Move m){
        return m.getStart().getPiece().getType() == Type.KING && Math.abs(m.getStart().getCol() - m.getEnd().getCol()) == 2;
    }

    /**
     * Returns whether the given move is an en passant move
     * @param m the move we want to examine
     * @return whether the given move is an en passant move
     */
    public boolean isEnPassantMove(Move m){
        return m.getStart().getPiece().getType() == Type.PAWN && m.getStart().getCol() != m.getEnd().getCol() &&
                !m.getEnd().isOccupied();
    }

    /**
     * Returns the corresponding rook move for a given king castling move
     * @param kingMove the king castling move
     * @return the corresponding rook move
     */
    public Move getCastlingRookMove(Move kingMove){
        int rookRow = kingMove.getStart().getRow();
        // if king castles right, return H->F file rook move, else return A->C file rook move
        return kingMove.getEnd().getCol() - kingMove.getStart().getCol() > 0 ?
                new Move(new Square(rookRow, 7, Color.WHITE), new Square(rookRow, 5, Color.WHITE)) :
                new Move(new Square(rookRow, 0, Color.WHITE), new Square(rookRow, 4, Color.WHITE));
    }

}

