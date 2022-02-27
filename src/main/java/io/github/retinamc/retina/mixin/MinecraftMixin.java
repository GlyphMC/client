package io.github.retinamc.retina.mixin;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import io.github.retinamc.retina.RetinaClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.ShaderInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(method = "<init>", at = @At("RETURN"))
	private void stop(GameConfig gameConfig, CallbackInfo ci) {
		try {
			RetinaClient.blur = new EffectInstance(Minecraft.getInstance().getResourceManager(), "blur");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
