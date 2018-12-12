import java.awt.*;

/**
 * Created by YOUR NAME on 10/11/17.
 */

//Put your name in this comment.

public class Quad {

    //===============instance fields=================

    int[] quadX;
    int[] quadY;

    //==================constructor==================

    public Quad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){


        int[] quadX = {x1, x2, x3, x4};
        int[] quadY = {y1, y2, y3, y4};

        this.quadX = quadX;
        this.quadY = quadY;

    }//end Quad

    //===================method======================

    public void fillQuad(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        g2.fillPolygon(quadX, quadY, 4);

    }//end drawQuad


}//end class