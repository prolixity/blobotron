package blobotron;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Entity {
	
    // x and y location of entity
	
	public int x;
    public int y;
    
    // width and height of entity
    
    public int width;
    public int height;
    
    // will entity be displayed
    
    public boolean visible;
    
    // image for entity
    
    public Image image;

    // constructor for entity; args = x location, y location, name of image
    
    public Entity (int x, int y, String entityImage) {
        
    	// creates image icon
    	
    	ImageIcon ii = new ImageIcon(this.getClass().getResource(entityImage));
    	
    	// gets image file for entity
    	
        image = ii.getImage();
        
        // assigns width and height to entity
        
        width = image.getWidth(null);
        height = image.getHeight(null);
        
        // sets initial visibility for entity to true
        
        visible = true;
        
        // sets x and y location of entity
        
        this.x = x;
        this.y = y;
    }

    // get x and y location of entity
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // get visibility of entity
    
    public boolean isVisible() {
        return visible;
    }

    // set visibility of entity
    
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    // get image for entity
    
    public Image getImage() {
        return image;
    }

    // get bounding box for entity, as determined by entity location (x, y)
    // and entity's width and height
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}