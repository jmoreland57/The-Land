import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.*;
import java.awt.event.*;

import java.awt.*;

public class Map {

	ArrayList<Tract> tracts;
	
	public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    
    public boolean zoomin = false;
    public boolean zoomout = false;
    
    public double yVelocity = 0;
    public double xVelocity = 0;
    
    public double scroll = 10;
    
    public Point.Double pos = new Point.Double(0,0); // position
    
    public Point mousePos = new Point();
    public Point lastMousePos = new Point();
	
	public Map (ArrayList<Tract> ntracts) {
		tracts = ntracts;
	}
	
	public void keyPressed(KeyEvent e) {
    	//gets the code of the key being pressed
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) { upPressed = true; }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) { rightPressed = true; }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) { downPressed = true; }
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) { leftPressed = true; }
        
        if (key == KeyEvent.VK_Z && scroll < 50) { zoomin = true; }
        if (key == KeyEvent.VK_X && scroll > 10) { zoomout = true; }
        
    }
    
    public void keyReleased(KeyEvent e) {
    	//gets the code of the key being pressed
    	int key = e.getKeyCode();
    	
    	if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) { upPressed = false; }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) { rightPressed = false; }  
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) { downPressed = false; }
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) { leftPressed = false; }
        
        if (key == KeyEvent.VK_Z) { zoomin = false; }
        if (key == KeyEvent.VK_X) { zoomout = false; }
        
    }
    
    public void mouseMoved(MouseEvent e) {
    	lastMousePos = mousePos;
    	mousePos = e.getPoint();
    }

    public void render() {
    	//called every frame

        System.out.println("Scroll: " + scroll);
//        //prevent them from moving through things
//        if (pos.y < 0) { pos.y = 0; }
//        else if (pos.y >= Board.MAX_Y - imageY) { pos.y = Board.MAX_Y - imageY; yVelocity = 0.0; }
//        
//        //prevent them from moving through walls
//        if (pos.x < 0) { pos.x = 0; xVelocity = 0; }
//        else if (pos.x >= Board.MAX_X - imageX) { pos.x = Board.MAX_X - imageX; xVelocity = 0.0; }
        
        int maxSpeed = 50;
        
        //if the player wants to move then let them
        if (rightPressed && !leftPressed) { xVelocity = 1 * (maxSpeed-scroll); }
        else if (!rightPressed && leftPressed) { xVelocity = -1 * (maxSpeed-scroll); } 
        if (upPressed && !downPressed) { yVelocity = -1 * (maxSpeed-scroll); }
        else if (!upPressed && downPressed) { yVelocity = 1 * (maxSpeed-scroll); } 
        
        //if player does not want to move or is indesive then slow them
        if ((rightPressed && leftPressed) || (!rightPressed && !leftPressed)) {
        	xVelocity = 0;
        }
        if ((upPressed && downPressed) || (!upPressed && !downPressed)) {
        	yVelocity = 0;
        }
        
        double oldScroll = scroll;
        if (zoomin && !zoomout) {
        	scroll += 1;
        }
        else if (!zoomin && zoomout){
        	scroll -= 1;
        	if (scroll <10) {
        		scroll = 10;
        	}
        	
        }
        else if (scroll > maxSpeed-1){
        	scroll = maxSpeed-1;
        }
        else if (scroll < 0.5) {
        	scroll = 0.5;
        }
        
        //calc new scroll loc
        
        //apply the changes
        
        
        pos.x = pos.x +  xVelocity;
        pos.y = pos.y +  yVelocity;
        
        
    }
    
    public void draw(Graphics g, ImageObserver observer, double rotation) {
		Point testpos = new Point(mousePos.x,mousePos.y);
		for (int i = 0; i < tracts.size(); i++) {
			tracts.get(i).draw(g, observer, rotation, new Point.Double ((double)(pos.x+(((scroll/10)-1)*(pos.x-800))),(double)(pos.y+(((scroll/10)-1)*(pos.y-400)))), scroll/10, testpos);
		}
		
        
    }
	
}
