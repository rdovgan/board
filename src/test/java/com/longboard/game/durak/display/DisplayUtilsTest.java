package com.longboard.game.durak.display;

import com.longboard.game.durak.engine.DurakBattle;
import com.longboard.game.durak.engine.DurakGame;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

class DisplayUtilsTest {

	private DisplayUtils displayUtils = new DisplayUtils();

	private DurakGame initGame() {
		DurakGame game = new DurakGame();
		game.initialiseGame(2);
		return game;
	}

	@Test
	void defineTopPanel() {
		Panel panelTop = displayUtils.defineTopPanel();
		Assertions.assertNotNull(panelTop);
		Panel panelMiddle = displayUtils.defineMiddlePanel();
		Assertions.assertNotNull(panelMiddle);
		Panel panelBottom = displayUtils.defineBottomPanel();
		Assertions.assertNotNull(panelBottom);
		displayUtils.clearBoards(panelTop, panelMiddle, panelBottom);
	}

	@Test
	void testEndCurrentTurn() {
		DurakGame game = initGame();
		Assertions.assertNotNull(displayUtils.endCurrentTurn(game, game.startBattle(null, game.defineFirstPlayer()), game.defineFirstPlayer()));
	}

	@Test
	void testDefinePlayerScore() {
		DurakGame game = initGame();
		Panel middlePanel = displayUtils.defineMiddlePanel();
		displayUtils.definePlayerScore(middlePanel, game, game.startBattle(null, game.defineFirstPlayer()));
		Assertions.assertNotNull(middlePanel.getComponent(0));
	}

	@Test
	void autoBattle() {
		DurakGame game = initGame();
		DurakBattle battle = game.startBattle(null, game.defineFirstPlayer());
		while (game.getActivePlayers().size() == 2) {
			battle = displayUtils.autoBattle(game, battle);
		}
		Assertions.assertTrue(CollectionUtils.isEmpty(game.getDeck()));
		Assertions.assertTrue(MapUtils.isNotEmpty(game.endGame()));
	}
}