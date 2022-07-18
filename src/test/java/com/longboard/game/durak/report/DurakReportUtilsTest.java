package com.longboard.game.durak.report;

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
}