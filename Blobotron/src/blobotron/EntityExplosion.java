package blobotron;

public class EntityExplosion extends Entity {

	// constant representing plasmaball image
	
	public static final String EXPLOSION_IMG = "explosion.gif";
		
	// constructor
	
	public EntityExplosion (int x, int y, String entityImage) {

		// invoke superclass Entity; args = x location, y location, name of image
		
		super(x, y, entityImage);
		
	}
	
}