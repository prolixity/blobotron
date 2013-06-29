package blobotron;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

// this is the main class for the game, creates loop within which all actions take place

public class Board extends JPanel implements ActionListener {

	// avoid serializable class error
	
	private static final long serialVersionUID = 1L;
	
	// create player and timer variables
	
	private EntityPlayer player;	
	private Timer timer;
	
	// creates ingame and paused variables
	
	private boolean ingame;
	public boolean paused;
	
	// create arraylists to hold plasmaballs, blobs, and explosions
	
	private ArrayList<EntityPlasmaball> plasmaballArrayList; 
	private ArrayList<EntityBlob> blobArrayList;
	private ArrayList<EntityExplosion> explosionArrayList;
	
	// create board size constants
	// (height is smaller than window height to accommodate title bar)
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 716;
	
	private int[][] blobPosition = { 
            {10,10}, {900, 700}
         };
	
	// constructor, creates board for the game
	
	public Board() {

		// is focusable, background color black, uses a buffer to paint, sets board size, sets ingame to true
				
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setSize(WIDTH, HEIGHT);
        
        ingame = true;
        paused = false;
        
        // initialize player in middle of screen
        
        player = new EntityPlayer(WIDTH/2-25, HEIGHT/2-35, EntityPlayer.PLAYER_IMG);
        
        // initialize blob, plasmaball and explosion arraylists
        
        plasmaballArrayList = new ArrayList<EntityPlasmaball>();
        blobArrayList = new ArrayList<EntityBlob>();
        explosionArrayList = new ArrayList<EntityExplosion>();
        
        // call method to initialize blobs
        
        initBlobs();
        
        // initialize timer w/5 millisecond delay
        
        timer = new Timer(5, this);
        timer.start();
        
        // start listening for player input
		
 		addKeyListener(new GameInput());
  		
	}
	
	// initialize blobs
	
	public void initBlobs() {

		// loop through array of blobPosition and add all members to arraylist of blobs
		
		for (int i=0; i < blobPosition.length; i++ ) {
            blobArrayList.add(new EntityBlob(blobPosition[i][0], blobPosition[i][1], EntityBlob.BLOB_IMG));
        }
    }
	
	// draw the game state
	
	public void paint(Graphics g) {
    	
		// if game is ongoing
		
		if (ingame) {
			
			// invoke superclass
			
	        super.paint(g);
	
	        // create renderer
	        
	        Graphics2D g2d = (Graphics2D)g;
	        
	        // if player should be displayed, draw at x/y coordinates
	        
	        if (player.isVisible()) {
	        	g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
	        }
	        
	        // draw plasmaballs at x/y coordinates
	        
	        plasmaballArrayList = player.getPlasmaballs();
	    	
	        for (int i = 0; i < plasmaballArrayList.size(); i++ ) {
	            EntityPlasmaball pb = (EntityPlasmaball) plasmaballArrayList.get(i);
	            g2d.drawImage(pb.getImage(), pb.getX(), pb.getY(), this);
	        }
	        
	        // draw blobs at x/y coordinates
	        
	        for (int i = 0; i < blobArrayList.size(); i++) {
	        	EntityBlob b = (EntityBlob) blobArrayList.get(i);
	            g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
	        }
	        
	        // draw explosions at x/y coordinates
	        
	        for (int i = 0; i < explosionArrayList.size(); i++) {
	        	EntityExplosion e = (EntityExplosion) explosionArrayList.get(i);
	            g2d.drawImage(e.getImage(), e.getX(), e.getY(), this);
	        }
	        
	        // synchronize painting and end drawing
	        
		    Toolkit.getDefaultToolkit().sync();
		    g.dispose();

		// if game is over, display game over screen
		    
		} else {
					
			Font fontSpec = new Font("Impact", Font.BOLD, 64);
            FontMetrics fontInfo = this.getFontMetrics(fontSpec);

            String msg = "Game Over! Press Esc";
            
            g.setColor(Color.blue);
            g.setFont(fontSpec);
            g.drawString(msg, (WIDTH/2 - fontInfo.stringWidth(msg)/2), HEIGHT/2);
			
		}

    }
	
	// this is the central game logic loop; invoked every 5ms pursuant to timer
	
	public void actionPerformed(ActionEvent e) {
		
		// if game is not paused...
		
		if (!paused) {
			
			// determine how many plasmaballs have been fired and move as necessary
			// if not visible, remove from arraylist
		
			for (int i = 0; i < plasmaballArrayList.size(); i++) {
	    	    EntityPlasmaball pb = (EntityPlasmaball) plasmaballArrayList.get(i);
	    	    if (pb.isVisible()) { 
	    	        pb.move();
	    	    }
	    	    else plasmaballArrayList.remove(i);
	    	}
	    	
	    	// determine how many blobs there are and move as necessary
	    	// if not visible, remove from arraylist
			// if all blobs dead, end game
	    	
	    	for (int i = 0; i < blobArrayList.size(); i++) {
	            EntityBlob b = (EntityBlob) blobArrayList.get(i);
	            if (b.isVisible()) { 
	                b.move(player.getX(), player.getY());
	            }
	            else blobArrayList.remove(i);
	            if (blobArrayList.size() == 0) {
	            	ingame = false;
	            }
	        }
	    	
			// move player
			
			player.move();
			
			// check if player has collided with blob
			
			checkPlayerCollision();
			
			// check if plasmaball has hit a blob
			
			checkPlasmaballCollision();
					
			//  redraw game state
			
			repaint();

		}
	}
	
	// checks to see if player collision has occurred; if so, game ends
	
	private void checkPlayerCollision() {
				
	    Rectangle playerRect = player.getBounds();
        ArrayList<EntityBlob> blobs = blobArrayList;
        
        for (int i = 0; i<blobs.size(); i++) {
    
        	EntityBlob b = (EntityBlob) blobs.get(i);
            Rectangle blobRect = b.getBounds();

            if (playerRect.intersects(blobRect)) {
                player.setVisible(false);
                ingame = false;
            }
        }

	}
	
	// checks to see if plasmaball has hit blob
	// if so, both plasmaball and struck blob are removed from game
	
	private void checkPlasmaballCollision() {
		
		plasmaballArrayList = player.getPlasmaballs();
		
		for (int i = 0; i < plasmaballArrayList.size(); i++) {
		    
			EntityPlasmaball pb = (EntityPlasmaball) plasmaballArrayList.get(i);
		    Rectangle pbRect = pb.getBounds();
		
		    for (int j = 0; j<blobArrayList.size(); j++) {
		     
		    	EntityBlob b = (EntityBlob) blobArrayList.get(j);
		    	Rectangle bRect = b.getBounds();
		
		        if (pbRect.intersects(bRect)) {
		            pb.setVisible(false);
		            b.setVisible(false);
		            
		            // explosion added to array at location of collision
		            
		            explosionArrayList.add(new EntityExplosion(pb.x, pb.y, EntityExplosion.EXPLOSION_IMG));
		        }
		    }
		}
		
	}
	
	// listen for key input from player
	// if key is pressed or released, call the appropriate method in EntityPlayer class
	
	private class GameInput extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
        	player.keyPressed(e);
        }
    }
	
}