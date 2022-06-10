package com.longboard.entity;

import com.longboard.base.Resource;

import java.util.Map;

public interface IsCost {

	void setCost(Map<Resource, Integer> cost);

	Map<Resource, Integer> getCost();

}
