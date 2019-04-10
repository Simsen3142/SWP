package view.components;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.JPanel;

public class ChipHolder extends JPanel {
	public static final Color BACKGRND_NORMAL=new Color(66, 134, 244);
	public static final Color BACKGRND_HOVER=new Color(100, 180, 255);
	public static final Color BORDER_COLOR=Color.BLACK;
	
	private boolean uppermost=false;
	private boolean bottommost=false;
	
	private int row;
	
	public void setUppermost(boolean uppermost) {
		this.uppermost=uppermost;
	}
	
	public void setBottommost(boolean bottommost) {
		this.bottommost=bottommost;
	}
	
	/**
	 * Create the panel.
	 */
	public ChipHolder(int row) {
		this.row=row;
		setBackground(BACKGRND_NORMAL);
	}
	
	public int getColumn() {
		return row;
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(getBackground());
		Area a = new Area(new Rectangle(0, 0, getWidth(), getHeight()));
		int randW=getWidth()/15;
		int randH=getHeight()/15;

		a.subtract(new Area(new Ellipse2D.Double(randW,randH, getWidth()-randW*2, getHeight()-randH*2)));
		g2d.fill(a);
		
		if(getBackground().equals(BACKGRND_HOVER)) {
			int width=1;
			g.setColor(BORDER_COLOR);
			
			g.fillRect(0, 0, width, getHeight());
			g.fillRect(getWidth()-width, 0, width, getHeight());
			if(uppermost) {
				g.fillRect(0, 0, getWidth(), width);
			}
			if(bottommost) {
				g.fillRect(0, getHeight()-width, getWidth(), width);
			}
		}
	}

}