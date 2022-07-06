package com.longboard.entity;

import com.longboard.entity.card.IsCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DeckTest implements IsDeck<CardTest> {

	private final UUID id = UUID.randomUUID();

	private List<CardTest> cards = new ArrayList<>();

	public DeckTest(List<CardTest> cards) {
		this.cards = cards;
	}

	public DeckTest() {
	}

	@Override
	public List<CardTest> getCards() {
		return cards;
	}

	@Override
	public UUID getId() {
		return id;
	}
}
