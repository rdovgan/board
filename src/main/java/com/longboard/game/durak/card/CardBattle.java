package com.longboard.game.durak.card;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CardBattle {

	private Map<PlayingCard36, PlayingCard36> battlingCards = new HashMap<>();

	public void addAttackerCard(PlayingCard36 card) {
		battlingCards.put(card, null);
	}

	public void addDefendingCard(PlayingCard36 attacker, PlayingCard36 defender) {
		battlingCards.put(attacker, defender);
	}

	public Map<PlayingCard36, PlayingCard36> getBattlingCards() {
		return Collections.unmodifiableMap(battlingCards);
	}

}
