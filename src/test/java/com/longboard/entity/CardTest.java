package com.longboard.entity;

import com.longboard.base.CardType;
import com.longboard.entity.card.IsCard;
import com.longboard.entity.card.IsCost;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CardTest implements IsCard {

	private final UUID id = UUID.randomUUID();
	private final Long number;
	private final String name;
	private final String description;
	private final CardType cardType;
	private IsCost cost;
	private Predicate<IsCard> condition;
	private IsPlayer<CardTest> owner;
	private UUID ownerId;
	private Consumer<IsCard> effect;

	public CardTest(Long number, String name, String description, CardType cardType, IsCost cost, Predicate<IsCard> condition, Consumer<IsCard> effect) {
		this.number = number;
		this.name = name;
		this.description = description;
		this.cardType = cardType;
		this.cost = cost;
		this.condition = condition;
		this.effect = effect;
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public Long getNumber() {
		return number;
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
	public CardType getCardType() {
		return cardType;
	}

	@Override
	public IsCost getCost() {
		return cost;
	}

	public void setCondition(Predicate<IsCard> condition) {
		this.condition = condition;
	}

	@Override
	public Predicate<IsCard> getCondition() {
		return condition;
	}

	@Override
	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	@Override
	public UUID getOwnerId() {
		return ownerId;
	}

	@Override
	public void setOwner(IsPlayer owner) {
		this.owner = owner;
	}

	@Override
	public IsPlayer getOwner() {
		return owner;
	}


	@Override
	public boolean hasOwner() {
		return IsCard.super.hasOwner();
	}

	@Override
	public Consumer<IsCard> getEffect() {
		return effect;
	}

}
