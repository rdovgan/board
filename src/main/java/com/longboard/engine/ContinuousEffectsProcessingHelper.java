package com.longboard.engine;

import com.longboard.entity.IsPlayer;
import com.longboard.entity.card.IsCard;

public class ContinuousEffectsProcessingHelper {

	public static void processStartNewTurn(IsPlayer<IsCard> player) {
		player.getContinuousEffects().getOnStartTurn().forEach((key, value) -> value.accept(key));
	}

}
