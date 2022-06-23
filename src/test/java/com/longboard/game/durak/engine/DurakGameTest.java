package com.longboard.game.durak.engine;

import com.longboard.base.PlayerColor;
import com.longboard.entity.IsPlayer;
import com.longboard.exception.InitialisationException;
import com.longboard.game.durak.card.CardRank;
import com.longboard.game.durak.card.CardSuit;
import com.longboard.game.durak.card.PlayingCard36;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

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
		DurakGame game = new DurakGame();
		game.initialiseGame(6);
		Assertions.assertEquals(0, game.getDeck().size());
		IsPlayer<PlayingCard36> firstPlayer = game.defineFirstPlayer();
		Assertions.assertNotNull(firstPlayer);
		PlayingCard36 minTrumpCardInFirstPlayerHand = firstPlayer.getHandCards().stream()
				.filter(card -> Objects.equals(card.getRank().getValue(), CardRank.Six.getValue()) && card.getSuit() == game.getTrump().getSuit()).findFirst().orElse(null);
		Assertions.assertNotNull(minTrumpCardInFirstPlayerHand);
		Assertions.assertEquals(CardRank.Six, minTrumpCardInFirstPlayerHand.getRank());
		Assertions.assertEquals(game.getTrump().getSuit(), minTrumpCardInFirstPlayerHand.getSuit());
	}

	@Test
	void definePlayerToAttack() {
		Integer playersCount = 2;
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);
		List<IsPlayer<PlayingCard36>> players = game.getActivePlayers();
		Assertions.assertEquals(playersCount, players.size());
		IsPlayer<PlayingCard36> firstPlayer = game.defineFirstPlayer();
		Assertions.assertNotNull(firstPlayer);
		IsPlayer<PlayingCard36> playerToAttack = game.definePlayerToAttack(firstPlayer);
		Assertions.assertNotNull(playerToAttack);
		Assertions.assertNotEquals(firstPlayer, playerToAttack);
	}
}