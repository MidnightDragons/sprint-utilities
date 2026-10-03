package org.midnight.sprintutilities;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class Attributes {
	private Attributes() {}

	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, SprintUtilities.MODID);

	public static final DeferredHolder<Attribute, Attribute> SPRINT_ENABLED = ATTRIBUTES.register("sprint_enabled", () -> new RangedAttribute("attribute.name.sprintutilities.sprint_enabled", 0.0, 0.0, 1.0).setSyncable(true));
	public static final DeferredHolder<Attribute, Attribute> SWIM_ENABLED = ATTRIBUTES.register("swim_enabled", () -> new RangedAttribute("attribute.name.sprintutilities.swim_enabled", 0.0, 0.0, 1.0).setSyncable(true));
}