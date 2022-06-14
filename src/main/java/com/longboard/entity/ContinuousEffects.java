package com.longboard.entity;

import com.longboard.entity.card.IsCard;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ContinuousEffects {

	private DualLinkedHashBidiMap<IsCard, Consumer<IsCard>> onStartTurn;

	public ContinuousEffects(DualLinkedHashBidiMap<IsCard, Consumer<IsCard>> onStartTurnEffects) {
		this.onStartTurn = onStartTurnEffects;
	}

	public DualLinkedHashBidiMap<IsCard, Consumer<IsCard>> getOnStartTurn() {
		return onStartTurn;
	}

	public void addEffectOnStartTurn(IsCard card, Consumer<IsCard> onStartTurn) {
		this.onStartTurn.put(card, onStartTurn);
	}

	public void removeEffectOnStartTurn(Consumer<IsCard> onStartTurn) {
		this.onStartTurn.removeValue(onStartTurn);
	}

	public void removeEffectOnStartTurn(IsCard onStartTurnCard) {
		this.onStartTurn.remove(onStartTurnCard);
	}
}
