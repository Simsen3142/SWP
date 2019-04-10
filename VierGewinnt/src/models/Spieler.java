package models;

import java.awt.Color;
import java.util.ArrayList;

public class Spieler {
	
	private String name;
	private boolean amZug=false;
	private static ArrayList<Spieler> spieler=new ArrayList<Spieler>();
	
	private Color color;
	private int nr;
	private static int unusednr=1;
	
	public Color getColor() {
		return color;
	}
	
	public int getNumber() {
		return nr;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	
	public Spieler(String name, Color c) {
		this.name=name;
		this.color=c;
		
		this.nr=unusednr;
		unusednr++;
		
		spieler.add(this);
	}
	
	public static Spieler getSpielerAmZug() {
		for(Spieler sp:spieler) {
			if(sp.isAmZug()) {
				return sp;
			}
		}
		return null;
	}
	
	public static ArrayList<Spieler> getSpieler(){
		return spieler;
	}
	

	public Spieler getNextSpieler() {
		boolean found=false;
		for(Spieler sp:Spieler.spieler) {
			if(sp==this) {
				found=true;
				continue;
			}
			if(found)
				return sp;
		}
		return Spieler.spieler.get(0);
	}
	
	public boolean isAmZug() {
		return amZug;
	}
	
	public static Spieler getSpielerByNr(int nr) {
		for(Spieler sp:spieler) {
			if(sp.nr==nr) {
				return sp;
			}
		}
		return null;
	}
	
	public void setAmZug(boolean amZug) {
		this.amZug=amZug;
		
		if(amZug) {
			for(Spieler sp:spieler) {
				if(sp==this)
					continue;
				sp.amZug=false;
			}
		}
	}
}
