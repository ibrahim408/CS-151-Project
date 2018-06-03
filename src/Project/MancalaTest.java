package Project;

/**
 * Main method to test the Mancala project.
 * @author Team Captain
 * Josh Gill, Ibrahim Ibrahim, Alexander Nguyen
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MancalaTest {
	final static int IconWidth = 100;
	final static int IconHeight = 100;
	final static int IconHeight2 = 250;

	public static void main(String[] args) {
		int numberOfStones = 0;
		JFrame frame = new JFrame();
		JFrame menu = new JFrame();
		Model model = new Model();
		MancalaStone[] bucketsView = new MancalaStone[14];
		
		JButton circleStyle = new JButton("Circle Style");
		JButton squareStyle = new JButton("Square Style");
		JButton enter = new JButton("Enter");

		JTextArea area = new JTextArea();
		area.setEditable(false);
		area.setText("How many stones for the pits? (3 or 4)");
		JTextArea topArea = new JTextArea();
		topArea.setEditable(false);
		topArea.setText("Please select a style first.");
		JTextField field = new JTextField(2);

		JPanel centerPanel = new JPanel(new FlowLayout());
		centerPanel.add(circleStyle);
		centerPanel.add(squareStyle);

		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(area);
		southPanel.add(field);
		southPanel.add(enter);

		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numberOfStones = Integer.parseInt(field.getText());
				if (numberOfStones != 3 || numberOfStones != 4) {
					area.setText("Error: not 3 or 4. Try again.");
				}
				if (numberOfStones == 3 || numberOfStones == 4) {
					area.setText("Number of stones set.");
					model.setUserNumberOfRocks(numberOfStones);
					model.update(0);
					menu.dispose();
				}
			}
		});

		circleStyle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int x = 0; x < 14; x++) {
					if (x == 0 || x == 7) {
						bucketsView[x] = new MancalaIcon(IconWidth, IconHeight2, 0);// pit;
					} else {
						bucketsView[x] = new MancalaIcon(IconWidth, IconHeight, 0);// pit2;
					}
				}
				frame.setLocation(menu.getX() + menu.getWidth(), menu.getY());
				MancalaBoard comp = new MancalaBoard(model, bucketsView);
				//comp.addUndoButton();
				
				model.setUserNumberOfRocks(numberOfStones);

				model.attach(comp);

				frame.getContentPane().setBackground(new Color(255, 222, 173));
				frame.setLayout(new BorderLayout());
				frame.setSize(850, 350); // 850 350
				frame.setResizable(false);

				frame.add(comp);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
			}
		});

		squareStyle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int x = 0; x < 14; x++) {
					if (x == 0 || x == 7) {
						bucketsView[x] = new SquareIcon(IconWidth, IconHeight2, 0);// pit;
					} else {
						bucketsView[x] = new SquareIcon(IconWidth, IconHeight, 0);// pit2;
					}
				}
				frame.setLocation(menu.getX() + menu.getWidth(), menu.getY());
				MancalaBoard comp = new MancalaBoard(model, bucketsView);
				
				//comp.addUndoButton();
				
				model.setUserNumberOfRocks(numberOfStones);
				model.attach(comp);

				frame.getContentPane().setBackground(new Color(255, 222, 173));
				frame.setLayout(new BorderLayout());
				frame.setSize(850, 350); // 850 350
				frame.setResizable(false);

				frame.add(comp);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		/*JPanel centerPanel = new JPanel(new FlowLayout());
		centerPanel.add(circleStyle);
		centerPanel.add(squareStyle);

		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(area);
		southPanel.add(field);
		southPanel.add(enter); */

		menu.setLayout(new BorderLayout());
		menu.add(topArea, BorderLayout.NORTH);
		menu.add(centerPanel, BorderLayout.CENTER);
		menu.add(southPanel, BorderLayout.SOUTH);

		menu.pack();
		menu.setVisible(true);
		menu.setResizable(false);
	}
}