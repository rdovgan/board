package com.longboard.entity;

import com.longboard.entity.card.IsCondition;

public class ConditionTest<IsCard> implements IsCondition<IsCard> {

	private boolean isResourcesAvailable;

	public boolean isPlayerActive() {

		return true;
	}

}
