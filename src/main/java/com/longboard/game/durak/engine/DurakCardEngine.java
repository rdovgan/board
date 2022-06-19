package com.longboard.game.durak.engine;

import com.longboard.engine.LogUtils;
import com.longboard.game.durak.card.CardSuit;
import com.longboard.game.durak.card.PlayingCard36;

public class DurakCardEngine {

	public boolean canBeat(PlayingCard36 cardToPlay, PlayingCard36 attacker, CardSuit trump) {
		if (attacker == null || cardToPlay == null) {
			LogUtils.error("Can't play card. Wrong initialisation");
			return false;
		}
		return (cardToPlay.getRank().getValue() > attacker.getRank().getValue() && cardToPlay.getSuit() == attacker.getSuit())
				|| (cardToPlay.getSuit() == trump && attacker.getSuit() != trump);
	}


}
