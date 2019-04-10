package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ZugListener;

public class Zug implements Comparable<Zug>{
	private static final Map<Integer,Zug> ZUEGE=new HashMap<>();
	private static int nextIndex=0;
	private int index;
	private Spieler spieler;
	private int[] coordinates;
	private long time;
	private boolean reversed;
	private static List<ZugListener> listeners=new ArrayList<>();
	private static List<Zug> undoneZuege=new ArrayList<>();
	
	public static  void addZugListener(ZugListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public static void removeZugListener(ZugListener listener) {
		listeners.remove(listener);
	}
	
	private static void onZugRedone(Zug zug) {
		listeners.forEach((listener)->{
			listener.onZugRedone(zug.spieler, zug.coordinates);
		});
	}
	
	private static void onZugUndone(Zug zug) {
		undoneZuege.add(zug);
		listeners.forEach((listener)->{
			listener.onZugUndone(zug.spieler, zug.coordinates);
		});
	}
	
	public Zug(Spieler spieler, int[] coordinates) {
		index=nextIndex++;
		this.spieler=spieler;
		this.coordinates=coordinates;
		this.time=System.currentTimeMillis();
		reversed=false;
		ZUEGE.put(this.index,this);
		undoneZuege.clear();
	}
	
	public static void undo() {
		Zug zug=ZUEGE.get(Zug.getLastIndex());
		if(zug!=null)
			zug.setReversed(true);
	}
	
	public static void redo() {
		if(undoneZuege.size()>0) {
			int index=undoneZuege.size()-1;
			undoneZuege.get(index).setReversed(false);
			undoneZuege.remove(index);
		}
	}
	
	public static Zug getZug(int index) {
		return ZUEGE.get(index);
	}
	
	
	public static int getLastIndex() {
		for(int i=ZUEGE.size()-1;i>=0;i--) {
			if(!ZUEGE.get(i).reversed)
				return ZUEGE.get(i).index;
		}
		return -1;
	}
	
	public static void reset() {
		nextIndex=0;
		ZUEGE.clear();
	}
	
	/**
	 * @return if reversed
	 */
	public boolean isReversed() {
		return reversed;
	}
	
	public void setReversed(boolean reversed) {
		if(this.reversed!=reversed) {
			this.reversed=reversed;
			if(reversed) {
				onZugUndone(this);
			}else {
				onZugRedone(this);
			}
		}
	}
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * @return the spieler
	 */
	public Spieler getSpieler() {
		return spieler;
	}

	/**
	 * @return the coordinates
	 */
	public int[] getCoordinates() {
		return coordinates;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	@Override
	public int compareTo(Zug zugO) {
		return new Long(this.time).compareTo(zugO.time);
	}
}
