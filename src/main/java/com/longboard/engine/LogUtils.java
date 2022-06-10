package com.longboard.engine;

public class LogUtils {

	public static void error(String log) {
		System.out.println("!!!\n!!!\t" + log + "\n!!!");
	}

	public static void info(String log) {
		System.out.println("> " + log);
	}

}
