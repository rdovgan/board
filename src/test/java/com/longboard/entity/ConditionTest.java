package com.longboard.entity;

public class ConditionTest<IsCard> implements IsCondition<IsCard> {

	private boolean isResourcesAvailable;

	public boolean isPlayerActive() {

		return true;
	}

}
