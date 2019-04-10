package controller;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.server.handler.SpielServerHandler;
import models.Spiel;
import models.Spieler;
import models.Zug;
import view.MainActivity;
import view.MainFrame;
import view.activities.CustomAppCompatActivity;
import view.activities.newgame.NewGameActivity;

public class MainController {
	private static MainController instance=new MainController();
	private static Spieler sp1;
	private static Spieler sp2;
	private static Spiel spiel;

	/**
	 * @return the instance
	 */
	public static MainController getInstance() {
		return instance;
	}
	
	/**
	 * @return the spiel
	 */
	public static Spiel getSpiel() {
		return spiel;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		sp2=new Spieler("Spieler2", Color.RED);
		sp1=new Spieler("Spieler1", Color.ORANGE);
		
		
		MainActivity.initInstance(sp1, sp2);
		MainActivity activity=MainActivity.getInstance();
		activity.setVisible(true);
		
		sp1.setAmZug(true);
		spiel=new Spiel();
		
		SpielHandler.getInstance().addSpielListener(activity);		
		SpielHandler.getInstance().addSpielListener(spiel);	
		UserActionHandler.getInstance().addUserActionListener(activity);
		Zug.addZugListener(activity);
		
		activity.onCreate();

		MainFrame.getInstance().setVisible(true);
		MainFrame.getInstance().setActiveActivity(activity);	
		
	}
	
}
