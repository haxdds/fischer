package chess.structure;

/**
 * Created by Rahul on 6/12/2017.
 */
public enum Color {
    BLACK(java.awt.Color.BLACK),
    WHITE(java.awt.Color.WHITE);

    private java.awt.Color color;

    Color(java.awt.Color color){
        this.color = color;
    }

    public java.awt.Color getColor(){
        return this.color;
    }
}
