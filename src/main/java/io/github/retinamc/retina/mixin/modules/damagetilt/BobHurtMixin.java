package io.github.retinamc.retina.mixin.modules.damagetilt;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(GameRenderer.class)
public class BobHurtMixin {
	private Minecraft minecraft;

	/**
	 * @author Akashii_Kun
	 */
	@Overwrite
	private void bobHurt(PoseStack matrixStack, float partialTicks) {
		if (this.minecraft.getCameraEntity() instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity)this.minecraft.getCameraEntity();

			float f = livingEntity.hurtTime - partialTicks;

			if (livingEntity.isDeadOrDying()) {
				float f1 = Math.min(livingEntity.deathTime + partialTicks, 20.0F);

				matrixStack.mulPose(Vector3f.ZP.rotationDegrees(40.0F - 8000.0F / (f1 + 200.0F)));
			}

			if (f < 0.0F) {
				return;
			}
			f /= livingEntity.hurtDuration;
			f = Mth.sin(f * f * f * f * 3.1415927F);
			float g = livingEntity.hurtDir;

			if(livingEntity instanceof Player player)
			g =	(float)(Mth.atan2(player.zo - player.getZ(), player.xo - player.getX()) * (180D / Math.PI) - (double)player.getYRot());

			matrixStack.mulPose(Vector3f.YP.rotationDegrees(-g));
			matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-f * 14.0F));
			matrixStack.mulPose(Vector3f.YP.rotationDegrees(g));
		}
	}
}
