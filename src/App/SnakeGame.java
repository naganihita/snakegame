package App;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener,KeyListener{
	private class Tile{
		int x;
		int y;
		Tile(int x,int y){
			this.x=x;
			this.y=y;
		}
	}
	//SNAKE
	Tile snakeHead;
	ArrayList<Tile> snakeBody;
	//FOOD
	Tile food;
	
	Random random;
	
	//Game logic
	
	Timer gameLoop;
	int velocityX;
	int velocityY;
	int boardwidth;
	int boardheight;
	int tileSize=25;
	boolean gameOver=false;
	SnakeGame(int boardwidth,int boardheight){
		this.boardwidth=boardwidth;
		this.boardheight=boardheight;
		setPreferredSize(new Dimension(this.boardwidth,this.boardheight));
		setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);
		snakeHead=new Tile(5,5);
		snakeBody=new ArrayList<Tile>();
		food=new Tile(10,10);
		random=new Random();
		placeFood();
		//velocityX=-1;
		//velocityY=0;
		
		gameLoop=new Timer(100,this);
		gameLoop.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		}
	public void draw(Graphics g) {
		//GRID
//		for(int i=0;i<boardwidth/tileSize;i++) {
//			g.drawLine(i*tileSize,0,i*tileSize,boardwidth);
//			g.drawLine(0,i*tileSize, boardwidth, i*tileSize);
//			
//		}
		//snakefood
		g.setColor(Color.yellow);
		//g.fillRect(food.x*tileSize,food.y*tileSize, tileSize, tileSize);
		g.fill3DRect(food.x*tileSize,food.y*tileSize, tileSize, tileSize,true);
		//snakehead
		g.setColor(Color.pink);
		//g.fillRect(snakeHead.x*tileSize,snakeHead.y*tileSize, tileSize, tileSize);
		g.fill3DRect(snakeHead.x*tileSize,snakeHead.y*tileSize, tileSize, tileSize,true);
		
		//SNAKE BODY
		for(int i=0;i<snakeBody.size();i++) {
			Tile snakePart=snakeBody.get(i);
			//g.fillRect(snakePart.x*tileSize,snakePart.y*tileSize , tileSize, tileSize);
			g.fill3DRect(snakePart.x*tileSize,snakePart.y*tileSize , tileSize, tileSize,true);
			
		}
		g.setFont(new Font("Arial",Font.PLAIN,30));
		if(gameOver) {
			g.setColor(Color.red);
			g.drawString("Game Over:"+ String.valueOf(snakeBody.size()),tileSize-16,tileSize);
		}
		else {
			g.drawString("Score: "+String.valueOf(snakeBody.size()),tileSize-16,tileSize);
		}
	}
	public void placeFood() {
		food.x=random.nextInt(boardwidth/tileSize);//0to24
		food.y=random.nextInt(boardheight/tileSize);
		
	}
	public boolean collision(Tile tile1,Tile tile2) {
		return tile1.x == tile2.x && tile1.y == tile2.y;
	}
	public void move() {
		if(collision(snakeHead,food)) {
			snakeBody.add(new Tile(food.x,food.y));
			placeFood();
		}
		
		//snakeBody
		
		for(int i=snakeBody.size()-1;i>=0;i--) {
			Tile snakePart=snakeBody.get(i);
			if(i==0) {
				snakePart.x=snakeHead.x;
				snakePart.y=snakeHead.y;
			}else {
				Tile prevSnakePart=snakeBody.get(i-1);
				snakePart.x=prevSnakePart.x;
				snakePart.y=prevSnakePart.y;
			}
		}
		snakeHead.x+=velocityX;
		snakeHead.y+=velocityY;
		
		//GAMEOVER
		for(int i=0;i<snakeBody.size();i++) {
			Tile snakePart=snakeBody.get(i);
			//collide SnakeHead
			if(collision(snakeHead,snakePart)) {
				gameOver=true;
			}
			if(snakeHead.x*tileSize<0|| snakeHead.x*tileSize>boardwidth||snakeHead.y*tileSize<0||snakeHead.y*tileSize>boardheight) {
				gameOver=true;
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		if(gameOver) {
			gameLoop.start();
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP && velocityY!=1) {
			velocityX=0;
			velocityY=-1;
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY != -1) {
			velocityX=0;
			velocityY=1;
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX!=1) {
			velocityX=-1;
			velocityY=0;
		}else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX!=-1){
			velocityX=1;
			velocityY=0;
		}
		
	}
	
	//do not need
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
