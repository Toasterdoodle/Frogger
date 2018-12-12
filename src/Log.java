/**
 * Created by student on 2/8/18.
 */
public class Log extends Sprite {

    //=======Constructor=======

    public Log(int x, int y, int direction, int speed){

        super(x, y, direction);
        setSpeed(speed);
        int rng = (int)(Math.random() * 3);

        if(rng == 0) {
            setPic("logShort.png", direction);
        }//end rng
        else if(rng == 1){
            setPic("logMedium.png", direction);
        }//end if
        else if(rng == 2){
            setPic("logLarge.png", direction);
        }//end else if

        super.setSpeed(speed);

    }//end Car

    //--------------------------

}//end class
