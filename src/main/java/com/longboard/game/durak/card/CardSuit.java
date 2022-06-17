package com.longboard.game.durak.card;

public enum CardSuit {
	Club("♣️"), Diamond("♦️"), Heart("♥️"), Spade("♠️");

	String symbol;

	CardSuit(String symbol) {
		this.symbol = symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
