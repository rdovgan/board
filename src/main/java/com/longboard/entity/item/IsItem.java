package com.longboard.entity.item;

import com.longboard.base.Body;
import com.longboard.base.ItemType;
import com.longboard.base.TargetType;
import com.longboard.engine.LogUtils;
import com.longboard.engine.card.CardEffectsHelper;
import com.longboard.entity.IsPlayer;
import com.longboard.entity.IsTarget;
import com.longboard.exception.GameException;

public interface IsItem extends IsTarget {

	String getName();

	String getDescription();

	Body.BodyPart appliedTo();

	ItemType getType();

	default void equip(IsPlayer player) {
		if (CardEffectsHelper.canEquip(this, player)) {
			player.getBody().setBodyStatus(appliedTo(), Body.BodyStatus.Equipped);
			setOwner(player);
		} else {
			LogUtils.info(appliedTo() + " cannot be used to equip new item for player " + player.getName() + ". Need to destroy previous item");
			throw new GameException("Can't equip item");
		}
	}

	void destroy();

	void enhance(IsEnhancementStone enhancementStone);

	IsPlayer getOwner();

	void setOwner(IsPlayer player);

	@Override
	default TargetType getTargetType() {
		return TargetType.Item;
	}

}
