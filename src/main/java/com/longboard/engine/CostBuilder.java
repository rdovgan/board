package com.longboard.engine;

import com.longboard.base.Resource;
import com.longboard.entity.IsCost;

import java.util.HashMap;
import java.util.Map;

public class CostBuilder implements IsCost {

	private Map<Resource, Integer> cost = new HashMap<>();

	public CostBuilder addCost(Resource resource, Integer amount) {
		cost.put(resource, amount);
		return this;
	}

	public IsCost build() {
		return this;
	}

	@Override
	public void setCost(Map<Resource, Integer> cost) {
		this.cost = cost;
	}

	@Override
	public Map<Resource, Integer> getCost() {
		return cost;
	}
}
