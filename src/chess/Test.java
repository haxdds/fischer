package chess;

import chess.controller.Controller;
import chess.controller.logic.MoveHandler;
import chess.gui.GUI;
import chess.structure.*;

import java.util.Arrays;

/**
 * Created by Rahul on 7/19/2017.
 */
public class Test {

    public static void main(String[] args) {
        Board b = new Board();
        GUI g = new GUI();
        Controller c = new Controller(b,g);

//        Translation t1 = new Translation(5,5);
//        Translation t2 = new Translation(2,2);
//        System.out.println(Arrays.toString(t1.getSignature()));
//        System.out.println(Arrays.toString(t2.getSignature()));
//        System.out.println(t1.equalSignatures(t2));




    }
}
