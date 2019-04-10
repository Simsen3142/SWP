package controller;

import models.Spieler;

public interface ZugListener {
	public void onZugRedone(Spieler sp, int[] coords);
	public void onZugUndone(Spieler sp, int[] coords);
}
