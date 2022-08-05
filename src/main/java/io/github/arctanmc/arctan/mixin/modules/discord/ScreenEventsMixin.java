package io.github.arctanmc.arctan.mixin.modules.discord;

import io.github.arctanmc.arctan.rpc.RPCEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ScreenEventsMixin {

	@Inject(at = @At("HEAD"), method = "setOverlay")
	public void setOverlay(Overlay overlay, CallbackInfo ci) {
		if (!(overlay instanceof LoadingOverlay)) {
			RPCEvents.getInstance().menuRPC();
		}
	}

	@Inject(at = @At("RETURN"), method = "setScreen")
	public void setScreen(Screen screen, CallbackInfo ci) {
		if (screen instanceof TitleScreen) {
			RPCEvents.getInstance().menuRPC();
		}
	}

}
