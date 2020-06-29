package main.chess.controller;

import main.chess.structure.Board;
import main.chess.structure.Square;
import main.chess.structure.Translation;

/**
 * Translation Signature
 *
 * The signature of a translation defines the direction of motion of the translation.
 * i.e. a translation where (x,y) = (2,2) moves in the same direction as
 * a translation defined by (x,y) = (5,5); both moves upwards and rightwards in the
 * positive y and x directions.
 * so a signature of [1,1] is defined for both these translations.
 * @see MoveGenerator#generateMoves(Board, Square)
 */
public class TranslationSignature {

    /**
     * x and y components of signature
     */
    private final int x;
    private final int y;

    public TranslationSignature(Translation t) {
        // 0 if 0 else -1 or 1
        this.x = t.getX() == 0 ? 0 : t.getX() / Math.abs(t.getX());
        this.y = t.getY() == 0 ? 0 : t.getY() / Math.abs(t.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TranslationSignature)) return false;
        TranslationSignature key = (TranslationSignature) o;
        return x == key.x && y == key.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y; // 31 arbitrary prime, (x, y) pair must be unique hash
    }

}
