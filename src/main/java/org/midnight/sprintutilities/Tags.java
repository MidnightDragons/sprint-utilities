package org.midnight.sprintutilities;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class Tags {
	private Tags() {}

	public static final TagKey<Item> ENABLE_SPRINT = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(SprintUtilities.MODID, "enable_sprint"));
	public static final TagKey<Item> ENABLE_SWIM = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(SprintUtilities.MODID, "enable_swim"));
}