package org.midnight.sprintutilities;

import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

@Mod(SprintUtilities.MODID)
public class SprintUtilities {
	public static final String MODID = "sprintutilities";

	public SprintUtilities(IEventBus modEventBus, ModContainer modContainer) {
		Config.register(modContainer);

		Attributes.ATTRIBUTES.register(modEventBus);
		modEventBus.addListener((EntityAttributeModificationEvent event) -> {
			event.add(EntityType.PLAYER, Attributes.SPRINT_ENABLED);
			event.add(EntityType.PLAYER, Attributes.SWIM_ENABLED);
		});

		if (ModList.get().isLoaded("curios")) {
			NeoForge.EVENT_BUS.register(CuriosCompat.class);
		}
	}
}