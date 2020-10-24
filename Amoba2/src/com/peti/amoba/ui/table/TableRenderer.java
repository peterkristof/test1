package com.peti.amoba.ui.table;


public abstract class TableRenderer {
	
	public abstract void printTable();
	
	public String getColumnNameByMatrixX(int x) {
		String ret = String.format("%2d", (x+1) );
		return ret;
	}
	
	public int getMatrixXByColumnName(String columnName) {
		int x = Integer.parseInt(columnName.trim());
		x -= 1;
		return x;
	}
	
	
	public String getRowNameByMatrixy(int row) {
		//Talán ennyi boilerplate code belefér...lehet változik majd
		String ret = String.format("%2d", (row+1) );
		return ret;
	}

	public int getMatrixYByRowName(String rowName) {
		int y = Integer.parseInt(rowName.trim());
		y -= 1;
		return y;
	}
}

