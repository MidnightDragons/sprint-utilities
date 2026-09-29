package org.midnight.sprintutilities;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.function.Predicate;

final class CuriosCompat {
	private CuriosCompat() {}

	static ItemStack find(Player player, Predicate<ItemStack> filter) {
		return CuriosApi.getCuriosInventory(player)
			.flatMap(handler -> handler.findFirstCurio(filter))
			.map(SlotResult::stack)
			.orElse(ItemStack.EMPTY);
	}
}