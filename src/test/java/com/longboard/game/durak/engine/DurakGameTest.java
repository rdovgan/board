package com.longboard.game.durak.engine;

import com.longboard.entity.IsPlayer;
import com.longboard.exception.InitialisationException;
import com.longboard.game.durak.card.CardRank;
import com.longboard.game.durak.card.PlayingCard36;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;

class DurakGameTest {

	@Test
	void testInitialiseGame() {
		Assertions.assertThrows(InitialisationException.class, () -> new DurakGame().initialiseGame(-2));
		Assertions.assertThrows(InitialisationException.class, () -> new DurakGame().initialiseGame(DurakGame.MIN_PLAYERS_COUNT - 1));
		Assertions.assertThrows(InitialisationException.class, () -> new DurakGame().initialiseGame(DurakGame.MAX_PLAYERS_COUNT + 1));

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
	void testDefineFirstPlayer() {
		DurakGame game = new DurakGame();
		game.initialiseGame(6);
		Assertions.assertEquals(0, game.getDeck().size());
		IsPlayer<PlayingCard36> firstPlayer = game.defineFirstPlayer();
		Assertions.assertNotNull(firstPlayer);
		PlayingCard36 minTrumpCardInFirstPlayerHand = firstPlayer.getHandCards().stream()
				.filter(card -> Objects.equals(card.getRank().getValue(), CardRank.Six.getValue()) && card.getSuit() == game.getTrump().getSuit()).findFirst()
				.orElse(null);
		Assertions.assertNotNull(minTrumpCardInFirstPlayerHand);
		Assertions.assertEquals(CardRank.Six, minTrumpCardInFirstPlayerHand.getRank());
		Assertions.assertEquals(game.getTrump().getSuit(), minTrumpCardInFirstPlayerHand.getSuit());
	}

	@Test
	void testDefinePlayerToAttack() {
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

	@Test
	void defineNextPlayerToAttack() {
		Integer playersCount = 3;
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);
		List<IsPlayer<PlayingCard36>> players = game.getActivePlayers();
		Assertions.assertTrue(CollectionUtils.isNotEmpty(players));
		Assertions.assertEquals(playersCount, players.size());
		IsPlayer<PlayingCard36> firstPlayer = game.defineFirstPlayer();
		DurakBattle battle = game.startBattle(null, firstPlayer);
		game.endBattle(battle);
		IsPlayer<PlayingCard36> secondPlayer = game.defineNextPlayerToAttack(battle);
		battle = game.startBattle(battle, secondPlayer);
		game.endBattle(battle);
		IsPlayer<PlayingCard36> thirdPlayer = game.defineNextPlayerToAttack(battle);
		Assertions.assertNotEquals(firstPlayer.getId(), secondPlayer.getId());
		Assertions.assertNotEquals(thirdPlayer.getId(), secondPlayer.getId());
		Assertions.assertNotEquals(firstPlayer.getId(), thirdPlayer.getId());
		Assertions.assertTrue(players.contains(firstPlayer));
		Assertions.assertTrue(players.contains(secondPlayer));
		Assertions.assertTrue(players.contains(thirdPlayer));
	}

	@Test
	void endGame() {
		int playersCount = 5;
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);
		DurakBattle battle = null;
		while (game.getActivePlayers().size() > 1) {
			IsPlayer<PlayingCard36> attacker = game.defineNextPlayerToAttack(battle);
			battle = game.startBattle(battle, attacker);
			PlayingCard36 attackCard = battle.getCardsForAttack().stream().findFirst().orElse(null);
			battle.playCardToAttack(attackCard);
			PlayingCard36 defendCard = battle.getCardsForDefend(attackCard).stream().findFirst().orElse(null);
			if (defendCard != null) {
				battle.playCardToDefend(defendCard, attackCard);
			}
			game.endBattle(battle);
		}
		Map<Integer, IsPlayer<PlayingCard36>> winners = game.endGame();
		Assertions.assertEquals(playersCount - 1, winners.entrySet().size());
	}

	@Test
	void startBattle() {
	}

	@Test
	void getDeck() {
	}

	@Test
	void getDiscard() {
	}

	@Test
	void getTrump() {
	}

	@Test
	void getActivePlayers() {
	}

	@Test
	void endBattle() {
	}
}