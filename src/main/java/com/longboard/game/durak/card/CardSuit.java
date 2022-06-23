package com.longboard.game.durak.card;

public enum CardSuit {
//	Club("♣️", 1), Diamond("♦️", 2), Heart("♥️", 3), Spade("♠️", 4);
	Club("♣", 1), Diamond("♦", 2), Heart("♥", 3), Spade("♠", 4);

	String symbol;
	Integer value;

	CardSuit(String symbol, Integer value) {
		this.symbol = symbol;
		this.value = value;
	}

	public String getSymbol() {
		return symbol;
	}

	public Integer getValue() {
		return value;
	}
}
