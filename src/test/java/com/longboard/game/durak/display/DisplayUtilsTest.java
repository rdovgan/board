package com.longboard.game.durak.display;

import com.longboard.base.PlayerColor;
import com.longboard.game.durak.engine.DurakBattle;
import com.longboard.game.durak.engine.DurakGame;
import com.longboard.game.durak.engine.DurakPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DisplayUtilsTest {

	private DisplayUtils displayUtils = new DisplayUtils();

	@Test
	void defineTopPanel() {
		Assertions.assertNotNull(displayUtils.defineTopPanel());
	}

	@Test
	void addMiddlePanel() {
		Assertions.assertNotNull(displayUtils.addMiddlePanel());
	}

	@Test
	void addBottomPanel() {
		Assertions.assertNotNull(displayUtils.addBottomPanel());
	}

	@Test
	void clearBoards() {
		displayUtils.clearBoards(displayUtils.defineTopPanel(), displayUtils.addMiddlePanel(), displayUtils.addBottomPanel());
	}

	@Test
	void endCurrentTurn() {
		displayUtils.endCurrentTurn(new DurakGame(), new DurakBattle(null, null, null), new DurakPlayer(null, PlayerColor.Green, new ArrayList<>()));
	}
}