
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
//import java.util.Random;
import javax.swing.*;
import java.lang.*;

public class Board extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    // controls the frame rate by setting delay between ticks
    private final int DELAY = 25;
    private final double FPS = 1000.0 / DELAY;
    
    // controls the size of the board
    public static final int MAX_X = 1600;
    public static final int MAX_Y = 800;
    
    public static final int HEALTH_BAR_Y = 25;

    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;
    
    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;
    
    private Map map;

    public Board() {
        // set the game board size
        setPreferredSize(new Dimension(MAX_X, MAX_Y));
        // set the game board background color
        setBackground(new Color(232, 232, 232));

        //g.fillRect(x, y, length, width);
        
        // initialize the game state
        
        ArrayList<Tract> tracts = new ArrayList<Tract>();
        tracts.add(new Tract(new Polygon(new int[]{340,340,543660,543660},new int[]{340,271660,271660,340},4)));
        tracts.add(new Tract(new Polygon(new int[]{10,10,20,20},new int[]{10,20,20,10},4)));
        tracts.add(new Tract(new Polygon(new int[]{100,100,200,200},new int[]{100,200,200,100},4)));
        tracts.add(new Tract(new Polygon(new int[]{500,500,550,550},new int[]{200,250,250,200},4)));
        tracts.add(new Tract(new Polygon(new int[]{200,200,250,250},new int[]{700,750,750,700},4)));
        tracts.add(new Tract(new Polygon(new int[]{750,750,850,850},new int[]{350,450,450,350},4)));
        tracts.add(new Tract(new Polygon(new int[]{50000,50000,55000,55000},new int[]{20000,25000,25000,20000},4)));
        tracts.add(new Tract(new Polygon(new int[]{20000,20000,25000,25000},new int[]{70000,75000,75000,70000},4)));
        tracts.add(new Tract(new Polygon(new int[]{75000,75000,85000,85000},new int[]{35000,45000,45000,35000},4)));

        map = new Map(tracts);
        
        // this timer will call the actionPerformed() method every DELAY ms
        //somehow this references actionPerformed()
        timer = new Timer(DELAY, this);
        timer.start();
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.

        // updates each objects each tick
        tickAll();
        //object.tck();

        //this updates all the graphics by calling the paintComponent() method
        repaint();
    }
    
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver 
        // because Component implements the ImageObserver interface, and JPanel 
        // extends from Component. So "this" Board instance, as a Component, can 
        // react to imageUpdate() events triggered by g.drawImage()
 
        // draw our graphics.
        //drawScore(g);
        map.draw(g, this, 0);
        
    	//drawing the health

        // set the text to be displayed
            
    	// we need to cast the Graphics to Graphics2D to draw nicer text

        // set the text color and font
        
//    	//filling green section
//        g.setColor(new Color(30, 201, 139));
//        g.fillRect(0, MAX_Y, (int)((player.health/100.0)*MAX_X), HEALTH_BAR_Y);
//        
//        //filling red section
//        g.setColor(new Color(200, 0, 0));
//        g.fillRect((int)((player.health/100.0)*MAX_X), MAX_Y, MAX_X, HEALTH_BAR_Y);
//        
//        g.setFont(new Font("Lato", Font.BOLD, 25));
//        g.setColor(new Color(0, 0, 0));
//        g.drawString(text, 0, MAX_Y+HEALTH_BAR_Y);

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    
    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent press) {
        // when player presses key call method of that player
    	//this method will set them pushing the key to true
        map.keyPressed(press);
    }

    @Override
    public void keyReleased(KeyEvent release) {
    	// when player releases key call method of that player
    	// this method will set them pushing the key to false
    	map.keyReleased(release);
    }
    
    public void mouseClicked(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        System.out.println(x+","+y);//these co-ords are relative to the component
    }
    
    private void tickAll() {
    	map.render();
    	
    }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		map.mouseMoved(e);
		System.out.println ("x: " + e.getX() + ", y: " + e.getY());
		
	}


}