package com.longboard.engine;

import com.longboard.entity.IsPlayer;

public class ContinuousEffectsProcessing {

	public static void processStartNewTurn(IsPlayer player) {
		player.getContinuousEffects().getOnStartTurn().forEach((key, value) -> value.accept(key));
	}

}
