package com.peti.amoba.util;

import java.util.Arrays;


public class PlayersUtil {

	private int currentPlayerIndexInPlayerIdsPlayList;
	
	//Ez tartalmazza, hogy milyen sorrendben jöjjenek a játékosok egymás után.
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

