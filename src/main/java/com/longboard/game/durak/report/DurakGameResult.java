package com.longboard.game.durak.report;

public class DurakGameResult {

	private Integer score;
	private Integer playersCount;
	private Double playerRank;
	private Integer firstPlayedCardRank = 0;
	private Integer[] startCardsRank = new Integer[6];

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getPlayersCount() {
		return playersCount;
	}

	public void setPlayersCount(Integer playersCount) {
		this.playersCount = playersCount;
	}

	public Double getPlayerRank() {
		return playerRank;
	}

	public void setPlayerRank(Double playerRank) {
		this.playerRank = playerRank;
	}

	public Integer getFirstPlayedCardRank() {
		return firstPlayedCardRank;
	}

	public void setFirstPlayedCardRank(Integer firstPlayedCardRank) {
		this.firstPlayedCardRank = firstPlayedCardRank;
	}

	public Integer[] getStartCardsRank() {
		return startCardsRank;
	}

	public void setStartCardsRank(Integer[] startCardsRank) {
		this.startCardsRank = startCardsRank;
	}
}
