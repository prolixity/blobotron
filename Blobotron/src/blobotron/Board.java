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
import java.util.Random;

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
	
	public boolean ingame;
	public boolean paused;
	
	// create arraylists to hold plasmaballs, blobs, and explosions
	
	private ArrayList<EntityPlasmaball> plasmaballArrayList; 
	private ArrayList<EntityBlob> blobArrayList;
	private ArrayList<EntityExplosion> explosionArrayList;
	
	// create board size constants
	// (height is smaller than window height to accommodate title bar)
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 716;
	
	// create variable for key listener for game input
	
	KeyAdapter gameKeyAdapter;
	
	// create variable for number of aliens killed by player
	
	public int blobsKilled;
	
	// constructor, creates board for the game
	
	public Board() {

		// is focusable, background color black, uses a buffer to paint, sets board size
				
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setSize(WIDTH, HEIGHT);
        
        // initialize game variables
        
        ingame = true;
        paused = false;
        blobsKilled = 0;
        
        // initialize player in middle of screen
        
        player = new EntityPlayer(WIDTH/2-25, HEIGHT/2-35);
        
        // initialize blob, plasmaball and explosion arraylists
        
        plasmaballArrayList = new ArrayList<EntityPlasmaball>();
        blobArrayList = new ArrayList<EntityBlob>();
        explosionArrayList = new ArrayList<EntityExplosion>();
        
        // call method to initialize blobs
        
        addBlob(true);
        
        // initialize timer w/5 millisecond delay
        
        timer = new Timer(5, this);
        timer.start();
        
        // start listening for player input

        gameKeyAdapter = new KeyAdapter() {
		     	
        	public void keyReleased(KeyEvent e) {
        		player.keyReleased(e);
        	}

        	public void keyPressed(KeyEvent e) {
        		player.keyPressed(e);
        	}
        };
        
        addKeyListener(gameKeyAdapter);
 		
	}
	
	// create a blob
	
	public void addBlob(boolean firstRun) {

		Random randomGenerator = new Random();

		// if this is the first time running, create three initial blobs at (semi)random x/y locations ...
		
		if (firstRun == true) {

			for (int i = 0; i < 3; i++) {
			
				// not entirely random; create safe area for player at center of screen
				
				int blobXPosition = randomGenerator.nextInt(400);
				if (blobXPosition > 200) {
					blobXPosition = WIDTH - (400 - blobXPosition);
				}
				
				int blobYPosition = randomGenerator.nextInt(350);
				if (blobYPosition > 175) {
					blobYPosition = HEIGHT - (350 - blobYPosition);
				}
				
				// add created blob to arraylist
				
				blobArrayList.add(new EntityBlob(blobXPosition, blobYPosition));
		
				// ensure this loop doesn't recur
				
				firstRun = false;
			}
			
		// ...otherwise, just create one blob
					
		} else {

			boolean blobPositionNotFound = true;
		
			while (blobPositionNotFound) {
			
				int newBlobXPosition = randomGenerator.nextInt(WIDTH);
				int newBlobYPosition = randomGenerator.nextInt(HEIGHT);
			
				// ensure chosen coordinate is not within 300 pixel zone around player
				// if it is, try a new coordinate
								
				int xDiff = Math.abs(player.getX() - newBlobXPosition);
				int yDiff = Math.abs(player.getY() - newBlobYPosition);
								
				if ((xDiff > 300) && (yDiff > 300)) {
				
					blobArrayList.add(new EntityBlob(newBlobXPosition, newBlobYPosition));
					blobPositionNotFound = false;
				}
			}
		}
    }
	
	// draw the game state
	
	public void paint(Graphics g) {
    	
		// if game is ongoing...
		
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
	        
	        // display current score and high score in bottom left corner
	        
	        Font fontSpec = new Font("Impact", Font.PLAIN, 24);
            String score = "Score: " + Integer.toString(blobsKilled);
            String highScore = "High Score: " + Integer.toString(Start.menuScreen.highScore);

            g.setColor(Color.white);
            g.setFont(fontSpec);
            g.drawString(score, 10, (HEIGHT - 35));
            g.drawString(highScore, 10, (HEIGHT - 10));
            
	        // synchronize painting and end drawing
	        
		    Toolkit.getDefaultToolkit().sync();
		    g.dispose();

		// if game is over...
		    
		} else {
			
			// display game over info
			
			Font fontSpec = new Font("Impact", Font.BOLD, 64);
            FontMetrics fontInfo = this.getFontMetrics(fontSpec);

            String msg1 = "Game Over! Score: " + Integer.toString(blobsKilled);
            String msg3 = "Press ESC for main menu";
            
            g.setColor(Color.blue);
            g.setFont(fontSpec);
            g.drawString(msg1, (WIDTH/2 - fontInfo.stringWidth(msg1)/2), HEIGHT/2 - 80);
                        
            // check to see if high score and display if true
			
         			if (blobsKilled > Start.menuScreen.highScore) {
         				Start.menuScreen.highScore = blobsKilled;
         				String msg2 = "New high score!";
         				g.drawString(msg2, (WIDTH/2 - fontInfo.stringWidth(msg2)/2), HEIGHT/2 + 0);
         			}
            
            g.drawString(msg3, (WIDTH/2 - fontInfo.stringWidth(msg3)/2), HEIGHT/2 + 140);
            
		}

    }
	
	// this is the central game logic loop; invoked every 5ms pursuant to timer
	
	public void actionPerformed(ActionEvent e) {
		
		// if game is active and not paused...
		
		if (ingame && !paused) {
			
			// determine how many plasmaballs have been fired and move as necessary
			// if not visible, remove from arraylist
		
			for (int i = 0; i < plasmaballArrayList.size(); i++) {
	    	    EntityPlasmaball pb = (EntityPlasmaball) plasmaballArrayList.get(i);
	    	    if (pb.isVisible()) { 
	    	        pb.move();
	    	    } else {
	    	    	plasmaballArrayList.remove(i);
	    	    }
	    	}
	    	
	    	// determine how many blobs there are and move as necessary
	    	// if not visible, remove from arraylist
			// if all blobs dead, end game
	    	
	    	for (int i = 0; i < blobArrayList.size(); i++) {
	            EntityBlob b = (EntityBlob) blobArrayList.get(i);
	            if (b.isVisible()) { 
	                b.move(player.getX(), player.getY());
	            } else {
	            	blobArrayList.remove(i);
	            }
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
	
	// checks to see if player collision has occurred
	
	private void checkPlayerCollision() {
				
	    Rectangle playerRect = player.getBounds();
        ArrayList<EntityBlob> blobs = blobArrayList;
        
        for (int i = 0; i<blobs.size(); i++) {
    
        	EntityBlob b = (EntityBlob) blobs.get(i);
            Rectangle blobRect = b.getBounds();

            // if collision, end game
            
            if (playerRect.intersects(blobRect)) {

            	ingame = false;
            	player.setVisible(false);
                
            }
        }
	}
	
	// check to see if plasmaball has hit blob
	
	private void checkPlasmaballCollision() {
		
		// (first check ingame status (otherwise score can increase after player death))
		
		if (ingame == true) {
			
			plasmaballArrayList = player.getPlasmaballs();
			
			for (int i = 0; i < plasmaballArrayList.size(); i++) {
			    
				EntityPlasmaball pb = (EntityPlasmaball) plasmaballArrayList.get(i);
			    Rectangle pbRect = pb.getBounds();
			
			    for (int j = 0; j<blobArrayList.size(); j++) {
			     
			    	EntityBlob b = (EntityBlob) blobArrayList.get(j);
			    	Rectangle bRect = b.getBounds();
			
			        if (pbRect.intersects(bRect)) {
		
			        	// both plasmaball and struck blob are removed from game
			        	
			        	pb.setVisible(false);
			            b.setVisible(false);
			            
			            // new blob added to game (false means this is not first blob created)
			            
			            addBlob(false);
			            
			            // score incremented
			            
			            blobsKilled++;
			            
			            // explosion added to array at x/y location of collision
			            
			            explosionArrayList.add(new EntityExplosion(pb.x, pb.y));
			        }
		        }
		    }
		}
	}
}