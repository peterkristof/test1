package com.peti.amoba;

import java.io.Console;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;


/**
 * �sszefogja a program teljes konfigur�ci�j�t �s k�rnyezeti objektumokat.
 * (Ezek az inform�ci�k elf�rn�nek a Main oszt�lyban is, de tal�n itt szebb.)
 * 
 * @author x
 *
 */
public class Environment {
	
	//System.out "mint�j�ra" final, publikus, r�viden el�rhet�.
	public final PrintWriter out;
	public final Reader reader;
	public final Config config;
	public final Console console;
	
	public Environment(Console console, Config config) {
		this.console = console;
		this.out = console.writer();
		this.reader = console.reader();
		this.config = config;
	}
	
	public String readLine() {
		return console.readLine();
	}

	public String readLine(String... possibleValues) {
		boolean done = false;
		String ret = "";
		List<String> list = Arrays.asList(possibleValues);
		
		while (!done) {
			String line = readLine();
			if (list.contains(line)) {
				ret = line;
				done = true;
			} else {
				//list.toString, production kornyezetbe term�szetesen nem val�
				//most rem�lem elmegy.
				out.println("Ismeretlen v�lasz. Lehets�ges v�laszok: " + list);
			}
		}
		
		return ret;
	}
	
	
}


