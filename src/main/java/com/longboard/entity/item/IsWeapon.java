package com.longboard.entity.item;

import com.longboard.base.Body;
import com.longboard.base.ItemType;

/**
 * Right-handed weapon
 */
public interface IsWeapon extends IsItem {

	@Override
	default Body.BodyPart appliedTo() {
		return Body.BodyPart.RightArm;
	}

	@Override
	default ItemType getType() {
		return ItemType.Weapon;
	}

	Integer getDamage();

}
