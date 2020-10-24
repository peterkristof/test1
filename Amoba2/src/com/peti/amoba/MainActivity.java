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

			// User input beolvas�sa
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
		// Uj jatek eseteben default table �s j�t�kosok l�trehoz�sa
		int tableWidth = env.config.getPropertyAsNum(PropertyKey.tableWidth);
		int tableHeight = env.config.getPropertyAsNum(PropertyKey.tableHeight);
		int numOfMarksToWin = env.config.getPropertyAsNum(PropertyKey.numOfMarkToWin);
		
		Table table = new Table(tableWidth, tableHeight, numOfMarksToWin);
		
		// TODO megbeszelni, nem kezdheti mindig a jatekot az X, mert mentett �s
		// visszatoltott jateknal lehet az O jon
		// Ha az O jonne �s megis X kezdene, akkor X �rv�nytelen el�nyh�z jutna.
		// Vagy csak akkor lehetne menteni, ha pont X j�nne.
		// �gyf�llel egyeztetn�m.

		Player player1 = new Player(1, "J�t�kos 1", "X");
		Player player2 = new Player(2, "J�t�kos 2", "O");
//		Player player3 = new Player(3, "J�t�kos 3", "*");

		Players players = new Players();
		players.put(player1);
		players.put(player2);
//		players.put(player3);

		// Az a j�t�kos id-je, akinek most kell l�pnie.
		int currentPlayerId = player1.getId();

		GameActivity game = new GameActivity(env, table, players, currentPlayerId);
		game.activate();
	}

	private void loadAndPlayGame() {

		boolean done = false;

		while (!done) {
			env.out.println("Add meg a file nev�t kiterjeszt�s n�lk�l. (�res filen�v -> visszal�p�s a men�be.) ");
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
						env.out.println("Nem siker�lt bet�lteni a j�t�kot.");
						// Ezt val�j�ban loggolni kellene, nem a k�perny�re ki�rni
						e.printStackTrace();
					}
				} else {
					env.out.println("File nem tal�lhat�!");
				}
			} else {
				done = true;
			}
		}
	}


	private void printWelcomeMessage() {
		// Main k�perny� ki�rat�sa
		//TODO ResourceBundle
		env.out.println("\r\nL�gy �dv�z�lve eme Am�ba j�t�kban.");
		env.out.println("\r\nV�lassz egy men�pontot. Pl: 1.");
		env.out.println("\r\n");
	}

	private void printMenu() {
		// Menu ki�rat�sa
		//TODO ResourceBundle
		env.out.println("");
		env.out.println("1. �j j�t�k ind�t�sa");
		env.out.println("2. J�t�k bet�lt�se");
		env.out.println("3. Kil�p�s");
	}

}
