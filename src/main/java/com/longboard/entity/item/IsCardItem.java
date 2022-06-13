package com.longboard.entity.item;

import com.longboard.base.ItemType;
import com.longboard.base.TargetType;
import com.longboard.entity.IsCard;

public interface IsCardItem extends IsCard, IsItem {

	ItemType getType();

	default TargetType getTargetType() {
		return TargetType.Card;
	}

}
