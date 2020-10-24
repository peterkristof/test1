package com.peti.amoba;

public class Main {

	public static void main(String[] args) {
		try {
			Main m = new Main();
			m.startMe();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hiba történt, kérem jelezze a rendszergazdának.");
			//
			// Most ez szimulálja a programnak a legfelsõ részét, ahol még tudok üzenni,
			// loggolni
			//
		}
	}

	private void startMe() throws Exception {
		
		Config config = new Config("resources/tictactoe.properties");
		
		Environment env = new Environment(System.console(), config);
		
		MainActivity mainActivity = new MainActivity(env);
		mainActivity.activate();
		
		env.out.println("\r\nBye.");
	}

}

