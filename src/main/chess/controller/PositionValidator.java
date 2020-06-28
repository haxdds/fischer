package main.chess.controller;

import main.chess.structure.*;

import javax.swing.tree.TreeNode;
import java.util.*;

public class PositionValidator {

    public boolean isValidPosition(Board board){
        int movesPlayed = board.getLog().size();
        Color toMove = movesPlayed % 2 == 0? Color.WHITE : Color.BLACK;
        if(toMove == Color.WHITE){
            Square blackKing = board.getBlackKing();
        }


    }

    public boolean isSafe(Board board, Square square){
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
        return isSafeGroup(board, square, diagonal, diagonalDangerousPieces) &&
                isSafeGroup(board,square, lateral, lateralDangerousPieces) &&
                    isSafeGroup(board, square, knight, knightDangerousPieces);
    }

    public boolean isSafeGroup(Board board, Square square, Group g, Set<Type> dangerousPieces){
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
                // mark that the direction is blocked
                directionBlocked.replace(signatureKey, true);
                if (end.getPiece().getColor() != square.getPiece().getColor()) {
                    if(dangerousPieces.contains(end.getPiece().getType())){
                        return false;
                    }
                }
            }

        }
        return true;
    }
}
