package com.longboard.entity;

import com.longboard.TestResourcesPoolConstants;
import com.longboard.base.TargetType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class IsDeckTest {

	@Test
	public void testDrawWithEmptyDeck() {
		IsDeck<CardTest> testDeck = new DeckTest();
		Assertions.assertThrows(IndexOutOfBoundsException.class, testDeck::draw);
	}
	@Test
	public void testTargetType() {
		IsDeck<CardTest> testDeck = new DeckTest();
		Assertions.assertEquals(TargetType.Deck, testDeck.getTargetType());
	}

	@Test
	public void testDeckShuffle() {
		List<CardTest> cards = List.of(TestResourcesPoolConstants.HEALING_SPELL, TestResourcesPoolConstants.HARD_MINING);
		IsDeck<CardTest> testDeck = new DeckTest(new ArrayList<>(cards));
		testDeck.shuffle();
	}

}