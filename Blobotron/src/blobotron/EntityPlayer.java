package blobotron;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class EntityPlayer extends Entity {

	// constants representing player image and size
	
	public static final String PLAYER_IMG = "player.gif";
	private final int PLAYER_WIDTH = 50;
	private final int PLAYER_HEIGHT = 71;

	// variables representing player movement
	
	private int dx;
	private int dy;
	
	// create array for plasmaballs fired by player
	
	private ArrayList<EntityPlasmaball> plasmaballs;
	
	// constructor
	
	public EntityPlayer(int x, int y, String entityImage) {

		// invoke superclass entity; args = x location, y location, name of image
		
		super(x, y, entityImage);
		
		// instantiate plasmaballs fired by player
		
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
        
        if (x > (Board.WIDTH - PLAYER_WIDTH)) {
            x = (Board.WIDTH - PLAYER_WIDTH);
        }

        if (y > (Board.HEIGHT - PLAYER_HEIGHT)) {
            y = (Board.HEIGHT - PLAYER_HEIGHT);
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
        
        // ESC key exits to main menu
        
        if (key == KeyEvent.VK_ESCAPE){

        	Start.boardScreen.setVisible(false);
    		Start.menuScreen.setVisible(true);	
        }
        
        // space bar pauses game
        
        if (key == KeyEvent.VK_SPACE){
        	Start.boardScreen.paused = !Start.boardScreen.paused;
        }
    }

	// on key release, stop modifying location
	
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
        	plasmaballs.add(new EntityPlasmaball(this.x, this.y + PLAYER_HEIGHT/2, EntityPlasmaball.PLASMABALL_IMG, direction));
        }
        if (direction == "e") {
        	plasmaballs.add(new EntityPlasmaball(this.x + PLAYER_WIDTH, this.y + PLAYER_HEIGHT/2, EntityPlasmaball.PLASMABALL_IMG, direction));
        }
        if (direction == "n") {
        	plasmaballs.add(new EntityPlasmaball(this.x + PLAYER_WIDTH/2, this.y, EntityPlasmaball.PLASMABALL_IMG, direction));
        }
        if (direction == "s") {
        	plasmaballs.add(new EntityPlasmaball(this.x + PLAYER_WIDTH/2, this.y + PLAYER_HEIGHT, EntityPlasmaball.PLASMABALL_IMG, direction));
        }
    }
    
	// retrieves array of plasmaballs fired by player
	
	public ArrayList<EntityPlasmaball> getPlasmaballs() {
        return plasmaballs;
    }
	
}