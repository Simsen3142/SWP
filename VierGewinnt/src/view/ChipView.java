package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import controller.SpielHandler;
import models.Spiel;
import view.components.ChipHolder;
import view.components.CirclePanel;

public class ChipView extends JPanel {
	
	private CirclePanel[][] circlePanel;
	private Spielfeld spielfeld;

	/**
	 * Create the panel.
	 */
	public ChipView(Spielfeld spielfeld) {
		this.spielfeld=spielfeld;
		initialize();
	}
	
	public void initialize() {
		setOpaque(false);
		circlePanel=new CirclePanel[SpielHandler.getInstance().getWidth()][SpielHandler.getInstance().getHeight()];
		setLayout(new GridLayout(SpielHandler.getInstance().getHeight(),SpielHandler.getInstance().getWidth()));
		this.removeAll();
		initChips();
	}
	
	private void initChips() {
		for(int y=0;y<SpielHandler.getInstance().getHeight();y++) {
			for(int x=0;x<SpielHandler.getInstance().getWidth();x++) {
				circlePanel[x][y]=new CirclePanel(Color.BLACK);
				circlePanel[x][y].setVisible(false);
				this.add(circlePanel[x][y]);
			}
		}
	}

	public void addChip(int[] pos, Color c) {
		circlePanel[pos[0]][pos[1]].setBackground(c);
		circlePanel[pos[0]][pos[1]].setVisible(true);
	}
	

	public CirclePanel removeChip(int[] pos) {
		CirclePanel pnl=circlePanel[pos[0]][pos[1]];
		pnl.setBackground(Color.BLACK);
		pnl.setVisible(false);
		
		return pnl;
	}
	
	public CirclePanel[][] getCirclePanels(){
		return circlePanel;
	}
	
	public void visualizeWonChips(ArrayList<int[]> wonCoordinates) {
		int value=100;
		int increasing=10;
		int delay=60;
		int repetitions=3;
				
		int[] coordinatesFirst=wonCoordinates.get(0);
		CirclePanel pnlFirst=circlePanel[coordinatesFirst[0]][coordinatesFirst[1]];
		Color startColor=pnlFirst.getBackground();
		int[] rgb=new int[] {startColor.getRed(), startColor.getGreen(), startColor.getBlue()};
		
		double brightness=Math.sqrt(
				0.299*Math.pow(rgb[0],2) +
				0.587*Math.pow(rgb[1],2) +
				0.114*Math.pow(rgb[2],2));
		boolean dark=brightness<255/2;

		new Thread(new Runnable() {
			private long startTime;
			private long time4anim=1000;
			
			@Override
			public void run() {
				
				for(int i=0;i<repetitions;i++) {
					animateWonChips(true);
					animateWonChips(false);
					
					paintWonChips(wonCoordinates, startColor);
				}
			}
			
			private void animateWonChips(boolean brighter) {
				long startTime=System.currentTimeMillis();
				for(;;) {
					long time=System.currentTimeMillis()-startTime;
					Color c=getColor(startColor, time, time4anim, value, dark, brighter);
					paintWonChips(wonCoordinates, c);
					if(time>time4anim) {
						break;
					}
					try {
						Thread.sleep(delay);
					}catch (Exception e) {
					}
					if(time>time4anim) {
						break;
					}
				}
			}
		}).start();
	}
	
	
	
	private void paintWonChips(ArrayList<int[]> wonCoordinates, Color c) {
		for (int[] coordinates : wonCoordinates) {
			CirclePanel pnl=circlePanel[coordinates[0]][coordinates[1]];
			pnl.setBackground(c);
		}
		EventQueue.invokeLater(()->{
			revalidate();
			repaint();
		});
	}
	
	private Color getColor(Color startColor, long progress, long time4anim, int value, boolean dark, boolean increasing) {
		double amt=(double)value;
		amt=(dark?amt:-amt);

		int[] rgb=new int[] {startColor.getRed(),startColor.getGreen(),startColor.getBlue()};		
		
		int a=(int)(increasing?0:amt);
		int b=(int)(((double)progress/(double)time4anim)*(increasing?amt:-amt));
		int c=a+b;
		
		for(int i=0;i<rgb.length;i++) {
			rgb[i]=c+rgb[i];
			if(rgb[i]>255)
				rgb[i]=255;
			else if(rgb[i]<0)
				rgb[i]=0;
		}
		
		return new Color(rgb[0],rgb[1],rgb[2]);
	}
}



