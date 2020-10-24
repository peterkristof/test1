package com.peti.amoba.util;

import java.util.Arrays;


public class PlayersUtil {

	private int currentPlayerIndexInPlayerIdsPlayList;
	
	//Ez tartalmazza, hogy milyen sorrendben j�jjenek a j�t�kosok egym�s ut�n.
	private int[] playerIdsPlayList;

	public PlayersUtil(int[] playerIDs, int currentPlayerId) {
		this.playerIdsPlayList = playerIDs;
		int index = Arrays.binarySearch(playerIdsPlayList, currentPlayerId);
		this.currentPlayerIndexInPlayerIdsPlayList = index;
	}

	public int getCurrentPlayerIdInPlayList() {
		return playerIdsPlayList[currentPlayerIndexInPlayerIdsPlayList];
	}

	public void nextPlayer() {
		currentPlayerIndexInPlayerIdsPlayList++;
		if (currentPlayerIndexInPlayerIdsPlayList >= playerIdsPlayList.length) {
			currentPlayerIndexInPlayerIdsPlayList = 0;
		}
	}
	
}

