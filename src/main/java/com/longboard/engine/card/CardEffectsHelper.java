package com.longboard.engine.card;

import com.longboard.base.Resource;
import com.longboard.engine.LogUtils;
import com.longboard.entity.card.IsCard;
import com.longboard.entity.item.IsCardItem;
import com.longboard.entity.item.IsItem;

import java.util.function.Consumer;

public class CardEffectsHelper {

	public static Consumer<IsCard> healPlayer(int valueToHeal) {
		return card -> {
			LogUtils.info("Heal player for " + valueToHeal + " health");
			card.getOwner().changeHealth(valueToHeal);
		};
	}

	public static Consumer<IsCard> continuousEffectOnTurnStart(Consumer<IsCard> effect) {
		return card -> {
			LogUtils.info("Added continuous effect on start turn");
			card.getOwner().getContinuousEffects().addEffectOnStartTurn(card, effect);
		};
	}

	public static Consumer<IsCard> dealDamageAndGiveGold(int damageAmount, int goldAmount) {
		return card -> {
			LogUtils.info("Deal " + damageAmount + " damage to player and give " + goldAmount + " gold");
			card.getOwner().changeHealth(-damageAmount);
			card.getOwner().getResources().put(Resource.Gold, card.getOwner().getResource(Resource.Gold) + goldAmount);
		};
	}

	public static Consumer<IsCardItem> equipItem() {
		return card -> card.getItem().equip(card.getOwner());
	}

	public static Consumer<IsCard> equipItem(IsItem item) {
		return card -> {
			item.equip(card.getOwner());
			LogUtils.info("Equipped item " + item.getName() + " for player " + card.getOwner());
		};
	}
}
