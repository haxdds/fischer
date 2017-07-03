package chess.transform;

/**
 * Created by Rahul on 6/21/2017.
 */
public class Translation{
    private int x;
    private int y;

    public Translation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX (){
       return x;
    }

    public int getY (){
        return y;
    }

    public Translation iterate(int k){
        return new Translation(k * x, k * y);
    }

    public boolean equals(Translation t){
        return this.getY() == t.getY() && this.getX() == t.getX();
    }
}



