package com.longboard.game.durak.display;

import com.longboard.entity.IsPlayer;
import com.longboard.game.durak.card.CardSuit;
import com.longboard.game.durak.card.PlayingCard36;
import com.longboard.game.durak.engine.DurakBattle;
import com.longboard.game.durak.engine.DurakGame;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DurakGameDisplay extends Frame {

	public int padding = 10;
	public int totalWidth = 1200;
	public int totalHeight = 800;

	private int cardWidth = 57 * 3 / 2;
	private int cardHeight = 89 * 3 / 2;

	private Panel topPlayerPanel;
	private Panel bottomPlayerPanel;
	private Panel middlePanel;

	private DurakGame game = new DurakGame();
	private IsPlayer<PlayingCard36> currentPlayer = null;
	private DurakBattle currentBattle = null;
	private PlayingCard36 cardToBeat = null;

	DurakGameDisplay() {
		super.setSize(totalWidth + 2 * padding, totalHeight + 4 * padding);

		addTopPanel();
		addMiddlePanel();
		addBottomPanel();

		add(new Panel());

		super.setVisible(true);
		super.setResizable(false);
		super.setLayout(null);
	}

	private void addTopPanel() {
		topPlayerPanel = new Panel();
		topPlayerPanel.setBounds(padding, padding, totalWidth, totalHeight / 8 * 3);
		topPlayerPanel.setBackground(new Color(204, 176, 137));
		topPlayerPanel.setVisible(true);

		add(topPlayerPanel);
	}

	private void addMiddlePanel() {
		middlePanel = new Panel();
		middlePanel.setBounds(padding, totalHeight / 8 * 3 + 2 * padding, totalWidth, totalHeight / 8 * 2);
		middlePanel.setBackground(new Color(172, 128, 191));

		Label selectPlayerLabel = new Label("Please select number of players");
		selectPlayerLabel.setBounds(padding * 2, padding * 2, totalWidth / 2, padding * 2);
		middlePanel.add(selectPlayerLabel);

		JComboBox<Integer> playerCountBox = new JComboBox<>();
		playerCountBox.addItem(2);
		playerCountBox.addItem(3);
		playerCountBox.addItem(4);
		playerCountBox.addItem(5);
		playerCountBox.addItem(6);
		playerCountBox.setBounds(padding * 3, padding * 2, padding * 5, padding * 2);
		middlePanel.add(playerCountBox);

		Button confirmPlayersCountAndStartGame = new Button("Confirm");
		confirmPlayersCountAndStartGame.setBounds(padding, padding * 2, padding * 5, padding * 2);
		confirmPlayersCountAndStartGame.addActionListener(e -> startGame(playerCountBox.getSelectedItem()));
		middlePanel.add(confirmPlayersCountAndStartGame);

		middlePanel.setVisible(true);
		add(middlePanel);
	}

	private void addBottomPanel() {
		bottomPlayerPanel = new Panel();
		bottomPlayerPanel.setBounds(padding, totalHeight / 8 * 5 + 3 * padding, totalWidth, totalHeight / 8 * 3 - padding);
		bottomPlayerPanel.setBackground(new Color(142, 204, 137));

		bottomPlayerPanel.setVisible(true);
		add(bottomPlayerPanel);
	}

	private void startGame(Object selectedItem) {
		int playersCount = (int) selectedItem;
		clearBoard();
		prepareBoard(playersCount);
	}

	private void prepareBoard(int playersCount) {
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
		refreshBoard();
	}

	//TODO implement AI for battle
	private void autoBattle(DurakBattle battle) {
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

	private void currentPlayerPlayCard(PlayingCard36 card) {
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
			super.setTitle(e.getMessage());
		}
		refreshBoard();
	}

	private void refreshBoard() {
		clearBoard();
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
		int width = padding * 20;
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

		if (currentBattle == null && MapUtils.isNotEmpty(game.endGame())) {
			middlePanel.removeAll();

			Panel playersScore = new Panel();
			playersScore.setBounds(cardWidth + padding * 2, padding * 2, cardWidth * 2, cardHeight + 2 * padding);
			Label playersScoreLabel = new Label();
			playersScoreLabel.setText(
					game.endGame().entrySet().stream().map(entry -> entry.getKey() + ". " + entry.getValue().getName()).collect(Collectors.joining("\n")));
			playersScoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
			playersScoreLabel.setBounds(padding, padding, cardWidth * 2, cardHeight + 2 * padding);
			playersScoreLabel.setForeground(new Color(3, 33, 51));
			playersScore.add(playersScoreLabel);
			playersScore.setBackground(new Color(22, 93, 105));

			middlePanel.add(playersScore);
		}
	}

	private void displayPlayersHand() {
		List<PlayingCard36> cardsInHand = currentPlayer.getHandCards().stream().sorted(Comparator.comparing(card -> card.getRank().getValue()))
				.sorted(Comparator.comparing(card -> card.getSuit().getValue())).collect(Collectors.toList());
		IntStream.range(0, cardsInHand.size()).forEach(i -> {
			PlayingCard36 card = cardsInHand.get(i);
			Button cardButton = new Button();
			cardButton.setName(card.getId().toString());
			cardButton.setBounds(padding + cardWidth * i, padding, cardWidth, cardHeight);
			cardButton.setLabel(card.getName());
			cardButton.setFont(new Font("Arial", Font.PLAIN, 32));
			if (card.getSuit() == CardSuit.Heart || card.getSuit() == CardSuit.Diamond) {
				cardButton.setForeground(Color.RED);
			}
			cardButton.addActionListener(listener -> currentPlayerPlayCard(card));
			bottomPlayerPanel.add(cardButton);
		});
		Button endTurn = new Button();
		endTurn.setBounds(padding, cardHeight + 5 * padding, cardWidth, 5 * padding);
		endTurn.setLabel("End turn");
		endTurn.addActionListener(listener -> endCurrentTurn());
		bottomPlayerPanel.add(endTurn);
	}

	private void endCurrentTurn() {
		try {//TODO here define winner
			if (currentBattle.getAttacker().getId() == currentPlayer.getId()) {
				game.endBattle(currentBattle);
				currentBattle = game.startBattle(currentBattle, game.defineNextPlayerToAttack(currentBattle));
			} else if (currentBattle.getDefender().getId() == currentPlayer.getId()) {
				game.endBattle(currentBattle);
				currentBattle = game.startBattle(currentBattle, game.defineNextPlayerToAttack(currentBattle));
			}
		} catch (Exception e) {
			super.setTitle(e.getMessage());
		}
		refreshBoard();
	}

	private void displayDeckWithCounter(Button trumpCard) {
		if (game.getDeck().size() > 0) {
			Panel deckCounter = new Panel();
			deckCounter.setBounds(width + padding * 3, padding * 6, width - 2 * padding, height / 2 - 4 * padding);
			Label counter = new Label();
			counter.setText(String.valueOf(game.getDeck().size()));
			counter.setFont(new Font("Arial", Font.PLAIN, 48));
			counter.setAlignment(Label.CENTER);
			counter.setBounds(padding, 4 * padding, width - 4 * padding, 4 * padding);
			counter.setForeground(new Color(44, 10, 13));
			deckCounter.add(counter);
			deckCounter.setBackground(new Color(170, 83, 91));
			middlePanel.add(deckCounter);

			Panel discardCounter = new Panel();
			discardCounter.setBounds(cardWidth * 3 / 2 + padding * 3, cardHeight / 2 + 3 * padding, cardWidth * 3 / 2 - 2 * padding, cardHeight / 2 + padding);
			Label discardCounterLabel = new Label();
			discardCounterLabel.setText(String.valueOf(game.getDiscard().size()));
			discardCounterLabel.setFont(new Font("Arial", Font.PLAIN, 48));
			discardCounterLabel.setAlignment(Label.CENTER);
			discardCounterLabel.setBounds(padding, 2 * padding, cardWidth, 4 * padding);
			discardCounterLabel.setForeground(new Color(44, 10, 13));
			discardCounter.add(discardCounterLabel);
			discardCounter.setBackground(new Color(170, 83, 91));

			middlePanel.add(discardCounter);
		} else {
			trumpCard.setEnabled(false);
		}
	}

	private Button displayTrumpCard() {
		Button trumpCard = new Button();
		trumpCard.setBounds(padding * 2, padding * 4, cardWidth, cardHeight);
		trumpCard.setLabel(game.getTrump().getName());
		trumpCard.setFont(new Font("Arial", Font.PLAIN, 38));
		if (game.getTrump().getSuit() == CardSuit.Heart || game.getTrump().getSuit() == CardSuit.Diamond) {
			trumpCard.setForeground(Color.RED);
		}
		middlePanel.add(trumpCard);
		return trumpCard;
	}

	private void displayCardToBeat() {
		Button cardToBeatButton = new Button();
		cardToBeatButton.setBounds(3 * cardWidth + padding * 6, padding * 4, cardWidth, cardHeight);
		cardToBeatButton.setLabel(cardToBeat.getName());
		cardToBeatButton.setFont(new Font("Arial", Font.PLAIN, 38));
		if (cardToBeat.getSuit() == CardSuit.Heart || cardToBeat.getSuit() == CardSuit.Diamond) {
			cardToBeatButton.setForeground(Color.RED);
		}
		middlePanel.add(cardToBeatButton);
	}

	private void displayBattlingCards() {
		if (currentBattle == null || currentBattle.getBattlingCards() == null || MapUtils.isEmpty(currentBattle.getBattlingCards().getBattlingCards())) {
			return;
		}
		int width = cardWidth + 4 * padding;
		int position = 0;
		for (Map.Entry<PlayingCard36, PlayingCard36> cardPair : currentBattle.getBattlingCards().getBattlingCards().entrySet()) {
			if (cardPair.getValue() != null) {
				PlayingCard36 firstCardToBeat = cardPair.getKey();
				Button firstCardToBeatButton = new Button();
				firstCardToBeatButton.setBounds(3 * width + cardWidth + (cardWidth / 2 + padding) * position, padding * 4, cardWidth / 2, cardHeight / 2);
				firstCardToBeatButton.setLabel(firstCardToBeat.getName());
				firstCardToBeatButton.setFont(new Font("Arial", Font.PLAIN, 18));
				if (firstCardToBeat.getSuit() == CardSuit.Heart || firstCardToBeat.getSuit() == CardSuit.Diamond) {
					firstCardToBeatButton.setForeground(Color.RED);
				}
				middlePanel.add(firstCardToBeatButton);

				PlayingCard36 secondCardToDefend = cardPair.getValue();
				Button secondCardToDefendButton = new Button();
				secondCardToDefendButton.setBounds(3 * width + cardWidth + (cardWidth / 2 + padding) * position, padding * 5 + cardHeight / 2, cardWidth / 2,
						cardHeight / 2);
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

	private void displayOpponentArea(List<IsPlayer<PlayingCard36>> opponents, int width, int height, int i) {
		IsPlayer<PlayingCard36> opponent = opponents.get(i);
		Panel playerPanel = new Panel();
		playerPanel.setName(opponent.getId().toString());
		playerPanel.setBounds(i * width + padding, padding * 3, width - padding, height - 4 * padding);
		playerPanel.setBackground(Color.CYAN);

		Label opponentName = new Label(opponent.getName());
		opponentName.setBounds(0, padding * 2, width - 2 * padding, padding * 4);
		opponentName.setAlignment(Label.CENTER);
		playerPanel.add(opponentName);

		Button buttonCardsCount = new Button();
		buttonCardsCount.setBounds(padding, padding * 8, width - 4 * padding, height / 2);
		buttonCardsCount.setLabel(String.valueOf(opponent.getHandCards().size()));
		buttonCardsCount.setFont(new Font("Arial", Font.PLAIN, 40));
		buttonCardsCount.setForeground(Color.GRAY);
		playerPanel.add(buttonCardsCount);

		topPlayerPanel.add(playerPanel);
	}

	private void clearBoard() {
		topPlayerPanel.removeAll();
		middlePanel.removeAll();
		bottomPlayerPanel.removeAll();
	}

	public static void main(String[] args) {
		new DurakGameDisplay();
	}

}
