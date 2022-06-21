package com.longboard.game.durak.engine;

import com.longboard.base.PlayerColor;
import com.longboard.engine.LogUtils;
import com.longboard.entity.IsPlayer;
import com.longboard.game.durak.card.DurakCardDeck;
import com.longboard.game.durak.card.PlayingCard36;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class DurakGame {

	private final int MIN_PLAYERS_COUNT = 2;
	private final int MAX_PLAYERS_COUNT = 6;

	private final int CARDS_COUNT_IN_HAND_ON_START = 6;

	private DurakCardDeck deck;
	private PlayingCard36 trump;

	private List<IsPlayer<PlayingCard36>> activePlayers = new LinkedList<>();
	private Map<Integer, IsPlayer<PlayingCard36>> playersScore = new HashMap<>();

	public void initialiseGame(int playersCount) {
		if (playersCount < MIN_PLAYERS_COUNT || playersCount > MAX_PLAYERS_COUNT) {
			LogUtils.error("Wrong players count. Should be not less than " + MIN_PLAYERS_COUNT + " and not greater than " + MAX_PLAYERS_COUNT);
			return;
		}
		deck = DurakCardDeck.initialise();
		trump = deck.getTrump();
		for (int currentPlayer = 0; currentPlayer < playersCount; currentPlayer++) {
			List<PlayingCard36> playerHand = new ArrayList<>();
			IntStream.of(CARDS_COUNT_IN_HAND_ON_START).forEach(i -> playerHand.add(deck.draw()));
			DurakPlayer player = new DurakPlayer(currentPlayer + " player", PlayerColor.Green, playerHand);
			player.validate();
			this.activePlayers.add(player);
		}
	}

	public IsPlayer<PlayingCard36> defineFirstPlayer() {
		if (CollectionUtils.isEmpty(activePlayers)) {
			LogUtils.error("Players initialisation is wrong");
			return null;
		}
		Integer minTrumpCardRank = null;
		IsPlayer<PlayingCard36> playerToPlayFirst = null;
		if (trump == null) {
			LogUtils.error("Trump card initialisation is wrong");
		}
		for (IsPlayer<PlayingCard36> player : activePlayers) {
			Integer minTrumpCardRankOfPlayer = player.getHandCards().stream().filter(card -> card.getSuit() == trump.getSuit()).map(card -> card.getRank().getValue()).min(Integer::compare).orElse(null);
			if (minTrumpCardRank == null || minTrumpCardRankOfPlayer != null && minTrumpCardRankOfPlayer < minTrumpCardRank) {
				minTrumpCardRank = minTrumpCardRankOfPlayer;
				playerToPlayFirst = player;
			}
		}
		return playerToPlayFirst;
	}

	public IsPlayer<PlayingCard36> definePlayerToAttack(IsPlayer<PlayingCard36> attacker) {
		if (CollectionUtils.isEmpty(this.activePlayers) || this.activePlayers.size() < MIN_PLAYERS_COUNT) {
			LogUtils.error("Wrong players count. Game cannot be processed");
			return null;
		}
		Iterator<IsPlayer<PlayingCard36>> iterator = this.activePlayers.iterator();
		while (iterator.hasNext()) {
			if (iterator.next() == attacker) {
				if (iterator.hasNext()) {
					return iterator.next();
				} else {
					return this.activePlayers.stream().findFirst().orElse(null);
				}
			}
		}
		LogUtils.error("Couldn't find attacker in active pool of players");
		return null;
	}

	private void defineWinner() {

	}

	private void endGame() {

	}



}
