package com.peti.amoba;

import java.io.File;

import com.peti.amoba.util.PersistentUtil;

public class MainActivity {

	private Environment env;

	public MainActivity(Environment env) {
		this.env = env;
	}

	public void activate() {
		printWelcomeMessage();

		boolean gameDone = false;
		while (!gameDone) {
			printMenu();

			// User input beolvasása
			String cmd = env.readLine();

			switch (cmd) {
			case "1":
				playNewGame();
				break;

			case "2":
				loadAndPlayGame();
				break;

			case "3":
				gameDone = true;
				break;

			default:
				env.out.println("Ismeretlen parancs: " + cmd);
				break;
			}
		}
	}

	private void playNewGame() {
		// Uj jatek eseteben default table és játékosok létrehozása
		int tableWidth = env.config.getPropertyAsNum(PropertyKey.tableWidth);
		int tableHeight = env.config.getPropertyAsNum(PropertyKey.tableHeight);
		int numOfMarksToWin = env.config.getPropertyAsNum(PropertyKey.numOfMarkToWin);
		
		Table table = new Table(tableWidth, tableHeight, numOfMarksToWin);
		
		// TODO megbeszelni, nem kezdheti mindig a jatekot az X, mert mentett és
		// visszatoltott jateknal lehet az O jon
		// Ha az O jonne és megis X kezdene, akkor X érvénytelen elõnyhöz jutna.
		// Vagy csak akkor lehetne menteni, ha pont X jönne.
		// Ügyféllel egyeztetném.

		Player player1 = new Player(1, "Játékos 1", "X");
		Player player2 = new Player(2, "Játékos 2", "O");
//		Player player3 = new Player(3, "Játékos 3", "*");

		Players players = new Players();
		players.put(player1);
		players.put(player2);
//		players.put(player3);

		// Az a játékos id-je, akinek most kell lépnie.
		int currentPlayerId = player1.getId();

		GameActivity game = new GameActivity(env, table, players, currentPlayerId);
		game.activate();
	}

	private void loadAndPlayGame() {

		boolean done = false;

		while (!done) {
			env.out.println("Add meg a file nevét kiterjesztés nélkül. (Üres filenév -> visszalépés a menübe.) ");
			String fileName = env.readLine();
			if (fileName.length() > 0) {
				File f = new File(env.config.savedFolder, fileName + env.config.savedFileExt);
				if (f.exists()) {
					try {
						done = true;
						GameState gameState = PersistentUtil.loadGameState(f);
						GameActivity game = new GameActivity(env, gameState.table, gameState.players, gameState.currentPlayerId);
						game.activate();
					} catch (Exception e) {
						env.out.println("Nem sikerült betölteni a játékot.");
						// Ezt valójában loggolni kellene, nem a képernyõre kiírni
						e.printStackTrace();
					}
				} else {
					env.out.println("File nem található!");
				}
			} else {
				done = true;
			}
		}
	}


	private void printWelcomeMessage() {
		// Main képernyõ kiíratása
		//TODO ResourceBundle
		env.out.println("\r\nLégy üdvözölve eme Amõba játékban.");
		env.out.println("\r\nVálassz egy menüpontot. Pl: 1.");
		env.out.println("\r\n");
	}

	private void printMenu() {
		// Menu kiíratása
		//TODO ResourceBundle
		env.out.println("");
		env.out.println("1. Új játék indítása");
		env.out.println("2. Játék betöltése");
		env.out.println("3. Kilépés");
	}

}
