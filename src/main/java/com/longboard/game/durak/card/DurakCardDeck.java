package com.longboard.game.durak.card;

import com.longboard.entity.IsDeck;
import com.longboard.entity.card.IsCard;
import com.longboard.game.durak.engine.DurakCardDeckUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class DurakCardDeck implements IsDeck<PlayingCard36> {

	private final UUID id = UUID.randomUUID();
	private List<PlayingCard36> deck;
	private PlayingCard36 trump;
	private List<PlayingCard36> discard;

	private DurakCardDeck() {
	}

	public static DurakCardDeck initialise() {
		DurakCardDeck cardDeck = new DurakCardDeck();
		cardDeck.discard = new ArrayList<>();
		cardDeck.deck = new ArrayList<>(DurakCardDeckUtils.buildNewDeck());
		Collections.shuffle(cardDeck.deck, new Random(UUID.randomUUID().timestamp()));
		cardDeck.trump = cardDeck.draw();
		return cardDeck;
	}

	@Override
	public List<PlayingCard36> getCards() {
		return deck;
	}

	@Override
	public UUID getId() {
		return id;
	}

	public PlayingCard36 getTrump() {
		return trump;
	}

	public PlayingCard36 drawTrump() {
		PlayingCard36 card = trump;
		this.trump = null;
		return card;
	}
}
