package com.longboard.game.durak.display;

import com.longboard.engine.LogUtils;
import com.longboard.entity.IsPlayer;
import com.longboard.game.durak.card.PlayingCard36;
import com.longboard.game.durak.engine.DurakBattle;
import com.longboard.game.durak.engine.DurakGame;

import javax.swing.*;
import java.awt.*;

public class DisplayUtils {

	public Panel defineTopPanel() {
		Panel topPlayerPanel = new Panel();
		topPlayerPanel.setBounds(DurakGameDisplay.PADDING, DurakGameDisplay.PADDING, DurakGameDisplay.TOTAL_WIDTH, DurakGameDisplay.TOTAL_HEIGHT / 8 * 3);
		topPlayerPanel.setBackground(new Color(204, 176, 137));
		topPlayerPanel.setVisible(true);

		return topPlayerPanel;
	}

	public Panel addMiddlePanel() {
		Panel middlePanel = new Panel();
		middlePanel.setBounds(DurakGameDisplay.PADDING, DurakGameDisplay.TOTAL_HEIGHT / 8 * 3 + 2 * DurakGameDisplay.PADDING, DurakGameDisplay.TOTAL_WIDTH,
				DurakGameDisplay.TOTAL_HEIGHT / 8 * 2);
		middlePanel.setBackground(new Color(172, 128, 191));

		Label selectPlayerLabel = new Label("Please select number of players");
		selectPlayerLabel.setBounds(DurakGameDisplay.PADDING * 2, DurakGameDisplay.PADDING * 2, DurakGameDisplay.TOTAL_WIDTH / 2, DurakGameDisplay.PADDING * 2);
		middlePanel.add(selectPlayerLabel);

		JComboBox<Integer> playerCountBox = new JComboBox<>();
		playerCountBox.addItem(2);
		playerCountBox.addItem(3);
		playerCountBox.addItem(4);
		playerCountBox.addItem(5);
		playerCountBox.addItem(6);
		playerCountBox.setBounds(DurakGameDisplay.PADDING * 3, DurakGameDisplay.PADDING * 2, DurakGameDisplay.PADDING * 5, DurakGameDisplay.PADDING * 2);
		middlePanel.add(playerCountBox);

		Button confirmPlayersCountAndStartGame = new Button("Confirm");
		confirmPlayersCountAndStartGame.setBounds(DurakGameDisplay.PADDING, DurakGameDisplay.PADDING * 2, DurakGameDisplay.PADDING * 5,
				DurakGameDisplay.PADDING * 2);
		confirmPlayersCountAndStartGame.addActionListener(e -> DurakGameDisplay.startGame(playerCountBox.getSelectedItem()));
		middlePanel.add(confirmPlayersCountAndStartGame);

		middlePanel.setVisible(true);
		return middlePanel;
	}

	public Panel addBottomPanel() {
		Panel bottomPlayerPanel = new Panel();
		bottomPlayerPanel.setBounds(DurakGameDisplay.PADDING, DurakGameDisplay.TOTAL_HEIGHT / 8 * 5 + 3 * DurakGameDisplay.PADDING,
				DurakGameDisplay.TOTAL_WIDTH, DurakGameDisplay.TOTAL_HEIGHT / 8 * 3 - DurakGameDisplay.PADDING);
		bottomPlayerPanel.setBackground(new Color(142, 204, 137));

		bottomPlayerPanel.setVisible(true);
		return bottomPlayerPanel;
	}

	public void clearBoards(Panel topPlayerPanel, Panel middlePanel, Panel bottomPlayerPanel) {
		topPlayerPanel.removeAll();
		middlePanel.removeAll();
		bottomPlayerPanel.removeAll();
	}

	public DurakBattle endCurrentTurn(DurakGame game, DurakBattle currentBattle, IsPlayer<PlayingCard36> currentPlayer) {
		try {
			if (currentBattle.getAttacker().getId() == currentPlayer.getId()) {
				game.endBattle(currentBattle);
				currentBattle = game.startBattle(currentBattle, game.defineNextPlayerToAttack(currentBattle));
			} else if (currentBattle.getDefender().getId() == currentPlayer.getId()) {
				game.endBattle(currentBattle);
				currentBattle = game.startBattle(currentBattle, game.defineNextPlayerToAttack(currentBattle));
			}
		} catch (Exception e) {
			LogUtils.error(e.getMessage());
		}
		return currentBattle;
	}

}
