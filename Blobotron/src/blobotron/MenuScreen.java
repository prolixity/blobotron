package blobotron;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuScreen extends JPanel implements ActionListener {

	// avoid serializable class error
	
	private static final long serialVersionUID = 1L;
	
	// create button to start game
	
	private JButton startButton;
	private JButton exitButton;
	private JLabel instrLabel;
	public JLabel scoreLabel;
	
	// create high score variable
	
	public int highScore;
    
	// constructor, displays menu screen
	
    public MenuScreen() {
   	    	    	
    	setFocusable(true);
        setBackground(Color.BLACK);
        setSize(Board.WIDTH, Board.HEIGHT);
               
        // buttons
        
        startButton = new JButton();
        startButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("start.gif")));
        startButton.setActionCommand("start");
    	startButton.addActionListener(this);
    	
    	exitButton = new JButton();
    	exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("exit.png")));
    	exitButton.setActionCommand("exit");
        exitButton.addActionListener(this);
        
        instrLabel = new JLabel("Press space to pause, ESC to return to menu");
        instrLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        instrLabel.setForeground(Color.WHITE);
        
        scoreLabel = new JLabel("High score: " + Integer.toString(highScore));
        scoreLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        scoreLabel.setForeground(Color.WHITE);
        
        // layout
        
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        instrLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // setBorder(new EmptyBorder(new Insets(100, 100, 100, 100)));
        
        add(Box.createVerticalGlue());
        add(startButton);
        add(Box.createVerticalGlue());
        add(exitButton);
        add(Box.createVerticalGlue());
        add(instrLabel);
        add(Box.createVerticalGlue());
        add(scoreLabel);
        add(Box.createVerticalGlue());

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