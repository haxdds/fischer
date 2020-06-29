package main.chess.controller;

import main.chess.structure.*;

import java.util.*;

public class PositionValidator {

    public boolean isValidPosition(Board board){
        int movesPlayed = board.getLog().size();
        Color toMove = movesPlayed % 2 == 0? Color.WHITE : Color.BLACK;
        if(toMove == Color.WHITE){
            Square blackKing = board.getBlackKing();
            return isSafeSquare(board, blackKing);
        }else{
            Square whiteKing = board.getWhiteKing();
            return isSafeSquare(board, whiteKing);
        }
    }

    public boolean isSafeSquare(Board board, Square square){
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
        return safeFromGroup(board, square, diagonal, diagonalDangerousPieces) &&
                safeFromGroup(board,square, lateral, lateralDangerousPieces) &&
                    safeFromGroup(board, square, knight, knightDangerousPieces);
    }

    public boolean safeFromGroup(Board board, Square square, Group g, Set<Type> dangerousPieces){
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
            if (end.isOccupied()) {
                Piece p = end.getPiece();
                // mark that the direction is blocked
                directionBlocked.replace(signatureKey, true);
                if (p.getColor() != square.getPiece().getColor() && dangerousPieces.contains(p.getType())) {
                        if(p.getType() == Type.KING){
                            // if squares are adjacent
                            if(Math.abs(square.getRow() - end.getRow()) <= 1 && Math.abs(square.getCol() - end.getCol()) <= 1){
                                return false;
                            }
                        }else if(p.getType() == Type.PAWN){
                            if(Math.abs(square.getCol() - end.getCol()) == 1){
                                if(square.getPiece().getColor() == Color.BLACK){
                                    if(square.getRow() - end.getRow() == 1){
                                        return false;
                                    }
                                }else{
                                    if(square.getRow() - end.getRow() == -1){
                                        return false;
                                    }
                                }
                            }
                        }else{
                            return false;
                        }
                }
            }

        }
        return true;
    }
}
