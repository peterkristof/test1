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
			
			// Ide lehetne user nevet is �rni, de most ez igy jobb
			env.out.println(currentPlayer.getAvatarCode() + " l�p�se (sor,oszlop): ");

			String cmd = env.readLine();

			if (cmd.startsWith("ment")) {
				String[] values = cmd.split(" ");
				if (values.length == 2) {
					String fileName = values[1];
					saveGameState(fileName);
				} else {
					env.out.println("Helytelen form�tum. Helyes form�tumok: ment <n�v>");
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
							env.out.println(currentPlayer.getAvatarCode() + " megnyerte a j�t�kot. !!!!");
							done = true;
						}
					} else {
						env.out.println("A cell�ban m�r van jel�l�s. K�rlek v�laszz egy m�sikat, ahol m�g nincs.");
					}
				} else {
					env.out.println("Sor �rt�ke helytelen. Az �rt�knek " + tableRenderer.getRowNameByMatrixy(0) + " �s "
							+ tableRenderer.getRowNameByMatrixy(table.getHeight() - 1) + " k�z�tt kell lennie.");
				}
			} else {
				env.out.println("Oszlop �rt�ke helytelen. Az �rt�knek " + tableRenderer.getColumnNameByMatrixX(0)
						+ " �s " + tableRenderer.getColumnNameByMatrixX(table.getWidth() - 1) + " k�z�tt kell lennie.");
			}
		} catch (NumberFormatException e) {
			env.out.println("Helytelen form�tum. Helyes form�tum pl.:1,2");
		}

		return done;
	}

	private void saveGameState(String fileName) {
		boolean doWrite = false;
		boolean done = false;
		File f = null;
		boolean needFileNameInput = false;
		
		while (!done) {
			//A legels� alkalommal nem k�rek fileNevet, mert m�r van.
			if (needFileNameInput) {
				env.out.println("Adj meg file nevet. (�res filen�v, ha m�gse.)");
				fileName = env.readLine();
				if (fileName.length() == 0) {
					break;
				}
			}
			needFileNameInput = true;
			
			//Ha nem tartalmazhat kiterjeszt�st, akkor pontot sem.
			if (fileName.indexOf(".") >= 0) {
				env.out.println("A filen�v ne tartalmazzon kiterjeszt�st! (pontot se)");
				continue;
			}
			
			f = new File(env.config.savedFolder, fileName + env.config.savedFileExt);
			if (f.exists()) {
				env.out.println("File m�r l�tezik. Fel�l�rjuk? igen / nem");
				
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
			
			//Ekkora m�r �tment minden ellen�rz�sen
			done = true;
		}
		
		if (doWrite) {
			try {
				GameState gameState = new GameState(table, players, currentPlayer.getId());
				PersistentUtil.saveGameState(f, gameState);
			} catch (Exception e) {
				e.printStackTrace();
				env.out.println("Hiba t�rt�nt. Nem sikerult menteni a j�t�kot.");
			}
		}
	}

	private int[] parseAndCheckCoordinates(String cmd) {
		int[] ret;

		// form�tum: <sor>,<oszlop>
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
