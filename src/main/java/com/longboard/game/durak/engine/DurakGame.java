package com.longboard.game.durak.engine;

import com.longboard.base.PlayerColor;
import com.longboard.engine.LogUtils;
import com.longboard.entity.IsPlayer;
import com.longboard.exception.InitialisationException;
import com.longboard.game.durak.card.DurakCardDeck;
import com.longboard.game.durak.card.PlayingCard36;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DurakGame {

	public static final int MIN_PLAYERS_COUNT = 2;
	public static final int MAX_PLAYERS_COUNT = 6;
	public static final int MAX_CARDS_COUNT = 36;
	public static final int CARDS_COUNT_IN_HAND_ON_START = 6;

	private DurakCardDeck deck;
	private PlayingCard36 trump;

	private List<IsPlayer<PlayingCard36>> activePlayers = new LinkedList<>();
	private Map<Integer, IsPlayer<PlayingCard36>> playersScore = new HashMap<>();
	private List<DurakBattle> battles = new LinkedList<>();

	/**
	 * Defined game data for provided amount of players.
	 * Initialise deck for game with 36 cards. Defines a trump card. Give {@value #CARDS_COUNT_IN_HAND_ON_START} cards to each player.
	 *
	 * @param playersCount should be not greater than {@value #MAX_PLAYERS_COUNT} and not less than {@value #MIN_PLAYERS_COUNT}
	 */
	public void initialiseGame(int playersCount) {
		if (playersCount < MIN_PLAYERS_COUNT || playersCount > MAX_PLAYERS_COUNT) {
			LogUtils.error("Wrong players count. Should be not less than " + MIN_PLAYERS_COUNT + " and not greater than " + MAX_PLAYERS_COUNT);
			throw new InitialisationException();
		}
		deck = DurakCardDeck.initialise();
		trump = deck.getTrump();
		for (int currentPlayer = 0; currentPlayer < playersCount; currentPlayer++) {
			List<PlayingCard36> playerHand = new ArrayList<>();
			IntStream.range(0, CARDS_COUNT_IN_HAND_ON_START).forEach(i -> playerHand.add(deck.draw()));
			DurakPlayer player = new DurakPlayer(currentPlayer + " player", PlayerColor.Green, playerHand);
			player.validate();
			this.activePlayers.add(player);
		}
	}

	/**
	 * Defines a player with the smallest trump card in hand. If there is no any trump card in players' hand, first player will be chosen randomly.
	 *
	 * @return player to attack first
	 */
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
			Integer minTrumpCardRankOfPlayer = player.getHandCards().stream().filter(card -> card.getSuit() == trump.getSuit())
					.map(card -> card.getRank().getValue()).min(Integer::compare).orElse(null);
			if (minTrumpCardRank == null || minTrumpCardRankOfPlayer != null && minTrumpCardRankOfPlayer < minTrumpCardRank) {
				minTrumpCardRank = minTrumpCardRankOfPlayer;
				playerToPlayFirst = player;
			}
		}
		return playerToPlayFirst;
	}

	/**
	 * Define an opponent for provided player. Should be at least {@value #MIN_PLAYERS_COUNT} active players in game.
	 *
	 * @param attacker provided player
	 * @return next player after {@param attacker}
	 */
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

	public IsPlayer<PlayingCard36> defineNextPlayerToAttack(DurakBattle endedBattle) {
		if (endedBattle.getAttacker().getId() == endedBattle.defineWinner().getId()) {
			return definePlayerToAttack(endedBattle.getDefender());
		} else {
			return endedBattle.getDefender();
		}
	}

	public void endGame() {

	}

	public DurakBattle startBattle(DurakBattle previousBattle, IsPlayer<PlayingCard36> attacker) {
		if (attacker == null) {
			LogUtils.error("Attacker player can't be null");
			throw new InitialisationException();
		}
		IsPlayer<PlayingCard36> defender = definePlayerToAttack(attacker);
		if (attacker.getId() == defender.getId()) {
			endGame();
			return null;
		}
		DurakBattle newBattle = new DurakBattle(attacker, defender, getTrump().getSuit(), previousBattle);
		//TODO check if currentPlayer is active
		if (previousBattle == null) {
			//first battle
			return newBattle;
		}
		battles.add(previousBattle);
		return newBattle;
	}

	public List<PlayingCard36> getDeck() {
		if (this.deck == null) {
			return new LinkedList<>();
		}
		return Collections.unmodifiableList(this.deck.getCards());
	}

	public PlayingCard36 getTrump() {
		return this.trump;
	}

	public List<IsPlayer<PlayingCard36>> getActivePlayers() {
		if (this.activePlayers == null) {
			return new LinkedList<>();
		}
		return Collections.unmodifiableList(this.activePlayers);
	}

	public void endBattle(DurakBattle battle) {
		if (battle.getAttacker().getId() == battle.defineWinner().getId()) {
			List<PlayingCard36> cardsToPutInHand = battle.defineCardsInBattle();
			cardsToPutInHand.forEach(card -> card.setOwnerId(battle.getDefender().getId()));
			battle.getDefender().addCardsToHand(cardsToPutInHand);
			if (deck.isEmpty() && CollectionUtils.isEmpty(battle.getAttacker().getHandCards())) {
				movePlayerToWinnerList(battle.getAttacker());
			} else {
				drawCards(battle.getAttacker());
			}
		} else {
			if (deck.isEmpty()) {
				if (CollectionUtils.isEmpty(battle.getAttacker().getHandCards())) {
					movePlayerToWinnerList(battle.getAttacker());
				}
				if (CollectionUtils.isEmpty(battle.getDefender().getHandCards())) {
					movePlayerToWinnerList(battle.getDefender());
				}
			} else {
				drawCards(battle.getAttacker());
				drawCards(battle.getDefender());
			}
		}
	}

	private void drawCards(IsPlayer<PlayingCard36> player) {
		List<PlayingCard36> cardsToDraw = IntStream.range(player.getHandCards().size(), CARDS_COUNT_IN_HAND_ON_START).mapToObj(i -> deck.draw())
				.filter(Objects::nonNull).collect(Collectors.toList());
		player.addCardsToHand(cardsToDraw);
	}

	private void movePlayerToWinnerList(IsPlayer<PlayingCard36> player) {
		activePlayers.remove(player);
		playersScore.put(playersScore.size() + 1, player);
	}

}
