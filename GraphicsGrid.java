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
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
/** A helper class that help us build the gird
 * @author Xinyuan Zhang; Chenghao Gong
 * @version 28/May/2015
 */
public class GraphicsGrid extends JPanel {

	private ArrayList<Coord> fillCells;
	private int width, height, pixels; 


	/**	Constructor
	 * @param p size of each grid square
	 */
	public GraphicsGrid(int p) {
		width = p*Tetris.COL;
		height = p*Tetris.ROW;
		pixels = p;
		fillCells = new ArrayList<Coord>();
		setFocusable(true);
		requestFocus(true);
	}

	/** Override getPreferredSize method in Jpanel class to make 
	 * a preferred dimension.
	 */
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(width+2, height+2);  
	}

	/** Override paintComponent method in Jpanel class to draw lines 
	 * for the grid and paint certain blocks with red color
	 * @param g current graphic object
	 */
	@Override
	protected synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		//draw the grid from the offset to center the grid in panel
		int x = (this.getWidth()-width)/2;  //offset in x direction
		int y = (this.getHeight()-height)/2; //offset in y direction
		if (fillCells == null) return;
		for (Coord fillCell : fillCells) {
			int cellX = (fillCell.c * pixels)+x;
			int cellY = (fillCell.r * pixels)+y;
			g.setColor(fillCell.color);
			g.fillRect(cellX, cellY, pixels, pixels);
		}
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);

		for (int i = 0; i < width; i += pixels) {
			g.drawLine(i+x, y, i+x, height+y);
		}

		for (int i = 0; i < height; i += pixels) {
			g.drawLine(x, i+y, width+x, i+y);
		}
	}

	/** A method that paints a block
	 * @param occupied an arraylist of coordinates of occupied blocks
	 */
	public synchronized void fillCell(ArrayList<Coord> occupied) {
		fillCells.addAll(occupied);
		repaint();
	}

	/** A method that erases a block
	*/
	public synchronized void clearCell() {
		fillCells.clear();
		repaint();
	} 


	/** Erase all blocks and paint new occupied blocks
	 * @param g a reference to a TetrisGrid object
	 */
	public synchronized void update(TetrisGrid g) {
		clearCell();
		fillCell(g.getOccupied());
	}

}



