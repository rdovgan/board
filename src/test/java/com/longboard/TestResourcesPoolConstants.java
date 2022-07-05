package com.longboard;

import com.longboard.base.CardType;
import com.longboard.base.Resource;
import com.longboard.engine.card.CardConditionHelper;
import com.longboard.engine.card.CardEffectsHelper;
import com.longboard.engine.card.CostBuilder;
import com.longboard.engine.item.WeaponBuilder;
import com.longboard.entity.CardItemTest;
import com.longboard.entity.CardTest;
import com.longboard.entity.item.IsWeapon;

public class TestResourcesPoolConstants {

	public static final IsWeapon IRON_SWORD = new WeaponBuilder().addName("Old Iron Sword").addDescription("Attack +3. Level 1").addDamage(3).build();

	public static final CardTest HEALING_SPELL = new CardTest(99001L, "Spell of Healing", "Restore up to 10 health. Cost: 5 Mana", CardType.Spell,
			new CostBuilder().addCost(Resource.Mana, 5).build(), CardConditionHelper.whenDamaged(), CardEffectsHelper.healPlayer(10));

	public static final CardTest HARD_MINING = new CardTest(99002L, "My Friend Vampire",
			"Visit your good friend to lend some money. Lose 1 HP and get 5 gold at the start of each turn", CardType.Effect, new CostBuilder().build(),
			CardConditionHelper.whenHealthIsGreaterThanOne(), CardEffectsHelper.continuousEffectOnTurnStart(CardEffectsHelper.dealDamageAndGiveGold(1, 5)));

	public static final CardItemTest IRON_SWORD_CARD = new CardItemTest(99003L, "Old Iron Sword", "Equip one-handed sword with Attack +3", CardType.Item,
			new CostBuilder().addCost(Resource.Gold, 10).build(), CardConditionHelper.whenHealthIsGreaterThanOne(), CardEffectsHelper.equipItem(IRON_SWORD),
			IRON_SWORD);

}
