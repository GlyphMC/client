package io.github.retinamc.retina.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.retinamc.retina.RetinaClient;
import io.github.retinamc.retina.renderer.gui.RetinaButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

	protected TitleScreenMixin(Component component) {
		super(component);

	}

	@Inject(method = "<init>(Z)V", at = @At("RETURN"))
	private void test(boolean bl, CallbackInfo ci) {
		buttonTest = new RetinaButton(this.width / 2, this.height / 2, 200, 20, new TextComponent("Fuck"), new RetinaButton.OnPress() {
			@Override
			public void onPress(RetinaButton button) {

			}
		});
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void renderCancel(PoseStack poseStack, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
		ci.cancel();

		if (this.fadeInStart == 0L && this.fading) {
			this.fadeInStart = Util.getMillis();
		}

		float f = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;

		this.panorama.render(partialTick, Mth.clamp(f, 0.0F, 1.0F));

		this.buttonTest.render(poseStack, mouseX, mouseY, partialTick);
	}
}
