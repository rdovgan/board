package com.longboard.game.durak.service;

import com.longboard.entity.IsPlayer;
import com.longboard.exception.GameException;
import com.longboard.game.durak.card.PlayingCard36;
import com.longboard.game.durak.engine.DurakBattle;
import com.longboard.game.durak.engine.DurakGame;

import java.util.Map;

public class AutoBattle {

	public Map<Integer, IsPlayer<PlayingCard36>> startAutoGame(Integer playersCount) {
		if (playersCount == null) {
			throw new GameException("Wrong players count");
		}
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);

		DurakBattle battle = null;

		while (!game.isGameFinished()) {
			battle = game.startBattle(null, game.defineNextPlayerToAttack(battle));
			processAutoBattle(battle);
			game.endBattle(battle);
		}

		return game.getScore();
	}

	public void processAutoBattle(DurakBattle battle) {
		PlayingCard36 attackCard = battle.getCardsForAttack().stream().findFirst().orElse(null);
		battle.playCardToAttack(attackCard);
		battle.getCardsForDefend(attackCard).stream().findFirst().ifPresent(defendCard -> battle.playCardToDefend(defendCard, attackCard));
	}

}
