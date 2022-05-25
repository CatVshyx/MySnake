package SnakePath;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import SnakePath.Snake.Part;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Visual extends JFrame implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final int[] SCREEN_SIZE = {500,500};
	final int PIXEL_SIZE = 20;
	//                           HEAD          TAIL       CELLS       BORDER OF CELLS       APPLES 
	final Color[] CLASSIC_COLOR = { Color.ORANGE, Color.RED , Color.GREEN , Color.GRAY,        Color.WHITE };
	final Color[] NEON_COLOR =  { new Color (Color.decode("#cd5c5c").getRGB()), new Color (Color.decode("#22e0ff").getRGB()), new Color (Color.decode("#00008b").getRGB()), 
			new Color (Color.decode("#ff22ff").getRGB()), new Color (Color.decode("#ff033e").getRGB())  };
	private BufferedImage canvas;
	private Snake snake;
	 ArrayList<Apple> appleArray;
	private Color[] currentColor = NEON_COLOR;
	
	Visual(){
		String[] variants = {"NEON","CLASSIC"};
		String variant = (String) JOptionPane.showInputDialog(null,"Choose snake skin?","Configuration",JOptionPane.QUESTION_MESSAGE, null, variants, variants[0]);
		this.setSnakeColor(variant);
		this.setSize(SCREEN_SIZE[0],SCREEN_SIZE[1]);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.addKeyListener(this);
		this.setResizable(false);
		this.startGame();
	}
	public void startGame() {
		appleArray = new ArrayList<>();
		snake = new Snake(this);
		setApples(10);
		this.setVisible(true);
		this.paint(getGraphics());
		snake.t1.run();
		
	}
	private BufferedImage updateCanvas() throws IOException {
		canvas = new BufferedImage(SCREEN_SIZE[0],SCREEN_SIZE[1], BufferedImage.TYPE_INT_RGB);
		Graphics g = canvas.getGraphics();
		g.setColor(Color.black);
		g.drawRect(0,0,500,500);
		g.fillRect(0,0,500,500);
		//painting the CEELS and pixels of borders
		for (int i = 2; i < SCREEN_SIZE[0]/PIXEL_SIZE - 1; i++) {
			for (int x = 1; x < SCREEN_SIZE[1]/PIXEL_SIZE - 1; x++) {
				g.setColor(currentColor[3]); //BORDER 
				g.drawRect(x*PIXEL_SIZE,i*PIXEL_SIZE,PIXEL_SIZE,PIXEL_SIZE);
				g.fillRect(x*PIXEL_SIZE,i*PIXEL_SIZE,PIXEL_SIZE,PIXEL_SIZE);
				
				g.setColor(currentColor[2]); // CELLS
				g.fillRect(x*PIXEL_SIZE,i*PIXEL_SIZE,PIXEL_SIZE-1,PIXEL_SIZE-1);
			}
		}
		// painting a snake and apples
		
		g.setColor(currentColor[1]); //TAIL
		ArrayList<Part> parts = snake.getParts();
		for ( int i = 0; i < parts.size(); i++) {
			g.drawRect(parts.get(i).x*PIXEL_SIZE,parts.get(i).y*PIXEL_SIZE,PIXEL_SIZE,PIXEL_SIZE);
			g.fillRect(parts.get(i).x*PIXEL_SIZE,parts.get(i).y*PIXEL_SIZE,PIXEL_SIZE,PIXEL_SIZE);
		}
		g.setColor(currentColor[0]); //HEAD
		g.drawRect(snake.getX()*PIXEL_SIZE,snake.getY()*PIXEL_SIZE,PIXEL_SIZE,PIXEL_SIZE);
		g.fillRect(snake.getX()*PIXEL_SIZE,snake.getY()*PIXEL_SIZE,PIXEL_SIZE,PIXEL_SIZE);
		
		for ( Apple el : appleArray) {

		g.setColor(currentColor[4]); //APPLES
		g.drawRect(el.getX()*PIXEL_SIZE,el.getY()*PIXEL_SIZE,PIXEL_SIZE,PIXEL_SIZE);
		g.fillRect(el.getX()*PIXEL_SIZE,el.getY()*PIXEL_SIZE,PIXEL_SIZE,PIXEL_SIZE);
		}
	   
		return canvas;
	}
	public void paint(Graphics g){
		BufferedImage myImage = new BufferedImage(SCREEN_SIZE[0],SCREEN_SIZE[1], BufferedImage.TYPE_INT_RGB);
		try {
			myImage = this.updateCanvas();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(myImage,0,0,this);
		
	}
	public void setApples(int numb) {
		if ( numb <= 0 )  numb = 1; 
		for (int i = 0; i < numb; i++ ) {
			Apple newApple = new Apple(snake);
			appleArray.add(newApple);
		}
	}
	public void setSnakeColor(String ans) {
		if (ans == null) {
			return;
		}
		currentColor = ans.equals("NEON") ? NEON_COLOR : CLASSIC_COLOR;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		char s = e.getKeyChar();
		String changed = (s+"").toLowerCase();
		s = changed.charAt(0);
		switch(s) {
			case 'a': snake.changeDirection(snake.SNAKE_LEFT);
					  break;
			case 'w':  snake.changeDirection(snake.SNAKE_UP);
			  		  break;
			case 's': snake.changeDirection(snake.SNAKE_DOWN);
					  break;
			case 'd': snake.changeDirection(snake.SNAKE_RIGHT);
			  		  break;	
						
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
