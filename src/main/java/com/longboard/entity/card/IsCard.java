package com.longboard.entity.card;

import com.longboard.base.CardType;
import com.longboard.base.TargetType;
import com.longboard.engine.LogUtils;
import com.longboard.engine.card.AfterPlay;
import com.longboard.entity.IsPlayer;
import com.longboard.entity.IsTarget;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface IsCard extends IsTarget {

	Long getNumber();

	String getName();

	String getDescription();

	CardType getCardType();

	IsCost getCost();

	Predicate<IsCard> getCondition();

	//TODO change to OwnerID
	void setOwner(IsPlayer<IsCard> player);

	IsPlayer<IsCard> getOwner();

	default boolean hasOwner() {
		return getOwner() != null;
	}

	default void play() {
		if (getCondition().test(this)) {
			getEffect().accept(this);
			AfterPlay.processCardAfterPlay(this);
		} else {
			LogUtils.info("Can't play card");
		}
	}

	Consumer<IsCard> getEffect();

	@Override
	default TargetType getTargetType() {
		return TargetType.Card;
	}

	default AfterPlay.AfterPlayType getAfterPlayType() {
		return AfterPlay.AfterPlayType.Discard;
	}

}
