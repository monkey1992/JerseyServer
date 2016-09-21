package com.jerseyserver.util;

public class TextUtil {
	public static boolean isEmpty(String content) {
		if (content == null || "".equals(content)) {
			return true;
		}
		return false;
	}
}
