package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.util.ArrayList;
import java.util.function.Function;

import javax.swing.JPanel;

import view.components.CirclePanel;

import javax.swing.JLabel;

public class AnimationLayer extends JPanel {

	private ArrayList<CirclePanel> pnls=new ArrayList<>();
	private AnimationLayer animLayer=this;
	private int time4Animation=400;
	private JLabel lblTest;
	/**
	 * Create the panel.
	 */
	public AnimationLayer() {
		initialize();
	}
	
	private void initialize() {
		setOpaque(false);
		setLayout(null);
		lblTest = new JLabel("TEST");
		add(lblTest);
	}
	
	public void startAnimationPanelToPosition(CirclePanel endPnl, CirclePanel pnl, Function<Void, Void> exeEnd) {
		new AnimationToPosition(time4Animation,endPnl,pnl,exeEnd).start();
	}
	
	public void startAnimationPositionToPanel(CirclePanel endPnl, CirclePanel pnl, Function<Void, Void> exeEnd) {
		new AnimationToPosition(time4Animation,endPnl,pnl,exeEnd,true).start();
	}

	private class AnimationToPosition extends Thread {
		private long time4Animation;
		private Point start;
		private Point end;
		private CirclePanel endPnl;
		private CirclePanel pnl;
		private Function<Void,Void> exeEnd;
		private boolean reverse=false;
		
		public AnimationToPosition(long time4Animation, CirclePanel endPnl, CirclePanel pnl, Function<Void, Void> exeEnd) {
			this(time4Animation, endPnl, pnl, exeEnd, false);
		}
		
		public AnimationToPosition(long time4Animation, CirclePanel endPnl, CirclePanel pnl, Function<Void, Void> exeEnd, boolean reverse) {
			this.endPnl=endPnl;
			setPoints();
			this.time4Animation=time4Animation;
			this.pnl=pnl;
			animLayer.add(pnl);
			this.exeEnd=exeEnd;
			this.reverse=reverse;
		}
		
		@Override
		public void run() {
			double progress;
			long startTime=System.currentTimeMillis();
			long time=startTime;
			do {
				setPoints();
				progress=(double)(time-startTime)/(double)time4Animation;
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
				}
				
				pnl.setBounds(end.x, start.y+(int)(progress*(double)(end.y-start.y)),endPnl.getWidth(),endPnl.getHeight());

				EventQueue.invokeLater(()->{
					animLayer.revalidate();
					animLayer.repaint();
				});
				time=System.currentTimeMillis();
			}while(time-startTime<time4Animation);
			
			pnl.setBounds(end.x, end.y,endPnl.getWidth(),endPnl.getHeight());
			EventQueue.invokeLater(()->{
				animLayer.revalidate();
				animLayer.repaint();
			});
			
			animLayer.remove(pnl);
			exeEnd.apply(null);
		}
		
		private void setPoints() {
			if(!reverse) {
				start=new Point(endPnl.getX(),0);
				end=new Point(endPnl.getX(),endPnl.getY());
			}else {
				end=new Point(endPnl.getX(),0);
				start=new Point(endPnl.getX(),endPnl.getY());
			}
		}
	}
}
