package blobotron;

import javax.swing.ImageIcon;

public class EntityBlob extends Entity {

	// constant representing blob image
	
	public static final String BLOB_IMG = "blob.gif";
	
	// constructor
	
	public EntityBlob(int x, int y) {
		
		// call superclass constructor
		
		super(x, y);
		
		// create image icon and get image file for blob
    	
    	ImageIcon ii = new ImageIcon(this.getClass().getResource(BLOB_IMG));
        image = ii.getImage();
        
        // assign width and height to blob
        
        width = image.getWidth(null);
        height = image.getHeight(null);
		
	}

	// move blob
	
	public void move(int playerX, int playerY) {
		
		// move blob toward player
		
		if (x < playerX) {
			x += 1;
		}
		
		if (x > playerX) {
			x -= 1;
		}
		
		if (y < playerY) {
			y += 1;
		}
		
		if (y > playerY) {
			y -= 1;
		}
		
		// if blob goes off board, reset location back on board
        
        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
        
        if (x > (Board.WIDTH - width)) {
            x = (Board.WIDTH - width);
        }

        if (y > (Board.HEIGHT - height)) {
            y = (Board.HEIGHT - height);
        }

	}
	
}