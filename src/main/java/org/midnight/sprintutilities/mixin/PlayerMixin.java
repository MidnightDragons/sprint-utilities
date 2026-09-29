package org.midnight.sprintutilities.mixin;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.midnight.sprintutilities.Config;
import org.midnight.sprintutilities.EquipmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.midnight.sprintutilities.SprintUtilities.MODID;

@Mixin(LivingEntity.class)
public abstract class PlayerMixin {
	@Unique
	private static final TagKey<Item> REQUIRED_ITEM_TAG_BOOTS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, "enable_sprint"));

	@Unique
	private static final TagKey<Item> REQUIRED_ITEM_TAG_FLIPPERS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, "enable_swim"));

	@Unique
	private int sprintutilities$tickCounter = 0;

	/**
	 * @author SprintUtilities
	 * @reason Disable sprinting without required equipment
	 */
	@ModifyVariable(method = "setSprinting", at = @At("HEAD"), argsOnly = true)
	public boolean sprintutilities$modifySprinting(boolean sprinting) {
		if (Config.SERVER.enableSprint.get()) return sprinting;

		LivingEntity entity = (LivingEntity) (Object) this;
		if (!(entity instanceof Player player)) return sprinting;

		boolean gameModes = player.isCreative() || player.isSpectator();
		boolean flying = player.getAbilities().flying;
		boolean underwater = player.isUnderWater();
		boolean hasBoots = sprintutilities$hasRequiredEquipmentEquipped(player, false);
		boolean hasFlippers = sprintutilities$hasRequiredEquipmentEquipped(player, true);

		if (!flying && !underwater && !hasBoots && !gameModes) return false;
		if (!flying && underwater && !hasFlippers && !gameModes) return false;
		return sprinting;
	}

	@Inject(method = "aiStep", at = @At("TAIL"))
	private void sprintutilities$damageEquipmentWhileSprinting(CallbackInfo ci) {
		if (Config.SERVER.enableSprint.get()) return;
		if (!Config.SERVER.enableEquipmentDamage.get()) return;

		LivingEntity entity = (LivingEntity) (Object) this;
		if (!(entity instanceof Player player)) return;
		if (!player.isSprinting()) return;
		if (player.isCreative() || player.isSpectator()) return;

		sprintutilities$tickCounter++;
		if (sprintutilities$tickCounter >= Config.SERVER.damageEquipmentTick.get()) {
			sprintutilities$tickCounter = 0;
			sprintutilities$damageEquipment(player);
		}
	}

	@Unique
	private void sprintutilities$damageEquipment(Player player) {
		TagKey<Item> tag = player.isUnderWater() ? REQUIRED_ITEM_TAG_FLIPPERS : REQUIRED_ITEM_TAG_BOOTS;
		ItemStack stack = EquipmentHelper.findEquipped(player, tag);

		if (!stack.isEmpty() && stack.isDamageableItem()) {
			stack.setDamageValue(Math.min(stack.getDamageValue() + 1, stack.getMaxDamage()));
		}
	}

	@Unique
	private boolean sprintutilities$hasRequiredEquipmentEquipped(Player player, boolean areFlippers) {
		TagKey<Item> tag = areFlippers ? REQUIRED_ITEM_TAG_FLIPPERS : REQUIRED_ITEM_TAG_BOOTS;
		return !EquipmentHelper.findEquipped(player, tag).isEmpty();
	}
}