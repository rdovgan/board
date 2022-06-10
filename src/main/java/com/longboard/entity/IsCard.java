package com.longboard.entity;

import com.longboard.base.CardType;

import java.util.function.Predicate;

public interface IsCard {

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

}
