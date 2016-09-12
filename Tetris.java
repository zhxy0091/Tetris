/*
 * NAME: Xinyuan Zhang
 * PID: A99023767
 * LOGIN: cs11shm
 * EMAIL: xiz016@ucsd.edu
 *
 * NAME: Chenghao Gong
 * PID: A91048063
 * LOGIN: cs11sbr
 * EMAIL: c2gong@ucsd.edu
 */
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import java.io.*;

/** The main class that creates the game and defines the game elements.
 * @author Xinyuan Zhang; Chenghao Gong
 * @version 28/May/2015
 */
public class Tetris extends JFrame	implements ActionListener, ChangeListener {
	/** Fields
	 * JButton that creates a new game.
	 * JButton that resets the game.
	 * JLabel that shows the current score.
	 * JLabel that shows the highest score.
	 * JSlider that defines the current speed.
	 * Container that holds the game page.
	 * JPanel that defines the top part of game page.
	 * JPanel that defines the bottom part of game page.
	 */
	private JButton newGame;
	private JButton reset;
	private JLabel score, nScore;
	private JLabel highScore, nHighScore;
	private JLabel gameOver;
	private JSlider speed;
	private Container contentPane = getContentPane();
	private JPanel topPanel;
	private JPanel bottomPanel;

	public static int gameScore = 0;//initial game score
	public static int gameHighScore = 0;//initial highest score
	public static int pixels = 20; //default pixels

	public static final int ROW = 20; //default #row
	public static final int COL = 10; //default #col
	public static final int ONE_ROW = 100;
	public static final int TWO_ROW = 400;
	public static final int THREE_ROW = 800;
	public static final int FOUR_ROW = 1600;
	public static final int MOVE_SCORE = 10;
	public static final int SPEED_CHANGE = 2000;
	public static final int MAX_SPEED = 20;

	/** Fields
	 * graphGrid a GraphicsGrid that draws the grid in the game page.
	 * grid a TetrisGrid that defines the abstract grid.
	 * thread a Thread that
	 * mover a ShapeMover that defines the moving action of the tetris.
	 * start a boolean variable that controls while the game is startinf or not.
	 */
	private GraphicsGrid graphGrid;
	private TetrisGrid grid;
	private Thread thread;
	private static ShapeMover mover;
	private static boolean start = false;

	/** Constructor
	 * @param p the pixels of the game.
	 */
	public Tetris(int p) {
		newGame = new JButton("New Game");//add the new game button
		newGame.addActionListener(this);	
		newGame.setFocusable(false);

		reset = new JButton("Reset!");// add the reset button
		reset.addActionListener(this);
		reset.setFocusable(false);

		speed = new JSlider(1,MAX_SPEED,1);// add the new speed slide
		speed.addChangeListener(this);
		speed.setFocusable(false);

		score = new JLabel("Score: ");
		highScore = new JLabel("High Score: ");
		nScore = new JLabel("0");
		nHighScore = new JLabel("0");
		gameOver = new JLabel("GAME OVER!");


		topPanel = new JPanel(new GridLayout(2,3));
		bottomPanel = new JPanel(new FlowLayout());

		pixels = p;
		graphGrid = new GraphicsGrid(p);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToPane();
		pack();
		validate();
		setVisible(true);

	}

	/** A method that defines the speed change.
	 * @param e a ChangeEvent
	 * */
	@Override 
	public void stateChanged(ChangeEvent e) {
		ShapeMover.speed = speed.getValue();
	}

	/** A method that add the game elements to the game page.
	*/
	private void addComponentsToPane() {
		bottomPanel.add(newGame);
		bottomPanel.add(reset);
		bottomPanel.add(speed);

		topPanel.add(score);
		topPanel.add(nScore);
		topPanel.add(gameOver);
		topPanel.add(highScore);
		topPanel.add(nHighScore);

		contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		contentPane.add(graphGrid, BorderLayout.CENTER);	
	}

	/** A method that make changes to the game elements depend on Actionevent
	 * @param e an ActionEvent 
	 */
	@Override	
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();

		if(source == newGame) {
			initialize();
			gameOver.setVisible(false);
			start = true;
		}
		if(source == reset) {
			gameScore = 0;
			gameHighScore = 0;
			nScore.setText(Integer.toString(gameScore));
			nHighScore.setText(Integer.toString(gameHighScore));
			mover.stopMove();
			graphGrid.setEnabled(false); //stop KeyEvent
			speed.setValue(1);	
			ShapeMover.speed = 1;
		}
	}

	/** A method that reset the game when the user press new game buttom
	*/
	private void initialize() {
		//stop the thread if press new game while game is running
		if(start) {
			mover.stopMove();
		}
		//disable KeyEvent
		graphGrid.setEnabled(false); //Stop KeyEvent

		contentPane.remove(graphGrid);
		graphGrid = new GraphicsGrid(pixels);
		grid = new TetrisGrid();

		contentPane.add(graphGrid, BorderLayout.CENTER);
		graphGrid.requestFocus();

		mover = new ShapeMover(graphGrid, this);
		speed.setValue(1);	
		ShapeMover.speed = 1;
		mover.beginMove();
		thread = new Thread(mover);
		thread.start();

		gameScore = 0;
		nScore.setText(Integer.toString(gameScore));
		//pack();
		validate();
		repaint();
	}

	/** A method that ends the game when gameover.
	*/
	public void gameOver() {
		graphGrid.setEnabled(false);
		gameOver.setVisible(true);
	}

	/** A method that updates score and high score
	 * @param num the number of completed row, if num=10, add 10 to score 
	 */
	public void updateScore(int num) {
		int s = 0; 
		if(num==1) s=ONE_ROW; 
		if(num==2) s=TWO_ROW;
		if(num==3) s=THREE_ROW;
		if(num==4) s=FOUR_ROW;
		if(num==10) s=MOVE_SCORE;

		gameScore += s;
		nScore.setText(Integer.toString(gameScore));

		if(gameHighScore <= gameScore){
			gameHighScore = gameScore;
			nHighScore.setText(Integer.toString(gameHighScore));
		}
		if(gameScore/SPEED_CHANGE >= 1){
			if(speed.getValue()<(gameScore/SPEED_CHANGE + 1) 
					&& speed.getValue()< MAX_SPEED)
				speed.setValue(gameScore/SPEED_CHANGE + 1);
		}
	}

	/** A method that print out usage message
	*/
	public static void usage() {
		System.out.println("Usage: Tetris [blocksize]");
		System.out.print("blocksize – Integer size of ");
		System.out.println("each block segement in pixels, default is 20.");
		System.exit(-1);
	}

	/** Main method
	 * Will check the user input and run the game
	 * @param args user's command for block size
	 */
	public static void main(String[] args) {
		try {
			if(args.length==1) {
				pixels = Integer.parseInt(args[0]);
				if(pixels<2) {
					usage();
				}
			}	
			else if(args.length!=0) {
				usage();
			}
		} catch (NumberFormatException e) {
			usage();
		}
		Tetris window = new Tetris(pixels);

		try {
			System.out.format("Hit Return to exit program");
			System.in.read();
		}
		catch (IOException e){}
		if(start) {
			mover.stopMove();
		}
		window.dispatchEvent(new WindowEvent(window, 
					WindowEvent.WINDOW_CLOSING));
		window.dispose();	
	}

}









