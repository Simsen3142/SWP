package controller;

import java.util.ArrayList;

public interface SpielListener {
	public void onChipPlaced(int column,int row, int user);
	public void onChipRemoved(int column, int row, int user);
	public void onWon(ArrayList<int[]> wonCoords, int user);
	public void onNewGame(int amtToWin, int columns, int rows);
}
