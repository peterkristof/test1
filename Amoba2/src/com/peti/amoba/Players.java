package com.peti.amoba;

import java.util.LinkedHashMap;

public class Players implements java.io.Serializable {
	
	private LinkedHashMap<Integer,Player> playersMap;
	
	public Players() {
		playersMap = new LinkedHashMap<>();
	}
	
	public void put(Player player) {
		playersMap.put(player.getId(), player);
	}
	
	public Player getPlayer(int playerId) {
		return playersMap.get(playerId);
	}
	
	
	public int[] getPlayerIDs() {
		int[] ret = playersMap.keySet().stream().mapToInt(Integer::intValue).toArray();
		return ret;
	}
}

