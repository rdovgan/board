package com.longboard.entity;

import java.util.ArrayList;
import java.util.List;

public class DeckTest implements IsDeck {

	private List<IsCard> cards = new ArrayList<>();

	@Override
	public List<IsCard> getCards() {
		return cards;
	}

}
