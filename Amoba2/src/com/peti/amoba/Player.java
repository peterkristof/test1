package com.peti.amoba;


public class Player  implements java.io.Serializable {
	private final int id;
	private final String name;
	private final String avatarCode;
	
	public Player(int id, String name, String avatarCode) {
		this.id = id;
		this.name = name;
		this.avatarCode = avatarCode;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	//Avatar code most pont megegyezik a kirajzolt karakterrel.
	public String getAvatarCode() {
		return avatarCode;
	}
}

