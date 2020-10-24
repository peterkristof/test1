package com.peti.amoba;

import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigTest {

	static Config config;
	
	@BeforeClass
	public static void init() throws Exception {
		config = new Config("resources/tictactoe.properties");
	}
	
	@Test
	public void test1() {
		config.checkConfigIntegrity();
	}
	
	
}
