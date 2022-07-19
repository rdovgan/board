package com.longboard.game.durak.report;

import com.longboard.entity.IsPlayer;
import com.longboard.game.durak.card.PlayingCard36;
import com.longboard.game.durak.engine.DurakGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DurakReportUtils {

	public List<DurakGameResult> generateReport(DurakGame durakGame) {
		List<DurakGameResult> gameResults = new ArrayList<>();
		if (durakGame == null) {
			return gameResults;
		}
		Integer allPlayersCount = durakGame.getActivePlayers().size() + durakGame.getScore().size();

		for (Map.Entry<Integer, IsPlayer<PlayingCard36>> playerScore : durakGame.getScore().entrySet()) {
			DurakGameResult gameResult = new DurakGameResult();
			gameResult.setScore(playerScore.getKey());
			gameResult.setPlayersCount(allPlayersCount);
			gameResult.setPlayerRank(defineGameRankForPlayer(playerScore.getKey(), allPlayersCount));
			gameResults.add(gameResult);
		}

		return gameResults;
	}

	public Double defineGameRankForPlayer(Integer playerRank, Integer playersCount) {
		if (playersCount == null || playerRank == null || playerRank <= 0 || playersCount <= 1 || playerRank > playersCount) {
			return 0.0;
		}
		return Math.round((1.0 - 1.0 / (playersCount - 1.0) * (playerRank - 1.0)) * 100.0) / 100.0;
	}

}
