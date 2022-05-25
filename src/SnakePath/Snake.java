package SnakePath;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Snake {
	private int x = 5;
	private int y = 5;
	final int[] SNAKE_LEFT = {-1, 0};
	final int[] SNAKE_RIGHT = {1, 0};
	final int[] SNAKE_UP = {0, -1};
	final int[] SNAKE_DOWN = {0, 1};
	private int[] currentDirection = {1,0};
	private Visual v;
	private ArrayList <Part> parts = new ArrayList<>();
	Thread t1;
	boolean isAlive = true;
	Runnable process  = () ->{
		while (isAlive) {
			this.move(currentDirection);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int answer = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Inform", JOptionPane.YES_NO_OPTION);
		if ( answer  ==  1 ) { v.dispose(); }
		else { v.startGame(); }
	};
	Snake (Visual v){
		for (int i = 0; i < 2; i ++) {
			Part part = new Part();
			part.setXY(this.x-i, this.y);
			parts.add(part);
		}
		this.v = v;
		t1 = new Thread(process);
	}
	
	public void changeDirection (int[] direction) { 
		if (Math.abs(direction[0]) == Math.abs(currentDirection[0]) && Math.abs(direction[1])  == Math.abs(currentDirection[1])  )  return;
		currentDirection = direction; 
	}
	public void move(int[] direction) {
		if (isTaken((x+direction[0]), (y + direction[1]))){
			isAlive = false;
		}
		for (Apple apple : v.appleArray) {
			if (x == apple.getX() && y == apple.getY()) {
				apple.setPosition();
				addPart();
				break;
			}
		}
		for (int i = parts.size()-1 ; i >= 0; i--) {
			Part myPart = parts.get(i);
			if (i == 0) {
				myPart.setXY(x, y);
				break;
			}
			myPart.setXY( parts.get(i-1).x,  parts.get(i-1).y);
		}
		x += direction[0];
		y += direction[1];
		v.paint(v.getGraphics());
	}
	private boolean isTaken(int x , int y) {
		 for (Part el : parts) { if (x == el.x && y == el.y )  return true; }
		 if (1  > x  || x > (v.SCREEN_SIZE[0]/v.PIXEL_SIZE - 2)  || 2  > y  || y > (v.SCREEN_SIZE[1]/v.PIXEL_SIZE - 2)) { return true; }
		 return false;
	}
	private void addPart() {
		Part newPart = new Part();
		Part preTail = parts.get(parts.size()-2);
		Part tail = parts.get(parts.size()-1);
		int[][] posCoords = {{tail.x-1,tail.y},{tail.x+1,tail.y},{tail.x,tail.y-1},{tail.x,tail.y+1}}; 
		for (int i = 0; i < 4; i++) {
			int x = posCoords[i][0];
			int y = posCoords[i][1];
			if ( ((x-1) <= preTail.x  && preTail.x <= (x+1)) && ((y-1) <= preTail.y  && preTail.y <= (y+1)) ) { continue; }
			newPart.setXY(x,y);
			parts.add(newPart);
			break;
		}
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public ArrayList<Part> getParts(){
		return parts;
	}
	class Part{
		int x = 0;
		int y = 0;
		public void setXY(int x , int y) {
			this.x = x;
			this.y = y;
		}
	}
}
