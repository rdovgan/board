package com.longboard.game.durak.engine;

import com.longboard.base.Body;
import com.longboard.base.PlayerColor;
import com.longboard.base.Resource;
import com.longboard.entity.ContinuousEffects;
import com.longboard.entity.IsPlayer;
import com.longboard.entity.card.IsCard;
import com.longboard.game.durak.card.PlayingCard36;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DurakPlayer implements IsPlayer<PlayingCard36> {

	private final UUID id = UUID.randomUUID();

	private String name;
	private PlayerColor color;

	private List<PlayingCard36> hand;

	private Integer health = 1;

	public DurakPlayer(String name, PlayerColor color, List<PlayingCard36> cards) {
		this.name = name;
		this.color = color;
		this.hand = cards;
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public PlayerColor getColor() {
		return color;
	}

	@Override
	public ContinuousEffects getContinuousEffects() {
		return null;
	}

	@Override
	public Body getBody() {
		return null;
	}

	@Override
	public Map<Resource, Integer> getResources() {
		return null;
	}

	@Override
	public List<PlayingCard36> getHandCards() {
		return hand;
	}

	@Override
	public List<PlayingCard36> getDiscardCards() {
		return null;
	}

	@Override
	public List<PlayingCard36> getTableCards() {
		return null;
	}

	@Override
	public Integer getCurrentHealth() {
		return this.health;
	}

	@Override
	public void changeHealth(Integer value) {
		this.health = value;
	}
}
