package blobotron;

import javax.swing.ImageIcon;

public class EntityPlasmaball extends Entity {
	
	// constant representing plasmaball image
	
	public static final String PLASMABALL_IMG = "plasmaball.gif";
	
	// set speed of plasmaball
	
	private final int PLASMABALL_SPEED = 5;
	
	// variable representing plasmaball direction
	
	private String direction;
	
	// constructor
	
	public EntityPlasmaball(int x, int y, String direction) {

		// call superclass constructor
		
		super(x, y);
	
		// create image icon and get image file for plasmaball
    	
    	ImageIcon ii = new ImageIcon(this.getClass().getResource(PLASMABALL_IMG));
        image = ii.getImage();
        
        // assign width and height to plasmaball
        
        width = image.getWidth(null);
        height = image.getHeight(null);

        // assign direction to plasmaball
		
		this.direction = direction;
		
	}

	// move plasmaball
	
	public void move() {
		
		// change plasmaball coordinates
		
		if (direction == "e") {
			x += PLASMABALL_SPEED;
		}
        
		if (direction == "w") {
			x -= PLASMABALL_SPEED;
		}
		if (direction == "n") {
			y -= PLASMABALL_SPEED;
		}
		
		if (direction == "s") {
			y += PLASMABALL_SPEED;
		}
		
		// if plasmaball goes off screen, set to not visible (i.e. will not be drawn)
		
		if ((x < 0) || (x > Board.WIDTH) || (y < 0) || (y > Board.HEIGHT))
            visible = false;
    }
	
}