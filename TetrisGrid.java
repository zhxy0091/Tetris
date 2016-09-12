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
import java.util.*;
import java.awt.*;

/** The helper class that defines the game grid
 * @author Xinyuan Zhang; Chenghao Gong
 * @version 28/May/2015
 */
public class TetrisGrid {
	/* Fields
	 * ROW the #row of the tetris game
	 * COL the #column of the tetris game
	 * grid the 2D array with the char type
	 */
	private ArrayList<Coord> occupied = new ArrayList<Coord>();
	public static final int ROW = Tetris.ROW;
	public static final int COL = Tetris.COL;
	private char[][] grid; 

	/** Constructor
	*/
	public TetrisGrid() {
		grid = new char[ROW][COL];
		clearGrid();
	}

	/** the method that clears the extant grid
	*/
	public void clearGrid() {
		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COL; j++) {
				grid[i][j] = '.';
			}
		}
	}
	/** the method that clear the extant grid and creates a new grid
	*/
	private void updateGrid() {
		clearGrid();
		for(Coord location : occupied) {
			if(location.r>=0) {  //ignore location out of the top bound
				grid[location.r][location.c] = '*';
			}
		}
	}
	/** the method that add the shape
	 * @param shape the tetris shape
	 * @param r the #row of the grid
	 * @param c the #column of the grid
	 * @return true if the shape can be added, return false else
	 */
	public boolean addShape(TetrisShape shape, int r, int c) {
		if(inBounds(shape, r, c) && !intersect(shape, r, c)) {
			for(Coord location : shape.getShape(r,c)) {
				if(location.r>=0) {
					occupied.add(location);
				}
			}
			updateGrid();
			return true;
		}
		else {
			return false;
		}
	}
	/** the method that remove the shape
	 * @param shape the tetris shape
	 * @param r the #row of the grid
	 * @param c the #column of the grid
	 * @return true if the shape can be removed, return false else
	 */
	public boolean removeShape(TetrisShape shape, int r, int c) {
		occupied.removeAll(shape.getShape(r,c));
		updateGrid();
		return true;
	}
	/** the method that check if the shape is in the boundary
	 * @param shape the tetris shape
	 * @param r the #row of the grid
	 * @param c the #column of the grid
	 * @return true if the shape is inside boundary, return false else
	 */

	public boolean inBounds(TetrisShape shape, int r, int c) {
		boolean inbound = true;
		for(Coord location : shape.getShape(r,c)) {  //ignore top bound
			if(location.r>=ROW || location.c<0 || location.c>=COL) {
				inbound = false;
			}
		}
		return inbound;
	}
	/** the method that check if the shapes are intersected with each other
	 * @param shape the tetris shape
	 * @param r the #row of the grid
	 * @param c the #column of the grid
	 * @return true if the shapes are intersected, return false else
	 */
	public boolean intersect(TetrisShape shape, int r, int c) {
		boolean intersect = false;
		for(Coord location : shape.getShape(r,c)) { 
			if(occupied.contains(location)) {
				intersect = true;
			}
		}
		return intersect;
	}

	/** Return a reference to an Arraylist of coordinats of occupied blocks
	 * @return a reference to an Arraylist of coordinats of occupied blocks
	 */
	public ArrayList<Coord> getOccupied() {
		return occupied;
	}

	/** create an int array and each index refer to each row 
	 * set completed row to 1 and incompleted row to 0;
	 * @return an int array with completed row equal to 1
	 */
	public int[] completeRow() {
		int[] row = new int[ROW]; //set all row to 0
		updateGrid();
		for(int i=0; i<ROW; i++) {
			int count=0;
			for(int j=0; j<COL; j++) {
				if(grid[i][j]=='*') {
					count++;
				}
			}
			if(count==10) {
				row[i] = 1; //set competed row to 1
			}
		}
		return row;
	}
	/** the method that delete the row
	 * @param r the array that coorespond to the row number
	 * @return number of deleted row
	 */
	public int deleteRow(int[] r) {
		int numDelete=0; //check how many rows are deleted
		for(int i=0; i<r.length; i++) {
			if(r[i]==1) { 
				for(int j=0; j<COL; j++) {
					occupied.remove(new Coord(i,j));
				}
				numDelete += 1;
				//shift down the above rows
				for(Coord location : occupied) {
					if(location.r<i) {
						location.r += 1;
					}
				}
			}
		}
		updateGrid();
		return numDelete;
	}


	/** Override toString method for TetrisGrid object
	*/
	@Override
	public String toString() {
		String s = "";
		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COL; j++) {
				s += grid[i][j];
			}
			s += "\n";  
		}
		return s;
	}
}

