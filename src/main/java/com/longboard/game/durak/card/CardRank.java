package com.longboard.game.durak.card;

public enum CardRank {

	Six("6", 1), Seven("7", 2), Eight("8", 3), Nine("9", 4), Ten("10", 5), Jack("J", 6), Queen("Q", 7), King("K", 8), Ace("A", 9);

	String symbol;
	Integer rank;

	CardRank(String symbol, Integer rank) {
		this.symbol = symbol;
		this.rank = rank;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
}
