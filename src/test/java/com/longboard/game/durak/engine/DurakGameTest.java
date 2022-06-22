package com.longboard.game.durak.engine;

import com.longboard.exception.InitialisationException;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DurakGameTest {

	@Test
	void initialiseGameWithWrongNumberOfPlayers() {
		Assertions.assertThrows(InitialisationException.class, () -> new DurakGame().initialiseGame(-2));
		Assertions.assertThrows(InitialisationException.class, () -> new DurakGame().initialiseGame(DurakGame.MIN_PLAYERS_COUNT - 1));
		Assertions.assertThrows(InitialisationException.class, () -> new DurakGame().initialiseGame(DurakGame.MAX_PLAYERS_COUNT + 1));
	}

	@Test
	void initialiseGame() {
		Integer playersCount = 3;
		DurakGame game = new DurakGame();
		Assertions.assertTrue(CollectionUtils.isEmpty(game.getDeck()));
		Assertions.assertNull(game.getTrump());
		Assertions.assertTrue(CollectionUtils.isEmpty(game.getActivePlayers()));
		game.initialiseGame(playersCount);
		Assertions.assertEquals(DurakGame.MAX_CARDS_COUNT - DurakGame.CARDS_COUNT_IN_HAND_ON_START * playersCount - 1, game.getDeck().size());
		Assertions.assertNotNull(game.getTrump());
		Assertions.assertEquals(playersCount, game.getActivePlayers().size());
	}

	@Test
	void defineFirstPlayer() {
	}

	@Test
	void definePlayerToAttack() {
	}
}