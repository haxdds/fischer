package chess.game;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Rahul on 7/31/2017.
 */
public class Clock{
    /**
     *
     */
    private final int length;
    private final boolean incremented;
    private int increment;
    private int time;
    private Timer timer;

    /**
     *
     * @param length
     * @param increments
     */
    public Clock(int length, boolean increments){
        this.length = length;
        this.incremented = increments;
        this.time = length;
        this.timer = new Timer();
    }

    /**
     *
     * @param length
     */
    public void setIncrement(int length){
        if(!isIncremented()) throw new IllegalArgumentException("Clock must be an incremented type");
        this.increment = length;
    }

    /**
     *
     * @return
     */
    public boolean isIncremented(){return this.incremented;}

    /**
     *
     */
    public void incrementTime(){
        if(!isIncremented()) throw new IllegalArgumentException("Clock must be an incremented type");
        time += increment;
    }

    /**
     *
     */
    public void start(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 1000);
    }

    /**
     *
     */
    public void stop(){
        timer.cancel();
        timer.purge();
    }

    /**
     *
     */
    public void update(){
        this.time--;
        if(time <= 0) {
            stop();
        }
    }

    /**
     *
     * @return
     */
    public int getTime(){
        return this.time;
    }

    /**
     *
     * @return
     */
    public int getLength(){
        return this.length;
    }

    /**
     *
     * @return
     */
    public int getIncrement(){
        return this.increment;
    }

    /**
     * 
     * @return
     */
    public String toString(){
        return "Time Left: " + this.time;
    }






}
