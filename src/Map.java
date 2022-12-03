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
    public Point.Double scrollOffset = new Point.Double(0,0);
    public Point.Double fedOffset = new Point.Double(0,0); // actual one that gets passed to tracts
    
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
        
        double m = -1;
//        double xEffect = ((1/10)-1)*(fedOffset.x-mousePos.x)*multiplier;
//    	double yEffect = ((1/10)-1)*(fedOffset.y-mousePos.y)*multiplier;
    	double xEffect = (0.1)*(pos.x-mousePos.x)*(m);
    	double yEffect = (0.1)*(pos.y-mousePos.y)*m;
        
        double oldScroll = scroll;
        if (zoomin && !zoomout) {
        	scroll += 1;
        	if (scroll > 50) {
        		scroll = 50;
        	}
        	else {
            	scrollOffset.x -= xEffect;
            	scrollOffset.y -= yEffect;
        	}
        }
        else if (!zoomin && zoomout){
        	scroll -= 1;
        	if (scroll <10) {
        		scroll = 10;
        	}
        	else {
        		scrollOffset.x += xEffect;
        		scrollOffset.y += yEffect;
        	}
        
        if (scrollOffset.x > 0) {
            scrollOffset.x = 0;
        }
        if (scrollOffset.y > 0) {
            scrollOffset.y = 0;
        }
        
        
        
        }
        
        //calc new scroll loc
        
        //apply the changes
        
        fedOffset = new Point.Double(scrollOffset.x+pos.x,scrollOffset.y+pos.y);
        
        pos.x = pos.x - xVelocity;
        if (fedOffset.x > 0) {
        	pos.x = -scrollOffset.x;
        	fedOffset.x = 0;
        }
        else if(fedOffset.x+1600*(scroll)/10<1600){
        	fedOffset.x = 1600-1600*(scroll)/10;
        	pos.x = fedOffset.x-scrollOffset.x; 
        }
//        else if(fedOffset.x>1600*(1-(scroll/10-1))){
//        	fedOffset.x=1600*(1-(scroll/10-1));
//        	pos.x = fedOffset.x-scrollOffset.x; 
//        }
        pos.y = pos.y - yVelocity;
        if (fedOffset.y > 0) {
        	pos.y = -scrollOffset.y;
        	fedOffset.y = 0;
        }
        else if(fedOffset.y+800*(scroll)/10<800){
        	fedOffset.y = 800-800*(scroll)/10;
        	pos.y = fedOffset.y-scrollOffset.y; 
        }
//        else if(fedOffset.y<80*(10-scroll)) {
//        	fedOffset.y=80*(10-scroll);
//        	pos.x = fedOffset.y-scrollOffset.y; 
//        }
        
        System.out.println("Scrolloffset: " + scrollOffset.x + ", " + scrollOffset.y);
        System.out.println("pos2: " + pos.x + ", " + pos.y);
        
        
        
//        if(fedOffset.x>0) {
//        	fedOffset.x=0;
//        }
//        else if(fedOffset.x<160*(10-scroll)) {
//        	fedOffset.x=160*(10-scroll);
//        }
//        if(fedOffset.y>0) {
//        	fedOffset.y=0;
//        }
//        else if(fedOffset.y<80*(10-scroll)) {
//        	fedOffset.y=80*(10-scroll);
//        }
       
    }
    
    public void draw(Graphics g, ImageObserver observer, double rotation) {
		Point testpos = new Point(mousePos.x,mousePos.y);
		for (int i = 0; i < tracts.size(); i++) {
			tracts.get(i).draw(g, observer, rotation, fedOffset, scroll/10, testpos);
		}
		
        
    }
	
}
