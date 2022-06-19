package com.longboard.engine;

import com.longboard.entity.IsDeck;
import com.longboard.entity.IsPlayer;
import com.longboard.entity.card.IsCard;
import org.apache.commons.collections4.CollectionUtils;

public class DeckUtils {

	public void drawCardFromDeck(IsPlayer player, IsDeck<? extends IsCard> deck) {
		if (deck != null && CollectionUtils.isNotEmpty(deck.getCards())) {
			IsCard card = deck.draw();
			card.setOwner(player);
			player.addCardToHand(card);
		} else {
			LogUtils.info("Deck is empty");
		}
	}

}
