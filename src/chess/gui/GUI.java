package chess.gui;

import chess.controller.Controller;
import chess.structure.Board;
import chess.structure.Move;
import chess.structure.Square;
import chess.structure.Type;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Rahul on 7/20/2017.
 *
 * This GUI is just for testing. A more spicy and dank
 * gui will be implemented in the near future. *
 *
 * FIXME: REWORK GUI TO MAKE IT MORE SIMPLE.
 * TODO: IMPLEMENT CLICK AND DRAG ON PIECE IMAGES
 * TODO: AS OF NOW WE'RE TREATING THE GUI AS A BLACK BOX UNTIL
 * TODO: THE LOGIC OF THE chess.game.game IS FINISHED.
 */
public class GUI implements BoardGUI{


    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private Image[][] chessPieceImages = new Image[2][6];
    private JPanel chessBoard;
    private final JLabel message = new JLabel(
            "Chess Champ is ready to play!");
    private static final String COLS = "ABCDEFGH";
    public static final int QUEEN = 0, KING = 1,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int[] STARTING_ROW = {
            ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK
    };
    public static final int BLACK = 0, WHITE = 1;

    Color gsquare = new Color(83, 164, 92);

    private JPanel promoteBox;
    private JButton[] promotionOptions = new JButton[4];

    private Controller controller;

    public GUI() {
        this.controller = controller;
        initializeGui();

        JFrame f = new JFrame("Play Some Goddamn Chess");
        f.add(gui);
        // Ensures JVM closes after frame(s) closed and
        // all non-daemon threads are finished
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // See https://stackoverflow.com/a/7143398/418556 for demo.
        f.setLocationByPlatform(true);

        // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        f.pack();
        // ensures the minimum size is enforced.
        f.setMinimumSize(f.getSize());
        f.setVisible(true);

        setupNewGame();
    }

    public final void initializeGui() {
        // create the chess.images for the chess pieces
        loadImages();


        chessBoard = new JPanel(new GridLayout(0, 9)) {

            /**
             * Override the preferred size to return the largest it can, in
             * a square shape.  Must (must, must) be added to a GridBagLayout
             * as the only component (it uses the parent as a guide to size)
             * with no GridBagConstaint (so it is centered).
             */
            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int) d.getWidth(), (int) d.getHeight());
                } else if (c != null &&
                        c.getWidth() > d.getWidth() &&
                        c.getHeight() > d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (w > h ? h : w);
                return new Dimension(s, s);
            }

        };
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8, 8, 8, 8),
                new LineBorder(Color.BLACK)
        ));
        // Set background
        Color background = new Color(88, 138, 148);
        chessBoard.setBackground(background);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(background);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);

        // create the chess boardGUI squares
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(gsquare);
                }
                chessBoardSquares[jj][ii] = b;
            }
        }

        /*
         * fill the chess boardGUI
         */
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            JLabel label = new JLabel(COLS.substring(ii, ii + 1),
                    SwingConstants.CENTER);
            label.setFont(new Font("Serif", Font.BOLD, 24));
            chessBoard.add(label);
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        JLabel label = new JLabel("" + (9 - (ii + 1)),
                                SwingConstants.CENTER);
                        label.setFont(new Font("Serif", Font.BOLD, 24));
                        chessBoard.add(label);
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
    }

    private void loadImages() {
        chessPieceImages[0][0] = getImage("BlackQueen");
        chessPieceImages[0][1] = getImage("BlackKing");
        chessPieceImages[0][2] = getImage("BlackRook");
        chessPieceImages[0][3] = getImage("BlackKnight");
        chessPieceImages[0][4] = getImage("BlackBishop");
        chessPieceImages[0][5] = getImage("BlackPawn");

        chessPieceImages[1][0] = getImage("WhiteQueen");
        chessPieceImages[1][1] = getImage("WhiteKing");
        chessPieceImages[1][2] = getImage("WhiteRook");
        chessPieceImages[1][3] = getImage("WhiteKnight");
        chessPieceImages[1][4] = getImage("WhiteBishop");
        chessPieceImages[1][5] = getImage("WhitePawn");
    }

    private BufferedImage getImage(String name) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/chess/images/" + name + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }


    /**
     * Initializes the icons of the initial chess boardGUI piece places
     */
    public void setupNewGame() {
        message.setText("Make your move!");
        // set up the black pieces
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][0].setIcon(new ImageIcon(
                    chessPieceImages[BLACK][STARTING_ROW[ii]]));
        }
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][1].setIcon(new ImageIcon(
                    chessPieceImages[BLACK][PAWN]));
        }
        // set up the white pieces
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][6].setIcon(new ImageIcon(
                    chessPieceImages[WHITE][PAWN]));
        }
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][7].setIcon(new ImageIcon(
                    chessPieceImages[WHITE][STARTING_ROW[ii]]));
        }
    }

    public void move(int row1, int column1, int row2, int column2) {
        getSquare(row2, column2).setIcon(chessBoardSquares[column1][7 - row1].getIcon());
        chessBoardSquares[column1][7 - row1].setIcon(null);
    }

    public void move(Square start, Square end) {
        move(start.getRow(), start.getCol(), end.getRow(), end.getCol());
    }

    public void remove(int row, int col){chessBoardSquares[col][7 - row].setIcon(null);}

    public void remove(Square s){remove(s.getRow(), s.getCol());}


    public JButton getSquare(int row, int column) {
        return chessBoardSquares[column][7 - row];
    }

    public JButton getSquare(Square square) {
        return chessBoardSquares[square.getCol()][7 - square.getRow()];
    }

    public void update(Move move){
        move(move.getStart(), move.getEnd());
    }

    public void highlight(Square start, ArrayList<Square> moves, Controller controller){
        for(Square s: moves){
            if(getSquare(s).getBackground() == Color.WHITE){
                if(s.isOccupied()) {
                    getSquare(s).setBackground(new Color(210, 70, 0));
                }else {
                    getSquare(s).setBackground(new Color(217, 240, 106));
                }
            }else{
                if(s.isOccupied()) {
                    getSquare(s).setBackground(new Color(210, 70, 0));
                }else {
                    getSquare(s).setBackground(new Color(217, 240, 106));
                }
            }
//            Square enPass = controller.getMoveHandler().hasEnPassanteMove(controller.getBoard(), start, moves);
//            if(enPass != null){
//                highlightRed(enPass);
//            }
        }
    }

    public void highlightSquare(Square s){
        if(getSquare(s).getBackground() == Color.WHITE){
            if(s.isOccupied()) {
                getSquare(s).setBackground(new Color(210, 70, 0));
            }else {
                getSquare(s).setBackground(new Color(217, 240, 106));
            }
        }else{
            if(s.isOccupied()) {
                getSquare(s).setBackground(new Color(210, 70, 0));
            }else {
                getSquare(s).setBackground(new Color(217, 240, 106));
            }
        }
    }

    public void highlightRed(Square s){
        getSquare(s).setBackground(new Color(210, 70, 0));
    }

    public void refreshColor(){
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                if ((jj % 2 == 1 && ii % 2 == 1)
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    chessBoardSquares[jj][ii].setBackground(Color.WHITE);
                } else {
                    chessBoardSquares[jj][ii].setBackground(gsquare);
                }
            }
        }
    }

    public void createPromotionBox(Color color){
        promoteBox = new JPanel(new GridLayout(0,4));
        Color background  = Color.WHITE;
        if(color.equals(Color.WHITE)){
            JButton q = new JButton();
            q.setBackground(background);
            q.setIcon(new ImageIcon(getImage("WhiteQueen")));
            q.setPreferredSize(new Dimension(64,64));
            promotionOptions[0] = q;

            JButton r = new JButton();
            r.setBackground(background);
            r.setIcon(new ImageIcon(getImage("WhiteRook")));
            q.setPreferredSize(new Dimension(64,64));
            promotionOptions[1] = r;

            JButton b = new JButton();
            b.setBackground(background);
            b.setIcon(new ImageIcon(getImage("WhiteBishop")));
            q.setPreferredSize(new Dimension(64,64));
            promotionOptions[2] = b;

            JButton k = new JButton();
            k.setBackground(background);
            k.setIcon(new ImageIcon(getImage("WhiteKnight")));
            q.setPreferredSize(new Dimension(64,64));
            promotionOptions[3] = k;
        }else if(color.equals(Color.BLACK)) {
            JButton q = new JButton();
            q.setBackground(background);
            q.setIcon(new ImageIcon(getImage("BlackQueen")));
            promotionOptions[0] = q;

            JButton r = new JButton();
            r.setBackground(background);
            r.setIcon(new ImageIcon(getImage("BlackRook")));
            promotionOptions[1] = r;

            JButton b = new JButton();
            b.setBackground(background);
            b.setIcon(new ImageIcon(getImage("BlackBishop")));
            promotionOptions[2] = b;

            JButton k = new JButton();
            k.setBackground(background);
            k.setIcon(new ImageIcon(getImage("BlackKnight")));
            promotionOptions[3] = k;
        }
        promoteBox.add(promotionOptions[0]);
        promoteBox.add(promotionOptions[1]);
        promoteBox.add(promotionOptions[2]);
        promoteBox.add(promotionOptions[3]);
        JPanel boardConstrain = new JPanel(new BorderLayout());
        boardConstrain.setBackground(Color.WHITE);
        boardConstrain.add(promoteBox);
        boardConstrain.setPreferredSize(new Dimension(500,100));
        boardConstrain.setLocation(0,0);
        gui.add(boardConstrain, BorderLayout.NORTH);

    }

    public void addPromotionListeners(Controller controller){
        for(int k = 0; k < 4; k++){
            final int id = k;
            promotionOptions[k].addActionListener(new ActionListener() {
                final int i = id;
                @Override
                public void actionPerformed(ActionEvent e) {
                    promote(i, controller);
                }
            });
        }
    }

    public void promote(int id, Controller controller){
        switch(id){
            case 0:
                controller.promote(Type.QUEEN);
            case 1:
                controller.promote(Type.ROOK);
            case 2:
                controller.promote(Type.BISHOP);
            case 3:
                controller.promote(Type.KNIGHT);
            default:
                controller.promote(Type.QUEEN);
        }
    }

    public void getPromotion(Color color, Controller controller){
        createPromotionBox(color);
        addPromotionListeners(controller);
    }

    public void removePromotionBox(){
        gui.remove(promoteBox);
        gui.invalidate();
        gui.repaint();
    }


}
