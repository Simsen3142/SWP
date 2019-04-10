package controller;

import java.util.ArrayList;
import java.util.List;

import models.Spieler;

public class UserActionHandler {
	
	private static UserActionHandler instance;
	public List<UserActionListener> userActionListeners=new ArrayList<>();
	
	
	/**
	 * @return the instance
	 */
	public static UserActionHandler getInstance() {
		if(instance==null)
			instance=new UserActionHandler();
		return instance;
	}

	private UserActionHandler() {}
	
	public void addUserActionListener(UserActionListener listener) {
		userActionListeners.add(listener);
	}
	
	public void removeUserActionListener(UserActionListener listener) {
		userActionListeners.remove(listener);
	}
	
	public void triggerResetClicked(Spieler user) {
		for(UserActionListener al:userActionListeners) {
			al.onResetClicked(user);
		}
	}
	
	public void triggerOnBackClicked(Spieler user) {
		for(UserActionListener al:userActionListeners) {
			al.onBackClicked(user);
		}
	}
	
	public void triggerOnChipPlacementDone(Spieler user) {
		for(UserActionListener al:userActionListeners) {
			al.onChipPlacementDone(user);
		}
	}
	
	public void triggerOnColumnClicked(Spieler user,int column) {
		for(UserActionListener al:userActionListeners) {
			al.onColumnClicked(user, column);
		}
	}
	
	public void triggerOnColumnSelected(Spieler user,int column) {
		for(UserActionListener al:userActionListeners) {
			al.onColumnSelected(user, column);
		}
	}
	
	public void triggerOnColorChanged(Spieler user,int[] newColor) {
		for(UserActionListener al:userActionListeners) {
			al.onColorChanged(user, newColor);
		}
	}
	
	public void triggerOnNameChanged(Spieler user, String oldName, String newName) {
		for(UserActionListener al:userActionListeners) {
			al.onNameChanged(user, oldName, newName);
		}
	}
}
