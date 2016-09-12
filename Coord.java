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

/** A helper class that help us coorespond the coordinates
 * @author Xinyuan Zhang; Chenghao Gong
 * @version 28/May/2015
 */
public class Coord {
	public int r; //row coordinate
	public int c; //column coordinate
	public Color color = Color.PINK;
	/** Constructor method
	*/
	public Coord() {
		this.r = 0;
		this.c = 0;
	}
	/** Constructor method
	 * @param r the row coordinate
	 * @param c the colume coordinate
	 */
	public Coord(int r, int c) {
		this.r = r;
		this.c = c;
	}

	public Coord(int r, int c, Color color) {
		this.r = r;
		this.c = c;
		this.color = color;
	}
	/** Constructor method
	 * @param initial the initial coordinates
	 */
	public Coord(Coord initial) {
		this.r = initial.r;
		this.c = initial.c;
	}
	/** Compare two Coord objects
	 * @param o a reference to an object
	 */
	@Override	
	public boolean equals(Object o) {
		if(!(o instanceof Coord)) {
			return false;
		}
		Coord other = (Coord) o;
		return this.r == other.r && this.c ==other.c;
	}

	/** Override toString method for Coord object
	*/
	@Override
	public String toString() {
		String s = "";
		s = s.format("(%d,%d)", r, c);
		return s;
	}
}	
