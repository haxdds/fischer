package main.chess.structure;

import main.chess.structure.Board;
import main.chess.structure.Move;
import main.chess.structure.Piece;
import main.chess.structure.Square;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void movePiece() {
        Board b = new Board();
        Square E2 = b.getSquare("E2");
        Piece pawnStart = E2.getPiece();
        Square E4 = b.getSquare("E4");
        Move test = new Move(E2, E4);
        b.movePiece(test);
        Piece pawnEnd = E4.getPiece();
        assertEquals(pawnStart, pawnEnd);
    }

    @Test
    void castle() {
    }

    @Test
    void enPassante() {
    }
}