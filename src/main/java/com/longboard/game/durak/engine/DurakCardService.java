package com.longboard.game.durak.engine;

import com.longboard.engine.LogUtils;
import com.longboard.game.durak.card.CardSuit;
import com.longboard.game.durak.card.PlayingCard36;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DurakCardService {

	public static boolean canBeat(PlayingCard36 cardToPlay, PlayingCard36 attacker, CardSuit trump) {
		if (attacker == null || cardToPlay == null) {
			LogUtils.error("Can't play card. Wrong initialisation");
			return false;
		}
		return (cardToPlay.getRank().getValue() > attacker.getRank().getValue() && cardToPlay.getSuit() == attacker.getSuit()) || (cardToPlay.getSuit() == trump
				&& attacker.getSuit() != trump);
	}

	public static boolean canAttack(PlayingCard36 cardToPlay, List<PlayingCard36> playedCards) {
		if (cardToPlay == null) {
			LogUtils.error("Wrong card initialisation");
			return false;
		}
		if (CollectionUtils.isEmpty(playedCards)) {
			return true;
		}
		return playedCards.stream().map(PlayingCard36::getRank).collect(Collectors.toList()).contains(cardToPlay.getRank());
	}

}
