package com.longboard.entity;

import com.longboard.base.CardType;
import com.longboard.base.TargetType;
import com.longboard.entity.card.IsCard;
import com.longboard.entity.card.IsCardTarget;
import com.longboard.entity.card.IsCost;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class CardTargetTest extends CardTest implements IsCardTarget {

	private IsTarget target = null;

	public CardTargetTest(Long number, String name, String description, CardType cardType, IsCost cost, Predicate<IsCard> condition, Consumer<IsCard> effect) {
		super(number, name, description, cardType, cost, condition, effect);
	}

	@Override
	public TargetType getTargetType() {
		return TargetType.Card;
	}

	@Override
	public void setTarget(IsTarget target) {
		this.target = target;
	}
}
