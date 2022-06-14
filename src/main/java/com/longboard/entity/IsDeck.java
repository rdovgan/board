package com.longboard.entity;

import com.longboard.base.TargetType;
import com.longboard.entity.card.IsCard;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;

public interface IsDeck extends IsTarget {

	List<IsCard> getCards();

	default boolean isEmpty() {
		return CollectionUtils.isEmpty(getCards());
	}

	default void shuffle() {
		Collections.shuffle(getCards());
	}

	default IsCard draw() {
		return getCards().remove(0);
	}

	@Override
	default TargetType getTargetType() {
		return TargetType.Deck;
	}

}
