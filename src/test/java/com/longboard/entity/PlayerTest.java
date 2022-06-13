package com.longboard.entity;

import com.longboard.base.PlayerColor;
import com.longboard.base.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerTest implements IsPlayer {

	private final UUID id = UUID.randomUUID();

	private String name;
	private PlayerColor color;
	private List<IsCard> hand = new ArrayList<>();
	private Map<Resource, Integer> resources;

	public PlayerTest(String name, PlayerColor color, List<IsCard> hand, Map<Resource, Integer> resources) {
		this.name = name;
		this.color = color;
		this.hand = hand;
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
	}

	@Override
	public String getId() {
		return id.toString();
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
	public Map<Resource, Integer> getResources() {
		return resources;
	}

	@Override
	public List<IsCard> getHand() {
		return hand;
	}

}
