/*
 * Copyright (c) 2022 ArctanMC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.arctanmc.arctan.mixin.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import io.github.arctanmc.arctan.ArctanClient;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

	@Shadow
	private RealmsNotificationsScreen realmsNotificationsScreen;

	protected TitleScreenMixin(Component component) {
		super(component);
	}

	@Shadow
	protected abstract boolean realmsNotificationsEnabled();

	@Shadow
	protected abstract void init();

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I"))
	private int arctan$changeText(GuiGraphics graphics, Font font, String text, int x, int y, int color) {
		graphics.drawString(font, "Arctan Client (" + SharedConstants.getCurrentVersion().getName() + ")", x, y, color);
		return x; // ????
	}

	@Inject(method = "init", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/components/Button;ACCESSIBILITY_TEXTURE:Lnet/minecraft/resources/ResourceLocation;"), cancellable = true)
	private void stopAccessibility(CallbackInfo ci) {
		ci.cancel();

		assert this.minecraft != null;
		this.minecraft.setConnectedToRealms(false);
		if (this.minecraft.options.realmsNotifications().get() && this.realmsNotificationsScreen == null) {
			this.realmsNotificationsScreen = new RealmsNotificationsScreen();
		}

		if (this.realmsNotificationsEnabled()) {
			this.realmsNotificationsScreen.init(this.minecraft, this.width, this.height);
		}

	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/PanoramaRenderer;render(FF)V"))
	private void renderImage(PanoramaRenderer instance, float deltaT, float alpha) {
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tesselator.getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderTexture(0, new ResourceLocation(ArctanClient.MOD_ID, "textures/gui/background.png"));
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
		bufferBuilder.vertex(0.0, this.height, 0.0).uv(0.0F, 1.0F + (float) 0).color(160, 160, 160, 255).endVertex();
		bufferBuilder.vertex(this.width, this.height, 0.0)
				.uv(1.0F, 1.0F + (float) 0)
				.color(160, 160, 160, 255)
				.endVertex();
		bufferBuilder.vertex(this.width, 0.0, 0.0).uv(1.0F, (float) 0).color(160, 160, 160, 255).endVertex();
		bufferBuilder.vertex(0.0, 0.0, 0.0).uv(0.0F, (float) 0).color(160, 160, 160, 255).endVertex();
		tesselator.end();

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, AbstractWidget.WIDGETS_LOCATION);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIFFIIII)V"))
	private void dontBlit(PoseStack poseStack, int i, int j, int k, int l, float m, float n, int o, int p, int q, int r) {

	}
}
