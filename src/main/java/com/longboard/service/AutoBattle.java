package com.longboard.service;

import com.longboard.entity.IsPlayer;
import com.longboard.exception.GameException;
import com.longboard.game.durak.card.PlayingCard36;
import com.longboard.game.durak.engine.DurakGame;

import java.util.Map;

public class AutoBattle {

	public Map<Integer, IsPlayer<PlayingCard36>> startAutoBattle(Integer playersCount) {
		if (playersCount == null) {
			throw new GameException("Wrong players count");
		}
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);

		return game.endGame();
	}

}
