package view;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import controller.SpielHandler;
import models.Spiel;
import view.components.ChipHolder;
import view.components.CirclePanel;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.function.Function;
import java.awt.Color;
import java.awt.EventQueue;

public class Spielfeld extends JLayeredPane {
	private Rahmen rahmen;
	private ChipView chipview;
	private AnimationLayer animLayer;
	private MainActivity activity;

	/**
	 * Create the panel.
	 */
	public Spielfeld(MainActivity activity) {
		this.activity=activity;
		initialize();
	}

	public ChipView getChipView() {
		return chipview;
	}
	
	public Rahmen getRahmen() {
		return rahmen;
	}
	
	public AnimationLayer getAnimLayer() {
		return animLayer;
	}
	
	public void initialize() {
		this.removeAll();
		setOpaque(false);
		addComponentListener(new ThisComponentListener());
		
		rahmen=new Rahmen(activity);
	    add(rahmen);
	    
	    animLayer=new AnimationLayer();
	    add(animLayer);
	    
	    chipview=new ChipView(this);
	    add(chipview);

	    setLayer(chipview, 2);
	    setLayer(animLayer, 1);
	    setLayer(rahmen, 3);
	}
	
	public void resizeThings() {
		double cols=(double)SpielHandler.getInstance().getWidth();
		double rows=(double)SpielHandler.getInstance().getHeight();
		
		double widthField=(double)getWidth();
		double heightField=(double)getHeight();
		
		double widthQuot=widthField/cols;
		double heightQuot=heightField/rows;
		
		int width;
		int height;
		
		if(heightQuot<widthQuot) {
			height=getHeight();
			width=(int) (heightField*cols/rows);
		}else {
			width=getWidth();
			height=(int) (widthField*rows/cols);
		}

		int x=(getWidth()-width)/2;
		int y=(getHeight()-height)/2;
		animLayer.setBounds(x,y, width, height);
		rahmen.setBounds(x,y, width, height);
		chipview.setBounds(x,y, width, height);
		
		EventQueue.invokeLater(()->{
			revalidate();
			repaint();
		});
	}
	
	public void addChip(int[] pos, Color c, Function<Void, Void> afterAnimation) {
		animLayer.startAnimationPanelToPosition(chipview.getCirclePanels()[pos[0]][pos[1]], new CirclePanel(c), 
			(Void)->{
				chipview.addChip(pos, c);
				
				afterAnimation.apply(null);
				return null;
			}
		);
		
	}
	
	public void removeChip(int[] pos, Color c, Function<Void, Void> afterAnimation) {
		chipview.removeChip(pos);
		EventQueue.invokeLater(()->{
			rahmen.revalidate();
			rahmen.repaint();
		});
		
		animLayer.startAnimationPositionToPanel(chipview.getCirclePanels()[pos[0]][pos[1]], new CirclePanel(c), 
			(Void)->{
				afterAnimation.apply(null);
				return null;
			}
		);
		
	}
	
	private class ThisComponentListener extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent arg0) {
			resizeThings();
		}
	}
}
