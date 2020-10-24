package com.peti.amoba;

import java.io.File;

import com.peti.amoba.ui.table.ConsoleTableRenderer;
import com.peti.amoba.ui.table.TableRenderer;
import com.peti.amoba.util.PersistentUtil;
import com.peti.amoba.util.PlayersUtil;
import com.peti.amoba.util.StringUtil;

public class GameActivity {

	private Environment env;
	private TableRenderer tableRenderer;
	private Table table;
	private Players players;
	private Player currentPlayer;

	private PlayersUtil playersUtil;

	public GameActivity(Environment env, Table table, Players players, int currentPlayerId) {
		this.env = env;
		this.table = table;
		this.players = players;
		this.tableRenderer = new ConsoleTableRenderer(table, players, env.out);
		playersUtil = new PlayersUtil(players.getPlayerIDs(), currentPlayerId);
		currentPlayer = null;
	}

	public void activate() {
		boolean done = false;

		while (!done) {
			tableRenderer.printTable();
			env.out.println("");
			
			int playerId = playersUtil.getCurrentPlayerIdInPlayList();
			currentPlayer = players.getPlayer(playerId);
			
			// Ide lehetne user nevet is írni, de most ez igy jobb
			env.out.println(currentPlayer.getAvatarCode() + " lépése (sor,oszlop): ");

			String cmd = env.readLine();

			if (cmd.startsWith("ment")) {
				String[] values = cmd.split(" ");
				if (values.length == 2) {
					String fileName = values[1];
					saveGameState(fileName);
				} else {
					env.out.println("Helytelen formátum. Helyes formátumok: ment <név>");
				}
				
			} else if ("exit".equals(cmd)) {
				done = true;
			} else {
				done = play(cmd);
			}
		}
	}

	private boolean play(String cmd) {
		boolean done = false;

		try {
			int[] values = parseAndCheckCoordinates(cmd);
			int x = values[0];
			int y = values[1];
			if (x >= 0 && x < table.getWidth()) {
				if (y >= 0 && y < table.getHeight()) {
					int mark = table.getMark(x, y);
					if (mark == 0) {
						table.setMark(currentPlayer.getId(), x, y);

						if (!table.isWin(x, y)) {
							playersUtil.nextPlayer();
						} else {
							tableRenderer.printTable();
							env.out.println("");
							env.out.println(currentPlayer.getAvatarCode() + " megnyerte a játékot. !!!!");
							done = true;
						}
					} else {
						env.out.println("A cellában már van jelölés. Kérlek válaszz egy másikat, ahol még nincs.");
					}
				} else {
					env.out.println("Sor értéke helytelen. Az értéknek " + tableRenderer.getRowNameByMatrixy(0) + " és "
							+ tableRenderer.getRowNameByMatrixy(table.getHeight() - 1) + " között kell lennie.");
				}
			} else {
				env.out.println("Oszlop értéke helytelen. Az értéknek " + tableRenderer.getColumnNameByMatrixX(0)
						+ " és " + tableRenderer.getColumnNameByMatrixX(table.getWidth() - 1) + " között kell lennie.");
			}
		} catch (NumberFormatException e) {
			env.out.println("Helytelen formátum. Helyes formátum pl.:1,2");
		}

		return done;
	}

	private void saveGameState(String fileName) {
		boolean doWrite = false;
		boolean done = false;
		File f = null;
		boolean needFileNameInput = false;
		
		while (!done) {
			//A legelsõ alkalommal nem kérek fileNevet, mert már van.
			if (needFileNameInput) {
				env.out.println("Adj meg file nevet. (Üres filenév, ha mégse.)");
				fileName = env.readLine();
				if (fileName.length() == 0) {
					break;
				}
			}
			needFileNameInput = true;
			
			//Ha nem tartalmazhat kiterjesztést, akkor pontot sem.
			if (fileName.indexOf(".") >= 0) {
				env.out.println("A filenév ne tartalmazzon kiterjesztést! (pontot se)");
				continue;
			}
			
			f = new File(env.config.savedFolder, fileName + env.config.savedFileExt);
			if (f.exists()) {
				env.out.println("File már létezik. Felülírjuk? igen / nem");
				
				String resp = env.readLine("igen", "nem", "");
				if ("igen".equals(resp)) {
					doWrite = true;
				} 
				else if ("nem".equals(resp)) {
					continue;
				} else {
					break;
				}
			} else {
				doWrite = true;
			}
			
			//Ekkora már átment minden ellenõrzésen
			done = true;
		}
		
		if (doWrite) {
			try {
				GameState gameState = new GameState(table, players, currentPlayer.getId());
				PersistentUtil.saveGameState(f, gameState);
			} catch (Exception e) {
				e.printStackTrace();
				env.out.println("Hiba történt. Nem sikerult menteni a játékot.");
			}
		}
	}

	private int[] parseAndCheckCoordinates(String cmd) {
		int[] ret;

		// formátum: <sor>,<oszlop>
		// Pl. 1,2
		String[] values = cmd.split(",");
		if (values.length == 2) {
			// Ekkor ok.
			String sor = values[0].trim();
			String oszlop = values[1].trim();
			if (StringUtil.isNumeric(sor) && StringUtil.isNumeric(oszlop)) {
				int x = tableRenderer.getMatrixXByColumnName(oszlop);
				int y = tableRenderer.getMatrixYByRowName(sor);

				ret = new int[] { x, y };
			} else {
				throw new NumberFormatException("bad values");
			}
		} else {
			throw new NumberFormatException("no values");
		}

		return ret;
	}
}
