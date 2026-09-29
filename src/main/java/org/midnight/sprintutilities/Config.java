package org.midnight.sprintutilities;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
	public static final ModConfigSpec SERVER_SPEC;
	public static final CConfig SERVER;

	static {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
		SERVER = new CConfig(builder);
		SERVER_SPEC = builder.build();
	}

	public static void register(ModContainer container) {
		container.registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
	}

	public static class CConfig {
		public final ModConfigSpec.BooleanValue enableSprint;
		public final ModConfigSpec.BooleanValue enableEquipmentDamage;
		public final ModConfigSpec.IntValue damageEquipmentTick;

		public CConfig(ModConfigSpec.Builder builder) {
			builder.push("sprint_control");

			enableSprint = builder
					.comment("If sprinting without proper sprint-equipment is enabled or not" +
							"\nDefault: false")
					.define("enableSprint", false);

			enableEquipmentDamage = builder
					.comment("Does sprint-equipment take damage when sprinting with it on" +
							"\nDefault: true")
					.define("enableEquipmentDamage", true);

			damageEquipmentTick = builder
					.comment("How many ticks when moving until damaging sprint-equipment" +
							"\nDefault: 20")
					.defineInRange("damageEquipmentTick", 20, 1, 6000);

			builder.pop();
		}
	}
}
