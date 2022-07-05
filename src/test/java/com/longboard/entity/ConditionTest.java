package com.longboard.entity;

import com.longboard.entity.card.IsCondition;

public class ConditionTest<C> implements IsCondition<C> {

	public boolean isPlayerActive() {
		return true;
	}

}
