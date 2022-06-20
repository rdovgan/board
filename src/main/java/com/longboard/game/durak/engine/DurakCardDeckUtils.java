package com.longboard.game.durak.engine;

import com.longboard.entity.card.IsCard;
import com.longboard.game.durak.card.CardRank;
import com.longboard.game.durak.card.CardSuit;
import com.longboard.game.durak.card.PlayingCard36;

import java.util.HashSet;
import java.util.Set;

public class DurakCardDeckUtils {

	public static Set<PlayingCard36> buildNewDeck() {
		Set<PlayingCard36> deck = new HashSet<>();

		deck.add(new PlayingCard36(CardSuit.Club, CardRank.Ace));
		deck.add(new PlayingCard36(CardSuit.Club, CardRank.King));
		deck.add(new PlayingCard36(CardSuit.Club, CardRank.Queen));
		deck.add(new PlayingCard36(CardSuit.Club, CardRank.Jack));
		deck.add(new PlayingCard36(CardSuit.Club, CardRank.Ten));
		deck.add(new PlayingCard36(CardSuit.Club, CardRank.Nine));
		deck.add(new PlayingCard36(CardSuit.Club, CardRank.Eight));
		deck.add(new PlayingCard36(CardSuit.Club, CardRank.Seven));
		deck.add(new PlayingCard36(CardSuit.Club, CardRank.Six));

		deck.add(new PlayingCard36(CardSuit.Diamond, CardRank.Ace));
		deck.add(new PlayingCard36(CardSuit.Diamond, CardRank.King));
		deck.add(new PlayingCard36(CardSuit.Diamond, CardRank.Queen));
		deck.add(new PlayingCard36(CardSuit.Diamond, CardRank.Jack));
		deck.add(new PlayingCard36(CardSuit.Diamond, CardRank.Ten));
		deck.add(new PlayingCard36(CardSuit.Diamond, CardRank.Nine));
		deck.add(new PlayingCard36(CardSuit.Diamond, CardRank.Eight));
		deck.add(new PlayingCard36(CardSuit.Diamond, CardRank.Seven));
		deck.add(new PlayingCard36(CardSuit.Diamond, CardRank.Six));

		deck.add(new PlayingCard36(CardSuit.Heart, CardRank.Ace));
		deck.add(new PlayingCard36(CardSuit.Heart, CardRank.King));
		deck.add(new PlayingCard36(CardSuit.Heart, CardRank.Queen));
		deck.add(new PlayingCard36(CardSuit.Heart, CardRank.Jack));
		deck.add(new PlayingCard36(CardSuit.Heart, CardRank.Ten));
		deck.add(new PlayingCard36(CardSuit.Heart, CardRank.Nine));
		deck.add(new PlayingCard36(CardSuit.Heart, CardRank.Eight));
		deck.add(new PlayingCard36(CardSuit.Heart, CardRank.Seven));
		deck.add(new PlayingCard36(CardSuit.Heart, CardRank.Six));

		deck.add(new PlayingCard36(CardSuit.Spade, CardRank.Ace));
		deck.add(new PlayingCard36(CardSuit.Spade, CardRank.King));
		deck.add(new PlayingCard36(CardSuit.Spade, CardRank.Queen));
		deck.add(new PlayingCard36(CardSuit.Spade, CardRank.Jack));
		deck.add(new PlayingCard36(CardSuit.Spade, CardRank.Ten));
		deck.add(new PlayingCard36(CardSuit.Spade, CardRank.Nine));
		deck.add(new PlayingCard36(CardSuit.Spade, CardRank.Eight));
		deck.add(new PlayingCard36(CardSuit.Spade, CardRank.Seven));
		deck.add(new PlayingCard36(CardSuit.Spade, CardRank.Six));

		return deck;
	}

}
