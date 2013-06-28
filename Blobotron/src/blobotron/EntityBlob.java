package blobotron;

public class EntityBlob extends Entity {

	// constants representing blob image and size
	
	public static final String BLOB_IMG = "blob.gif";
	private final int BLOB_WIDTH = 75;
	private final int BLOB_HEIGHT = 100;
	
	// constructor
	
	public EntityBlob(int x, int y, String entityImage) {
		
		// invoke superclass entity; args = x location, y location, name of image
		
		super(x, y, entityImage);
		
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
        
        if (x > (Board.WIDTH - BLOB_WIDTH)) {
            x = (Board.WIDTH - BLOB_WIDTH);
        }

        if (y > (Board.HEIGHT - BLOB_HEIGHT)) {
            y = (Board.HEIGHT - BLOB_HEIGHT);
        }

	}
	
}