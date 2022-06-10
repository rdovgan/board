package com.longboard.entity;

public interface IsCondition<C> {

	default boolean isPlayable() {
		return true;
	}

}
