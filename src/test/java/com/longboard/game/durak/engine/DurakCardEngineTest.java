package com.longboard.game.durak.engine;

import com.longboard.game.durak.card.CardRank;
import com.longboard.game.durak.card.CardSuit;
import com.longboard.game.durak.card.PlayingCard36;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DurakCardEngineTest {

	private PlayingCard36 sixClub = new PlayingCard36(CardSuit.Club, CardRank.Six);
	private PlayingCard36 jackClub = new PlayingCard36(CardSuit.Club, CardRank.Jack);
	private PlayingCard36 queenHeart = new PlayingCard36(CardSuit.Heart, CardRank.Queen);

	@Test
	void canBeat() {
	}

	@Test
	void canAttack() {
	}
}