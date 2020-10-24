package com.peti.amoba.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	
	public static boolean isNumeric(String num) {
		String regex = "\\d+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(num);
		return matcher.matches();
	}

	
}
