package com.longboard.game.durak.display;

import com.longboard.engine.LogUtils;
import com.longboard.entity.IsPlayer;
import com.longboard.game.durak.card.CardSuit;
import com.longboard.game.durak.card.PlayingCard36;
import com.longboard.game.durak.engine.DurakBattle;
import com.longboard.game.durak.engine.DurakGame;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DurakGameDisplay extends Frame {

	public static final int PADDING = 10;
	public static final int TOTAL_WIDTH = 1200;
	public static final int TOTAL_HEIGHT = 800;

	public static final int CARD_WIDTH = 57 * 3 / 2;
	public static final int CARD_HEIGHT = 89 * 3 / 2;

	private static Panel topPlayerPanel;
	private static Panel bottomPlayerPanel;
	private static Panel middlePanel;

	private static DurakGame game = new DurakGame();
	private static IsPlayer<PlayingCard36> currentPlayer = null;
	private static DurakBattle currentBattle = null;
	private static PlayingCard36 cardToBeat = null;

	private static final DisplayUtils displayUtils = new DisplayUtils();

	DurakGameDisplay() {
		super.setSize(TOTAL_WIDTH + 2 * PADDING, TOTAL_HEIGHT + 4 * PADDING);

		add(topPlayerPanel = displayUtils.defineTopPanel());
		add(middlePanel = displayUtils.defineMiddlePanel());
		add(bottomPlayerPanel = displayUtils.defineBottomPanel());

		add(new Panel());

		super.setVisible(true);
		super.setResizable(false);
		super.setLayout(null);
	}

	public static void startGame(Object selectedItem) {
		int playersCount = (int) selectedItem;
		displayUtils.clearBoards(topPlayerPanel, middlePanel, bottomPlayerPanel);
		prepareBoard(playersCount);
	}

	private static void prepareBoard(int playersCount) {
		game.initialiseGame(playersCount);
		currentPlayer = game.getActivePlayers().get(0);
		currentBattle = game.startBattle(null, game.defineFirstPlayer());
		if (currentBattle.getDefender().getId() == currentPlayer.getId()) {
			//attack current player with card
			cardToBeat = currentBattle.getCardsForAttack().stream().findAny().orElse(null);
			if (cardToBeat != null) {
				currentBattle.playCardToAttack(cardToBeat);
			}
		}
		refreshBoard(topPlayerPanel, middlePanel, bottomPlayerPanel);
	}

	//TODO implement AI for battle
	private static void autoBattle(DurakBattle battle) {
		while (CollectionUtils.isNotEmpty(battle.getCardsForAttack())) {
			//battle
			PlayingCard36 cardToAttack = battle.getCardsForAttack().stream().findAny().orElse(null);
			if (cardToAttack == null) {
				break;
			}
			battle.playCardToAttack(cardToAttack);
			PlayingCard36 cardToDefend = battle.getCardsForDefend(cardToAttack).stream().findFirst().orElse(null);
			if (cardToDefend == null) {
				break;
			}
			battle.playCardToDefend(cardToDefend, cardToAttack);
		}
		game.endBattle(battle);
		currentBattle = game.startBattle(battle, game.defineNextPlayerToAttack(battle));
	}

	private static void currentPlayerPlayCard(PlayingCard36 card) {
		try {
			if (currentBattle.getAttacker().getId() == currentPlayer.getId()) {
				//player attack
				currentBattle.playCardToAttack(card);
				//bot defend
				PlayingCard36 cardToDefend = currentBattle.getCardsForDefend(card).stream().findFirst().orElse(null);
				if (cardToDefend == null) {
					game.endBattle(currentBattle);
					currentBattle = game.startBattle(currentBattle, game.defineNextPlayerToAttack(currentBattle));
				} else {
					currentBattle.playCardToDefend(cardToDefend, card);
				}
			}
			if (currentBattle != null && currentBattle.getDefender().getId() == currentPlayer.getId()) {
				//player defend
				currentBattle.playCardToDefend(card, cardToBeat);
				//bot attack current player with new card
				cardToBeat = currentBattle.getCardsForAttack().stream().findAny().orElse(null);
				if (cardToBeat != null && currentBattle.getBattlingCards().getBattlingCards().size() < DurakGame.CARDS_COUNT_IN_HAND_ON_START) {
					currentBattle.playCardToAttack(cardToBeat);
				} else {
					game.endBattle(currentBattle);
					currentBattle = game.startBattle(currentBattle, game.defineNextPlayerToAttack(currentBattle));
				}
			}
		} catch (Exception e) {
			LogUtils.error(e.getMessage());
		}
		refreshBoard(topPlayerPanel, middlePanel, bottomPlayerPanel);
	}

	private static void refreshBoard(Panel topPlayerPanel, Panel middlePanel, Panel bottomPlayerPanel) {
		displayUtils.clearBoards(topPlayerPanel, middlePanel, bottomPlayerPanel);
		//auto battle
		if (currentBattle != null) {
			while (currentBattle.getDefender().getId() != currentPlayer.getId() && currentBattle.getAttacker().getId() != currentPlayer.getId()) {
				autoBattle(currentBattle);
			}
		}

		if (currentBattle != null && currentBattle.getDefender().getId() == currentPlayer.getId() && currentBattle.defineCardsInBattle().size() == 0) {
			cardToBeat = currentBattle.getCardsForAttack().stream().findAny().orElse(null);
			if (cardToBeat != null) {
				currentBattle.playCardToAttack(cardToBeat);
			}
		}

		List<IsPlayer<PlayingCard36>> opponents = game.getActivePlayers().stream().filter(player -> player.getId() != currentPlayer.getId())
				.collect(Collectors.toList());
		int width = PADDING * 20;
		int height = topPlayerPanel.getHeight();
		//Draw opponents area on top panel
		IntStream.range(0, opponents.size()).forEach(i -> displayOpponentArea(opponents, width, height, i));

		Button trumpCard = displayTrumpCard();

		if (cardToBeat != null) {
			displayCardToBeat();
		}

		displayBattlingCards();

		displayDeckWithCounter(trumpCard);

		displayPlayersHand();

		displayUtils.definePlayerScore(middlePanel, game, currentBattle);
	}

	private static void displayPlayersHand() {
		List<PlayingCard36> cardsInHand = currentPlayer.getHandCards().stream().sorted(Comparator.comparing(card -> card.getRank().getValue()))
				.sorted(Comparator.comparing(card -> card.getSuit().getValue())).collect(Collectors.toList());
		IntStream.range(0, cardsInHand.size()).forEach(i -> {
			PlayingCard36 card = cardsInHand.get(i);
			Button cardButton = new Button();
			cardButton.setName(card.getId().toString());
			cardButton.setBounds(PADDING + CARD_WIDTH * i, PADDING, CARD_WIDTH, CARD_HEIGHT);
			cardButton.setLabel(card.getName());
			cardButton.setFont(new Font("Arial", Font.PLAIN, 32));
			if (card.getSuit() == CardSuit.Heart || card.getSuit() == CardSuit.Diamond) {
				cardButton.setForeground(Color.RED);
			}
			cardButton.addActionListener(listener -> {
				currentPlayerPlayCard(card);
				refreshBoard(topPlayerPanel, middlePanel, bottomPlayerPanel);
			});
			bottomPlayerPanel.add(cardButton);
		});
		Button endTurn = new Button();
		endTurn.setBounds(PADDING, CARD_HEIGHT + 5 * PADDING, CARD_WIDTH, 5 * PADDING);
		endTurn.setLabel("End turn");
		endTurn.addActionListener(listener -> {
			currentBattle = displayUtils.endCurrentTurn(game, currentBattle, currentPlayer);
			refreshBoard(topPlayerPanel, middlePanel, bottomPlayerPanel);
		});
		bottomPlayerPanel.add(endTurn);
	}

	private static void displayDeckWithCounter(Button trumpCard) {
		if (game.getDeck().size() > 0) {

			Panel deckCounter = new Panel();
			deckCounter.setBounds(CARD_WIDTH * 3 / 2 + PADDING * 3, PADDING * 2, CARD_WIDTH * 3 / 2 - 2 * PADDING, CARD_HEIGHT / 2 + PADDING);
			Label counterLabel = new Label();
			counterLabel.setText(String.valueOf(game.getDeck().size()));
			counterLabel.setFont(new Font("Arial", Font.PLAIN, 48));
			counterLabel.setAlignment(Label.CENTER);
			counterLabel.setBounds(PADDING, 2 * PADDING, CARD_WIDTH, 4 * PADDING);
			counterLabel.setForeground(new Color(10, 51, 3));
			deckCounter.add(counterLabel);
			deckCounter.setBackground(new Color(76, 105, 22));

			middlePanel.add(deckCounter);

			Panel discardCounter = new Panel();
			discardCounter.setBounds(CARD_WIDTH * 3 / 2 + PADDING * 3, CARD_HEIGHT / 2 + 3 * PADDING, CARD_WIDTH * 3 / 2 - 2 * PADDING,
					CARD_HEIGHT / 2 + PADDING);
			Label discardCounterLabel = new Label();
			discardCounterLabel.setText(String.valueOf(game.getDiscard().size()));
			discardCounterLabel.setFont(new Font("Arial", Font.PLAIN, 48));
			discardCounterLabel.setAlignment(Label.CENTER);
			discardCounterLabel.setBounds(PADDING, 2 * PADDING, CARD_WIDTH, 4 * PADDING);
			discardCounterLabel.setForeground(new Color(44, 10, 13));
			discardCounter.add(discardCounterLabel);
			discardCounter.setBackground(new Color(170, 83, 91));

			middlePanel.add(discardCounter);
		} else {
			trumpCard.setEnabled(false);
		}
	}

	private static Button displayTrumpCard() {
		Button trumpCard = new Button();
		trumpCard.setBounds(PADDING * 2, PADDING * 4, CARD_WIDTH, CARD_HEIGHT);
		trumpCard.setLabel(game.getTrump().getName());
		trumpCard.setFont(new Font("Arial", Font.PLAIN, 38));
		if (game.getTrump().getSuit() == CardSuit.Heart || game.getTrump().getSuit() == CardSuit.Diamond) {
			trumpCard.setForeground(Color.RED);
		}
		middlePanel.add(trumpCard);
		return trumpCard;
	}

	private static void displayCardToBeat() {
		Button cardToBeatButton = new Button();
		cardToBeatButton.setBounds(3 * CARD_WIDTH + PADDING * 6, PADDING * 4, CARD_WIDTH, CARD_HEIGHT);
		cardToBeatButton.setLabel(cardToBeat.getName());
		cardToBeatButton.setFont(new Font("Arial", Font.PLAIN, 38));
		if (cardToBeat.getSuit() == CardSuit.Heart || cardToBeat.getSuit() == CardSuit.Diamond) {
			cardToBeatButton.setForeground(Color.RED);
		}
		middlePanel.add(cardToBeatButton);
	}

	private static void displayBattlingCards() {
		if (currentBattle == null || currentBattle.getBattlingCards() == null || MapUtils.isEmpty(currentBattle.getBattlingCards().getBattlingCards())) {
			return;
		}
		int width = CARD_WIDTH + 4 * PADDING;
		int position = 0;
		for (Map.Entry<PlayingCard36, PlayingCard36> cardPair : currentBattle.getBattlingCards().getBattlingCards().entrySet()) {
			if (cardPair.getValue() != null) {
				PlayingCard36 firstCardToBeat = cardPair.getKey();
				Button firstCardToBeatButton = new Button();
				firstCardToBeatButton.setBounds(3 * width + CARD_WIDTH + (CARD_WIDTH / 2 + PADDING) * position, PADDING * 4, CARD_WIDTH / 2, CARD_HEIGHT / 2);
				firstCardToBeatButton.setLabel(firstCardToBeat.getName());
				firstCardToBeatButton.setFont(new Font("Arial", Font.PLAIN, 18));
				if (firstCardToBeat.getSuit() == CardSuit.Heart || firstCardToBeat.getSuit() == CardSuit.Diamond) {
					firstCardToBeatButton.setForeground(Color.RED);
				}
				middlePanel.add(firstCardToBeatButton);

				PlayingCard36 secondCardToDefend = cardPair.getValue();
				Button secondCardToDefendButton = new Button();
				secondCardToDefendButton.setBounds(3 * width + CARD_WIDTH + (CARD_WIDTH / 2 + PADDING) * position, PADDING * 5 + CARD_HEIGHT / 2,
						CARD_WIDTH / 2, CARD_HEIGHT / 2);
				secondCardToDefendButton.setLabel(secondCardToDefend.getName());
				secondCardToDefendButton.setFont(new Font("Arial", Font.PLAIN, 18));
				if (secondCardToDefend.getSuit() == CardSuit.Heart || secondCardToDefend.getSuit() == CardSuit.Diamond) {
					secondCardToDefendButton.setForeground(Color.RED);
				}
				middlePanel.add(secondCardToDefendButton);

				position++;
			}
		}
	}

	private static void displayOpponentArea(List<IsPlayer<PlayingCard36>> opponents, int width, int height, int i) {
		IsPlayer<PlayingCard36> opponent = opponents.get(i);
		Panel playerPanel = new Panel();
		playerPanel.setName(opponent.getId().toString());
		playerPanel.setBounds(i * width + PADDING, PADDING * 3, width - PADDING, height - 4 * PADDING);
		playerPanel.setBackground(Color.CYAN);

		Label opponentName = new Label(opponent.getName());
		opponentName.setBounds(0, PADDING * 2, width - 2 * PADDING, PADDING * 4);
		opponentName.setAlignment(Label.CENTER);
		playerPanel.add(opponentName);

		Button buttonCardsCount = new Button();
		buttonCardsCount.setBounds(PADDING, PADDING * 8, width - 4 * PADDING, height / 2);
		buttonCardsCount.setLabel(String.valueOf(opponent.getHandCards().size()));
		buttonCardsCount.setFont(new Font("Arial", Font.PLAIN, 40));
		buttonCardsCount.setForeground(Color.GRAY);
		playerPanel.add(buttonCardsCount);

		topPlayerPanel.add(playerPanel);
	}

	public static void main(String[] args) {
		new DurakGameDisplay();
	}

}
