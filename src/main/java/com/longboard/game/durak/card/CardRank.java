package com.longboard.game.durak.card;

public enum CardRank {

	Six("6"), Seven("7"), Eight("8"), Nine("9"), Ten("10"), Jack("J"), Queen("Q"), King("K"), Ace("A");

	String symbol;

	CardRank(String symbol) {
		this.symbol = symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
