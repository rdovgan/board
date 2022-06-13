package com.longboard.entity;

import com.longboard.base.TargetType;

import java.util.UUID;

/**
 * Used to set target for card or effect
 */
public interface IsTarget {

	UUID getId();

	TargetType getTargetType();

}
