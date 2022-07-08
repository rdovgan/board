package com.longboard.entity.card;

import com.longboard.base.Resource;
import com.longboard.engine.ResourceUtils;
import com.longboard.entity.IsPlayer;

import java.util.Map;

public interface IsCost {

	default boolean isPlayable(IsPlayer player) {
		return ResourceUtils.checkCost(player, this);
	}

	default void play(IsPlayer player) {
		getCost().forEach((key, value) -> player.getResources().put(key, (Integer) player.getResources().get(key) - value));
	}

	void setCost(Map<Resource, Integer> cost);

	Map<Resource, Integer> getCost();

}
