package com.longboard.engine.card;

import com.longboard.entity.card.IsCard;

import java.util.function.Predicate;

public class CardCondition {

	public static Predicate<IsCard> whenDamaged() {
		return card -> card.getOwner().getCurrentHealth() < card.getOwner().getMaxHealth();
	}

	public static Predicate<IsCard> whenHealthIsGreaterThanOne() {
		return card -> card.getOwner().getCurrentHealth() > 1;
	}

	public static Predicate<IsCard> whenOneHandIsFree() {
		return card -> card.getOwner().getCurrentHealth() > 1;
	}

}
