package com.longboard.game.durak.display;

import javax.swing.*;
import java.awt.*;

public class DurakGameDisplay extends Frame {

	public int padding = 10;
	public int totalWidth = 800;
	public int totalHeight = 800;
	private Panel topPlayerPanel;
	private Panel bottomPlayerPanel;
	private Panel middlePanel;

	private String[] optionsToChoose = { "Apple", "Orange", "Banana", "Pineapple", "None of the listed" };

	DurakGameDisplay() {

		super.setBounds(padding, padding, totalWidth + 2 * padding, totalHeight + 4 * padding);
		super.setBackground(Color.LIGHT_GRAY);

		addTopPanel();

		addMiddlePanel();

		addBottomPanel();

		super.setVisible(true);
		super.setResizable(false);
		super.setLayout(null);
	}

	private void addTopPanel() {
		topPlayerPanel = new Panel();
		topPlayerPanel.setBounds(padding, padding, totalWidth, totalHeight / 8 * 3);
		topPlayerPanel.setBackground(new Color(204, 176, 137));

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
		playerCountBox.setBounds(padding * 3 + totalWidth / 2, padding * 2, padding * 5, padding * 2);
		middlePanel.add(playerCountBox);

		Button confirmPlayersCountAndStartGame = new Button("Confirm");
		confirmPlayersCountAndStartGame.addActionListener(e -> {
			startGame(playerCountBox.getSelectedItem());
		});

		add(middlePanel);
	}

	private void startGame(Object selectedItem) {
		int playersCount = (int) selectedItem;
		clearBoard();
		prepareBoard(playersCount);
	}

	private void prepareBoard(int playersCount) {
	}

	private void clearBoard() {
		middlePanel.removeAll();
	}

	private void addBottomPanel() {
		bottomPlayerPanel = new Panel();
		bottomPlayerPanel.setBounds(padding, totalHeight / 8 * 5 + 3 * padding, totalWidth, totalHeight / 8 * 3);
		bottomPlayerPanel.setBackground(new Color(142, 204, 137));

		add(bottomPlayerPanel);
	}

	public static void main(String[] args) {
		new DurakGameDisplay();
	}

}
