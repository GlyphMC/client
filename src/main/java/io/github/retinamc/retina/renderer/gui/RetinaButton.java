package io.github.retinamc.retina.renderer.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import io.github.retinamc.retina.RetinaClient;
import io.github.retinamc.retina.mixin.MinecraftMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Consumer;
import java.util.function.IntSupplier;

@Environment(EnvType.CLIENT)
public class RetinaButton extends RetinaAbstractButton {
	public static final RetinaButton.OnTooltip NO_TOOLTIP = (button, matrices, mouseX, mouseY) -> {

	};
	protected final RetinaButton.OnPress onPress;
	protected final RetinaButton.OnTooltip onTooltip;

	@Environment(EnvType.CLIENT)
	public static interface OnTooltip {
		void onTooltip(RetinaButton param1Button, PoseStack param1PoseStack, int param1Int1, int param1Int2);

		default void narrateTooltip(Consumer<Component> consumer) {}
	}

	public RetinaButton(int i, int j, int k, int l, Component component, RetinaButton.OnPress onPress) {
		this(i, j, k, l, component, onPress, NO_TOOLTIP);
	}

	public RetinaButton(int i, int j, int k, int l, Component component, RetinaButton.OnPress onPress, RetinaButton.OnTooltip onTooltip) {
		super(i, j, k, l, component);

		this.onPress = onPress;
		this.onTooltip = onTooltip;
	}


	public void onPress() {
		this.onPress.onPress(this);
	}


	@Override
	public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		Minecraft minecraft = Minecraft.getInstance();
		Font font = minecraft.font;


		RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		int i = getYImage(isHoveredOrFocused());

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();

		blitStuff(poseStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height, true);
		blitStuff(poseStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height, true);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);

		blitStuff(poseStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height, false);
		blitStuff(poseStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height, false);

		renderBg(poseStack, minecraft, mouseX, mouseY);

		int j = this.active ? 16777215 : 10526880;
		drawCenteredString(poseStack, font, getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);

		if (isHoveredOrFocused()) {
			renderToolTip(poseStack, mouseX, mouseY);
		}
	}

	public void blitStuff(PoseStack poseStack, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight, boolean internal) {
		blitStuff(poseStack, x, y, this.getBlitOffset(), (float)uOffset, (float)vOffset, uWidth, vHeight, 256, 256, internal);
	}

	/**
	 * Draws a textured rectangle from a region in a texture.
	 *
	 * <p>The width and height of the region are the same as
	 * the dimensions of the rectangle.
	 *
	 * @param poseStack the matrix stack used for rendering
	 * @param x the X coordinate of the rectangle
	 * @param y the Y coordinate of the rectangle
	 * @param blitOffset the Z coordinate of the rectangle
	 * @param uOffset the left-most coordinate of the texture region
	 * @param vOffset the top-most coordinate of the texture region
	 * @param uWidth the width of the rectangle
	 * @param vHeight the height of the rectangle
	 * @param textureHeight the height of the entire texture
	 * @param textureWidth the width of the entire texture
	 */
	public static void blitStuff(
		PoseStack poseStack, int x, int y, int blitOffset, float uOffset, float vOffset, int uWidth, int vHeight, int textureHeight, int textureWidth, boolean internal
	) {
		innerBlitStuff(poseStack, x, x + uWidth, y, y + vHeight, blitOffset, uWidth, vHeight, uOffset, vOffset, textureHeight, textureWidth, internal);
	}
	private static void innerBlitStuff(
		PoseStack poseStack,
		int x1,
		int x2,
		int y1,
		int y2,
		int blitOffset,
		int uWidth,
		int vHeight,
		float uOffset,
		float vOffset,
		int textureWidth,
		int textureHeight, boolean internal
	) {
		innerBlit(
			poseStack.last().pose(),
			x1,
			x2,
			y1,
			y2,
			blitOffset,
			(uOffset + 0.0F) / (float)textureWidth,
			(uOffset + (float)uWidth) / (float)textureWidth,
			(vOffset + 0.0F) / (float)textureHeight,
			(vOffset + (float)vHeight) / (float)textureHeight, internal
		);
	}

	private static void innerBlit(Matrix4f matrix, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV, boolean internal) {
		if (internal) {
			RetinaClient.blur.setSampler("DiffuseSampler", Minecraft.getInstance().getMainRenderTarget()::getColorTextureId);
			Minecraft minecraft = Minecraft.getInstance();

			RetinaClient.blur.safeGetUniform("ProjMat").set(Matrix4f.orthographic(0.0F, Minecraft.getInstance().getMainRenderTarget().width, Minecraft.getInstance().getMainRenderTarget().height, 0.0F, 0.1F, 1000.0F));
			RetinaClient.blur.safeGetUniform("InSize").set((float)minecraft.getWindow().getWidth(), (float)minecraft.getWindow().getHeight());
			RetinaClient.blur.safeGetUniform("OutSize").set((float)minecraft.getWindow().getWidth(), (float)minecraft.getWindow().getHeight());
			RetinaClient.blur.safeGetUniform("Time").set(Minecraft.getInstance().getFrameTime());
			RetinaClient.blur.safeGetUniform("ScreenSize").set((float)minecraft.getWindow().getWidth(), (float)minecraft.getWindow().getHeight());
			Minecraft.getInstance().getMainRenderTarget().bindWrite(true);

		}
		//RenderSystem.setShader(GameRenderer::getPositionTexShader);
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, internal ? DefaultVertexFormat.POSITION : DefaultVertexFormat.POSITION_TEX);
		if (internal) {
			bufferBuilder.vertex(matrix, (float)x1, (float)y2, (float)blitOffset).endVertex();
			bufferBuilder.vertex(matrix, (float)x2, (float)y2, (float)blitOffset).endVertex();
			bufferBuilder.vertex(matrix, (float)x2, (float)y1, (float)blitOffset).endVertex();
			bufferBuilder.vertex(matrix, (float)x1, (float)y1, (float)blitOffset).endVertex();
		} else {
			bufferBuilder.vertex(matrix, (float)x1, (float)y2, (float)blitOffset).uv(minU, maxV).endVertex();
			bufferBuilder.vertex(matrix, (float)x2, (float)y2, (float)blitOffset).uv(maxU, maxV).endVertex();
			bufferBuilder.vertex(matrix, (float)x2, (float)y1, (float)blitOffset).uv(maxU, minV).endVertex();
			bufferBuilder.vertex(matrix, (float)x1, (float)y1, (float)blitOffset).uv(minU, minV).endVertex();
		}
		bufferBuilder.end();
		if (internal) {
			RetinaClient.blur.apply();

			BufferUploader._endInternal(bufferBuilder);
			RetinaClient.blur.clear();

		} else {
			BufferUploader.end(bufferBuilder);
		}
	}

	public void renderToolTip(PoseStack poseStack, int relativeMouseX, int relativeMouseY) {
		this.onTooltip.onTooltip(this, poseStack, relativeMouseX, relativeMouseY);
	}


	public void updateNarration(NarrationElementOutput narrationElementOutput) {
		defaultButtonNarrationText(narrationElementOutput);
		this.onTooltip.narrateTooltip(text -> narrationElementOutput.add(NarratedElementType.HINT, text));
	}

	@Environment(EnvType.CLIENT)
	public interface OnPress {
		void onPress(RetinaButton param1Button);
	}
}
