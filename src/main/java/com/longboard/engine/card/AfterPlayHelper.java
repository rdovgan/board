package com.longboard.engine.card;

import com.longboard.entity.card.IsCard;

import java.util.Collections;

public class AfterPlayHelper {

	public enum AfterPlayType {
		Discard, ToTable, ToHand, Burn
	}

	public static void processCardAfterPlay(IsCard card) {
		card.getOwner().getHandCards().remove(card);
		switch (card.getAfterPlayType()) {
		case Discard:
			card.getOwner().addCardsToDiscard(Collections.singletonList(card));
			break;
		case ToTable:
			card.getOwner().addCardsToTable(Collections.singletonList(card));
			break;
		case ToHand:
			card.getOwner().addCardToHand(card);
			break;
		case Burn:
			card.setOwner(null);
			break;
		default:
			break;
		}
	}

}
