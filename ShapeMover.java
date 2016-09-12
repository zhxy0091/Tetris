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

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.IOException;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;

/** A helper class that defines the movement of tetris
 * @author Xinyuan Zhang; Chenghao Gong
 * @version 28/May/2015
 */
public class ShapeMover implements Runnable, KeyListener {
	/** Fields
	 * graphGrid that creates the main grid.
	 * grid that builds the game grid.
	 * game the game body.
	 * shape the random tetris shape.
	 * speed the spead of the game
	 * go that determines if the game keeps going.
	 * keyAction that determines whether to run special action.
	 * WAIT_TIME the waiting time of each tetris.
	 */
	private GraphicsGrid graphGrid;
	private TetrisGrid grid;
	private Tetris game;
	private int row = 0; //initial position of the shape
	private int col = 4; //initial position of the shape
	private TetrisShape shape;
	public static int speed = 1;
	private boolean go = true;
	private boolean keyAction = false; //check if hit valid key
	private boolean spaceKey = false;  //check if hit valid space key
	public static final int WAIT_TIME = 50;

	/** Constructor method
	 * @param g the graphicsGrid
	 * @param game the game body
	 */
	public ShapeMover(GraphicsGrid g, Tetris game) {

		grid = new TetrisGrid();
		graphGrid = g;
		this.game = game;
		shape = TetrisShape.createShape();
		grid.addShape(shape, row, col);		
		graphGrid.update(grid);
		graphGrid.addKeyListener(this);
	}

	/** Return a reference of GraphicsGrid object
	 * @return a reference of GraphicsGrid object
	 */
	public GraphicsGrid getGraphGrid() {
		return graphGrid;
	}

	/** Override KeyTyped method
	 * @param e a Keyevent
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	/** Override KeyPressed method
	 * @param e a Keyevent
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		move(e);
	}

	/** Override KeyReleased method
	 * @param e a Keyevent
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}

	/** The method that decides how the tetris moves
	 * @param e a Keyevent
	 */
	public void move(KeyEvent e) {
		int command = e.getKeyCode();
		if(command==37) {

			if(moveLeft(grid,shape,row,col)) {
				col-=1;
				keyAction = true;
			}
		}
		else if(command==39) {
			if(moveRight(grid,shape,row,col)) {;
				col+=1;
				keyAction = true;
			}
		}
		else if(command==32) {
			while(moveDown(grid,shape,row,col)) {
				game.updateScore(10);	
				row++;
				keyAction = true;
				spaceKey = true;
			}
		}
		else if(command==38) {
			if(shape.rotate) {
				if(rotateCounterClockwise(grid,shape,row,col)) {
					keyAction = true;
				}
			}
		}
		else if(command==40) {
			if(shape.rotate) {
				if(rotateClockwise(grid,shape,row,col)) {
					keyAction = true;

				}
			}
		}
		graphGrid.update(grid);
		if(keyAction) {
			game.updateScore(10);
		}
	}

	/** The method that control the clockwise rotation
	 * @param grid the game grid
	 * @param shape the random tetris shape
	 * @param r the #row of the game
	 * @param c the #columb of the game
	 * @return true if can rotate, false otherwise
	 */
	private boolean rotateClockwise(TetrisGrid grid, TetrisShape shape,
			int r, int c){

		grid.removeShape(shape,r,c);
		shape.rotateClockwise();
		if(grid.addShape(shape,r,c))
			return true;
		else {
			shape.rotateCounterClockwise();
			grid.addShape(shape,r,c);
			return false;
		}
	}

	/** The method that control the counter clockwise rotation
	 * @param grid the game grid
	 * @param shape the random tetris shape
	 * @param r the #row of the game
	 * @param c the #columb of the game
	 * @return true if can rotate, false otherwise
	 */
	private boolean rotateCounterClockwise(TetrisGrid grid, TetrisShape shape, 
			int r, int c) {
		grid.removeShape(shape,r,c);
		shape.rotateCounterClockwise();
		if(grid.addShape(shape,r,c))
			return true;
		else {
			shape.rotateClockwise();
			grid.addShape(shape,r,c);
			return false;
		}
	}

	/** The method that control the left movement of the tetris
	 * @param grid the game grid
	 * @param shape the random tetris shape
	 * @param r the #row of the game
	 * @param c the #columb of the game
	 * @return true if can move, false otherwise
	 */
	private boolean moveLeft(TetrisGrid grid, TetrisShape shape, 
			int r, int c) {
		grid.removeShape(shape,r,c);
		if(grid.addShape(shape,r,c-1))
			return true;
		else {
			grid.addShape(shape,r,c);
			return false;
		}
	}

	/** The method that control the right movement of the tetris
	 * @param grid the game grid
	 * @param shape the random tetris shape
	 * @param r the #row of the game
	 * @param c the #columb of the game
	 * @return true if can move, false otherwise
	 */
	private boolean moveRight(TetrisGrid grid, TetrisShape shape, 
			int r, int c) {
		grid.removeShape(shape,r,c);
		if(grid.addShape(shape,r,c+1))
			return true;
		else {
			grid.addShape(shape,r,c);
			return false;
		}
	}

	/** The method that control the down movement of the tetris
	 * @param grid the game grid
	 * @param shape the random tetris shape
	 * @param r the #row of the game
	 * @param c the #column of the game
	 * @return true if can move, false otherwise
	 */
	private boolean moveDown(TetrisGrid grid, TetrisShape shape, 
			int r, int c) {
		grid.removeShape(shape,r,c);
		if(grid.addShape(shape,r+1,c))
			return true;
		else {
			grid.addShape(shape,r,c);
			return false;
		}
	}


	/** Check if shape is placed, if so, then delete the completed row,
	 *  then create a new shape and check if game over
	 * @return true if row is deleted, false else
	 */
	private boolean checkRow() {
		int numDelete;
		grid.removeShape(shape,row,col);
		if(!grid.inBounds(shape,row+1,col) 
				|| grid.intersect(shape, row+1, col) ) {
			grid.addShape(shape,row,col);

			numDelete = grid.deleteRow(grid.completeRow());
			//update score if rows are deleted
			game.updateScore(numDelete); 

			row = 0; //initialize row for a new shape
			col = 4; //initialize column for a new shape
			shape = TetrisShape.createShape();
			if(!checkOver()) { //check if game over  
				grid.addShape(shape, row, col);
				graphGrid.update(grid);
			}
			return true;
				}
		else {
			grid.addShape(shape,row,col);
			return false;

		}
	}

	/** check if next random piece will fit 
	 * @return true if it cannot fit and game over, false otherwise
	 */
	private boolean checkOver() {
		if(grid.intersect(shape, row, col)) {   
			stopMove();
			game.gameOver();
			return true;
		}
		else {
			return false;
		}
	}

	/** Stop the thread
	*/
	public void stopMove() {
		go = false;
	}

	/** Begin the thread
	*/
	public void beginMove() {
		go = true;
	}

	/** The method that start the movement
	*/
	public void run() {
		while(go) {
			for(int i=0; i<=20-speed; i++) {
				if(!go) break; //stop immediately when thread stop
				try { TimeUnit.MILLISECONDS.sleep(WAIT_TIME);}
				catch (InterruptedException e){};
				if(keyAction) {
					i = -1;
					keyAction = false;
				}
				if(spaceKey) { 
					graphGrid.setEnabled(false); 
					spaceKey = false;
				}

			}

			if(!go) break;  //stop immediately when thread stop
			graphGrid.setEnabled(true);

			checkRow();

			if(moveDown(grid,shape,row,col)) {
				row+=1;
				graphGrid.update(grid);
				game.updateScore(10);
			}
			//checkRow();


		}
	}
}






