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
    private MoveValidator validator;
    private PositionValidator positionValidator;


    /**
     * A constructor for MoveHandler objects.
     */
    public MoveHandler(){
        generator = new MoveGenerator();
        validator = new MoveValidator();
        positionValidator = new PositionValidator();
    }


    public List<Move> generateAllMoves(Board board){
        Color toMove = board.getLog().size() % 2 == 0? Color.WHITE : Color.BLACK;
        List<Move> allMoves = new ArrayList<Move>();
        if(toMove == Color.WHITE){
            Collection<Square> whitePieces = board.getWhitePieceSet().getOccupiedSquares();
            for(Square start : whitePieces) {
                allMoves.addAll(generator.generateMoves(board, start));
            }
        }else{
            Collection<Square> blackPieces = board.getBlackPieceSet().getOccupiedSquares();
            for(Square start : blackPieces) {
                allMoves.addAll(generator.generateMoves(board, start));
            }
        }
        return allMoves;
    }

    public List<Move> generateAllValidMoves(Board board){
        List<Move> allMoves = generateAllMoves(board);

        List<Move> invalidMoves = new ArrayList<>();
        for(Move m : allMoves){
            Board dummy = board.clone();
            dummy.movePiece(m);
            if(!positionValidator.isValidPosition(dummy)){
                invalidMoves.add(m);
            }
        }

        allMoves.removeAll(invalidMoves);

        allMoves.addAll(generateCastlingMoves(board));

        return allMoves;
    }

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

        if(log.containsSquare(king) || log.containsSquare(AFileRook)
                || log.containsSquare(HFileRook)) return castlingMoves;

        if(!positionValidator.isSafeSquare(board, king, enemyColor)) return castlingMoves;

        if(!DFile.isOccupied() && positionValidator.isSafeSquare(board, DFile, enemyColor) &&
            !CFile.isOccupied() && positionValidator.isSafeSquare(board, CFile, enemyColor))
                castlingMoves.add(new Move(king, CFile));

        if(!FFile.isOccupied() && positionValidator.isSafeSquare(board, FFile, enemyColor) &&
                !GFile.isOccupied() && positionValidator.isSafeSquare(board, GFile, enemyColor))
            castlingMoves.add(new Move(king, GFile));

        return castlingMoves;
    }

    public boolean isCastlingMove(Move m){
        return m.getStart().getPiece().getType() == Type.KING && Math.abs(m.getStart().getCol() - m.getEnd().getCol()) == 2;
    }

    public boolean isEnPassantMove(Move m){
        return m.getStart().getPiece().getType() == Type.PAWN && m.getStart().getCol() != m.getEnd().getCol() &&
                !m.getEnd().isOccupied();
    }

    public Move getCastlingRookMove(Move kingMove){
        int rookRow = kingMove.getStart().getRow();
        return kingMove.getEnd().getCol() - kingMove.getStart().getCol() > 0 ?
                new Move(new Square(rookRow, 7, Color.WHITE), new Square(rookRow, 5, Color.WHITE)) :
                new Move(new Square(rookRow, 0, Color.WHITE), new Square(rookRow, 4, Color.WHITE));
    }







}

