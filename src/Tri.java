import java.awt.*;

/**
 * Created by YOUR NAME on 10/11/17.
 */

//Put your name in this comment.

public class Tri {

    //===============instance fields=================

    int[] triX;
    int[] triY;

    //==================constructor==================

    public Tri(int x1, int y1, int x2, int y2, int x3, int y3){


        int[] triX = {x1, x2, x3};
        int[] triY = {y1, y2, y3};

        this.triX = triX;
        this.triY = triY;

    }//end Quad

    //===================method======================

    public void fillTri(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        g2.fillPolygon(triX, triY, 3);

    }//end fillTri


}//end class