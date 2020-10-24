package com.peti.amoba.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.peti.amoba.GameState;

public class PersistentUtil {
	
	//
	//nem long term perzisztencia, de remélem most ez megfelel.
	//másik egyszerû opció a bean-el XMLEncoder, XMLDecoder, ...
	//
	public static void saveGameState(File f, GameState gameState) throws Exception {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f)) ){
			oos.writeObject(gameState);
		}
	}

	public static GameState loadGameState(File f) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f) )){
			return (GameState) ois.readObject();
		}
	}
	
}

