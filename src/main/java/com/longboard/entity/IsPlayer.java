package com.longboard.entity;

import com.longboard.base.Body;
import com.longboard.base.PlayerColor;
import com.longboard.base.Resource;
import com.longboard.base.TargetType;
import com.longboard.engine.ContinuousEffects;
import com.longboard.engine.LogUtils;
import com.longboard.exception.InitialisationException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IsPlayer extends IsTarget {

	Integer MAX_HEALTH = 30;

	String getName();
	PlayerColor getColor();
	ContinuousEffects getContinuousEffects();
	Body getBody();

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
			throw new InitialisationException();
		}
		if (CollectionUtils.isNotEmpty(cards)) {
			getHand().addAll(cards);
		}
	}


	Integer getCurrentHealth();
	void changeHealth(Integer value);

	default Integer getMaxHealth() {
		return MAX_HEALTH;
	}

	default boolean isDead() {
		return getCurrentHealth() != null && getCurrentHealth() <= 0;
	}

	default void checkDeath() {
		if (isDead()) {
			LogUtils.info("Player " + getName() + " is dead");
			//TODO need to end game
		}
	}

	@Override
	default TargetType getTargetType() {
		return TargetType.Player;
	}
}