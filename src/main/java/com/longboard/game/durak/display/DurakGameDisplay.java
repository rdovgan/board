package com.longboard.game.durak.display;

import com.longboard.entity.IsPlayer;
import com.longboard.game.durak.card.CardSuit;
import com.longboard.game.durak.card.PlayingCard36;
import com.longboard.game.durak.engine.DurakGame;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DurakGameDisplay extends Frame {

	public int padding = 10;
	public int totalWidth = 800;
	public int totalHeight = 800;

	private int cardWidth = 57 * 3 / 2;
	private int cardHeight = 89 * 3 / 2;

	private Panel topPlayerPanel;
	private Panel bottomPlayerPanel;
	private Panel middlePanel;

	private DurakGame game = new DurakGame();
	private IsPlayer<PlayingCard36> currentPlayer = null;

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
		confirmPlayersCountAndStartGame.setBounds(padding, +padding * 2, padding * 5, padding * 2);
		confirmPlayersCountAndStartGame.addActionListener(e -> {
			startGame(playerCountBox.getSelectedItem());
		});
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
		refreshBoard();
	}

	private void refreshBoard() {
		List<IsPlayer<PlayingCard36>> opponents = game.getActivePlayers().stream().filter(player -> player.getId() != currentPlayer.getId())
				.collect(Collectors.toList());
		int width = topPlayerPanel.getWidth() / 5;
		int height = topPlayerPanel.getHeight();
		//Draw opponents area on top panel
		IntStream.range(0, opponents.size()).forEach(i -> drawOpponentArea(opponents, width, height, i));

		Button trumpCard = drawTrumpCard();

		drawDeckWithCounter(width, height, trumpCard);

		drawPlayersHand();
	}

	private void drawPlayersHand() {
		List<PlayingCard36> cardsInHand = currentPlayer.getHandCards().stream()
				.sorted(Comparator.comparing(card -> card.getRank().getValue())).sorted(Comparator.comparing(card -> card.getSuit().getValue())).collect(Collectors.toList());
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
			bottomPlayerPanel.add(cardButton);
		});
	}

	private void drawDeckWithCounter(int width, int height, Button trumpCard) {
		if (game.getDeck().size() > 0) {
			Button deckButton = new Button();
			deckButton.setBounds(width + padding * 6, padding * 6, width - 2 * padding, height / 2 - 4 * padding);
			deckButton.setLabel(String.valueOf(game.getDeck().size()));
			deckButton.setFont(new Font("Arial", Font.PLAIN, 48));
			deckButton.setBackground(new Color(170, 83, 91));
			deckButton.setForeground(Color.GREEN);
			middlePanel.add(deckButton);
		} else {
			trumpCard.setEnabled(false);
		}
	}

	private Button drawTrumpCard() {
		Button trumpCard = new Button();
		trumpCard.setBounds(padding * 8, padding * 4, cardWidth, cardHeight);
		trumpCard.setLabel(game.getTrump().getName());
		trumpCard.setFont(new Font("Arial", Font.PLAIN, 38));
		if (game.getTrump().getSuit() == CardSuit.Heart || game.getTrump().getSuit() == CardSuit.Diamond) {
			trumpCard.setForeground(Color.RED);
		}
		middlePanel.add(trumpCard);
		return trumpCard;
	}

	private void drawOpponentArea(List<IsPlayer<PlayingCard36>> opponents, int width, int height, int i) {
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
