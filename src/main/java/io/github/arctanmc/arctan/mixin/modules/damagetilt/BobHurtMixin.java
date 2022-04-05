package io.github.arctanmc.arctan.mixin.modules.damagetilt;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public class BobHurtMixin {
	/**
	 * @author Akashii_Kun
	 */
	@Redirect(method = "bobHurt", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;hurtDir:F"))
	private float editHurtDirection(LivingEntity entity) {
		return (float) (Mth.atan2(Mth.lerp(Minecraft.getInstance().getFrameTime(), entity.zo - entity.getZ(), entity.getZ()), Mth.lerp(Minecraft.getInstance().getFrameTime(), entity.xo - entity.getX(), entity.getX())) * (180D / Math.PI) - (double) entity.getYRot());
	}
}