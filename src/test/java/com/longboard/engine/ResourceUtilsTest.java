package com.longboard.engine;

import com.longboard.base.PlayerColor;
import com.longboard.base.Resource;
import com.longboard.engine.card.CostBuilder;
import com.longboard.entity.CardTest;
import com.longboard.entity.IsPlayer;
import com.longboard.entity.PlayerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ResourceUtilsTest {

	private static final Integer START_MANA = 1;
	private static final Integer START_GOLD = 10;

	private ResourceUtils resourceUtils = new ResourceUtils();

	private PlayerTest initPlayer() {
		Map<Resource, Integer> resources = new HashMap<>();
		resources.put(Resource.Mana, START_MANA);
		resources.put(Resource.Gold, START_GOLD);
		resources.put(Resource.Health, IsPlayer.MAX_HEALTH);
		return new PlayerTest("Green Player Test", PlayerColor.Green, new ArrayList<>(), resources);
	}

	@Test
	void addResourceToPlayer() {
		PlayerTest greenPlayer = initPlayer();
		Integer manaToAdd = 3;
		Assertions.assertEquals(START_MANA, greenPlayer.getResource(Resource.Mana));
		resourceUtils.addResourceToPlayer(greenPlayer, Resource.Mana, manaToAdd);
		Assertions.assertEquals(START_MANA + manaToAdd, greenPlayer.getResource(Resource.Mana));
		Integer wrongValueManaToAdd = -3;
		resourceUtils.addResourceToPlayer(greenPlayer, Resource.Mana, wrongValueManaToAdd);
		Assertions.assertEquals(START_MANA + manaToAdd, greenPlayer.getResource(Resource.Mana));

		resourceUtils.addResourceToPlayer(null, Resource.Mana, wrongValueManaToAdd);
		resourceUtils.addResourceToPlayer(new PlayerTest("Empty", PlayerColor.Red, new ArrayList<>(), null), Resource.Mana, wrongValueManaToAdd);
	}

	@Test
	void subResourcesFromPlayer() {
		PlayerTest greenPlayer = initPlayer();
		Integer goldToSubtract = 2;
		resourceUtils.subResourcesFromPlayer(greenPlayer, Resource.Gold, goldToSubtract);
		Assertions.assertEquals(START_GOLD - goldToSubtract, greenPlayer.getResource(Resource.Gold));
		Integer exceedGoldToSubtract = 12;
		resourceUtils.subResourcesFromPlayer(greenPlayer, Resource.Gold, exceedGoldToSubtract);
		Assertions.assertEquals(START_GOLD - goldToSubtract, greenPlayer.getResource(Resource.Gold));

		resourceUtils.subResourcesFromPlayer(greenPlayer, Resource.Experience, 1);
	}

	@Test
	void isPlayable() {
		Assertions.assertFalse(resourceUtils.isPlayable(null));
		CardTest emptyCard = new CardTest(null, null, null, null, null, null, null);
		Assertions.assertFalse(resourceUtils.isPlayable(emptyCard));
		emptyCard.setOwner(initPlayer());
		Assertions.assertFalse(resourceUtils.isPlayable(emptyCard));
	}

	@Test
	void checkCost() {
		PlayerTest greenPlayer = initPlayer();
		Assertions.assertTrue(ResourceUtils.checkCost(greenPlayer, new CostBuilder().addCost(Resource.Gold, 5).build()));
		Assertions.assertTrue(ResourceUtils.checkCost(greenPlayer, new CostBuilder().build()));
		Assertions.assertTrue(ResourceUtils.checkCost(null, new CostBuilder().build()));
	}
}