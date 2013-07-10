package blobotron;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class EntityPlayer extends Entity {

	// constant representing player image
	
	public static final String PLAYER_IMG = "player.gif";
	
	// variables representing player movement
	
	private int dx;
	private int dy;
	
	// create array for plasmaballs fired by player
	
	private ArrayList<EntityPlasmaball> plasmaballs;
	
	// constructor
	
	public EntityPlayer(int x, int y) {

		// call superclass constructor
		
		super(x, y);
		
		// create image icon and get image file for player
    	
    	ImageIcon ii = new ImageIcon(this.getClass().getResource(PLAYER_IMG));
        image = ii.getImage();
        
        // assign width and height to player
        
        width = image.getWidth(null);
        height = image.getHeight(null);
        		
		// instantiate arraylist of plasmaballs fired by player
		
		plasmaballs = new ArrayList<EntityPlasmaball>();
		
	}

	// change player coordinates
	
	public void move() {

		// change x and y locations
		
    	x += dx;
        y += dy;
         
        // if player goes off board, reset location back on board
        
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
	
	// while key pressed...
	
	public void keyPressed(KeyEvent e) {
		
        int key = e.getKeyCode();
        
        // WASD fires plasmaballs
                       
        if (key == KeyEvent.VK_A) {
            fire("w");
        }

        if (key == KeyEvent.VK_D) {
            fire("e");
        }
        
        if (key == KeyEvent.VK_W) {
            fire("n");
        }
        
        if (key == KeyEvent.VK_S) {
            fire("s");
        }
        
        // arrow directions move player
        
        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }

        // ESC key exits to main menu at any time
        
        if (key == KeyEvent.VK_ESCAPE){
        	Start.gameScreen.setVisible(false);
        	Start.menuScreen.scoreLabel.setText("High score: " + Integer.toString(Start.menuScreen.highScore));
    		Start.menuScreen.setVisible(true);
        }
        
        // space bar pauses game
        
        if (key == KeyEvent.VK_SPACE){
        	Start.gameScreen.paused = !Start.gameScreen.paused;
        }
    }

	// on key release, stop player movement
	
    public void keyReleased(KeyEvent e) {
    	
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
    
    // when player fires, plasmaball added to array of plasmaballs fired by player
    // starting x/y location of plasmaball depends on which direction it's being fired in
    
    public void fire(String direction) {
        
    	if (direction == "w") {
        	plasmaballs.add(new EntityPlasmaball(x, y + height/2, direction));
        }
        if (direction == "e") {
        	plasmaballs.add(new EntityPlasmaball(x + width, y + height/2, direction));
        }
        if (direction == "n") {
        	plasmaballs.add(new EntityPlasmaball(x + width/2, y, direction));
        }
        if (direction == "s") {
        	plasmaballs.add(new EntityPlasmaball(x + width/2, y + height, direction));
        }
    }
    
	// retrieves array of plasmaballs fired by player
	
	public ArrayList<EntityPlasmaball> getPlasmaballs() {
        return plasmaballs;
    }
	
}