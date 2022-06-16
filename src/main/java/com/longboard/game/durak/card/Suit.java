package com.longboard.game.durak.card;

public enum Suit {
	Clubs("♣️"), Diamonds("♦️"), Hearts("♥️"), Spades("♠️");

	String symbol;

	Suit(String symbol) {
		this.symbol = symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
