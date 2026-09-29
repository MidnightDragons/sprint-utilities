package org.midnight.sprintutilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;

import java.util.function.Predicate;

final class CuriosCompat {
	private CuriosCompat() {}

	static ItemStack find(Player player, Predicate<ItemStack> filter) {
		return CuriosApi.getCuriosInventory(player)
			.flatMap(handler -> handler.findFirstCurio(filter))
			.map(SlotResult::stack)
			.orElse(ItemStack.EMPTY);
	}

	@SubscribeEvent
	public static void onCurioAttributes(CurioAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();
		SlotContext ctx = event.getSlotContext();

		if (stack.is(Tags.ENABLE_SPRINT)) {
			event.addModifier(Attributes.SPRINT_ENABLED, modifier("sprint", ctx));
		}
		if (stack.is(Tags.ENABLE_SWIM)) {
			event.addModifier(Attributes.SWIM_ENABLED, modifier("swim", ctx));
		}
	}

	private static AttributeModifier modifier(String kind, SlotContext ctx) {
		return new AttributeModifier(
				ResourceLocation.fromNamespaceAndPath(
						SprintUtilities.MODID,
						TagAddAttribute.PREFIX + kind + "_" + ctx.identifier() + "_" + ctx.index()
				),
				1.0,
				AttributeModifier.Operation.ADD_VALUE
		);
	}
}