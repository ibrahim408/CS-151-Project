package Project;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;
/**
 * A interface for MancalaStone styles.
 * @author Team Captain
 * Josh Gill, Ibrahim Ibrahim, Alex Nguyen
 */
public interface MancalaStone extends Icon
{
	
	public void setLocation(int x, int y);

	public void setNumberOfRocks(int num);
	
	public int getNumberOfRocks();
	
	public int getXLocation();
	
	public int getYLocation();
	
	public boolean containCheck(double x, double y);

	public void PaintRocks(Component c, Graphics g);
}