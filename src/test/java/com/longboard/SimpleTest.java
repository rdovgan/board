package com.longboard;

import com.longboard.base.PlayerColor;
import com.longboard.base.Resource;
import com.longboard.entity.IsCard;
import com.longboard.entity.IsPlayer;
import com.longboard.entity.PlayerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleTest {

	/**
	 * Test Red Player with 10 Mana, 0 Exp and 50 Gold going to play 2 Cards.
	 */

	private IsPlayer redPlayer;
	private List<IsCard> playersDeck;

	private static final Integer START_MANA = 10;
	private static final Integer START_GOLD = 50;
	private static final Integer START_EXPERIENCE = 0;

	private List<IsCard> initHand() {
		List<IsCard> hand = new ArrayList<>();
		hand.add(TestResourcesPool.HEALING_SPELL);
		return hand;
	}

	private List<IsCard> initDeck() {
		List<IsCard> deck = new ArrayList<>();
		deck.add(TestResourcesPool.HARD_MINING);
		deck.add(TestResourcesPool.IRON_SWORD);
		return deck;
	}

	private IsPlayer initPlayer() {
		return new PlayerTest("Red Player Test", PlayerColor.Red, initHand(),
				Map.of(Resource.Mana, START_MANA, Resource.Gold, START_GOLD, Resource.Experience, START_EXPERIENCE));
	}

	@BeforeEach
	public void initialise() {
		redPlayer = initPlayer();
		playersDeck = initDeck();
	}

	@Test
	public void testPlayer() {
		Assertions.assertNotNull(redPlayer);
		Assertions.assertNotNull(redPlayer.getHand());
		Assertions.assertNotNull(redPlayer.getResources());
		Assertions.assertEquals(IsPlayer.MAX_HEALTH, redPlayer.getCurrentHealth());
		Assertions.assertEquals(IsPlayer.MAX_HEALTH, redPlayer.getResource(Resource.Health));
		Assertions.assertEquals(START_MANA, redPlayer.getResource(Resource.Mana));
		Assertions.assertEquals(START_GOLD, redPlayer.getResource(Resource.Gold));
		Assertions.assertEquals(START_EXPERIENCE, redPlayer.getResource(Resource.Experience));
		Assertions.assertEquals(1, redPlayer.getHand().size());
	}

	@Test
	public void addCardsToHand() {
		Assertions.assertEquals(1, redPlayer.getHand().size());
		Assertions.assertEquals(2, playersDeck.size());
		redPlayer.addCardsToHand(playersDeck);
		Assertions.assertEquals(3, redPlayer.getHand().size());
	}

	@Test
	public void playCard() {
		IsCard healSpell = redPlayer.getHand().stream().filter(card -> TestResourcesPool.HEALING_SPELL.getName().equals(card.getName())).findFirst()
				.orElseThrow();
		Assertions.assertNotNull(healSpell);

	}
}
