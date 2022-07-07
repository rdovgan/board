package com.longboard.game.durak.display;

import com.longboard.engine.LogUtils;
import com.longboard.entity.IsPlayer;
import com.longboard.game.durak.card.PlayingCard36;
import com.longboard.game.durak.engine.DurakBattle;
import com.longboard.game.durak.engine.DurakGame;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.stream.Collectors;

public class DisplayUtils {

	public static final String PLAYERS_COUNT_COMBO_BOX = "ComboBox-PlayersCount";

	public Panel defineTopPanel() {
		Panel topPlayerPanel = new Panel();
		topPlayerPanel.setBounds(DurakGameDisplay.PADDING, DurakGameDisplay.PADDING, DurakGameDisplay.TOTAL_WIDTH, DurakGameDisplay.TOTAL_HEIGHT / 8 * 3);
		topPlayerPanel.setBackground(new Color(204, 176, 137));
		topPlayerPanel.setVisible(true);

		return topPlayerPanel;
	}

	public Panel defineMiddlePanel() {
		Panel middlePanel = new Panel();
		middlePanel.setBounds(DurakGameDisplay.PADDING, DurakGameDisplay.TOTAL_HEIGHT / 8 * 3 + 2 * DurakGameDisplay.PADDING, DurakGameDisplay.TOTAL_WIDTH,
				DurakGameDisplay.TOTAL_HEIGHT / 8 * 2);
		middlePanel.setBackground(new Color(172, 128, 191));

		Label selectPlayerLabel = new Label("Please select number of players");
		selectPlayerLabel.setBounds(DurakGameDisplay.PADDING * 2, DurakGameDisplay.PADDING * 2, DurakGameDisplay.TOTAL_WIDTH / 2, DurakGameDisplay.PADDING * 2);
		middlePanel.add(selectPlayerLabel);

		JComboBox<Integer> playerCountBox = new JComboBox<>();
		playerCountBox.setName(PLAYERS_COUNT_COMBO_BOX);
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
		confirmPlayersCountAndStartGame.addKeyListener(getKeyListener(playerCountBox));
		confirmPlayersCountAndStartGame.requestFocus();
		middlePanel.add(confirmPlayersCountAndStartGame);

		middlePanel.setVisible(true);
		return middlePanel;
	}

	private KeyListener getKeyListener(JComboBox<Integer> playerCountBox) {
		return new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("Hello");
					DurakGameDisplay.startGame(playerCountBox.getSelectedItem());
				}
			}
		};
	}

	public Panel defineBottomPanel() {
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

	public void definePlayerScore(Panel middlePanel, DurakGame game, DurakBattle currentBattle) {
		if (currentBattle == null && MapUtils.isNotEmpty(game.endGame())) {
			middlePanel.removeAll();

			Panel playersScore = new Panel();
			playersScore.setBounds(DurakGameDisplay.CARD_WIDTH + DurakGameDisplay.PADDING * 2, DurakGameDisplay.PADDING * 2, DurakGameDisplay.CARD_WIDTH * 2,
					DurakGameDisplay.CARD_HEIGHT + 2 * DurakGameDisplay.PADDING);
			Label playersScoreLabel = new Label();
			playersScoreLabel.setText(
					game.endGame().entrySet().stream().map(entry -> entry.getKey() + ". " + entry.getValue().getName()).collect(Collectors.joining("\n")));
			playersScoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
			playersScoreLabel.setBounds(DurakGameDisplay.PADDING, DurakGameDisplay.PADDING, DurakGameDisplay.CARD_WIDTH * 2,
					DurakGameDisplay.CARD_HEIGHT + 2 * DurakGameDisplay.PADDING);
			playersScoreLabel.setForeground(new Color(3, 33, 51));
			playersScore.add(playersScoreLabel);
			playersScore.setBackground(new Color(22, 93, 105));

			middlePanel.add(playersScore);
		}
	}


	//TODO implement AI for battle
	public DurakBattle autoBattle(DurakGame game, DurakBattle battle) {
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
		return game.startBattle(battle, game.defineNextPlayerToAttack(battle));
	}

}
