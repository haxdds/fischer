package chess.engine;

import chess.gui.BoardGUI;
import chess.gui.GUI;
import chess.gui.Move;
import chess.structure.Board;
import chess.structure.Piece;
import chess.structure.Square;
import chess.transform.Translation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rahul on 6/26/2017.
 */
public class MoveIterator {

    Board board;
    BoardGUI boardGUI;

    public MoveIterator(BoardGUI gui, Board board){
        this.board = board;
        this.boardGUI = gui;
    }

    public ArrayList<Square> iterateMoves(Square sq){
        assert board.hasPiece(sq);
        Square square = board.getSquare(sq);
        ArrayList<Square> moves = new ArrayList<>();
        Piece p = square.getPiece();
        int[] sig = new int[2];
        for(Translation t: p.getType().getGroup().getGroup()){
            Square s = square.add(t);
            if(!s.inBounds()) continue;
            System.out.println("Translation");
            System.out.println(t.getX() + "  "  + t.getY());
            System.out.println(Arrays.toString(sig) + "   " + Arrays.toString(signature(t)));
            if(sig[0] == signature(t)[0] && sig[1] == signature(t)[1]){
                System.out.println("hi");
                continue;
            }
                if (board.getSquare(s).hasPiece()) {
                    sig = signature(t);
                    if (board.getSquare(s).getPiece().getColor() != p.getColor()) {
                        moves.add(s);
                    }else{
                        continue;
                    }
                }else {
                    moves.add(s);
                }

        }
        System.out.println(moves.size());
        return moves;
    }

    public int[] signature(Translation t){
        int[] sig = new int[2];

        if(t.getX() >= 0) sig[0] = 1;
        if(t.getX() < 0) sig[0] = -1;
        if(t.getY() >= 0) sig[1] = 1;
        if(t.getY() < 0) sig[1] = -1;

        return sig;
    }

}
