package com.peti.amoba;

public class GameState implements java.io.Serializable {
	Table table;
	Players players;
	int currentPlayerId;
	
	public GameState(Table table, Players players, int currentPlayerId) {
		this.table = table;
		this.players = players;
		this.currentPlayerId = currentPlayerId;
	}
	
}
