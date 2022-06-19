package com.longboard.game.durak.card;

public enum CardRank {

	Six("6", 1), Seven("7", 2), Eight("8", 3), Nine("9", 4), Ten("10", 5), Jack("J", 6), Queen("Q", 7), King("K", 8), Ace("A", 9);

	String symbol;
	Integer value;

	CardRank(String symbol, Integer rank) {
		this.symbol = symbol;
		this.value = rank;
	}

	public String getSymbol() {
		return symbol;
	}

	public Integer getValue() {
		return value;
	}
}
