package com.longboard.entity;

import com.longboard.base.CardType;

import java.util.function.Predicate;

public class CardTest implements IsCard {

	private Long number;
	private String name;
	private String description;
	private CardType cardType;
	private IsCost cost;
	private Predicate<IsCard> condition;
	private IsPlayer owner;

	public CardTest(Long number, String name, String description, CardType cardType, IsCost cost, Predicate<IsCard> condition) {
		this.number = number;
		this.name = name;
		this.description = description;
		this.cardType = cardType;
		this.cost = cost;
		this.condition = condition;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	@Override
	public Long getNumber() {
		return number;
	}

	public void setName(String name) {
		this.name = name;
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
	public void play(Predicate<IsCard> effect) {
		//TODO
	}
}
