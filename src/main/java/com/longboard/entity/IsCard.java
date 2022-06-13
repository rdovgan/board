package com.longboard.entity;

import com.longboard.base.CardType;
import com.longboard.base.TargetType;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface IsCard extends IsTarget {

	Long getNumber();

	String getName();
	String getDescription();

	CardType getCardType();

	IsCost getCost();

	Predicate<IsCard> getCondition();

	IsPlayer getOwner();

	default boolean hasOwner() {
		return getOwner() != null;
	}

	void play();

	Consumer<IsCard> getEffect();

	@Override
	default TargetType getTargetType() {
		return TargetType.Card;
	}

}
