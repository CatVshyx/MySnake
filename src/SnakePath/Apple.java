package SnakePath;

import java.util.Random;

import SnakePath.Snake.Part;
class MyThread extends Thread{
	private Apple ap;
	MyThread(Apple currApple){
		this.ap = currApple;
	}
	@Override
    public void run() {
        while(true) {
        	int px = (new Random().nextInt(24)%20)+2;
        	int py = (new Random().nextInt(24)%20)+2;
        	if (px != ap.snake.getX()) {
        		boolean isClear = true;
        		for(Part el : ap.snake.getParts()) {
        			if ( px != el.x) continue;
        			isClear = false;
        			break;
        		}
        		if (isClear) {
        			ap.setX(px);
        			ap.setY(py);
        			break;
        		}
        		continue;
        	}
        }
    }
}
public class Apple {
	private int x;
	private int y;
	public Snake snake;
	Apple(Snake snake){
		this.snake = snake;
		setPosition();
	}
	public void setPosition() {
			new MyThread(this).start();;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
}
