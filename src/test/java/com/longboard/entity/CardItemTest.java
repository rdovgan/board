package com.longboard.entity;

import com.longboard.base.CardType;
import com.longboard.entity.card.IsCard;
import com.longboard.entity.card.IsCost;
import com.longboard.entity.item.IsCardItem;
import com.longboard.entity.item.IsItem;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class CardItemTest extends CardTest implements IsCardItem {

	private final IsItem item;

	public CardItemTest(Long number, String name, String description, CardType cardType, IsCost cost, Predicate<IsCard> condition,
			Consumer<IsCard> effect, IsItem item) {
		super(number, name, description, cardType, cost, condition, effect);
		this.item = item;
	}

	@Override
	public IsItem getItem() {
		return item;
	}
}
