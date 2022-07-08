package com.longboard.engine.item;

import com.longboard.base.Body;
import com.longboard.base.ItemType;
import com.longboard.engine.LogUtils;
import com.longboard.entity.IsPlayer;
import com.longboard.entity.item.IsCardItem;
import com.longboard.entity.item.IsEnhancementStone;
import com.longboard.entity.item.IsItem;
import com.longboard.exception.GameException;

import java.util.UUID;
import java.util.function.Consumer;

public class ItemBuilder implements IsItem {

	private final UUID id = UUID.randomUUID();

	private String name;
	private String description;
	private Body.BodyPart appliedTo;
	private ItemType type;
	private IsPlayer owner;

	public ItemBuilder addName(String name) {
		this.name = name;
		return this;
	}

	public ItemBuilder addDescription(String description) {
		this.description = description;
		return this;
	}

	public ItemBuilder addAppliedTo(Body.BodyPart appliedTo) {
		this.appliedTo = appliedTo;
		return this;
	}

	public ItemBuilder addItemType(ItemType type) {
		this.type = type;
		return this;
	}

	public ItemBuilder addOwner(IsPlayer player) {
		this.owner = player;
		return this;
	}

	public ItemBuilder validate() {
		if (this.name == null || this.appliedTo == null || this.type == null || this.description == null) {
			LogUtils.error("Wrong item initialisation");
			throw new GameException("Wrong item initialisation");
		}
		return this;
	}

	public IsItem build() {
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
		return description;
	}

	@Override
	public Body.BodyPart appliedTo() {
		return appliedTo;
	}

	@Override
	public ItemType getType() {
		return type;
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
}
