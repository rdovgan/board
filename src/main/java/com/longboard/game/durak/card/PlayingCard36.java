package com.longboard.game.durak.card;

import com.longboard.base.CardType;
import com.longboard.entity.IsPlayer;
import com.longboard.entity.card.IsCard;
import com.longboard.entity.card.IsCost;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PlayingCard36 implements IsCard {

	private final UUID id = UUID.randomUUID();
	private final CardSuit suit;
	private final CardRank rank;
	private final Long number;
	private final String name;
	private UUID ownerId;

	public PlayingCard36(CardSuit suit, CardRank rank) {
		this.suit = suit;
		this.rank = rank;
		this.number = (long) (suit.getValue() + rank.getValue());
		this.name = rank.getSymbol() + suit.getSymbol();
	}

	public CardSuit getSuit() {
		return suit;
	}

	public CardRank getRank() {
		return rank;
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
		return name;
	}

	@Override
	public CardType getCardType() {
		return null;
	}

	@Override
	public IsCost getCost() {
		return null;
	}

	@Override
	public Predicate<IsCard> getCondition() {
		return null;
	}

	@Override
	public void setOwner(IsPlayer<IsCard> player) {

	}

	@Override
	public IsPlayer<IsCard> getOwner() {
		return null;
	}

	@Override
	public Consumer<IsCard> getEffect() {
		return null;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}
}
