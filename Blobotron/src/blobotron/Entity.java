package blobotron;

import java.awt.Image;
import java.awt.Rectangle;

public class Entity {
	
    // create variables for x and y coordinates of entity
	
	public int x;
    public int y;
    
    // variables for width and height of entity
    
    public int width;
    public int height;
    
    // variable for entity graphics
    
    public Image image;
    
    // variable to toggle whether entity is displayed
    
    public boolean visible;
        
    // constructor for entity; args = x/y coordinates
    
    public Entity (int x, int y) {
        
        // set initial visibility for entity to true
        
        visible = true;
        
        // set x and y location of entity
        
        this.x = x;
        this.y = y;
    }
    
    // get image for entity
    
    public Image getImage() {
        return image;
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

    // get bounding box for entity, as determined by entity x/y coordinates, width, and height
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}