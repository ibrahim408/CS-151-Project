package Project;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import javax.swing.Icon;
/**
 * Style that creates circular pits.
 * @author Team Captain
 * Josh Gill, Ibrahim Ibrahim, Alexander Nguyen
 */
public class MancalaIcon implements MancalaStone {
	Ellipse2D.Double pit;
	int width;
	int height;
	int xlocation;
	int ylocation;
	int numberOfRocks;
	Random rand = new Random();
	Color randomColor;
	/**
	 * Creates an Icon of Mancala pits, with a count of rocks.
	 * @param width Width of the Icon.
	 * @param height height of the Icon.
	 * @param rockCount Number of rocks in the pit.
	 */
	public MancalaIcon(int width, int height, int rockCount) {
		this.width = width;
		this.height = height;
		this.numberOfRocks = rockCount;

		randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
	}
	/**
	 * Gets the value of Height
	 * @return the value of Height.
	 */
	public int getIconHeight() {
		return height;
	}
	/**
	 * Gets the value of width.
	 * @return the value of width.
	 */
	public int getIconWidth() {
		return width;
	}
	/**
	 * Sets the Icon width.
	 * @param x value to change width.
	 */
	public void setIconWidth(int x) {
		width = x;
	}
	/**
	 * Sets the height of the icon.
	 * @param y the int to change height to.
	 */
	public void setIconHeight(int y) {
		height = y;
	}
	/**
	 * Sets the location of x and y for painting.
	 */
	public void setLocation(int x, int y) {
		xlocation = x;
		ylocation = y;
	}
	/**
	 * Sets the number of rocks.
	 */
	public void setNumberOfRocks(int num) {
		this.numberOfRocks = num;
	}
	/**
	 * Returns the value of NumberOfRocks.
	 * @return the int of number of rocks
	 */
	public int getNumberOfRocks() {
		return numberOfRocks;
	}
	/**
	 * Gets the x value of the location
	 * @return the int of x.
	 */
	public int getXLocation() {
		return xlocation;
	}
	/**
	 * Gets the y value of the location.
	 * @return the int of y.
	 */
	public int getYLocation() {
		return ylocation;
	}
	/**
	 * Checks a x and y value if they are contained with a pit.
	 * @param x X-value being checked
	 * @param y Y-value being checked 
	 */
	public boolean containCheck(double x, double y) {
		return pit.contains(x, y);
	}
	/**
	 * Paints the pits and stones for the board.
	 * @param c Component that is passed for painting.
	 * @param g Graphic to draw pits. 
	 * @param x Location to draw in x direction.
	 * @param y Location to draw in y direction.
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		pit = new Ellipse2D.Double(x, y, width, height);
		g2.setColor(randomColor);
		g2.fill(pit);
		g2.draw(pit);
		PaintRocks(c, g);
	}
	/**
	 * Paints the stones inside the pits.
	 * @param c passed variable of Paint Icon
	 * @param g Graphic to fill ovals.
	 */
	public void PaintRocks(Component c, Graphics g) {
		Color rockColor = Color.WHITE;
		int rockLocation1 = 20;
		int rockLocation2 = 20;
		int sizex = width / 4;
		int sizey = height / 4;
		if (height == 250) {
			sizey = 100 / 4;
		}
		for (int k = 0; k < numberOfRocks; k++) {
			g.setColor(rockColor);
			g.fillOval(xlocation + rockLocation1, ylocation + rockLocation2, sizex, sizey);

			if (height != 250 && !pit.contains(xlocation + rockLocation1 + sizex, ylocation + rockLocation2 + sizey)) {
				rockLocation1 = 10;
				rockLocation2 = 10;
				rockColor = Color.BLACK;
			}
			if (height == 250 && !pit.contains(xlocation + rockLocation1 + sizex, ylocation + rockLocation2 + sizey)) {
				rockLocation1 = 25;
				rockLocation2 = 25;
				rockColor = Color.BLACK;
			}

			if (k % 2 == 0) {
				rockLocation1 += 25;
			} else {
				rockLocation1 -= 25;
				rockLocation2 += 25;
			}
		}
	}
}