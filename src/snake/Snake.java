/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

class GamePanel extends JPanel implements Runnable, KeyListener{ 
 public static final int WIDTH = 200;
 public static final int HEIGHT = 200;
  
 private Graphics2D g2d;
 private BufferedImage image;
 
 private Thread thread;
 private boolean running;
 private long targetTime;
 
 private final int SIZE = 10;
 private Entity head, apple;
 private ArrayList<Entity> snake;
 private int score;
 private int level;
 private boolean gameover;
 
 private int dx, dy;
 private boolean up, down, right, left, start;
 
 public GamePanel()
 {
     setPreferredSize(new Dimension(WIDTH, HEIGHT));
     setFocusable(true);
     requestFocus();
     addKeyListener(this);
 }

    @Override
    public void addNotify() {
        super.addNotify();
        thread = new Thread(this);
        thread.start();
    }
  
    private void setFPS(int fps){
        targetTime = 1000 / fps;
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
      int k = ke.getKeyCode();
      if (k == KeyEvent.VK_UP) up = true;
      if (k == KeyEvent.VK_DOWN) down = true;
      if (k == KeyEvent.VK_RIGHT) right = true;
      if (k == KeyEvent.VK_LEFT) left = true;
      if (k == KeyEvent.VK_ENTER) start = true;
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
      int k = ke.getKeyCode();
      if (k == KeyEvent.VK_UP) up = false;
      if (k == KeyEvent.VK_DOWN) down = false;
      if (k == KeyEvent.VK_RIGHT) right = false;
      if (k == KeyEvent.VK_LEFT) left = false;
      if (k == KeyEvent.VK_ENTER) start = false;
    }
    
  
     @Override
    public void run() {
       if (running) return;
       init();
       long startTime;
       long elapsed;
       long wait;
       while(running){
           startTime = System.nanoTime();
           update();
           requestRender();
           elapsed = System.nanoTime() -startTime;
           wait = targetTime - elapsed / 1000000;
           if(wait > 0){
               try {
                   Thread.sleep(wait);
               } catch (Exception ex) {
                   ex.printStackTrace();
               }
           } 
       }
    }

    private void init() {
        image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_ARGB );
        g2d = image.createGraphics();
        running = true;
        setUplevel();
        gameover = false;
        level = 1;
        setFPS(level * 10);
    }
    private void setUplevel(){
        snake = new ArrayList <Entity>();
        head = new Entity(SIZE);
        head.setPosition(WIDTH / 2, HEIGHT / 2);
        snake.add(head);
        
        for (int i = 1; i < 5; i++)
        {
            Entity e = new Entity (SIZE);
            e.setPosition(head.getX() + (i * SIZE), head.getY());
            snake.add(e);
        }
        apple = new Entity (SIZE);
        setApple();
        score = 0;
        gameover = false;
        level = 1;
    }
    public void setApple(){
        int x = (int)(Math.random()* (WIDTH - SIZE));
        int y = (int)(Math.random()* (HEIGHT - SIZE));
        x = x - (x % SIZE);
        y = y - (y % SIZE);
        apple.setPosition(x, y);

    }
            
            
    private void requestRender() {
        render(g2d);
        Graphics g = getGraphics();
        g.drawImage (image, 0,0,null);
        g.dispose();
    }

    private void update() {
        if(gameover){
            if(start){
                setUplevel();
            }
            return;
        }
       if (up && dy == 0)
        {
         dy = -SIZE;
         dx = 0;
        }
       if (down && dy == 0)
       {
         dy = SIZE;
         dx = 0;
       }
        if (left && dx == 0)
       {
         dy = 0;
         dx = -SIZE;
       }
         if (right && dx == 0 && dy != 0)
       {
         dy = 0;
         dx = SIZE;
       }
         if (dx != 0 || dy != 0){
         for (int i = snake.size() - 1; i > 0; i--)
         {
             snake.get(i).setPosition(snake.get(i-1).getX(),
                     snake.get(i-1).getY());
         }
         head.move(dx, dy);
         }
         for (Entity e : snake)
         {
             if (e.isCollision(head))
             {
                 gameover = true;
                 break;
             }
         }
         
         if (apple.isCollision(head))
         {
             score++;
             setApple();
             Entity e = new Entity (SIZE);
             e.setPosition(-100, -100);
             snake.add(e);
             level++;
             
         }
         
         if (head.getX() < 0) head.setX(WIDTH);
         if (head.getY() < 0) head.setY(HEIGHT);
         if (head.getX() > WIDTH) head.setX(0);
         if (head.getY() > HEIGHT) head.setY(0);

    }
    public void render (Graphics2D g2d){
        g2d.clearRect(0, 0, WIDTH, WIDTH);
        g2d.setColor(Color.GREEN);
        for (Entity e : snake)
        {
            e.render(g2d);
        }
        g2d.setColor(Color.RED);
        apple.render(g2d);
        if (gameover){
            g2d.drawString("GameOver!",50,100);
        }
        
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score : " + score, 10, 10);
        if(dx == 0 && dy == 0)
        {
            g2d.drawString("Ready!",50, 100);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {}
}

class Entity{
    private int x,y,size;
    public Entity(int size)
    {
        this.size = size;
    }
    public int getX()
    {
    return x;
    }
    public int getY()
    {
    return y;
    }
    public void setX(int x)
    {
      this.x = x;
    }
    public void setY (int y)
    {
        this.y = y;
    }
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public void move (int dx, int dy)
    {
        x += dx;
        y += dy;
    }
    public Rectangle getBound()
    {
        return new Rectangle (x, y, size, size);
    }
    public boolean isCollision(Entity o)
    {
        if (o == this) return false;
        return getBound().intersects(o.getBound());
    }
    public void render (Graphics2D g2d)
    {
        g2d.fillRect(x + 1, y + 1, size - 2, size - 2);
    }
}
/**
 *
 * @author Henri Ojakangas
 */
public class Snake {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      JFrame frame = new JFrame ("Snake");
      frame.setContentPane(new GamePanel());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(false);
      frame.pack();
      frame.setPreferredSize(new Dimension(GamePanel.WIDTH,GamePanel.HEIGHT));
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    }
    
}
