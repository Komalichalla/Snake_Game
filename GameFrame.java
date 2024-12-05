package snakeGame;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	public static void Frame() {
		GameFrame gm = new GameFrame();
		SnakePanel sp = new SnakePanel();
		gm.setTitle("Snakeladder");
		gm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gm.setResizable(false);
		gm.add(sp);
		gm.pack();
		gm.setVisible(true);
		gm.setLocationRelativeTo(null);
		sp.setPreferredSize(new Dimension(SnakePanel.panelHeight, SnakePanel.panelWidth));

	}

}
