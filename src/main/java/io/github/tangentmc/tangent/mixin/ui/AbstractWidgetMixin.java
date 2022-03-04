package io.github.tangentmc.tangent.mixin.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.tangentmc.tangent.TangentClient;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractWidget.class)
public class AbstractWidgetMixin {
	private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation(TangentClient.MOD_ID, "textures/gui/widgets.png");

	@Redirect(method = "renderButton", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"))
	private void renderImage(int i, ResourceLocation resourceLocation) {
		RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
	}
}
