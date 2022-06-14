package com.longboard.entity.card;

public interface IsCondition<C> {

	default boolean isPlayable() {
		return true;
	}

}
