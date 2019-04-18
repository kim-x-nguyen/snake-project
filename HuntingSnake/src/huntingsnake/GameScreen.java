package huntingsnake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import sun.java2d.pipe.DrawImage;

public class GameScreen extends JPanel implements Runnable{//Same structure for every game
    static int [][] backGround = new int [20][20];
    static int padding = 10;
    static int WIDTH = 400;
    static int HEIGHT = 400;
    static boolean Activation = false;
    static boolean enableTextStartGame = true;
    Snake snake;
    Thread thread;
    static int currentLevel = 1;
    static int grade = 0;
    static boolean endGame = false;
    public GameScreen(){
        
        snake = new Snake();
        Data.loadImage();//creat snake head
        Data.loadAllAnimation();//create the animation
        backGround[10][10] = 2;
        thread = new Thread(this);
        thread.start();
    }

    public void run(){
        long t = 0;
        long t2 = 0;
        while (true) { 
            if(System.currentTimeMillis()-t2>500){
                enableTextStartGame =! enableTextStartGame;
                t2 = System.currentTimeMillis();
            }
            if(Activation){
                    if(System.currentTimeMillis()-t>200){
                    Data.Worm.update();
                    t = System.currentTimeMillis();
                }
                snake.update();
            }
            
            repaint();//repaint the new frame
            try {
                sleep(20);
            } catch (InterruptedException ex) {}
        }
    }
    public void paintBackGround(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH + padding * 2 + 200, HEIGHT + padding * 2);//adjust the play ground
        for(int i = 0; i < 20; i++)
            for(int j = 0; j < 20; j++){
                //g.fillRect(i * 20 + 1, j * 20 + 1, 18, 18);//Attention here, we will create a net of cordination
                if(backGround[i][j]==2){
                    g.drawImage(Data.Worm.getCurrentImage(), i*20-7 + padding, j*20-7 + padding, null);//draw the worm
                }
         }
        
}
    private void createBorder(Graphics g){
        g.setColor(Color.orange);
        g.drawRect(0, 0, WIDTH+padding*2, HEIGHT+padding*2);
        g.drawRect(1, 1, WIDTH+padding*2-2, HEIGHT+padding*2-2);
        g.drawRect(2, 2, WIDTH+padding*2-4, HEIGHT+padding*2-4);
        
        g.drawRect(0, 0, WIDTH+padding*2 + 200, HEIGHT+padding*2);
        g.drawRect(1, 1, WIDTH+padding*2-2 + 200, HEIGHT+padding*2-2);
        g.drawRect(2, 2, WIDTH+padding*2-4 + 200, HEIGHT+padding*2-4);
    }
    public void paint(Graphics g){
        paintBackGround(g);
        snake.paintASnake(g);
        createBorder(g);
        if(!Activation){
            if(enableTextStartGame){
                g.setColor(Color.white);
                g.setFont(g.getFont().deriveFont(18.0f));
                g.drawString("PRESS SPACE TO PLAY!", 110, 200);
            }
        }
        if(endGame){
            g.setColor(Color.white);
            g.setFont(g.getFont().deriveFont(28.0f));
            g.drawString("YOU DIED", 150, 250);
        }
        g.setColor(Color.white);//PART OF THE DISPLAY LEVEL
        g.setFont(g.getFont().deriveFont(28.0f));
        g.drawString("LEVEL: "+ currentLevel, 450, 100);
        
        g.setFont(g.getFont().deriveFont(20.0f));//PART OF THE DISPLAY GRADE
        g.drawString("GRADE: "+ grade, 450, 150);
    }
}
