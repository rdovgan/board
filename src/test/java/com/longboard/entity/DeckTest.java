package com.longboard.entity;

import com.longboard.entity.card.IsCard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeckTest implements IsDeck {

	private final UUID id = UUID.randomUUID();

	private List<IsCard> cards = new ArrayList<>();

	@Override
	public List<IsCard> getCards() {
		return cards;
	}

	@Override
	public UUID getId() {
		return id;
	}
}
