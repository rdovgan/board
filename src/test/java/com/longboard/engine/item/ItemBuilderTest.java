package com.longboard.engine.item;

import com.longboard.base.Body;
import com.longboard.base.ItemType;
import com.longboard.entity.item.IsItem;
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

}