package huntingsnake;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Data {
    public static BufferedImage sprite;
    
    
    public static Image imageHead;
    public static Image imageHead_GoLeft;
    public static Image imageHead_GoRight;
    public static Image imageHead_GoUp;
    public static Image imageHead_GoDown;
    
    public static Image imageWorm2;
    public static Image imageWorm3;
    
    public static Image imageBody;
    public static Image imageWorm;
    
    public static Animation HeadGoesUp;
    public static Animation HeadGoesDown;
    public static Animation HeadGoesRight;
    public static Animation HeadGoesLeft;
    
    public static Animation Worm;
    public static void loadImage(){
        try{
            sprite = ImageIO.read(new File("res/sprite1.png"));
            
            imageHead = sprite.getSubimage(2, 3, 30, 30);
            imageBody = sprite.getSubimage(6, 79, 20, 20);
            
            imageHead_GoLeft = sprite.getSubimage(75, 3, 30, 30);
            imageHead_GoRight = sprite.getSubimage(110, 3, 30, 30);
            imageHead_GoUp = sprite.getSubimage(145, 3, 30, 30);
            imageHead_GoDown = sprite.getSubimage(39, 3, 30, 30);
            
            imageWorm = sprite.getSubimage(2, 40, 30, 30);
            imageWorm2 = sprite.getSubimage(32, 40, 30, 30);
            imageWorm3 = sprite.getSubimage(63, 40, 30, 30);
        }catch(Exception e){}
    }
    public static void loadAllAnimation(){
        HeadGoesUp = new Animation();
        HeadGoesUp.addImage(imageHead);
        HeadGoesUp.addImage(imageHead_GoUp);
        
        HeadGoesDown = new Animation();
        HeadGoesDown.addImage(imageHead);
        HeadGoesDown.addImage(imageHead_GoDown);
        
        HeadGoesRight = new Animation();
        HeadGoesRight.addImage(imageHead);
        HeadGoesRight.addImage(imageHead_GoRight);
        
        HeadGoesLeft = new Animation();
        HeadGoesLeft.addImage(imageHead);
        HeadGoesLeft.addImage(imageHead_GoLeft);
        
        Worm = new Animation();
        Worm.addImage(imageWorm);
        Worm.addImage(imageWorm2);
        Worm.addImage(imageWorm3);
        Worm.addImage(imageWorm2);
    }
}
