package com.longboard.game.durak.report;

import com.longboard.entity.IsPlayer;
import com.longboard.game.durak.card.PlayingCard36;
import com.longboard.game.durak.engine.DurakGame;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
			gameResult.setFirstPlayedCardRank(defineFirstPlayerCardRankForPlayer(durakGame, playerScore.getValue()));
			gameResults.add(gameResult);
		}
		if (durakGame.getActivePlayers().size() == 1) {
			DurakGameResult gameResult = new DurakGameResult();
			gameResult.setScore(allPlayersCount);
			gameResult.setPlayersCount(allPlayersCount);
			gameResult.setPlayerRank(defineGameRankForPlayer(allPlayersCount, allPlayersCount));
			gameResult.setFirstPlayedCardRank(defineFirstPlayerCardRankForPlayer(durakGame, durakGame.getActivePlayers().stream().findFirst().orElse(null)));
			gameResults.add(gameResult);
		}

		return gameResults;
	}

	public PlayingCard36 defineFirstPlayerCardForPlayer(DurakGame durakGame, IsPlayer<PlayingCard36> player) {
		if (durakGame == null || CollectionUtils.isEmpty(durakGame.getBattles()) || player == null) {
			return null;
		}
		return durakGame.getBattles().stream().filter(battle -> Objects.equals(battle.getAttacker(), player)).findFirst()
				.flatMap(battle -> battle.getBattlingCards().getBattlingCards().entrySet().stream().findFirst().map(Map.Entry::getKey)).orElse(null);
	}

	public Integer defineFirstPlayerCardRankForPlayer(DurakGame durakGame, IsPlayer<PlayingCard36> player) {
		return Optional.ofNullable(defineFirstPlayerCardForPlayer(durakGame, player)).map(card -> card.getRank().getValue()).orElse(0);
	}

	public Double defineGameRankForPlayer(Integer playerRank, Integer playersCount) {
		if (playersCount == null || playerRank == null || playerRank <= 0 || playersCount <= 1 || playerRank > playersCount) {
			return 0.0;
		}
		return Math.round((1.0 - 1.0 / (playersCount - 1.0) * (playerRank - 1.0)) * 100.0) / 100.0;
	}

}
