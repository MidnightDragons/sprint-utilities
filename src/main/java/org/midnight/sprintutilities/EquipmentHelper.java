package org.midnight.sprintutilities;

import net.minecraft.tags.TagKey;
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
}