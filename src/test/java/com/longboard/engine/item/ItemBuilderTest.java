package com.longboard.engine.item;

import com.longboard.base.Body;
import com.longboard.base.ItemType;
import com.longboard.base.PlayerColor;
import com.longboard.entity.PlayerTest;
import com.longboard.entity.item.IsItem;
import com.longboard.exception.GameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ItemBuilderTest {

	@Test
	public void testItemBuilder() {
		String itemDescription = "Test item";
		ItemBuilder itemBuilder = new ItemBuilder().addName("Test").addItemType(ItemType.Weapon).addAppliedTo(Body.BodyPart.RightHand).addOwner(null)
				.addDescription(itemDescription);
		IsItem item = itemBuilder.build();
		Assertions.assertNotNull(item);
		Assertions.assertEquals(itemDescription, item.getDescription());
	}

	@Test
	public void testItemBuilderValidate() {
		String itemDescription = "Test item";
		ItemBuilder itemBuilder = new ItemBuilder().addName("Test").addItemType(ItemType.Weapon).addAppliedTo(Body.BodyPart.RightHand).addOwner(null)
				.addDescription(itemDescription);
		IsItem item = itemBuilder.validate().build();
		Assertions.assertNotNull(item);
		Assertions.assertThrows(GameException.class, itemBuilder.addDescription(null)::validate);
	}

	@Test
	public void testItemBuilderGetters() {
		String itemDescription = "Test item";
		ItemBuilder itemBuilder = new ItemBuilder().addName("Test").addItemType(ItemType.Weapon).addAppliedTo(Body.BodyPart.RightHand).addOwner(null)
				.addDescription(itemDescription);
		Assertions.assertNotNull(itemBuilder.getId());
		Assertions.assertNotNull(itemBuilder.getName());
		Assertions.assertNotNull(itemBuilder.appliedTo());
		Assertions.assertNotNull(itemBuilder.getType());
		Assertions.assertNull(itemBuilder.getOwner());
		itemBuilder.addOwner(new PlayerTest("Test player", PlayerColor.Green, null, null));
		Assertions.assertNotNull(itemBuilder.getOwner());
		IsItem item = itemBuilder.validate().build();
		Assertions.assertNotNull(item);
	}

}