package org.midnight.sprintutilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

@EventBusSubscriber(modid = SprintUtilities.MODID)
public final class TagAddAttribute {
	public static final String PREFIX = "tag_granted_";

	@SubscribeEvent
	public static void onItemAttributes(ItemAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();

		Equipable equipable = Equipable.get(stack);
		if (equipable == null) return;

		EquipmentSlot slot = equipable.getEquipmentSlot();
		if (!slot.isArmor()) return;

		EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(slot);
		if (stack.is(Tags.ENABLE_SPRINT)) {
			event.addModifier(Attributes.SPRINT_ENABLED, modifier("sprint_" + slot.getName()), group);
		}
		if (stack.is(Tags.ENABLE_SWIM)) {
			event.addModifier(Attributes.SWIM_ENABLED, modifier("swim_" + slot.getName()), group);
		}
	}

	private static AttributeModifier modifier(String name) {
		return new AttributeModifier(
				ResourceLocation.fromNamespaceAndPath(SprintUtilities.MODID, PREFIX + name),
				1.0, AttributeModifier.Operation.ADD_VALUE
		);
	}
}