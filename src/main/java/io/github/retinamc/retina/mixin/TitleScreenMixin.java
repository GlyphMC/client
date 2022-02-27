package io.github.retinamc.retina.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.tterrag.blur.Blur;
import com.tterrag.blur.config.BlurConfig;
import io.github.retinamc.retina.RetinaClient;
import io.github.retinamc.retina.renderer.gui.RetinaButton;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(TitleScreen.class)
@Environment(EnvType.CLIENT)
public class TitleScreenMixin extends Screen {
	@Shadow
	@Final
	private PanoramaRenderer panorama;

	@Shadow
	private long fadeInStart;

	@Shadow
	@Final
	private boolean fading;

	private RetinaButton buttonTest;
	private ManagedShaderEffect blur;

	protected TitleScreenMixin(Component component) {
		super(component);

	}

	@Inject(method = "<init>(Z)V", at = @At("RETURN"))
	private void test(boolean bl, CallbackInfo ci) {
		buttonTest = new RetinaButton(this.width / 2 - 100, this.height / 4 + 48, 200, 20, new TranslatableComponent("menu.singleplayer"), button -> this.minecraft.setScreen(new SelectWorldScreen(this)));
		blur = ShaderEffectManager.getInstance().manage(new ResourceLocation("blur", "shaders/post/fade_in_blur.json"), (shader) -> {
			shader.setUniformValue("Radius", (float) BlurConfig.radius);
		});
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void renderCancel(PoseStack poseStack, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
		ci.cancel();

		if (this.fadeInStart == 0L && this.fading) {
			this.fadeInStart = Util.getMillis();
		}

		float f = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;

		this.panorama.render(partialTick, 0.1F);
		Matrix4f matrix4f = Matrix4f.perspective(85.0, (float)minecraft.getWindow().getWidth() / (float)minecraft.getWindow().getHeight(), 0.05F, 10.0F);
		RenderSystem.backupProjectionMatrix();
		RenderSystem.setProjectionMatrix(matrix4f);
		EffectInstance effectInstance = null;
		/*try {
			effectInstance = new EffectInstance(Minecraft.getInstance().getResourceManager(), "blur");
		} catch (IOException e) {
			e.printStackTrace();
		}
		effectInstance.safeGetUniform("InSize").set((float)this.width, (float)this.height);
		effectInstance.safeGetUniform("OutSize").set((float)this.width, (float)this.height);
		effectInstance.apply();
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
		bufferBuilder.vertex(0.0, 0.0, 500.0).endVertex();
		bufferBuilder.vertex((double)this.width, 0.0, 500.0).endVertex();
		bufferBuilder.vertex((double)this.width, (double)this.height, 500.0).endVertex();
		bufferBuilder.vertex(0.0, (double)this.height, 500.0).endVertex();
		bufferBuilder.end();
		BufferUploader._endInternal(bufferBuilder);

		effectInstance.clear();*/
		blur.render(partialTick);

		RenderSystem.restoreProjectionMatrix();
		this.buttonTest.render(poseStack, mouseX, mouseY, partialTick);
	}
}
