package chess.gui;

import chess.structure.Square;
import chess.transform.Translation;

/**
 * Created by Rahul on 6/15/2017.
 */
public class Move {

    private Square start;
    private Square end;
    private boolean mature = false;


    Move(Square start){
        this.start = start;
    }

    public void setMature(boolean mature) {
        this.mature = mature;
    }

    public boolean isMature(){
        return mature;
    }

    public Square getStart() {
        return start;
    }

    public void setStart(Square start) {
        this.start = start;
    }

    public Square getEnd() {
        return end;
    }

    public void setEnd(Square end) {
        this.end = end;
        this.mature = true;
    }

    public void reset(){
        this.start = null;
        this.end = null;
        this.setMature(false);
    }

    public Translation getTranslation(){
        return new Translation(end.getColumn()-start.getColumn(), end.getRow() - start.getRow());
    }

}
