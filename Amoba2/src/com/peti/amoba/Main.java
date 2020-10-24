package com.peti.amoba;

public class Main {

	public static void main(String[] args) {
		try {
			Main m = new Main();
			m.startMe();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hiba t�rt�nt, k�rem jelezze a rendszergazd�nak.");
			//
			// Most ez szimul�lja a programnak a legfels� r�sz�t, ahol m�g tudok �zenni,
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

