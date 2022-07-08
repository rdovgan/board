package com.longboard.game.durak.engine;

import com.longboard.game.durak.card.CardRank;
import com.longboard.game.durak.card.CardSuit;
import com.longboard.game.durak.card.PlayingCard36;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DurakCardUtilsTest {

	private static final PlayingCard36 SIX_CLUB = new PlayingCard36(CardSuit.Club, CardRank.Six);
	private static final PlayingCard36 SIX_DIAMOND = new PlayingCard36(CardSuit.Diamond, CardRank.Six);
	private static final PlayingCard36 JACK_CLUB = new PlayingCard36(CardSuit.Club, CardRank.Jack);
	private static final PlayingCard36 QUEEN_HEART = new PlayingCard36(CardSuit.Heart, CardRank.Queen);

	@Test
	void canBeat() {
		Assertions.assertFalse(DurakCardUtils.canBeat(SIX_CLUB, null, CardSuit.Club));
		Assertions.assertFalse(DurakCardUtils.canBeat(null, QUEEN_HEART, CardSuit.Diamond));
		//QUEEN_HEART can't beat SIX_CLUB because suits are different
		Assertions.assertFalse(DurakCardUtils.canBeat(QUEEN_HEART, SIX_CLUB, CardSuit.Diamond));
		//JACK_CLUB can beat SIX_CLUB because suits are the same
		Assertions.assertTrue(DurakCardUtils.canBeat(JACK_CLUB, SIX_CLUB, CardSuit.Club));
		//SIX_CLUB can't beat JACK_CLUB because rank are lower
		Assertions.assertFalse(DurakCardUtils.canBeat(SIX_CLUB, JACK_CLUB, CardSuit.Club));
		//QUEEN_HEART can beat SIX_CLUB because HEART is trump
		Assertions.assertTrue(DurakCardUtils.canBeat(QUEEN_HEART, SIX_CLUB, CardSuit.Heart));
	}

	@Test
	void canAttack() {
		Assertions.assertFalse(DurakCardUtils.canAttack(null, null));
		//can play because there is no played cards
		Assertions.assertTrue(DurakCardUtils.canAttack(SIX_CLUB, null));
		//can't play because there is no played card with rank '6'
		Assertions.assertFalse(DurakCardUtils.canAttack(SIX_CLUB, List.of(JACK_CLUB, QUEEN_HEART)));
		//can play because there is played card with rank '6'
		Assertions.assertTrue(DurakCardUtils.canAttack(SIX_CLUB, List.of(JACK_CLUB, SIX_DIAMOND)));
	}
}