package blobotron;

import java.awt.Container;

import javax.swing.JFrame;

public class Start extends JFrame {
	
	// avoid serializable class error
	
    private static final long serialVersionUID = 1L;
    
    // create variables for various screens
    
    public static Container gameWindow;
    public static MenuScreen menuScreen;
    public static Board gameScreen;
            
    // constructor, creates game window
    
	public Start() {
		
        // initialize container
		
		gameWindow = this.getContentPane();
		
		// creates window for game, game ends when window closed, window size is 1024x748,
		// window is placed in center of screen, window title is set, window is unresizeable,
		// window is visible
		       	
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 748);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Blobotron!");
                
        // initialize menu screen
        
        menuScreen = new MenuScreen();
        add(menuScreen);
		
        // show window
        
        setVisible(true);
	}
	
	// main method, entry to game, creates start object
	
    public static void main(String[] args) {

    	new Start();
    }
}