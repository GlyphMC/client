package io.github.arctanmc.arctan.mixin.modules.discord;

import io.github.arctanmc.arctan.rpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class GameSDKMixin {
	@Inject(method = "runTick", at = @At("TAIL"))
	private void runCallbacks(boolean tick, CallbackInfo ci) {
		DiscordRPC.getCore().runCallbacks();
	}

}
