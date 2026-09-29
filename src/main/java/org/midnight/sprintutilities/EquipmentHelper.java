package org.midnight.sprintutilities;

import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

public final class EquipmentHelper {
	private EquipmentHelper() {}

	private static final boolean CURIOS_LOADED = ModList.get().isLoaded("curios");

	public static ItemStack findEquipped(Player player, TagKey<Item> tag) {
		for (ItemStack stack : player.getArmorSlots()) {
			if (isUsable(stack, tag)) return stack;
		}

		if (CURIOS_LOADED) {
			return CuriosCompat.find(player, stack -> isUsable(stack, tag));
		}

		return ItemStack.EMPTY;
	}

	private static boolean isUsable(ItemStack stack, TagKey<Item> tag) {
		if (stack.isEmpty() || !stack.is(tag)) return false;
		return !stack.isDamageableItem() || stack.getDamageValue() < stack.getMaxDamage();
	}

	public static boolean hasAttribute(Player player, Holder<Attribute> attribute) {
		AttributeInstance instance = player.getAttribute(attribute);
		if (instance == null) return false;

		for (AttributeModifier modifier : instance.getModifiers()) {
			if (modifier.id().getPath().startsWith(TagAddAttribute.PREFIX)) continue;
			if (modifier.amount() > 0) return true;
		}
		return false;
	}
}