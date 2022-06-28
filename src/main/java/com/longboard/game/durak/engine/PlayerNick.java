package com.longboard.game.durak.engine;

import java.util.List;
import java.util.Random;

public enum PlayerNick {

	Duck, Bird, Cat, Mouse, Slime, Cockroach;

	private static final List<PlayerNick> VALUES = List.of(values());
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static PlayerNick randomNick()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

}
