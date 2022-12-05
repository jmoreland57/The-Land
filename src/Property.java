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


public class Property {

	public Polygon shape;
	Tract tract;
	ArrayList<Property> properties;
	
	public Property(Polygon nshape, Tract ntract) {
		shape = nshape;
		tract = ntract;
	}
	
	public void Draw (Graphics g, ImageObserver observer) {
		if (properties.size()==0) {
			g.drawPolygon(shape);
		}
		else {
			for (int i = 0; i < properties.size(); i++) {
				properties.get(i).Draw(g, observer);
			}
		}
	}
	
	public void addProperty(Property newP) {
		if (properties.size()==0) {
			
		}
	}
	
	private boolean validNewProperty(Property newP) {
		boolean out = false;
		ArrayList<Polygon> shapes = new ArrayList<Polygon>();
		if (properties.size()==0) {
			shapes.add(shape);
		}
		else {
			for (int i = 0; i < properties.size(); i++) {
				shapes.add(properties.get(i).shape);
			}
		}
		for (Polygon p: shapes) {
			for (int i = 0; i < p.npoints; i++) {
//				if (shape.contains(p)){
//					return false;
//				}
			}
		}
		return out;
	}
	
}
