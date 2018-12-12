import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class FroggerMain extends JPanel {

    //==========Instance Fields==========

    //instance fields for the general environment
    public static final int FRAMEWIDTH = 1000, FRAMEHEIGHT = 600;
    private Timer timer;
    private boolean[] keys;
    private int carLimit = 30;
    private int logLimit = 10;
    private Frog frog;
    private ArrayList<Car> cars;
    private ArrayList<Log> logs;
    private int lane, direction, carType, speed, x;
    private boolean onLog, inWater;
    private int levelCounter = 0;
    private int deathCounter = 0;
    private int worldCounter = 0;
    private int triLength = FRAMEWIDTH / 20;

    //=======FroggerMain=======

    public FroggerMain(){

        //=======background music=========

        try {
            // open the sound file as a Java input stream
            String hop = "res/music1.wav";
            InputStream in = new FileInputStream(hop);
            // create an audiostream from the inputstream
            AudioStream audioStream = new AudioStream(in);
            // play the audio clip with the audioplayer class
            AudioPlayer.player.start(audioStream);
        } //end try
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading sound file.");
        }//end catch

        //===========looper========

        Timer music = new Timer(201000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                try {
                    // open the sound file as a Java input stream
                    String hop = "res/music1.wav";
                    InputStream in = new FileInputStream(hop);
                    // create an audiostream from the inputstream
                    AudioStream audioStream = new AudioStream(in);
                    // play the audio clip with the audioplayer class
                    AudioPlayer.player.start(audioStream);
                } //end try
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error loading sound file.");
                }//end catch

            }});//end timer

        music.start();

        //=======initializing array lists=======

        keys = new boolean[512]; //should be enough to hold any key code.
        frog = new Frog();
        cars = new ArrayList<Car>();
        logs = new ArrayList<Log>();

        //=======car generator=======

        while(cars.size() < carLimit + 1){

            carType = (int)(Math.random() * 5);
            x = (int)(21 * Math.random()) * 50;
            direction = (int)(Math.random() * 2) * 180;

            if(direction == 180){
                speed = 5;
                lane = 500 - (int)(Math.random() * 3) * 100;
            }//end if
            else{
                speed = -5;
                lane = 450 - (int)(Math.random() * 3) * 100;
            }//end else

            Car frogKiller = new Car(carType, x, lane, direction, speed);

            for (int j = 0; j < cars.size(); j++) {
                if(cars.get(j).getLoc().getX() == x && cars.get(j).getLoc().getY() == lane){
                    cars.remove(j);
                    j--;
                }//end if
            }//end for

            cars.add(frogKiller);

        }//end for

        //============log generator===========

        while(logs.size() < logLimit + 1){

            x = (int)(5 * Math.random()) * 200;
            direction = (int)(Math.random() * 2) * 180;

            if(direction == 180){
                speed = 5;
                lane = 150 - (int)(Math.random() * 2) * 100;
            }//end if
            else{
                speed = -5;
                lane = 100;
            }//end else

            Log platform = new Log(x, lane, direction, speed);

            for (int j = 0; j < logs.size(); j++) {
                if(logs.get(j).getLoc().getX() == x && logs.get(j).getLoc().getY() == lane){
                    logs.remove(j);
                    j--;
                }//end if
            }//end for

            logs.add(platform);

        }//end for

        //==========Timer==========

        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                //=====move the frog=====
                if(keys[KeyEvent.VK_W]){
                    if(frog.getLoc().getY() > 0) {
                        frog.setDir(Sprite.NORTH);
                        frog.update();
                        keys[KeyEvent.VK_W] = false; //probably.  Makes 1 move per button press.
                    }//end if
                }//end if
                
                if(keys[KeyEvent.VK_A]){
                    
                    if(frog.getLoc().getX() > 0) {
                        
                        frog.setDir(Sprite.WEST);
                        frog.update();
                        keys[KeyEvent.VK_A] = false; //probably.  Makes 1 move per button press.
                        
                    }//end if
                    
                }//end if
                
                if(keys[KeyEvent.VK_S]){
                    
                    if(frog.getLoc().getY() + 50 < getHeight()) {
                        
                        frog.setDir(Sprite.SOUTH);
                        frog.update();
                        keys[KeyEvent.VK_S] = false; //probably.  Makes 1 move per button press.
                        
                    }//end if
                    
                }//end if

                if(keys[KeyEvent.VK_D]){
                    
                    if(frog.getLoc().getX() + 50 < getWidth()) {
                        
                        frog.setDir(Sprite.EAST);
                        frog.update();
                        keys[KeyEvent.VK_D] = false; //probably.  Makes 1 move per button press.
                        
                    }//end if
                    
                }//end if

                //======level skipper=======

                if(keys[KeyEvent.VK_P]){

                    if(frog.getLoc().getX() > 0) {

                        levelCounter++;
                        frog.setLoc(new Point(550, 500));

                    }//end if

                }//end if

                //=======world skipper=======

                if(keys[KeyEvent.VK_Z]){

                    if(frog.getLoc().getX() > 0) {

                        worldCounter++;
                        frog.setLoc(new Point(550, 500));

                    }//end if

                }//end if

                //=======moves the car=======

                for(Car frogKiller : cars) {

                    frogKiller.setDir(Sprite.EAST);
                    frogKiller.update();

                    if(frogKiller.getLoc().getX() > FRAMEWIDTH){
                        
                        frogKiller.setLoc(new Point(-50, (int)frogKiller.getLoc().getY()));
                        
                    }//end if

                    if(frogKiller.getLoc().getX() + 50 < 0){
                        
                        frogKiller.setLoc(new Point(getWidth(), (int)frogKiller.getLoc().getY()));
                        
                    }//end if

                    //=======detector========

                    if (frog.intersects(frogKiller)) {
                        
                        frog.setLoc(new Point(500, 550));
                        deathCounter++;
                        
                    }//end if

                }//end for

                onLog = false;
                inWater = false;

                if(frog.getLoc().getY() < 180) {
                    inWater = true;
                }//end if

                for(Log platform : logs) {

                    platform.setDir(Sprite.EAST);
                    platform.update();

                    if (platform.getLoc().getX() > FRAMEWIDTH) {
                        
                        platform.setLoc(new Point(-200, (int) platform.getLoc().getY()));
                        
                    }//end if

                    if (platform.getLoc().getX() + 200 < 0) {
                        
                        platform.setLoc(new Point(getWidth(), (int) platform.getLoc().getY()));
                        
                    }//end if

                    //=======detector========

                    if (frog.intersects(platform)) {
                        
                        onLog = true;
                        frog.setLoc(new Point((int) (frog.getLoc().getX() + platform.getSpeed()), (int) (frog.getLoc().getY())));
                        
                    }//end if

                }//end for

                //=====resets frog location=====

                if(inWater){
                    
                    if(!onLog){
                        
                        if(frog.getLoc().getY() > 40) {
                            
                            frog.setLoc(new Point(500, 550));
                            deathCounter++;
                            
                        }//end if
                        
                    }//end if
                    
                }//end if

                //=====win detector=====

                if(frog.getLoc().getY() < 40){
                    
                    levelCounter++;

                    //whenever the player beats level 5, it resets the level and changes to a new world
                    if(levelCounter > 4){

                        //the player advances to the next world after beating the 5th level
                        worldCounter++;
                        levelCounter = 0;
                        carLimit = 30;

                    }//end if

                    //=====car remover=====

                    for(int i = 0; i < cars.size(); i++){
                        
                        cars.remove(i);
                        i--;
                        
                    }//end for

                    //=====log remover=====

                    for (int i = 0; i < logs.size(); i++) {
                        
                        logs.remove(i);
                        i--;
                        
                    }//end for

                    frog.setLoc(new Point(500, 550));

                    //=======car generator=======

                    //each level spawns more cars
                    carLimit = carLimit + 10;

                    while(cars.size() < carLimit + 1){

                        carType = (int)(Math.random() * 5);
                        x = (int)(21 * Math.random()) * 50;
                        direction = (int)(Math.random() * 2) * 180;

                        if(direction == 180){

                            //each world makes cars faster
                            speed = 5 + worldCounter;
                            lane = 500 - (int)(Math.random() * 3) * 100;
                            
                        }//end if
                        else{
                            
                            speed = -5 - worldCounter;
                            lane = 450 - (int)(Math.random() * 3) * 100;
                            
                        }//end else

                        Car frogKiller = new Car(carType, x, lane, direction, speed);

                        for (int j = 0; j < cars.size(); j++) {
                            
                            if(cars.get(j).getLoc().getX() == x && cars.get(j).getLoc().getY() == lane){
                                
                                cars.remove(j);
                                j--;
                                
                            }//end if
                            
                        }//end for

                        cars.add(frogKiller);

                    }//end for

                    //============log generator===========

                    while(logs.size() < logLimit + 1){

                        x = (int)(5 * Math.random()) * 200;
                        direction = (int)(Math.random() * 2) * 180;

                        if(direction == 180){

                            //logs just get faster with each level, their speed resets with each new world
                            speed = 5 + levelCounter;
                            lane = 150 - (int)(Math.random() * 2) * 100;
                            
                        }//end if
                        else{

                            speed = -5 - levelCounter;
                            lane = 100;
                            
                        }//end else

                        Log platform = new Log(x, lane, direction, speed);

                        for (int j = 0; j < logs.size(); j++) {
                            
                            if(logs.get(j).getLoc().getX() == x && logs.get(j).getLoc().getY() == lane){
                                
                                logs.remove(j);
                                j--;
                                
                            }//end if
                            
                        }//end for

                        logs.add(platform);

                    }//end for

                }//end if

                repaint(); //always the last line.  after updating, refresh the graphics.

            }//end actionPerformed

        });//end timer

        timer.start();

        //==========KeyListener==========

        /*
        You probably don't need to modify this keyListener code.
         */

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {/*intentionally left blank*/ }
            //when a key is pressed, its boolean is switch to true.
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                keys[keyEvent.getKeyCode()] = true;
            }
            //when a key is released, its boolean is switched to false.
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                keys[keyEvent.getKeyCode()] = false;
            }
        });

        //==========mouseListener==========

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }//end mouseClicked
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX() + ", " + e.getY());
            }//end mousePressed
            @Override
            public void mouseReleased(MouseEvent e) {
            }//end mouseReleased
            @Override
            public void mouseEntered(MouseEvent e) {
            }//end mouseEntered
            @Override
            public void mouseExited(MouseEvent e) {
            }//end mouseExited
        });

    }//end FroggerMain

    //==========paintComponent==========

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //=======Background=======

        //-----water-----

        if(worldCounter == 0) {

            g2.setColor(new Color(48, 137, 203));

        }//end if
        else if(worldCounter == 1){

            g2.setColor(new Color(159, 200, 255));

        }//end else if

        g2.fillRect(0, 0, 1000, 240);

        //-----sand-----

        if(worldCounter == 0) {

            g2.setColor(new Color(217, 221, 51));
            g2.fillRect(0, 185, 1000, 50);
            g2.setColor(new Color(222, 199, 54));

            for (int i = 0; i < 20; i++) {

                Tri tri1 = new Tri(i * triLength, 235, i * triLength + triLength / 2, 185, i * triLength + triLength, 235);
                tri1.fillTri(g2);

            }//end for

        }//end if
        else if(worldCounter == 1){

            g2.setColor(new Color(211, 216, 194));
            g2.fillRect(0, 185, 1000, 50);
            g2.setColor(new Color(233, 255, 243));

            for (int i = 0; i < 20; i++) {

                Tri tri1 = new Tri(i * triLength, 235, i * triLength + triLength / 2, 185, i * triLength + triLength, 235);
                tri1.fillTri(g2);

            }//end for

        }//end else if

        //-----grass-----

        if(worldCounter == 0){

            g2.setColor(new Color(98, 224, 52));
            g2.fillRect(0, 530, 1000, 600 - 530);
            g2.setColor(new Color(48, 203, 50));

            for (int i = 0; i < 20; i++) {

                Tri tri1 = new Tri(i * triLength, 535, i * triLength + triLength / 2, 600, i * triLength + triLength, 535);
                tri1.fillTri(g2);

            }//end for

        }//end if
        else if(worldCounter == 1){

            g2.setColor(new Color(90, 118, 59));
            g2.fillRect(0, 530, 1000, 600 - 530);
            g2.setColor(new Color(170, 208, 184));

            for (int i = 0; i < 20; i++) {

                Tri tri1 = new Tri(i * triLength, 535, i * triLength + triLength / 2, 600, i * triLength + triLength, 535);
                tri1.fillTri(g2);

            }//end for

        }//end else if

            //============road=============

            g2.setColor(new Color(74, 74, 74));
            g2.fillRect(0, 235, 1000, 300);

            for (int i = 0; i < 7; i++) {

                g2.setColor(new Color(118, 118, 118));
                g2.setStroke(new BasicStroke(5));
                g2.drawLine(0, 535 - i * 50, 1000, 535 - i * 50);

            }//end for

            g2.setStroke(new BasicStroke(2));
            g2.setColor(new Color(158, 171, 48));

            for (int i = 0; i < 6; i++) {

                for (int j = 0; j < 20; j++) {

                    g2.drawLine(j * 1000 / 20, 535 - i * 50 - 25, j * 1000 / 20 + 20, 535 - i * 50 - 25);

                }//end for

            }//end for

            //=====finish line=====

            for (int i = 0; i < 3; i++) {

                for (int j = 0; j < 100; j++) {

                    if ((j + i) % 2 == 0) {

                        g2.setColor(new Color(41, 41, 41));

                    }//end if
                    else {

                        g2.setColor(new Color(214, 214, 214));

                    }//end else

                    g2.fillRect(j * 10, i * 10, 10, 10);

                }//end for

            }//end for

        //=======drawing level counter=======

        if(worldCounter == 0) {

            g2.setColor(new Color(198, 218, 234));

            g2.fillRect(850, 550, 140, 40);
            g2.fillRect(5, 550, 155, 40);
            g2.fillRect(490, 550, 145, 40);

            g2.setColor(new Color(82, 119, 165));

        }//end if
        else if(worldCounter == 1){

            g2.setColor(new Color(76, 81, 234));

            g2.fillRect(850, 550, 140, 40);
            g2.fillRect(5, 550, 155, 40);
            g2.fillRect(490, 550, 145, 40);

            g2.setColor(new Color(222, 228, 246));

        }//end else if

        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.drawString("Level: " + (levelCounter + 1), 860, 580);

        //=======drawing death counter=======

        g2.drawString("Deaths: " + deathCounter, 15, 580);

        //=======drawing world counter=======

        g2.drawString("World: " + (worldCounter + 1), 500, 580);

        //====drawing sprites=====

        for(Log platform : logs){

            platform.draw(g2);

        }//end for

        frog.draw(g2);

        for(Car frogKiller : cars){

            frogKiller.draw(g2);

        }//end for

    }//end paintComponent

    //==========psvm==========

    //sets ups the panel and frame.  Probably not much to modify here.
    public static void main(String[] args) {

        //=====JFrame=====
        JFrame window = new JFrame("Frogger!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, FRAMEWIDTH, FRAMEHEIGHT + 22); //(x, y, w, h) 22 due to title bar.

        //=====panel=====
        FroggerMain panel = new FroggerMain();
        panel.setSize(FRAMEWIDTH, FRAMEHEIGHT);
        panel.setFocusable(true);
        panel.grabFocus();

        //=====window=====
        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);

    }//end psvm

    //-----------------------------

}//end class