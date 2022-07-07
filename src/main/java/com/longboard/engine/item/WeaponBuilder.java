package com.longboard.engine.item;

import com.longboard.engine.LogUtils;
import com.longboard.entity.IsPlayer;
import com.longboard.entity.item.IsCardItem;
import com.longboard.entity.item.IsEnhancementStone;
import com.longboard.entity.item.IsItem;
import com.longboard.entity.item.IsWeapon;
import com.longboard.exception.GameException;

import java.util.UUID;
import java.util.function.Consumer;

public class WeaponBuilder implements IsWeapon {

	private final UUID id = UUID.randomUUID();

	private String name;
	private String description;
	private IsPlayer owner;
	private Integer damage;

	public WeaponBuilder addName(String name) {
		this.name = name;
		return this;
	}

	public WeaponBuilder addDescription(String description) {
		this.description = description;
		return this;
	}

	public WeaponBuilder addOwner(IsPlayer player) {
		this.owner = player;
		return this;
	}

	public WeaponBuilder addDamage(Integer damage) {
		this.damage = damage;
		return this;
	}

	public void validate() {
		if (this.name == null || this.description == null || this.damage == null) {
			LogUtils.error("Wrong weapon initialisation");
			throw new GameException("Wrong weapon initialisation");
		}
	}

	public IsWeapon build() {
		validate();
		return this;
	}

	public Consumer<IsItem> retrieveItemFromCard(IsCardItem card) {
		return item -> card.getItem();
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
	public String getDescription() {
		return name;
	}

	@Override
	public void destroy() {
		//TODO
	}

	@Override
	public void enhance(IsEnhancementStone enhancementStone) {
		//TODO
	}

	@Override
	public IsPlayer getOwner() {
		return owner;
	}

	@Override
	public void setOwner(IsPlayer player) {
		this.owner = player;
	}

	@Override
	public Integer getDamage() {
		return damage;
	}
}
