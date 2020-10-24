package com.peti.amoba;


public class Table implements java.io.Serializable {
	//[sor][oszlop]
	//[y][x]
	int[][] matrix;
	
	//Ezt itt kell tárolni, mert ez a táblához tartozik. 
	//Tábla mentése
	//Config.numOfMarkToWin módosítása
	//Tábla betöltése esetén az eredeti állapot tovább játszható legyen!!!
	int numOfMarksToWin;
	
	public Table() {
	}
	
	public Table(int tableWidth, int tableHeight, int numOfMarksToWin) {
		matrix = new int[tableHeight][tableWidth];
		this.numOfMarksToWin = numOfMarksToWin;
	}

	public int getWidth() {
		return matrix[0].length;
	}
	
	public int getHeight() {
		return matrix.length;
	}
	
	public void setMark(int playerId, int x, int y) {
		matrix[y][x] = playerId;
	}
	
	public int getMark(int x, int y) {
		return matrix[y][x];
	}

	public boolean isWin(int x, int y) {
		int markOwner = matrix[y][x];
		if (markOwner == 0) {
			throw new RuntimeException("Unexpected Error");
		}
		
		int num = 1;
		num += getNumOfSameMarks(markOwner, x, y, 1, 0, numOfMarksToWin);
		num += getNumOfSameMarks(markOwner, x, y, -1, 0, numOfMarksToWin);
		
		if (num >= numOfMarksToWin) {
			return true;
		}

		num = 1;
		num += getNumOfSameMarks(markOwner, x, y, 0, 1, numOfMarksToWin);
		num += getNumOfSameMarks(markOwner, x, y, 0, -1, numOfMarksToWin);
		
		if (num >= numOfMarksToWin) {
			return true;
		}

		num = 1;
		num += getNumOfSameMarks(markOwner, x, y, 1, 1, numOfMarksToWin);
		num += getNumOfSameMarks(markOwner, x, y, -1, -1, numOfMarksToWin);
		
		if (num >= numOfMarksToWin) {
			return true;
		}

		num = 1;
		num += getNumOfSameMarks(markOwner, x, y, 1, -1, numOfMarksToWin);
		num += getNumOfSameMarks(markOwner, x, y, -1, 1, numOfMarksToWin);
		
		if (num >= numOfMarksToWin) {
			return true;
		}

		return false;
	}
	
	
	/**
	 * Visszaadja az x,y pozíción kívüli markOwner playerId-vel rendelkezõ egymás utáni markok számát.
	 * @param markOwner
	 * @param x
	 * @param y
	 * @param stepX
	 * @param stepY
	 * @param maxSteps
	 * @return
	 */
	private int getNumOfSameMarks(int markOwner, int x, int y, int stepX, int stepY, int maxSteps) {
		int counter = 0;
		
		boolean done = false;
		for (int i=0;i < maxSteps && !done;i++) {
			x += stepX;
			y += stepY;
			
			if (x >= 0 && x < matrix[0].length) {
				if (y >= 0 && y < matrix.length) {
					if (matrix[y][x] == markOwner) {
						counter++;
						continue;
					}		
				}
			}
			
			done = true;
		}
		
		return counter;
	}
	
}


