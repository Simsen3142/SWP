package controller;

import models.Spieler;

public interface UserActionListener {
	public void onColumnClicked(Spieler user, int rownr);
	public void onColumnSelected(Spieler user, int rownr);
	public void onChipPlacementDone(Spieler user);
	public void onNameChanged(Spieler user, String oldName, String newName);
	public void onColorChanged(Spieler user, int[] newColor);
	public void onResetClicked(Spieler user);
	public void onBackClicked(Spieler user);
}