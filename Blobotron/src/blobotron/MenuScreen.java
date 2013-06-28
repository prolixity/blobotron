package blobotron;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuScreen extends JPanel implements ActionListener {

	// avoid serializable class error
	
	private static final long serialVersionUID = 1L;
	
	// create button to start game
	
	private JButton startButton;
	private JButton exitButton;
    
	// constructor, displays menu screen
	
    public MenuScreen() {
   	    	    	
    	setFocusable(true);
        setBackground(Color.BLACK);
        setSize(Board.WIDTH, Board.HEIGHT);
                
        // buttons
        
        startButton = new JButton("Start Game");
    	startButton.setActionCommand("start");
    	startButton.addActionListener(this);
    	
    	exitButton = new JButton("Exit Game");
    	exitButton.setActionCommand("exit");
        exitButton.addActionListener(this);
        
        // layout
        
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(startButton);
        add(exitButton);

    }
    
    public void actionPerformed(ActionEvent e) {
    	
    	// if start button...
    	
    	if (e.getActionCommand() == "start") {
    		
    		// turn off the menu screen
    		
    		setVisible(false);
    		
    		// instantiate board, add to window, give it focus
    		
    		Start.boardScreen = new Board();
    		Start.gameWindow.add(Start.boardScreen);
    		Start.boardScreen.requestFocusInWindow();
    	}
		
    	// if exit button, end game
    	
    	if (e.getActionCommand() == "exit") {
    		System.exit(0);
    	}
	}

}