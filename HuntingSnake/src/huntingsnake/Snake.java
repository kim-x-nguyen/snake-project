 package huntingsnake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Snake {
    int lenght = 3;
    int []x;
    int []y;
    
    public static int GO_UP = 1;
    public static int GO_DOWN = -1;
    public static int GO_LEFT = 2;
    public static int GO_RIGHT = -2;
    
    int vector = Snake.GO_DOWN;
    
    long t1 = 0; //starting time
    long t2 = 0; //open mount speed
    
    int speed = 200;
    
    int maxLenght = 10;
    
    boolean updateAfterChangeVector = true;
    
    public Snake(){
        x = new int[20];
        y = new int[20];
        
        x[0] = 5;
        y[0] = 4;
        
        x[1] = 5;
        y[1] = 3;
        
        x[2] = 5;
        y[2] = 2;
    }
    public void resetGame(){
        x = new int[20];
        y = new int[20];
        x[0] = 5;
        y[0] = 4;
        
        x[1] = 5;
        y[1] = 3;
        
        x[2] = 5;
        y[2] = 2;
        
        lenght = 3;
        vector = Snake.GO_DOWN;
    }
    public void setVector(int v){
        if(vector != -v && updateAfterChangeVector){
            vector = v;
            updateAfterChangeVector = false;
        }
    }
    public boolean checkTheCoordinates(int x1, int y1){//check whether the target is generated inside the snake or not
        for(int i = 0; i < lenght; i++){
            if(x[i] == x1  && y[1] == y1)
                return true;         
        }
        return false;
    }
    public Point castTheCoordinates(){//Create the new target for the snake
        Random r = new Random();
        int x;
        int y;
        do{
            x = r.nextInt(19);
            y = r.nextInt(19);
        }
        while(checkTheCoordinates(x, y));
        
        return new Point(x, y);
    }
    public int getCurrentSpeed(){
        int speed = 200;
        for(int i = 0; i < GameScreen.currentLevel; i++)
            speed*=0.8;
        return speed;
    }
    public void update(){
        if(lenght == maxLenght){//when the snake get to the max lenght, reset game and increase the speed
            GameScreen.Activation = false;
            resetGame();
            speed = (int)(speed * 0.8);
            GameScreen.currentLevel++;
            maxLenght += 5;
            speed = getCurrentSpeed();
        }
        for(int i = 1; i < lenght; i++){//The snake touchs itself, game over
            if(x[0]==x[i]&& y[0]==y[i]){
                GameScreen.Activation = false;
                GameScreen.endGame = true;
                GameScreen.grade = 0;//Whenever restart game, restart level and grade
                GameScreen.currentLevel = 1;
            }
                
        }
        
        if(System.currentTimeMillis()-t2>100){
            updateAfterChangeVector = true;
            
            Data.HeadGoesUp.update();
            Data.HeadGoesDown.update();
            Data.HeadGoesRight.update();
            Data.HeadGoesLeft.update();

        t2 = System.currentTimeMillis();
        }
        //moving speed
        if(System.currentTimeMillis()-t1>speed){//moving speed
            Data.HeadGoesUp.update();
            if(GameScreen.backGround[x[0]][y[0]]==2){
                lenght++;//change the lenght of the snake when it eats the point
                GameScreen.backGround[x[0]][y[0]]=0;
                //ater the snake eating the target, new point will appear
                GameScreen.backGround[castTheCoordinates().x][castTheCoordinates().y]=2;
                GameScreen.grade += 100;//every time snake eats 1 target, add 100 grade
            }
            for(int i = lenght-1; i> 0; i--){
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }
            if(vector == Snake.GO_UP)
                y[0]--;
            if(vector == Snake.GO_DOWN)
                y[0]++;
            if(vector == Snake.GO_LEFT)
                x[0]--;
            if(vector == Snake.GO_RIGHT)
                x[0]++;
            
            if(x[0] < 0)//if the sanke moves away from the border, it will come back from the border otherside
                x[0] = 19;
            if(x[0] > 19)
                x[0] = 0;
            if(y[0] < 0)
                y[0] = 19;
            if(y[0] > 19)
                y[0] = 0;
            
            t1 = System.currentTimeMillis();//set t1 to the new value
        }
    }
    public void paintASnake(Graphics g){//paint a Snake on the net
        g.setColor(Color.red);
        for(int i = 0; i < lenght; i++)
            g.drawImage(Data.imageBody, x[i] * 20 + GameScreen.padding, y[i] * 20 + GameScreen.padding, null);//draw snake body
        
        if(vector == Snake.GO_UP)
            g.drawImage(Data.HeadGoesUp.getCurrentImage(), x[0]*20 - 6 + GameScreen.padding, y[0]*20 -6 + GameScreen.padding, null);//adjust the snake head
        else if(vector == Snake.GO_DOWN)
            g.drawImage(Data.HeadGoesDown.getCurrentImage(), x[0]*20 - 6 + GameScreen.padding, y[0]*20 -6 + GameScreen.padding, null);
        else if(vector == Snake.GO_RIGHT)
            g.drawImage(Data.HeadGoesRight.getCurrentImage(), x[0]*20 - 6 + GameScreen.padding, y[0]*20 -6 + GameScreen.padding, null);
        else if(vector == Snake.GO_LEFT)
            g.drawImage(Data.HeadGoesLeft.getCurrentImage(), x[0]*20 - 6 + GameScreen.padding, y[0]*20 -6 + GameScreen.padding, null);
    }
}
