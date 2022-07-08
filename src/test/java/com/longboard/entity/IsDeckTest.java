package com.longboard.entity;

import com.longboard.base.CardType;
import com.longboard.base.TargetType;
import com.longboard.engine.card.CostBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

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
		List<CardTest> cards = new ArrayList<>();
		IntStream.range(0, 100)
				.forEach(i -> cards.add(new CardTest(98001L, "Test Spell", "Do nothing", CardType.Spell, new CostBuilder().build(), null, null)));
		UUID idOfFourthCard = cards.stream().skip(3).findFirst().map(CardTest::getId).orElse(null);
		Assertions.assertNotNull(idOfFourthCard);

		IsDeck<CardTest> testDeck = new DeckTest(new ArrayList<>(cards));
		UUID idOfFourthCardBeforeShuffle = testDeck.getCards().stream().skip(3).findFirst().map(CardTest::getId).orElse(null);
		Assertions.assertEquals(idOfFourthCard, idOfFourthCardBeforeShuffle);

		testDeck.shuffle();
		UUID idOfFourthCardAfterShuffle = testDeck.getCards().stream().skip(3).findFirst().map(CardTest::getId).orElse(null);
		Assertions.assertNotEquals(idOfFourthCard, idOfFourthCardAfterShuffle);
	}

}