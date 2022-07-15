package com.longboard.game.durak.report;

import com.longboard.entity.IsPlayer;
import com.longboard.exception.GameException;
import com.longboard.game.durak.card.PlayingCard36;
import com.longboard.game.durak.engine.DurakBattle;
import com.longboard.game.durak.engine.DurakGame;
import com.longboard.game.durak.report.DurakGameResult;

import java.util.Map;

public class DurakAutoBattle {

	private DurakReportUtils durakReportUtils = new DurakReportUtils();

	public DurakGame startAutoGame(Integer playersCount) {
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

		return game;
	}

	public void processAutoGameAndSaveScore(Integer playersCount) {
		DurakGame game = startAutoGame(playersCount);
		DurakGameResult gameResult = durakReportUtils.generateReport(game);
	}

	public void processAutoBattle(DurakBattle battle) {
		PlayingCard36 attackCard = battle.getCardsForAttack().stream().findFirst().orElse(null);
		battle.playCardToAttack(attackCard);
		battle.getCardsForDefend(attackCard).stream().findFirst().ifPresent(defendCard -> battle.playCardToDefend(defendCard, attackCard));
	}

}
