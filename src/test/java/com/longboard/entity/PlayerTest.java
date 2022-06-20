package com.longboard.entity;

import com.longboard.base.Body;
import com.longboard.base.PlayerColor;
import com.longboard.base.Resource;
import com.longboard.entity.card.IsCard;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerTest implements IsPlayer {

	private final UUID id = UUID.randomUUID();

	private String name;
	private PlayerColor color;
	private Body body = new Body();
	private List<IsCard> hand = new ArrayList<>();
	private List<IsCard> discard = new ArrayList<>();
	private List<IsCard> table = new ArrayList<>();
	private Map<Resource, Integer> resources;
	private ContinuousEffects continuousEffects = new ContinuousEffects(new DualLinkedHashBidiMap<>());

	public PlayerTest(String name, PlayerColor color, List<IsCard> hand, Map<Resource, Integer> resources) {
		this.name = name;
		this.color = color;
		this.hand = hand;
		if (this.hand != null) {
			this.hand.forEach(card -> card.setOwner(this));
		}
		this.resources = resources;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
	}

	@Override
	public Integer getCurrentHealth() {
		return getResource(Resource.Health);
	}

	@Override
	public void changeHealth(Integer value) {
		getResources().put(Resource.Health, Math.min(getResource(Resource.Health) + value, MAX_HEALTH));
		checkDeath();
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
	public Body getBody() {
		return body;
	}

	@Override
	public ContinuousEffects getContinuousEffects() {
		return continuousEffects;
	}

	@Override
	public Map<Resource, Integer> getResources() {
		return resources;
	}

	@Override
	public List<IsCard> getHandCards() {
		return hand;
	}

	@Override
	public List<IsCard> getDiscardCards() {
		return discard;
	}

	@Override
	public List<IsCard> getTableCards() {
		return table;
	}

}
