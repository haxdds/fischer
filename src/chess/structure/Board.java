package chess.structure;

import chess.gui.GUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 6/12/2017.
 */
public class Board  {
    private Square[][] board = new Square[8][8];
    private List<Piece> whitePieceSet = new ArrayList<>();
    private List<Piece> blackPieceSet = new ArrayList<>();
    private boolean check = false;
    private boolean checkmate = false;


    public Board(){
        setSquares();
        setPieces();

    }


    public Piece getPiece(int row, int column){
        return board[row][column].getPiece();
    }

    public Piece getPiece(Square square){
        return getPiece(square.getRow(), square.getColumn());
    }

    public void removePiece(int row, int column){
        Piece p = board[row][column].getPiece();
        if(p.getColor() == Color.BLACK){
            blackPieceSet.remove(p);
        }else{
            whitePieceSet.remove(p);
        }
        board[row][column].removePiece();
    }

    public void movePiece(int row, int column, int toRow, int toColumn){
        board[toRow][toColumn].addPiece(board[row][column].getPiece());
        board[row][column].removePiece();
    }

    public void movePiece(Square start, Square end){
        movePiece(start.getRow(), start.getColumn(), end.getRow(), end.getColumn());
    }


    public void printBoard(){
        System.out.print("\n");
        for(int i = 7; i >= 0; i--){
            for(int j = 0; j < 8; j++){
                System.out.print("|");
                System.out.print((board[i][j].hasPiece()) ? (board[i][j].getPiece().getType().getName()): " ");
                System.out.print("|\t");
            }
            System.out.print("\n");
        }
    }


    public void setPieces(){
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

        for(int column = 0; column < 8; column++){
            board[1][column].addPiece(wpawn);
            board[6][column].addPiece(bpawn);
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

    private void setSquares(){
        for(int row = 0; row <= 7; row++){
            for (int column = 0; column <= 7; column++) {
                if((row + column) % 2 == 0){
                    Square sq = new Square(Color.BLACK, row, column);
                    board[row][column] = sq;
                }else{
                    Square sq = new Square(Color.WHITE, row, column);
                    board[row][column] = sq;
                }
            }
        }
    }

    public boolean hasPiece(Square square){
        return board[square.getRow()][square.getColumn()].hasPiece();
    }

    public Square getSquare(Square square){
        return board[square.getRow()][square.getColumn()];
    }






}
