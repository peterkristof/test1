package com.peti.amoba.ui.table;

import java.io.PrintWriter;

import com.peti.amoba.Player;
import com.peti.amoba.Players;
import com.peti.amoba.Table;

public class ConsoleTableRenderer extends TableRenderer {

	private Table table;
	private PrintWriter out;
	private Players players;

	public ConsoleTableRenderer(Table table, Players players, PrintWriter out) {
		this.table = table;
		this.players = players;
		this.out = out;
	}

	public void printTable() {
		StringBuilder ret = new StringBuilder();

		addHeaderContent(ret);
		ret.append("\r\n");

		for (int row = 0; row < table.getHeight(); row++) {
			addRowContent(ret, row);
			ret.append("\r\n");
		}

		out.print(ret.toString());
	}

	private void addHeaderContent(StringBuilder ret) {
		// Fejlec
		ret.append("   ");

		// fejlec kiirasa
		// x, vizszintes irány
		for (int i = 0; i < table.getWidth(); i++) {
			ret.append('\u2502');
			ret.append(getColumnNameByMatrixX(i));
			ret.append(".");
		}
	}

	private void addRowContent(StringBuilder ret, int row) {

		// TODO codepage-re szabni
		ret.append('\u2500');
		ret.append('\u2500');
		ret.append('\u2500');
		ret.append('\u253C');

		for (int i = 0; i < table.getWidth(); i++) {
			ret.append('\u2500');
			ret.append('\u2500');
			ret.append('\u2500');
			ret.append('\u253C');
		}

		ret.append("\r\n");

		ret.append(getRowNameByMatrixy(row));
		ret.append(".");

		for (int i = 0; i < table.getWidth(); i++) {
			// TODO codepage-re szabni
			ret.append('\u2502');
			ret.append(" ");

			String mark;
			int playerId = table.getMark(i, row);
			if (playerId != 0) {
				Player player = players.getPlayer(playerId);
				mark = player.getAvatarCode();
			} else {
				mark = " ";
			}

			ret.append(mark);
			ret.append(" ");
		}
	}

}
