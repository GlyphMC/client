package io.github.retinamc.retina.renderer.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.retinamc.retina.RetinaClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public abstract class RetinaAbstractWidget extends AbstractWidget {

	public static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation(RetinaClient.MOD_ID, "textures/gui/widgets.png");

	public RetinaAbstractWidget(int i, int j, int k, int l, Component component) {
		super(i, j, k, l, component);
	}

	@Override
	public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		Minecraft minecraft = Minecraft.getInstance();
		Font font = minecraft.font;

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);

		int i = getYImage(isHoveredOrFocused());

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		blit(poseStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
		blit(poseStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);

		renderBg(poseStack, minecraft, mouseX, mouseY);

		int j = this.active ? 16777215 : 10526880;
		drawCenteredString(poseStack, font, getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
	}

}
