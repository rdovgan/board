package com.longboard.game.durak.engine;

import com.longboard.entity.IsPlayer;
import com.longboard.game.durak.card.CardBattle;
import com.longboard.game.durak.card.CardRank;
import com.longboard.game.durak.card.CardSuit;
import com.longboard.game.durak.card.PlayingCard36;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DurakBattle {

	private IsPlayer<PlayingCard36> attacker;
	private IsPlayer<PlayingCard36> defender;
	private CardBattle battlingCards;
	private CardSuit trump;

	public DurakBattle(IsPlayer<PlayingCard36> attacker, IsPlayer<PlayingCard36> defender, CardSuit trump) {
		this.attacker = attacker;
		this.defender = defender;
		this.battlingCards = new CardBattle();
		this.trump = trump;
	}

	public List<PlayingCard36> getCardsForAttack() {
		Set<CardRank> battlingCardsRank = new HashSet<>();
		if (CollectionUtils.isEmpty(battlingCardsRank)) {
			return attacker.getHandCards();
		}
		return attacker.getHandCards().stream().filter(card -> battlingCardsRank.contains(card.getRank())).collect(Collectors.toList());
	}
	
	public List<PlayingCard36> getCardsForDefend(PlayingCard36 attackCard) {
		return attacker.getHandCards().stream().filter(card -> DurakCardEngine.canBeat(card, attackCard, trump)).collect(Collectors.toList());
	}

	/**
	 * Defines a player who won a current battle based on battling cards. 
	 * If there is any attacker card in map that didn't match with defender one, attacker player won. In other case, defender won the battle
	 *
	 * @return a player who won the battle
	 */
	public IsPlayer<PlayingCard36> endBattle() {
		if (battlingCards.getBattlingCards().entrySet().stream().anyMatch(entry -> entry.getValue() == null)) {
			return attacker;
		}
		return defender;
	}

}
