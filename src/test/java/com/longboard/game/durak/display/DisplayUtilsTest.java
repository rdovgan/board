package com.longboard.game.durak.display;

import com.longboard.base.PlayerColor;
import com.longboard.game.durak.engine.DurakBattle;
import com.longboard.game.durak.engine.DurakGame;
import com.longboard.game.durak.engine.DurakPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

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
}