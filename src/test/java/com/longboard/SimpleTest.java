package com.longboard;

import com.longboard.base.PlayerColor;
import com.longboard.base.Resource;
import com.longboard.entity.card.IsCard;
import com.longboard.entity.IsPlayer;
import com.longboard.entity.PlayerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
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
	private static final Integer HUGE_DAMAGE = -25;

	private List<IsCard> initHand() {
		List<IsCard> hand = new ArrayList<>();
		hand.add(TestResourcesPool.HEALING_SPELL);
		return hand;
	}

	private List<IsCard> initDeck() {
		List<IsCard> deck = new ArrayList<>();
		deck.add(TestResourcesPool.HARD_MINING);
		deck.add(TestResourcesPool.IRON_SWORD_CARD);
		return deck;
	}

	private IsPlayer initPlayer() {
		Map<Resource, Integer> resources = new HashMap<>();
		resources.put(Resource.Mana, START_MANA);
		resources.put(Resource.Gold, START_GOLD);
		resources.put(Resource.Experience, START_EXPERIENCE);
		resources.put(Resource.Health, IsPlayer.MAX_HEALTH);
		return new PlayerTest("Red Player Test", PlayerColor.Red, initHand(), resources);
	}

	@BeforeEach
	public void initialise() {
		redPlayer = initPlayer();
		redPlayer.postConstruct();
		playersDeck = initDeck();
	}

	@Test
	public void testPlayer() {
		Assertions.assertNotNull(redPlayer);
		Assertions.assertNotNull(redPlayer.getHandCards());
		Assertions.assertNotNull(redPlayer.getResources());
		Assertions.assertEquals(IsPlayer.MAX_HEALTH, redPlayer.getCurrentHealth());
		Assertions.assertEquals(IsPlayer.MAX_HEALTH, redPlayer.getResource(Resource.Health));
		Assertions.assertEquals(START_MANA, redPlayer.getResource(Resource.Mana));
		Assertions.assertEquals(START_GOLD, redPlayer.getResource(Resource.Gold));
		Assertions.assertEquals(START_EXPERIENCE, redPlayer.getResource(Resource.Experience));
		Assertions.assertEquals(1, redPlayer.getHandCards().size());
	}

	@Test
	public void addCardsToHand() {
		Assertions.assertEquals(1, redPlayer.getHandCards().size());
		Assertions.assertEquals(2, playersDeck.size());
		redPlayer.addCardsToHand(playersDeck);
		Assertions.assertEquals(3, redPlayer.getHandCards().size());
	}

	@Test
	public void takeDamageAndPlayHealSpell() {
		IsCard healSpell = redPlayer.getHandCards().stream().filter(card -> TestResourcesPool.HEALING_SPELL.getName().equals(card.getName())).findFirst()
				.orElseThrow();
		Assertions.assertNotNull(healSpell);
		Assertions.assertTrue(redPlayer.getHandCards().contains(healSpell));
		healSpell.play();
		Assertions.assertTrue(redPlayer.getHandCards().contains(healSpell));
		redPlayer.changeHealth(HUGE_DAMAGE);
		Assertions.assertEquals(IsPlayer.MAX_HEALTH + HUGE_DAMAGE, redPlayer.getCurrentHealth());
		healSpell.play();
		Assertions.assertFalse(redPlayer.getHandCards().contains(healSpell));
		Assertions.assertEquals(IsPlayer.MAX_HEALTH + HUGE_DAMAGE + 10, redPlayer.getCurrentHealth());
	}
}
