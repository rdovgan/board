package com.longboard.engine;

import com.longboard.base.Resource;
import com.longboard.entity.IsCard;
import com.longboard.entity.IsCost;
import com.longboard.entity.IsPlayer;

public class ResourceUtils {

	private boolean validatePlayerAndResources(IsPlayer player, Integer value) {
		if (value == null || value <= 0) {
			LogUtils.error("Wrong " + value + " resource value");
			return true;
		}
		if (player == null) {
			LogUtils.error("Player not initialised");
			return true;
		}
		if (player.getResources() == null) {
			LogUtils.error("Wrong player " + player.getName() + " initialisation. Resources pool is `null`");
			return true;
		}
		return false;
	}

	public void addResourceToPlayer(IsPlayer player, Resource resource, Integer value) {
		if (validatePlayerAndResources(player, value)) {
			return;
		}
		Integer previousValue = player.getResources().get(resource);
		player.getResources().put(resource, previousValue + value);
		LogUtils.info("Increased resource " + resource.name() + " for player " + player.getName() + " by " + value + ". Now it has " + (previousValue + value));
	}

	public void subResourcesFromPlayer(IsPlayer player, Resource resource, Integer value) {
		if (validatePlayerAndResources(player, value)) {
			return;
		}
		Integer previousValue = checkResource(player, resource, value);
		if (previousValue == null) {
			return;
		}
		player.getResources().put(resource, previousValue - value);
		LogUtils.info("Decreased resource " + resource.name() + " for player " + player.getName() + " by " + value + ". Now it has " + (previousValue - value));
	}

	private Integer checkResource(IsPlayer player, Resource resource, Integer value) {
		Integer previousValue = player.getResources().get(resource);
		if (previousValue == null || previousValue < value) {
			LogUtils.error("Player " + player.getName() + " can't pay " + value + " " + resource.name() + " resource. Available only " + previousValue);
			return null;
		}
		return previousValue;
	}

	public boolean isPlayable(IsCard card) {
		if (card == null) {
			LogUtils.error("Card is not initialised");
			return false;
		}
		if (card.getOwner() == null) {
			LogUtils.error("Card is not initialised properly. There is no owner for card " + card.getName() + " : " + card.getNumber());
			return false;
		}
		if (card.getCost() == null) {
			LogUtils.error("Card is not initialised properly. There is no cost for card " + card.getName() + " : " + card.getNumber());
			return false;
		}
		IsPlayer player = card.getOwner();
		IsCost cost = card.getCost();
		return cost.getCost().entrySet().stream().noneMatch(resourceCost -> checkResource(player, resourceCost.getKey(), resourceCost.getValue()) == null);
	}

}
