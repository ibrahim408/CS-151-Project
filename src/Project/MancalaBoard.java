package Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Random;
/**
 * Creates a Panel that draws a board and holds a number of pits that will contain stones.
 * @author Team Captain
 * Josh Gill, Ibrahim Ibrahim, Alexander Nguyen
 */
public class MancalaBoard extends JPanel implements MouseListener, MouseMotionListener, ChangeListener {

	private Model model;

	// info needed to pain each bucket
	MancalaStone bucketsView[];

	// coordinates to paint buckets
	int mancalaX = 5;
	int mancalaY = 30;
	int pitX = 105;
	int pitY = 30;

	// text
	Font sansbold14 = new Font("SansSerif", Font.BOLD, 14);
	Font serif = new Font("Serif", Font.BOLD, 36);
	String message = "MANCALA";

	Random rand = new Random();
	/**
	 * Creates a panel with a model and array of the style.
	 * @param m
	 * @param style
	 */
	public MancalaBoard(Model m, MancalaStone[] style) 
	{
		model = m;
		bucketsView = style;

		addMouseMotionListener(this);
		addMouseListener(this);
		this.setLayout(null);
		this.setBackground(new Color(255, 222, 173));
	}
	/**
	 * Paints board and pits on the board.
	 * @param g Graphic to be drawn.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(sansbold14);
		String text = "A";
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////// Draw the buckets
		for (int x = 0; x < 14; x++) {

			if (x == 0 || x == 7) {
				bucketsView[x].setLocation(mancalaX, mancalaY);
				bucketsView[x].paintIcon(this, g, mancalaX, mancalaY);
			} else {
				bucketsView[x].setLocation(pitX, pitY);
				bucketsView[x].paintIcon(this, g, pitX, pitY);
				if (x > 6) {
					pitX -= 105;
				} else {
					pitX += 105;
				}
			}
			if (x == 6) {
				pitY = pitY + 150;
				// pitX = 105;
				pitX = 630;
				mancalaX += 725;
			}
		}
		// draw labels
		pitX = 210;
		pitY = 30;
		for (int i = 0; i < 2; i++) {
			for (int j = 1; j <= 6; j++) {
				g2.setColor(Color.black);
				g2.drawString(text + j, pitX - 65, pitY - 15);
				pitX += 105;
			}
			text = "B";
			pitX = 210;
			pitY = 330;
		}
		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D bounds = serif.getStringBounds(message, context);

		double x = (getWidth() - bounds.getWidth()) / 2;
		double y = (getHeight() - bounds.getHeight()) / 2;

		double ascentleading = -bounds.getY();
		double baseY = y + ascentleading;

		g2.drawString("<-- Player A", (int) x + 37, 26);
		g2.drawString("Player B -->", (int) x + 37, 295);

		g2.setFont(serif);

		g2.drawString(message, (int) x, (int) baseY);
		g2.setPaint(Color.BLACK);

		g2.draw(new Line2D.Double(x, baseY, x + bounds.getWidth(), baseY));

		mancalaX = 5;
		mancalaY = 30;
		pitX = 105;
		pitY = 30;
		
		addUndoButton();
		
		if (model.getPlayerOne())
		{
			g2.setFont(sansbold14);
			g2.drawString("Player A has won!", (int) x - 175, (int) baseY - 10);
		}
		
		if (model.getPlayerTwo())
		{
			g2.setFont(sansbold14);
			g2.drawString("Player B has won!", (int) x - 175, (int) baseY - 10);
		}
	}
	/**
	 * Implementation for Change Listener for a change in the model has occurred.
	 * @param e A change Event object.
	 */
	public void stateChanged(ChangeEvent e) {

		for (int x = 0; x < 14; x++) {
			bucketsView[x].setNumberOfRocks(model.getbucket(x).getnumOfRocks());
		}
		this.repaint();
	}

	/**
	 * Creates and adds Undo button.
	 */
	public void addUndoButton()
	{
		JButton undo = new JButton("Undo");
		undo.setBounds(550, 145, 100, 30);
		undo.setBackground(Color.white);
		undo.setForeground(Color.black);

		undo.addActionListener(new ActionListener() {
			/**
			 * Performs and action
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					model.update(99);
				} catch (Exception exc) {
					System.out.println(exc);
				}
			}
		});
		add(undo);
	}
	/**
	 * If mouse pressed on a pit it calls update of model to play game.
	 */
	public void mousePressed(MouseEvent e) {

		for (int x = 0; x < 14; x++) {
			if (x == 0 || x == 7) {

				if (bucketsView[x].containCheck((double) e.getX(), (double) e.getY())) {
					// System.out.println("this is a mancala");
					// System.out.println("this is index "+ x);
				}
			} else {
				if (bucketsView[x].containCheck((double) e.getX(), (double) e.getY())) {
					// System.out.println("this is a pit");
					// System.out.println("this is index "+ x);

					model.update(x);
				}
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
}