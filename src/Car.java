/**
 * Created by student on 2/1/18.
 */


public class Car extends Sprite{

    //=======Constructor=======

    public Car(int carType, int x, int y, int direction, int speed){

        super(x, y, direction);

        if(carType == 0){
            setPic("car1.png", direction);
        }//end if
        else if(carType == 1){
            setPic("car2.png", direction);
        }//end if
        else if(carType == 2){
            setPic("car3.png", direction);
        }//end if
        else if(carType == 3){
            setPic("truck.png", direction);
        }//end else if
        else{
            setPic("car4.png", direction);
        }//end

        super.setSpeed(speed);

    }//end Car

    //--------------------------

}//end class
