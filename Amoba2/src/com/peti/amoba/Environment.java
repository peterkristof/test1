package com.peti.amoba;

import java.io.Console;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;


/**
 * Összefogja a program teljes konfigurációját és környezeti objektumokat.
 * (Ezek az információk elférnének a Main osztályban is, de talán itt szebb.)
 * 
 * @author x
 *
 */
public class Environment {
	
	//System.out "mintájára" final, publikus, röviden elérhetõ.
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
				//list.toString, production kornyezetbe természetesen nem való
				//most remélem elmegy.
				out.println("Ismeretlen válasz. Lehetséges válaszok: " + list);
			}
		}
		
		return ret;
	}
	
	
}


