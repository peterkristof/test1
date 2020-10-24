package com.peti.amoba;

import java.io.FileInputStream;
import java.util.Properties;

enum PropertyKey {
	tableWidth, tableHeight, numOfMarkToWin;
}


public class Config {
	
	public final String savedFolder = "saves";
	public final String savedFileExt = ".sav";
	
	//V�dve van, csak getProperty �rhet� el.
	private Properties properties;

	public Config(String fileName) throws Exception {
		properties = new Properties();
		try (FileInputStream fis = new FileInputStream(fileName) ) {
			properties.load(fis);
		}
		
		checkConfigIntegrity();
	}
	
	void checkConfigIntegrity() {
		
		//Kulcsok megl�t�nek ellen�rz�se a konfigur�ci�ban.
		PropertyKey[] values = PropertyKey.values();
		for (PropertyKey key : values) {
			getProperty(key);
		}
		
		int tableWidth = getPropertyAsNum(PropertyKey.tableWidth);
		if (tableWidth < 5 || tableWidth >= 100 ) {
			throw new RuntimeException("Nem megfelel� property �rt�k: " + PropertyKey.tableWidth.name());
		}
		
		int tableHeight = getPropertyAsNum(PropertyKey.tableHeight);
		if (tableHeight < 5 || tableHeight >= 100 ) {
			throw new RuntimeException("Nem megfelel� property �rt�k: " + PropertyKey.tableHeight.name());
		}
	}
	
	public String getProperty(PropertyKey entry) {
		String value = properties.getProperty(entry.name());
		if (value == null) {
			throw new RuntimeException("Configuration entry is missing from property file! Key: " + entry.name());
		}
		
		value = value.trim();
		
		if (value.length() == 0) {
			throw new RuntimeException("Configuration entry is empty! Key: " + entry.name());
		}
		
		return value;
	}
	
	public int getPropertyAsNum(PropertyKey entry) {
		String v = getProperty(entry);
		return Integer.valueOf(v);
	}
	
}

