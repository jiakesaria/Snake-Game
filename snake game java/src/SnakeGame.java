

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*; 

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    private class Tile 
    {
        int x, y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    int boardwidth;
    int boardheight;
    int tileSize= 25; 
    Tile snakeHead; 
    ArrayList<Tile> snakeBody; 
    Tile  apple;
    Random random;

    //game logic
    Timer gameLoop; 
    int velocity_x;
    int velocity_y;

    boolean gameOver = false; 
    
    SnakeGame(int boardwidth, int boardheight)
    {
        this.boardwidth = boardwidth;
        this.boardheight = boardheight;
        setPreferredSize(new Dimension(this.boardwidth, this.boardheight));
        setBackground(Color.black); 

        addKeyListener(this);
        setFocusable(true); 

        snakeHead= new Tile(5,5); 
        snakeBody= new ArrayList<Tile>(); 

        apple = new Tile(10,10); 

        random = new Random();
        placeApple();

        gameLoop = new Timer(170, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g); 
        draw(g); 
    }

    public void draw(Graphics g){
        //Apple
        g.setColor(Color.red); 
        //g.fillRect(apple.x*tileSize, apple.y* tileSize, tileSize, tileSize);
        g.fill3DRect(apple.x*tileSize, apple.y* tileSize, tileSize, tileSize, true);  
        //Snake
        g.setColor(Color.green); 
        //g.fillRect(snakeHead.x* tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x* tileSize, snakeHead.y*tileSize, tileSize, tileSize,true);  

        //Snake Body
        for(int i=0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i); 
            g.fill3DRect(snakePart.x* tileSize, snakePart.y*tileSize, tileSize, tileSize,true); 

        }

        g.setFont(new Font("Poppins", Font.ITALIC, 16 ));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over...  Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else{
            g.drawString("Score: "+String.valueOf(snakeBody.size()), tileSize - 16, tileSize);

        }
          
    }

    public void placeApple()
    {
        apple.x = random.nextInt(0,24); 
        apple.y = random.nextInt(0,24); 
    }

    public boolean meet(Tile t1, Tile t2){
       return t1.x == t2.x && t1.y == t2.y; 
    }

    public void move(){
        if (meet(snakeHead, apple)) {
            snakeBody.add(new Tile(apple.x, apple.y)); 
            placeApple(); 
        }

        for (int i=snakeBody.size()-1; i >=0; i--)
        {
            Tile snakePart = snakeBody.get(i);
            {
                if(i==0) {
                    snakePart.x=snakeHead.x;
                    snakePart.y=snakeHead.y;
                    }
                    else{
                    Tile prevSnakePart = snakeBody.get(i-1);
                    snakePart.x =  prevSnakePart.x;
                    snakePart.y =  prevSnakePart.y;
                 }
            }
        }

        snakeHead.x += velocity_x;
        snakeHead.y += velocity_y;
        for(int i=0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i); 

            if(meet(snakeHead, snakePart)){
                gameOver=true;
            }
        }

        if( snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardwidth || 
            snakeHead.y*tileSize < 0 ||  snakeHead.y*tileSize > boardheight)
            {
                gameOver=true; 
            }
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameOver) {
            gameLoop.stop(); 
        }
        move();
        repaint(); 
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_UP && velocity_y!=1) {
        velocity_x=0;
        velocity_y=-1; 
       }
       else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocity_y!=-1){
        velocity_x=0;
        velocity_y=1; 
       }
       else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocity_x!=1 ){
        velocity_x=-1;
        velocity_y=0; 
       }
       else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocity_x!=-1){
        velocity_x=1;
        velocity_y=0; 
       }
    }
        

    @Override
    public void keyTyped(KeyEvent e) {}
  
    @Override
    public void keyReleased(KeyEvent e) {}
       


}
