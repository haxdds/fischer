package main.chess.controller;

import main.chess.structure.*;

import java.util.*;

/**
 *
 */
public class PositionValidator {
    /**
     *
     * @param board
     * @return
     */
    public boolean isValidPosition(Board board){
        int movesPlayed = board.getLog().size();
        Color toMove = movesPlayed % 2 == 0? Color.WHITE : Color.BLACK;
        if(toMove == Color.WHITE){
            Square blackKing = board.getBlackKing();
            return isSafeSquare(board, blackKing, Color.WHITE);
        }else{
            Square whiteKing = board.getWhiteKing();
            return isSafeSquare(board, whiteKing, Color.BLACK);
        }
    }

    /**
     *
     * @param board
     * @param square
     * @param enemyColor
     * @return
     */
    public boolean isSafeSquare(Board board, Square square, Color enemyColor){
        Group diagonal = new ChessGroupFactoryImpl().createBishopGroup();
        Group lateral = new ChessGroupFactoryImpl().createRookGroup();
        Group knight = new ChessGroupFactoryImpl().createKnightGroup();

        Set<Type> diagonalDangerousPieces = new HashSet<>();
        diagonalDangerousPieces.add(Type.PAWN);
        diagonalDangerousPieces.add(Type.BISHOP);
        diagonalDangerousPieces.add(Type.QUEEN);
        diagonalDangerousPieces.add(Type.KING);

        Set<Type> lateralDangerousPieces = new HashSet<>();
        lateralDangerousPieces.add(Type.ROOK);
        lateralDangerousPieces.add(Type.QUEEN);
        lateralDangerousPieces.add(Type.KING);

        Set<Type> knightDangerousPieces = new HashSet<>();
        knightDangerousPieces.add(Type.KNIGHT);

        // all directions safe
        return safeFromGroup(board, square, enemyColor, diagonal, diagonalDangerousPieces) &&
                safeFromGroup(board,square, enemyColor, lateral, lateralDangerousPieces) &&
                    safeFromGroup(board, square, enemyColor, knight, knightDangerousPieces);
    }

    /**
     *
     * @param board
     * @param square
     * @param enemyColor
     * @param g
     * @param dangerousPieces
     * @return
     */
    public boolean safeFromGroup(Board board, Square square, Color enemyColor, Group g, Set<Type> dangerousPieces){
        HashMap<TranslationSignature, Boolean> directionBlocked = new HashMap<>();
        for(Translation t: g) {

            // if resulting square after translation is out of bounds
            if(!square.translate(t).inBounds()) continue;

            TranslationSignature signatureKey = new TranslationSignature(t);
            // direction has not been explored yet
            directionBlocked.putIfAbsent(signatureKey, false);

            // if that direction is blocked by piece -- doesn't apply to knights.
            if(directionBlocked.get(signatureKey) == true) continue;

            Square end = board.translateSquare(square, t);
            if (!end.isOccupied()) continue;

            Piece enemyPiece = end.getPiece();
            // mark that the direction is blocked
            directionBlocked.replace(signatureKey, true);
            if (enemyPiece.getColor() != enemyColor || !dangerousPieces.contains(enemyPiece.getType())) continue;

            // deadly square
            if(enemyPiece.getType() != Type.KING || enemyPiece.getType() != Type.PAWN) return false;

            int deltaRow = square.getRow() - end.getRow();
            int deltaCol = square.getCol() - end.getCol();

            // if kings are adjacent
            if(enemyPiece.getType() == Type.KING && Math.abs(deltaRow) <= 1 || Math.abs(deltaCol) <= 1) return false;

            if(enemyPiece.getType() == Type.PAWN){
                if(Math.abs(deltaCol) != 1) continue;

                if(enemyColor == Color.WHITE && deltaRow == 1) return false;

                if(enemyColor == Color.BLACK && deltaRow == -1)  return false;
            }
        }
        return true;
    }
}
