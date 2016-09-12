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
/** The helper class that defines the tetris shapes
 * @author Xinyuan Zhang; Chenghao Gong
 * @version 28/May/2015
 */
public class TetrisShape {
	/** Fields
	 * shape the arraylist of the type Coord
	 * rotate the boolean variable that determina the rotation of the shape
	 */
	private ArrayList<Coord> shape = new ArrayList<Coord>();
	public boolean rotate = true; //if shape can rotate	
	/** Create a random shape
	 * @return	a reference of a new random shape object
	 */
	public static TetrisShape createShape() {
		switch ((int)(Math.random()*7)) {
			case 0: 
				return new TetrisShape('o');
			case 1:
				return new TetrisShape('i');
			case 2: 
				return new TetrisShape('s');
			case 3: 
				return new TetrisShape('z');
			case 4: 
				return new TetrisShape('l');
			case 5: 
				return new TetrisShape('j');
			case 6: 
				return new TetrisShape('t');
			default:
				return null;
		}
	}

	/** Create a specific type of shape
	 * @param type the char that coorespond to the shape
	 * @return a reference of a new specific shape object
	 */
	public static TetrisShape createShape(char type) {
		switch (type) {
			case 'o': 
				return new TetrisShape('o');
			case 'i':
				return new TetrisShape('i');
			case 's': 
				return new TetrisShape('s');
			case 'z': 
				return new TetrisShape('z');
			case 'l': 
				return new TetrisShape('l');
			case 'j': 
				return new TetrisShape('j');
			case 't': 
				return new TetrisShape('t');
			default:
				return null;
		}
	}


	/** Constructor: set initial coordinates for each type of shapes
	 * @param type the character that coorespond to the different shape
	 */
	private TetrisShape(char type) {
		switch (type) {
			case 'o':
				shape.add(new Coord(0,1,Color.ORANGE));
				shape.add(new Coord(-1,1,Color.ORANGE));
				shape.add(new Coord(0,0,Color.ORANGE));
				shape.add(new Coord(-1,0,Color.ORANGE));
				rotate = false;
				break;
			case 'i':
				shape.add(new Coord(-2,0,Color.GRAY));
				shape.add(new Coord(-1,0,Color.GRAY));
				shape.add(new Coord(0,0,Color.GRAY));
				shape.add(new Coord(1,0,Color.GRAY));
				break;
			case 's':
				shape.add(new Coord(-1,0,Color.RED));
				shape.add(new Coord(-1,1,Color.RED));
				shape.add(new Coord(0,-1,Color.RED));
				shape.add(new Coord(0,0,Color.RED));
				break;
			case 'z':
				shape.add(new Coord(-1,-1,Color.GREEN));
				shape.add(new Coord(-1,0,Color.GREEN));
				shape.add(new Coord(0,0,Color.GREEN));
				shape.add(new Coord(0,1,Color.GREEN));
				break;
			case 'l':
				shape.add(new Coord(-1,0,Color.BLUE));
				shape.add(new Coord(0,0,Color.BLUE));
				shape.add(new Coord(1,0,Color.BLUE));
				shape.add(new Coord(1,1,Color.BLUE));
				break;
			case 'j':
				shape.add(new Coord(-1,0,Color.PINK));
				shape.add(new Coord(0,0,Color.PINK));
				shape.add(new Coord(1,0,Color.PINK));
				shape.add(new Coord(1,-1,Color.PINK));
				break;
			case 't':
				shape.add(new Coord(0,-1,Color.MAGENTA));
				shape.add(new Coord(0,0,Color.MAGENTA));
				shape.add(new Coord(0,1,Color.MAGENTA));
				shape.add(new Coord(1,0,Color.MAGENTA));
				break;
			default: 
				break;
		}
	}
	/** The method that control the counter clockwise rotation
	*/
	public void rotateCounterClockwise() {
		int temp = 0;
		if(rotate) {
			for(Coord location : shape) {
				temp = location.r;
				location.r = -location.c;
				location.c = temp;
			}
		}
	}
	/** The method that control the clockwise rotation
	*/
	public void rotateClockwise() {
		int temp = 0;
		if(rotate) {
			for(Coord location : shape) {
				temp = location.r;
				location.r = location.c;
				location.c = -temp;
			}
		}
	}
	/** The method that get the arraylist of coordinates of the shape
	 * @return a reference to an arraylist of coordinates of the shape
	 */
	public ArrayList<Coord> getShape() {
		return shape;
	}

	/** The method that get the arraylist of offset coordinates of the shape
	 * @param x offset of x coordinate
	 * @param y offset of y coordinate
	 * @return a reference to an arraylist of offset coordinates of the shape
	 */
	public ArrayList<Coord> getShape(int x, int y) {
		ArrayList<Coord> newShape = new ArrayList<Coord>();
		for(Coord location : shape) {
			Coord newLocation = new Coord();
			newLocation.r = location.r + x;
			newLocation.c = location.c + y;
			newLocation.color = location.color;
			newShape.add(newLocation);
		}
		return newShape;
	}
}




