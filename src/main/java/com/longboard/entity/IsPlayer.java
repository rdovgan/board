package com.longboard.entity;

import com.longboard.base.Body;
import com.longboard.base.PlayerColor;
import com.longboard.base.Resource;
import com.longboard.base.TargetType;
import com.longboard.engine.LogUtils;
import com.longboard.entity.card.IsCard;
import com.longboard.exception.GameException;
import com.longboard.exception.InitialisationException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IsPlayer<C extends IsCard> extends IsTarget {

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

	List<C> getHandCards();

	List<C> getDiscardCards();

	List<C> getTableCards();

	default void addCardToHand(C card) {
		if (getHandCards() == null) {
			LogUtils.error("Player is not initialised properly. Hand is `null`");
			throw new InitialisationException();
		}
		if (card != null) {
			getHandCards().add(card);
		} else {
			LogUtils.error("Card is `null`");
		}
	}

	default void addCardsToHand(List<C> cards) {
		if (getHandCards() == null) {
			LogUtils.error("Player is not initialised properly. Hand is `null`");
			throw new InitialisationException();
		}
		if (CollectionUtils.isNotEmpty(cards)) {
			cards.forEach(card -> card.setOwner((IsPlayer<IsCard>) this));
			cards.forEach(card -> card.setOwnerId(getId()));
			getHandCards().addAll(cards);
		} else {
			LogUtils.error("Card list is empty");
		}
	}

	default void addCardsToDiscard(List<C> cards) {
		if (getDiscardCards() == null) {
			LogUtils.error("Player is not initialised properly. Hand is `null`");
			throw new InitialisationException();
		}
		if (CollectionUtils.isNotEmpty(cards)) {
			getDiscardCards().addAll(cards);
		}
	}

	default void addCardsToTable(List<C> cards) {
		if (getTableCards() == null) {
			LogUtils.error("Player is not initialised properly. Hand is `null`");
			throw new InitialisationException();
		}
		if (CollectionUtils.isNotEmpty(cards)) {
			getTableCards().addAll(cards);
		}
	}

	@Override
	default TargetType getTargetType() {
		return TargetType.Player;
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

	default void validate() {
		if (getCurrentHealth() == 0) {
			LogUtils.error("Wrong player initialisation. Health is zero");
			throw new GameException("Wrong player initialisation. Health is zero");
		}
		if (getHandCards() == null) {
			return;
		}
		if (getHandCards().stream().anyMatch(card -> card.getOwner() == null)) {
			LogUtils.error("Wrong player initialisation. There is card in hand without owner link");
			//throw new GameException("Wrong player initialisation. There is card in hand without owner link");
		}
	}

}