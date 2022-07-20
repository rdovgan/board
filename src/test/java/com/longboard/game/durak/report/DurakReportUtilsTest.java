package com.longboard.game.durak.report;

import com.longboard.entity.IsPlayer;
import com.longboard.game.durak.card.PlayingCard36;
import com.longboard.game.durak.engine.DurakBattle;
import com.longboard.game.durak.engine.DurakGame;
import com.longboard.game.durak.engine.DurakPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DurakReportUtilsTest {

	private final DurakReportUtils durakReportUtils = new DurakReportUtils();

	@Test
	void generateReport() {
	}

	@Test
	void defineGameRankForPlayerValidation() {
		Assertions.assertEquals(0.0, durakReportUtils.defineGameRankForPlayer(null, 4));
		Assertions.assertEquals(0.0, durakReportUtils.defineGameRankForPlayer(5, null));
		Assertions.assertEquals(0.0, durakReportUtils.defineGameRankForPlayer(-1, 5));
		Assertions.assertEquals(0.0, durakReportUtils.defineGameRankForPlayer(1, -1));
		Assertions.assertEquals(0.0, durakReportUtils.defineGameRankForPlayer(3, 2));
	}

	@Test
	void defineGameRankForPlayerForWinner() {
		Assertions.assertEquals(0.0, durakReportUtils.defineGameRankForPlayer(1, 1));
		Assertions.assertEquals(1.0, durakReportUtils.defineGameRankForPlayer(1, 2));
		Assertions.assertEquals(1.0, durakReportUtils.defineGameRankForPlayer(1, 3));
		Assertions.assertEquals(1.0, durakReportUtils.defineGameRankForPlayer(1, 4));
		Assertions.assertEquals(1.0, durakReportUtils.defineGameRankForPlayer(1, 5));
		Assertions.assertEquals(1.0, durakReportUtils.defineGameRankForPlayer(1, 6));
	}

	@Test
	void defineGameRankForPlayerForLooser() {
		Assertions.assertEquals(0.0, durakReportUtils.defineGameRankForPlayer(1, 1));
		Assertions.assertEquals(0.0, durakReportUtils.defineGameRankForPlayer(2, 2));
		Assertions.assertEquals(0.0, durakReportUtils.defineGameRankForPlayer(3, 3));
		Assertions.assertEquals(0.0, durakReportUtils.defineGameRankForPlayer(4, 4));
		Assertions.assertEquals(0.0, durakReportUtils.defineGameRankForPlayer(5, 5));
		Assertions.assertEquals(0.0, durakReportUtils.defineGameRankForPlayer(6, 6));
	}

	@Test
	void defineGameRankForPlayer() {
		Assertions.assertEquals(0.5, durakReportUtils.defineGameRankForPlayer(2, 3));
		Assertions.assertEquals(0.75, durakReportUtils.defineGameRankForPlayer(2, 5));
		Assertions.assertEquals(0.5, durakReportUtils.defineGameRankForPlayer(3, 5));
		Assertions.assertEquals(0.4, durakReportUtils.defineGameRankForPlayer(4, 6));
		Assertions.assertEquals(0.67, durakReportUtils.defineGameRankForPlayer(2, 4));
		Assertions.assertEquals(0.33, durakReportUtils.defineGameRankForPlayer(3, 4));
	}

	@Test
	void defineFirstPlayerCardForPlayer() {
		int playersCount = 2;
		DurakGame game = new DurakGame();
		game.initialiseGame(playersCount);

		Assertions.assertEquals(0, durakReportUtils.defineFirstPlayerCardRankForPlayer(null, game.getActivePlayers().stream().findFirst().orElse(null)));
		Assertions.assertEquals(0, durakReportUtils.defineFirstPlayerCardRankForPlayer(game, null));
		Assertions.assertNull(durakReportUtils.defineFirstPlayerCardForPlayer(null, game.getActivePlayers().stream().findFirst().orElse(null)));
		Assertions.assertNull(durakReportUtils.defineFirstPlayerCardForPlayer(game, null));

		IsPlayer<PlayingCard36> firstPlayer = game.defineFirstPlayer();
		DurakBattle battle = game.startBattle(null, firstPlayer);
		PlayingCard36 attackCard = battle.getCardsForAttack().stream().findFirst().orElse(null);
		battle.playCardToAttack(attackCard);
		PlayingCard36 defendCard = battle.getCardsForDefend(attackCard).stream().findFirst().orElse(null);
		if (defendCard != null) {
			battle.playCardToDefend(defendCard, attackCard);
		}
		game.endBattle(battle);

		Assertions.assertNotNull(attackCard);
		Assertions.assertEquals(attackCard, durakReportUtils.defineFirstPlayerCardForPlayer(game, firstPlayer));
		Assertions.assertEquals(attackCard.getRank().getValue(), durakReportUtils.defineFirstPlayerCardRankForPlayer(game, firstPlayer));
		Assertions.assertNull(durakReportUtils.defineFirstPlayerCardForPlayer(game, battle.getDefender()));
		Assertions.assertEquals(0, durakReportUtils.defineFirstPlayerCardRankForPlayer(game, battle.getDefender()));
	}

}