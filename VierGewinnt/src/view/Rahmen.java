package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import controller.SpielHandler;
import controller.UserActionHandler;
import models.Spiel;
import models.Spieler;
import view.components.ChipHolder;

public class Rahmen extends JPanel {
	
	private ChipHolder[][] chipHolder;
	private int lastFocusedCol=-1;
	private RowListener rowListener;
	private MainActivity activity;
	
	/**
	 * Create the panel.
	 */
	public Rahmen(MainActivity activity) {
		this.activity=activity;
		initialize();
	}
	
	private void initialize() {
		chipHolder=new ChipHolder[SpielHandler.getInstance().getWidth()][SpielHandler.getInstance().getHeight()];
		setOpaque(false);
		setLayout(new GridLayout(SpielHandler.getInstance().getHeight(),SpielHandler.getInstance().getWidth()));
		rowListener=new RowListener();
		initChipHolders();
	}
	
	private void initChipHolders() {
		for(int y=0;y<SpielHandler.getInstance().getHeight();y++) {
			boolean uppermost=y==0;
			boolean bottommost=y==SpielHandler.getInstance().getHeight()-1;
			for(int x=0;x<SpielHandler.getInstance().getWidth();x++) {
				ChipHolder ch=new ChipHolder(x);
				if(uppermost)
					ch.setUppermost(true);
				if(bottommost)
					ch.setBottommost(true);

				ch.addMouseListener(rowListener);
				chipHolder[x][y]=ch;
				this.add(ch);
			}
		}
	}

	public RowListener getRowListener() {
		return rowListener;
	}
	
	public int getLastFocusedRow() {
		return lastFocusedCol;
	}
	
	public void requestFocusX() {
		activity.requestFocus();
	}
	
	private class RowListener implements MouseListener, KeyListener{
		private ArrayList<Integer> pressedKeys = new ArrayList<Integer>();
		private long lastTimeClicked=0;
		private long clickAgainTimer=50;
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			ChipHolder ch=(ChipHolder)arg0.getSource();
			int row=ch.getColumn();
			
			if(lastFocusedCol!=row) {
				markColumnToHovering(row, true);
				EventQueue.invokeLater(()->{
					revalidate();
					repaint();
				});
				lastFocusedCol=row;
			}
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			ChipHolder ch=(ChipHolder)arg0.getSource();
			int column=ch.getColumn();
			
			if(lastFocusedCol!=column) {
				UserActionHandler.getInstance().triggerOnColumnSelected(null, column);
//				markRowToHovering(row, true);
				EventQueue.invokeLater(()->{
					revalidate();
					repaint();
				});
				lastFocusedCol=column;
			}
		}
		
		@Override
		public void mousePressed(MouseEvent arg0) {
			ChipHolder ch=(ChipHolder)arg0.getSource();
			int column=ch.getColumn();
			if(column!=lastFocusedCol) {
				UserActionHandler.getInstance().triggerOnColumnSelected(null, column);
//				markRowToHovering(column, true);
				EventQueue.invokeLater(()->{
					revalidate();
					repaint();
				});
				lastFocusedCol=column;
			}
			
			if(arg0.getButton()==MouseEvent.BUTTON1) {
				requestFocusX();
				if(System.currentTimeMillis()-lastTimeClicked>clickAgainTimer) {
					UserActionHandler.getInstance().triggerOnColumnClicked(Spieler.getSpielerAmZug(), column);
				}
				lastTimeClicked=System.currentTimeMillis();
			}
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			if(arg0.getButton()==MouseEvent.BUTTON1) {
				lastTimeClicked=System.currentTimeMillis();
			}
		}
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			int key=arg0.getKeyCode();
			if(!pressedKeys.contains(new Integer(key))) {
				pressedKeys.add(key);
			}else {
				return;
			}
			if(key==KeyEvent.VK_LEFT || key==KeyEvent.VK_A) {
				if(lastFocusedCol-1>=0) {
//					markRowToHovering(lastFocusedCol-1,true);
					UserActionHandler.getInstance().triggerOnColumnSelected(null, lastFocusedCol-1);
					lastFocusedCol--;
					
					EventQueue.invokeLater(()->{
						revalidate();
						repaint();
					});
				}
			}else if(key==KeyEvent.VK_RIGHT || key==KeyEvent.VK_D) {
				if(lastFocusedCol+1<SpielHandler.getInstance().getWidth()) {
					UserActionHandler.getInstance().triggerOnColumnSelected(null, lastFocusedCol+1);
//					markRowToHovering(lastFocusedCol+1,true);
					lastFocusedCol++;
					
					EventQueue.invokeLater(()->{
						revalidate();
						repaint();
					});
				}
			}else if(key==KeyEvent.VK_DOWN || key==KeyEvent.VK_S || key==KeyEvent.VK_ENTER) {
//				main.doZug(lastFocusedRow);
				UserActionHandler.getInstance().triggerOnColumnClicked(Spieler.getSpielerAmZug(), lastFocusedCol);
			}else if(pressedKeys.contains(KeyEvent.VK_ESCAPE) && pressedKeys.contains(KeyEvent.VK_DELETE)) {
				activity.getButtonReset().doClick();
				pressedKeys.clear();
			}/**/
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
			int key=arg0.getKeyCode();
			pressedKeys.remove(new Integer(key));
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}		
	}
	
	public void markColumnToHovering(int column, boolean hovering) {
		if(lastFocusedCol>=0) {
			for(int y=0;y<SpielHandler.getInstance().getHeight();y++) {
				chipHolder[lastFocusedCol][y].setBackground(ChipHolder.BACKGRND_NORMAL);
			}
		}
		
		for(int y=0;y<SpielHandler.getInstance().getHeight();y++) {
			chipHolder[column][y].setBackground(hovering?ChipHolder.BACKGRND_HOVER:ChipHolder.BACKGRND_NORMAL);
		}
		if(hovering) {
			MainActivity.playSound("MouseClickFast.wav");
		}
	}
}
