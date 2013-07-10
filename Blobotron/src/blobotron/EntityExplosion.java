package blobotron;

import javax.swing.ImageIcon;

public class EntityExplosion extends Entity {

	// constant representing explosion image
	
	public static final String EXPLOSION_IMG = "explosion.gif";
	
	// constructor
	
	public EntityExplosion (int x, int y) {

		// call superclass constructor
		
		super(x, y);
	
		// create image icon and get image file for explosion
    	
    	ImageIcon ii = new ImageIcon(this.getClass().getResource(EXPLOSION_IMG));
        image = ii.getImage();
		
	}
	
}