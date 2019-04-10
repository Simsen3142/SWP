package controller;

import java.util.ArrayList;
import java.util.List;

import models.Spiel;

public class SpielHandler {
	private List<SpielListener> spielListeners=new ArrayList<>();
	private int height=6;
	private int width=7;
	private int amtToWin=4;
	private static SpielHandler instance;
	
	/**
	 * @return the instance
	 */
	public synchronized static SpielHandler getInstance() {
		if(instance==null)
			instance=new SpielHandler();
		return instance;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the amtToWin
	 */
	public int getAmtToWin() {
		return amtToWin;
	}

	/**
	 * @param amtToWin the amtToWin to set
	 */
	public void setAmtToWin(int amtToWin) {
		this.amtToWin = amtToWin;
	}
	
	private SpielHandler() {
		
	}
	

	public void addSpielListener(SpielListener listener) {
		spielListeners.add(listener);
	}
	
	public void removeSpielListener(SpielListener listener) {
		spielListeners.remove(listener);
	}
	
	public void triggerOnChipPlaced(int column, int row, int user) {
		for(SpielListener al:spielListeners) {
			al.onChipPlaced(column,row,user);
		}
		doCheckWon();
	}
		
	
	public void triggerOnChipPlaced(int column, int user) {
		int[] coords=getSpiel().getNextFreeCoordinates(column);
		if(coords!=null) {
			triggerOnChipPlaced(coords[0],coords[1],user);
		}
	}
	
	public void triggerOnChipRemoved(int column, int row, int user) {
		for(SpielListener al:spielListeners) {
			al.onChipRemoved(column,row,user);
		}
	}
	
	public void triggerOnChipRemoved(int column, int row) {
		int user=getSpiel().getSpielfeld()[column][row];
		triggerOnChipRemoved(column,row,user);
	}
	
	private void triggerOnWon(ArrayList<int[]> wonCoords,int user) {
		for(SpielListener al:spielListeners) {
			al.onWon(wonCoords,user);
		}
	}
	
	private void triggerOnWon(int user) {
		ArrayList<int[]> wonCoords=getSpiel().getWonCoordinates();
		triggerOnWon(wonCoords,user);
	}
	
	public void triggerOnNewGame(int amtToWin, int columns, int rows) {
		this.amtToWin=amtToWin;
		this.height=rows;
		this.width=columns;
		for(SpielListener al:spielListeners) {
			al.onNewGame(amtToWin,columns, rows);
		}
	}
	
	private void doCheckWon() {
		int won=getSpiel().checkWon();
		if(won!=0) {
			System.out.println("WON!");
			triggerOnWon(won);
		}
	}
	
	private Spiel getSpiel() {
		for(SpielListener al:spielListeners) {
			if(al instanceof Spiel) {
				return (Spiel) al;
			}
		}
		return null;
	}
	
}
