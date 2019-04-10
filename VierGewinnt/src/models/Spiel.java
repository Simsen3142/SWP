package models;

import java.util.ArrayList;

import controller.SpielListener;

public class Spiel implements SpielListener {
	private int height=6;
	private int width=7;
	private int amtToWin=4;
	private int[][] spielfeld=new int[width][height];
	private ArrayList<int[]> wonCoordinates=new ArrayList<int[]>();
	
	/**
	 * @return the spielfeld
	 */
	public int[][] getSpielfeld() {
		return spielfeld;
	}

	/**
	 * @return the wonCoordinates
	 */
	public ArrayList<int[]> getWonCoordinates() {
		return wonCoordinates;
	}

	/**
	 * @param wonCoordinates the wonCoordinates to set
	 */
	public void setWonCoordinates(ArrayList<int[]> wonCoordinates) {
		this.wonCoordinates = wonCoordinates;
	}

	public int getAmtToWin() {
		return amtToWin;
	}
	
	public void initStartValues(int amtToWin, int width, int height) {
		this.amtToWin=amtToWin;
		this.height=height;
		this.width=width;
	}
	
	public Spiel() {
	}
	
	public void initPlayground() {
		for(int x=0;x<width;x++) {
			for(int y=0;y<height;y++) {
				spielfeld[x][y]=0;
			}
		}
	}
	
	public void addChip(int column, int nr) {
		int[] coords=getNextFreeCoordinates(column);
		
		if(nr<1)
			throw new RuntimeException("Nummer darf nicht kleiner als 0 sein: "+nr);
		if(column<0 || column >= this.width) {
			throw new RuntimeException("Spalte darf nicht negativ und nicht größer als "+this.width+" sein: "+column);
		}
		
		spielfeld[coords[0]][coords[1]]=nr;
	}
	
	public int[] getNextFreeCoordinates(int column) {
		for(int y=height-1;y>=0;y--) {
			if(spielfeld[column][y]==0) {
				return new int[] {column,y};
			}
		}
		return null;
	}
	

	public void removeChip(int column) {
		if(column<0 || column >= this.width)
			throw new RuntimeException("Spalte darf nicht negativ und nicht größer als "+this.width+" sein: "+column);

		for(int y=0;y<height;y++) {
			if(spielfeld[column][y]!=0) {
				spielfeld[column][y]=0;
				return;
			}
		}
	}
	
	
	private int checkHorizontal() {
		for(int y=0;y<height;y++) {
			int lastnr=0;
			int amtRight=0;
			for(int x=0;x<width;x++) {
				int i=spielfeld[x][y];
				if(i==0) {
					amtRight=0;
					wonCoordinates.clear();
				}else {
					if(i!=lastnr) {
						amtRight=1;
						wonCoordinates.clear();
						wonCoordinates.add(new int[] {x,y});
					}else {
						amtRight++;
						wonCoordinates.add(new int[] {x,y});
						if(amtRight>=amtToWin) {
							return lastnr;
						}
					}
				}
				lastnr=i;
			}
		}
		return 0;
	}
	
	private int checkVertical() {
		for(int x=0;x<width;x++) {
			int lastnr=0;
			int amtRight=0;
			for(int y=0;y<height;y++) {
				int i=spielfeld[x][y];
				if(i==0) {
					amtRight=0;
					wonCoordinates.clear();
				}else {
					if(i!=lastnr) {
						amtRight=1;
						wonCoordinates.clear();
						wonCoordinates.add(new int[] {x,y});
					}else {
						amtRight++;
						wonCoordinates.add(new int[] {x,y});
						if(amtRight>=amtToWin) {
							return lastnr;
						}
					}
				}
				lastnr=i;
			}
		}
		return 0;
	}
	// This: /
	private int checkDiagonal1() {
		for(int x=0;x<width+height;x++) {
			int lastnr=0;
			int amtRight=0;
			wonCoordinates.clear();

			for(int y=0;y<height;y++) {
				int xr=x-y;
				
				if(xr>width-1)
					continue;
				if(xr<0)
					break;
				
				int i=spielfeld[xr][y];
				if(i==0) {
					amtRight=0;
					wonCoordinates.clear();
				}else {
					if(i!=lastnr) {
						amtRight=1;
						wonCoordinates.clear();
						wonCoordinates.add(new int[] {xr,y});
					}else {
						amtRight++;
						wonCoordinates.add(new int[] {xr,y});
						if(amtRight>=amtToWin) {
							return lastnr;
						}
					}
				}
				lastnr=i;
			}
		}
		return 0;
	}
	
	private int checkDiagonal2() {
		for(int x=height+width-1;x>=0-height;x--) {
			int lastnr=0;
			int amtRight=0;
			wonCoordinates.clear();

			for(int y=0;y<height;y++) {
				int xr=x+y;
				

				if(xr<0)
					continue;
				if(xr>=width)
					break;
				
				int i=spielfeld[xr][y];
				if(i==0) {
					amtRight=0;
					wonCoordinates.clear();
				}else {
					if(i!=lastnr) {
						amtRight=1;
						wonCoordinates.clear();
						wonCoordinates.add(new int[] {xr,y});
					}else {
						amtRight++;
						wonCoordinates.add(new int[] {xr,y});
						if(amtRight>=amtToWin) {
							return lastnr;
						}
					}
				}
				lastnr=i;
			}
		}
		return 0;
	}
	
	public int checkWon() {
		int won=0;
		wonCoordinates.clear();
		if((won=checkHorizontal())!=0) {
			return won;
		}
		wonCoordinates.clear();
		if((won=checkVertical())!=0) {
			return won;
		}
		wonCoordinates.clear();
		if((won=checkDiagonal1())!=0) {
			return won;
		}
		wonCoordinates.clear();
		if((won=checkDiagonal2())!=0) {
			return won;
		}
		wonCoordinates.clear();

		return 0;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	@Override
	public void onChipPlaced(int column,int row, int user) {
		this.addChip(column, user);
		System.out.println(toString());
	}

	@Override
	public void onChipRemoved(int column, int row, int user) {
		this.removeChip(column);
	}

	@Override
	public void onWon(ArrayList<int[]> wonCoords, int user) {
	}

	@Override
	public void onNewGame(int amtToWin, int columns, int rows) {
		initStartValues(amtToWin, columns,rows);
		initPlayground();
	}
	
	@Override
	public String toString() {
		String s="";
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				s+=spielfeld[x][y]+" ";
			}
			s+="\n";
		}
		
		return s;
	}

}
