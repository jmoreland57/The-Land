import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.lang.*;
import java.util.ArrayList;
import java.awt.geom.Area;


public class Tract {
	
	boolean hovered;
	
	public Polygon shape;
	
	public Tract (Polygon nshape) {
		shape = nshape;
		hovered = false;
	}
	
	public void draw(Graphics g, ImageObserver observer, double rotation, Point.Double offset, double scroll, Point mouse) {
		System.out.println("drawscroll: " + scroll);
		int npoints = shape.npoints;
		int[] newxpoints = shape.xpoints.clone();
		int[] newypoints = shape.ypoints.clone();
		System.out.println("Render");
		for (int i = 0; i < npoints; i ++) {
			newxpoints[i] *= scroll / 340;
			System.out.println("real:" + shape.xpoints[i] + "-" + shape.ypoints[i]);
			System.out.println(newxpoints[i]);
			newypoints[i] *= scroll / 340;
			System.out.println(newypoints[i]);
		}
		Polygon drawn = new Polygon(newxpoints,newypoints,npoints);
		drawn.translate((int)(offset.x),(int)(offset.y));
		System.out.println("Just before check: " + mouse.x + ", " + mouse.y);
		for (int i = 0; i < 4; i++) {
			System.out.println (drawn.xpoints[i] + ", " + drawn.ypoints[i]);
		}
		if (drawn.contains(mouse.x-10,mouse.y-30)&&shape.xpoints[0]!=340) {
			g.fillPolygon(drawn);
			System.out.println("DETECT: ");
		}
		System.out.println(mouse.x + ", " + mouse.y);
        g.drawPolygon(drawn);
    }

}
