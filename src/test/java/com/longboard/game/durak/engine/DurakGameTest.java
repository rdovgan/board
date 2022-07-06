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
import java.util.UUID;

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
		int playersCount = 3;
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);
		DurakBattle battle = game.startBattle(null, game.defineFirstPlayer());
		Assertions.assertNotNull(battle);
		battle = game.startBattle(battle, game.defineNextPlayerToAttack(battle));
		Assertions.assertNotNull(battle);
	}

	@Test
	void getDeck() {
		int playersCount = 2;
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);
		Assertions.assertTrue(CollectionUtils.isNotEmpty(game.getDeck()));
		Assertions.assertEquals(DurakGame.MAX_CARDS_COUNT - playersCount * DurakGame.CARDS_COUNT_IN_HAND_ON_START - 1, game.getDeck().size());
	}

	@Test
	void getDiscard() {
		int playersCount = 2;
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);
		Assertions.assertTrue(CollectionUtils.isEmpty(game.getDiscard()));
		DurakBattle battle = game.startBattle(null, game.defineFirstPlayer());
		PlayingCard36 attackCard = battle.getCardsForAttack().stream().findFirst().orElse(null);
		battle.playCardToAttack(attackCard);
		game.endBattle(battle);
		Assertions.assertTrue(CollectionUtils.isNotEmpty(game.getDiscard()));
		Assertions.assertEquals(1, game.getDiscard().size());
	}

	@Test
	void getTrump() {
		int playersCount = 5;
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);
		PlayingCard36 cardTrump = game.getTrump();
		Assertions.assertNotNull(cardTrump);
		int cardsCountNeedToDraw = 6;
		DurakBattle battle = null;
		while (cardsCountNeedToDraw > 1) {
			battle = game.startBattle(battle, game.defineFirstPlayer());
			while (CollectionUtils.isNotEmpty(battle.getCardsForAttack())) {
				battle.playCardToAttack(battle.getCardsForAttack().stream().findFirst().orElse(null));
				cardsCountNeedToDraw--;
			}
			game.endBattle(battle);
		}
		cardTrump = game.getTrump();
		Assertions.assertNotNull(cardTrump);
	}
	@Test
	void getTrumpForEmptyDeck() {
		int playersCount = 6;
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);
		PlayingCard36 cardTrump = game.getTrump();
		Assertions.assertNotNull(cardTrump);
	}

	@Test
	void getActivePlayers() {
		int playersCount = 4;
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);
		Assertions.assertTrue(CollectionUtils.isNotEmpty(game.getActivePlayers()));
		Assertions.assertEquals(playersCount, game.getActivePlayers().size());
	}

	@Test
	void endBattle() {
		int playersCount = 2;
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);
		DurakBattle battle = game.startBattle(null, game.defineFirstPlayer());
		PlayingCard36 attackCard = battle.getCardsForAttack().stream().findFirst().orElse(null);
		battle.playCardToAttack(attackCard);
		PlayingCard36 defendCard = battle.getCardsForDefend(attackCard).stream().findFirst().orElse(null);
		if (defendCard != null) {
			battle.playCardToDefend(defendCard, attackCard);
		}
		game.endBattle(battle);
		battle = game.startBattle(battle, battle.getAttacker());
		Assertions.assertEquals(DurakGame.CARDS_COUNT_IN_HAND_ON_START, battle.getAttacker().getHandCards().size());
		Assertions.assertTrue(battle.getDefender().getHandCards().size() >= DurakGame.CARDS_COUNT_IN_HAND_ON_START);
		UUID attackerId = battle.getAttacker().getId();
		UUID defenderId = battle.getDefender().getId();
		Assertions.assertTrue(battle.getAttacker().getHandCards().stream().allMatch(card -> card.getOwnerId() == attackerId));
		Assertions.assertTrue(battle.getDefender().getHandCards().stream().allMatch(card -> card.getOwnerId() == defenderId));
	}

	@Test
	void getCurrentBattle() {
		int playersCount = 4;
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);
		game.startBattle(null, game.defineFirstPlayer());
	}
}