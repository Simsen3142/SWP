package view.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CirclePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public CirclePanel(Color c) {
		this.setBackground(c);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillOval(0, 0, getWidth(), getHeight());
	}
}
