package org.midnight.sprintutilities;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(SprintUtilities.MODID)
public class SprintUtilities {
	public static final String MODID = "sprintutilities";

	public SprintUtilities(ModContainer modContainer) {
		Config.register(modContainer);
	}
}