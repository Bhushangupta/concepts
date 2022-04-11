package com.bhu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Timer;

/** InnerMazePosition class is pojo class for Maze position */
class InnerMazePosition {
	private LinkedList<int[]> innerMazePosition = new LinkedList<int[]>();

	public InnerMazePosition(LinkedList<int[]> ip) {
		this.innerMazePosition = ip;
	}

	public InnerMazePosition() {
	}

	public LinkedList<int[]> getInnerMazePosition() {
		return innerMazePosition;
	}

	public void setInnerMazePosition(LinkedList<int[]> innerMazePosition) {
		this.innerMazePosition = innerMazePosition;
	}

}

public class NokiaSnakeVer4 extends Frame implements KeyListener, MouseMotionListener {

	/**
	 * Constant attributes for snake directions
	 */
	private static final int MOVING_UP = 1;
	private static final int MOVING_DOWN = 2;
	private static final int MOVING_LEFT = 3;
	private static final int MOVING_RIGHT = 4;
	private static final int MOVING_NE = 5;
	private static final int MOVING_SE = 6;
	private static final int MOVING_SW = 7;
	private static final int MOVING_NW = 8;
	
	
	private int pos_x = 20, pos_y = 40;
	private int sLength = 1;
	private int currentDirection;
	private int lastDirection;
	private int[] headPos = new int[2];
	private static final long serialVersionUID = 6891709241892538643L;
	private LinkedList<int[]> snakePosition = new LinkedList<int[]>();
	private LinkedList<int[]> mazePosition = new LinkedList<int[]>();
	private LinkedList<InnerMazePosition> iList = new LinkedList<InnerMazePosition>();

	/** true if we are in drag */
	boolean inDrag = false;

	/** starting location of a drag */
	int startX = -1, startY = -1;

	/** current location of a drag */
	int curX = -1, curY = -1;
	
	/** Set Snake Speed */
	private int snakeSpeed = 300;

	/** 
	 * Storing the key pair for diganol move
	 * @param args
	 */
	private int keyPair[] = new int[2];
	
	public static void main(String args[]) {
		new NokiaSnakeVer4();
	}

	public NokiaSnakeVer4() {
		super("Nokia Snake");
		headPos[0] = pos_x;
		headPos[1] = pos_y;
		snakePosition.add(new int[] { pos_x, pos_y });
		for (int i = 30; i <= 290; i += 10) {
			mazePosition.add(new int[] { 10, i });
		}
		for (int i = 10; i <= 290; i += 10) {
			mazePosition.add(new int[] { i, 290 });
		}
		for (int i = 290; i >= 30; i -= 10) {
			mazePosition.add(new int[] { 290, i });
		}
		for (int i = 290; i >= 10; i -= 10) {
			mazePosition.add(new int[] { i, 30 });
		}
		this.addMouseMotionListener(this);
		ActionListener listener = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4028663413927263024L;

			public void actionPerformed(ActionEvent e) {
				while (snakePosition.size() > sLength) {
					snakePosition.removeFirst();
				}

				switch (currentDirection) {
				case MOVING_UP:
					pos_y = (pos_y <= 0) ? 300 : pos_y - 10;
					break;
				case MOVING_DOWN:
					pos_y = (pos_y >= 300) ? 0 : pos_y + 10;
					break;
				case MOVING_LEFT:
					pos_x = (pos_x <= 0) ? 400 : pos_x - 10;
					break;
				case MOVING_RIGHT:
					pos_x = (pos_x >= 400) ? 0 : pos_x + 10;
					break;
				case MOVING_NE:
					pos_x = (pos_x >= 400) ? 0 : pos_x + 10;
					pos_y = (pos_y <= 0) ? 300 : pos_y - 10;
					break;
				case MOVING_SE:
					pos_x = (pos_x >= 400) ? 0 : pos_x + 10;
					pos_y = (pos_y >= 300) ? 0 : pos_y + 10;
					break;
				case MOVING_SW:
					pos_x = (pos_x <= 0) ? 400 : pos_x - 10;
					pos_y = (pos_y >= 300) ? 0 : pos_y + 10;
					break;
				case MOVING_NW:
					pos_x = (pos_x <= 0) ? 400 : pos_x - 10;
					pos_y = (pos_y <= 0) ? 300 : pos_y - 10;
					break;
				}
				headPos[0] = pos_x;
				headPos[1] = pos_y;

				snakePosition.offer(Arrays.copyOf(headPos, 2));
				repaint();
				eatFood();
				killSnake();
			}
		};
		
		Timer timer = new Timer(snakeSpeed , listener);
		timer.start();
		// Set the size for the frame.
		setSize(620, 640);
		setVisible(true);

		this.addKeyListener(this);
		// Now, we want to be sure we properly dispose of resources
		// this frame is using when the window is closed. We use
		// an anonymous inner class adapter for this.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
	}

	/**
	 * The paint method provides the real magic. Here we cast the Graphics
	 * object to Graphics2D to illustrate that we may use the same old graphics
	 * capabilities with Graphics2D that we are used to using with Graphics.
	 **/
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.green);
		g2d.setStroke(new BasicStroke(10));

		if (inDrag == false && ip != null && ip.size() > 0) {
			iList.add(new InnerMazePosition(ip));
			ip = null;
		}

		int xMazePoints[] = new int[mazePosition.size()];
		int yMazePoints[] = new int[mazePosition.size()];

		int i = 0;
		for (int[] a : mazePosition) {
			xMazePoints[i] = a[0];
			yMazePoints[i] = a[1];
			i++;
		}
		g2d.setColor(Color.green);
		g2d.drawPolyline(xMazePoints, yMazePoints, xMazePoints.length);
		g2d.setColor(Color.red);
		g2d.drawLine(food_x, food_y, food_x, food_y);

		Iterator<InnerMazePosition> it = iList.iterator();
		while (it.hasNext()) {
			i = 0;
			LinkedList<int[]> innerMP = it.next().getInnerMazePosition();
			xMazePoints = new int[innerMP.size()];
			yMazePoints = new int[innerMP.size()];
			for (int[] a : innerMP) {
				xMazePoints[i] = a[0];
				yMazePoints[i] = a[1];
				i++;
			}
			g2d.setColor(Color.gray);
			g2d.drawPolyline(xMazePoints, yMazePoints, xMazePoints.length);
		}
		int xPoints[] = new int[snakePosition.size()];
		int yPoints[] = new int[snakePosition.size()];

		i = 0;
		for (int[] a : snakePosition) {
			xPoints[i] = a[0];
			yPoints[i] = a[1];
			i++;
		}
		g2d.setColor(Color.black);
		g2d.drawPolyline(xPoints, yPoints, xPoints.length);
		g2d.setColor(Color.blue);
		if (xPoints != null && yPoints != null) {
			g2d.fillOval(xPoints[i - 1] - 5, yPoints[i - 1] - 5, 10, 10);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	private boolean flag;
	@Override
	public void keyPressed(KeyEvent e) {
		if(flag){
			keyPair[0]=e.getKeyCode();
			flag=false;
		}else{
			keyPair[1]=e.getKeyCode();
			flag=true;
		}
		System.out.println(keyPair[0]+","+keyPair[1]);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		lastDirection = currentDirection;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			currentDirection = (lastDirection == MOVING_DOWN) ? MOVING_DOWN : MOVING_UP;
			break;
		case KeyEvent.VK_DOWN:
			currentDirection = (lastDirection == MOVING_UP) ? MOVING_UP : MOVING_DOWN;
			break;
		case KeyEvent.VK_LEFT:
			currentDirection = (lastDirection == MOVING_RIGHT) ? MOVING_RIGHT : MOVING_LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			currentDirection = (lastDirection == MOVING_LEFT) ? MOVING_LEFT : MOVING_RIGHT;
			break;
		case KeyEvent.VK_5:
			currentDirection = (lastDirection == MOVING_SW) ? MOVING_SW : MOVING_NE;
			break;
		case KeyEvent.VK_6:
			currentDirection = (lastDirection == MOVING_NE) ? MOVING_NE : MOVING_SW;
			break;
		case KeyEvent.VK_7:
			currentDirection = (lastDirection == MOVING_SE) ? MOVING_SE : MOVING_NW;
			break;
		case KeyEvent.VK_8:
			currentDirection = (lastDirection == MOVING_NW) ? MOVING_NW : MOVING_SE;
			break;
		}
		/**
		 * Move snake to diagonal direction with arrow keys
		 */
		if((keyPair[0]==KeyEvent.VK_DOWN && keyPair[1]==KeyEvent.VK_LEFT)||
				(keyPair[1]==KeyEvent.VK_DOWN && keyPair[0]==KeyEvent.VK_LEFT)){
			currentDirection = (lastDirection == MOVING_NE) ? MOVING_NE : MOVING_SW;
		}else if((keyPair[0]==KeyEvent.VK_DOWN && keyPair[1]==KeyEvent.VK_RIGHT)||
				(keyPair[1]==KeyEvent.VK_DOWN && keyPair[0]==KeyEvent.VK_RIGHT)){
			currentDirection = (lastDirection == MOVING_NW) ? MOVING_NW : MOVING_SE;
		}else if((keyPair[0]==KeyEvent.VK_UP && keyPair[1]==KeyEvent.VK_LEFT)||
				(keyPair[1]==KeyEvent.VK_UP && keyPair[0]==KeyEvent.VK_LEFT)){
			currentDirection = (lastDirection == MOVING_SE) ? MOVING_SE : MOVING_NW;
		}else if((keyPair[0]==KeyEvent.VK_UP && keyPair[1]==KeyEvent.VK_RIGHT)||
				(keyPair[1]==KeyEvent.VK_UP && keyPair[0]==KeyEvent.VK_RIGHT)){
			currentDirection = (lastDirection == MOVING_SW) ? MOVING_SW : MOVING_NE;
		}
	}

	private int food_x = 100, food_y = 100;

	private void eatFood() {
		if (headPos[0] == food_x && headPos[1] == food_y) {
			sLength += 1;
			setNextFoodPos();
		}
	}

	private void setNextFoodPos() {
		Random rand = new Random();
		int valueX = 0;
		while (valueX <= 35 || valueX >= 290) {
			valueX = rand.nextInt(290);
		}
		int valueY = 0;
		while (valueY <= 35 || valueY >= 290) {
			valueY = rand.nextInt(290);
		}
		Iterator<InnerMazePosition> it = iList.iterator();
		while (it.hasNext()) {
			LinkedList<int[]> innerMP = it.next().getInnerMazePosition();
			for (int i = 0; i < innerMP.size(); i++) {
				int[] p = innerMP.get(i);
				if (p[0] == valueX && p[1] == valueY) {
					valueX = rand.nextInt(290);
					valueY = rand.nextInt(290);
					break;
				}
			}
		}

		food_x = valueX - (valueX % 10);
		food_y = valueY - (valueY % 10);
	}

	private void killSnake() {
		boolean killit = false;
		int[] pos = snakePosition.peekLast();
		if (snakePosition.size() >= 4) {
			for (int i = 0; i < (snakePosition.size() - 1); i++) {
				int[] p = snakePosition.get(i);
				if (p[0] == pos[0] && p[1] == pos[1]) {
					killit = true;
					break;
				}
			}
		}
		for (int i = 0; killit == false && pos != null && i < mazePosition.size(); i++) {
			int[] p = mazePosition.get(i);
			if (p[0] == pos[0] && p[1] == pos[1]) {
				killit = true;
				break;
			}
		}
		Iterator<InnerMazePosition> it = iList.iterator();
		while (it.hasNext()) {
			LinkedList<int[]> innerMP = it.next().getInnerMazePosition();
			for (int i = 0; killit == false && pos != null && i < innerMP.size(); i++) {
				int[] p = innerMP.get(i);
				if (p[0] == pos[0] && p[1] == pos[1]) {
					killit = true;
					break;
				}
			}
		}
		if (killit) {
			System.exit(0);
		}
	}

	LinkedList<int[]> ip = new LinkedList<int[]>();

	// And two methods from MouseMotionListener:
	public void mouseDragged(MouseEvent e) {
		inDrag = true;
		Point p = e.getPoint();
		curX = p.x;
		curY = p.y;
		curX = curX - (curX % 10);
		curY = curY - (curY % 10);
		if (ip == null) {
			ip = new LinkedList<int[]>();
		}
		if (ip.size() > 0) {
			int[] lastEntry = ip.getLast();
			if (lastEntry[0] == curX && lastEntry[1] == curY) {
			} else {
				ip.add(new int[] { curX, curY });
			}
		} else {
			ip.add(new int[] { curX, curY });
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		inDrag = false;
	}
}