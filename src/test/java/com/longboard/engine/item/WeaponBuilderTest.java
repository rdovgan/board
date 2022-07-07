package com.longboard.engine.item;

import com.longboard.exception.GameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WeaponBuilderTest {

	@Test
	public void testWeaponBuilder() {
		WeaponBuilder weaponBuilder = new WeaponBuilder().addName("Test weapon").addDescription("Test weapon description").addDamage(5).addOwner(null);
		Assertions.assertNotNull(weaponBuilder);
		Assertions.assertNotNull(weaponBuilder.getId());
		Assertions.assertNotNull(weaponBuilder.getName());
		Assertions.assertNotNull(weaponBuilder.getDescription());
		Assertions.assertNotNull(weaponBuilder.getDamage());
		Assertions.assertNotNull(weaponBuilder.getTargetType());
		Assertions.assertNull(weaponBuilder.getOwner());

		weaponBuilder.validate();
		weaponBuilder.addDescription(null);
		Assertions.assertThrows(GameException.class, weaponBuilder::validate);
	}

}