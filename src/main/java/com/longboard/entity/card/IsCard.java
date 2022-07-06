package com.longboard.entity.card;

import com.longboard.base.CardType;
import com.longboard.base.TargetType;
import com.longboard.engine.LogUtils;
import com.longboard.engine.card.AfterPlayHelper;
import com.longboard.entity.IsPlayer;
import com.longboard.entity.IsTarget;
import com.longboard.entity.item.IsCardItem;
import com.longboard.entity.item.IsItem;

import java.util.UUID;
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
	void setOwnerId(UUID ownerId);

	IsPlayer<IsCard> getOwner();
	UUID getOwnerId();

	default boolean hasOwner() {
		return getOwner() != null;
	}

	default void play() {
		if (getCondition().test(this) && getCost().isPlayable(getOwner())) {
			getCost().play(getOwner());
			getEffect().accept(this);
			AfterPlayHelper.processCardAfterPlay(this);
		} else {
			LogUtils.info("Can't play card");
		}
	}

	Consumer<IsCard> getEffect();

	@Override
	default TargetType getTargetType() {
		return TargetType.Card;
	}

	default AfterPlayHelper.AfterPlayType getAfterPlayType() {
		return AfterPlayHelper.AfterPlayType.Discard;
	}

}
