package com.longboard.service;

import com.longboard.exception.GameException;
import com.longboard.game.durak.report.DurakAutoBattle;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class AutoBattleTest {
	@Test
	void startAutoBattle() {
		DurakAutoBattle autoBattle = new DurakAutoBattle();
		Assertions.assertThrows(GameException.class, () -> autoBattle.startAutoGame(null));
		Assertions.assertTrue(MapUtils.isNotEmpty(autoBattle.startAutoGame(2).getScore()));
		Assertions.assertTrue(MapUtils.size(autoBattle.startAutoGame(6).getScore()) > 4);
	}
}