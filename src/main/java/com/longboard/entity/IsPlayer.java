package com.longboard.entity;

import com.longboard.base.PlayerColor;
import com.longboard.base.Resource;
import com.longboard.engine.LogUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IsPlayer {

	Integer MAX_HEALTH = 30;

	String getId();
	String getName();
	PlayerColor getColor();

	Map<Resource, Integer> getResources();

	default Integer getResource(Resource resource) {
		if (MapUtils.isEmpty(getResources())) {
			return 0;
		}
		return Optional.ofNullable(getResources().get(resource)).orElse(0);
	}

	List<IsCard> getHand();

	default void addCardsToHand(List<IsCard> cards) {
		if (getHand() == null) {
			LogUtils.error("Player is not initialised properly. Hand is `null`");
		}
		if (CollectionUtils.isNotEmpty(cards)) {
			getHand().addAll(cards);
		}
	}

	default Integer getMaxHealth() {
		return MAX_HEALTH;
	}

	Integer getCurrentHealth();

	default boolean isDead() {
		return getCurrentHealth() != null && getCurrentHealth() <= 0;
	}

}