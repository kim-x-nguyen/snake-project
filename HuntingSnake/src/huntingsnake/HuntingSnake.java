package huntingsnake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class HuntingSnake extends JFrame{
    GameScreen game;
    
    public HuntingSnake(){
        setSize(650,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//The application will stop when user click Close X      
        game = new GameScreen();
        add(game);
        this.addKeyListener(new handler());
        setVisible(true);
        
    }
    public static void main(String[] args) {
        HuntingSnake f = new HuntingSnake();
    }
    private class handler implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_SPACE){//press space bar to play and pause
                GameScreen.Activation =! GameScreen.Activation;
                if(GameScreen.endGame){//the snake touchs itself, game will stop
                    GameScreen.endGame = false; 
                    game.snake.resetGame();
                }
            }
            
            if(e.getKeyCode()==KeyEvent.VK_UP){
                game.snake.setVector(Snake.GO_UP);
            }
            if(e.getKeyCode()==KeyEvent.VK_DOWN){
                game.snake.setVector(Snake.GO_DOWN);
            }
            if(e.getKeyCode()==KeyEvent.VK_LEFT){
                game.snake.setVector(Snake.GO_LEFT);
            }
            if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                game.snake.setVector(Snake.GO_RIGHT);
            }
        
        }

        @Override
        public void keyReleased(KeyEvent e) {}
        
    }
}
