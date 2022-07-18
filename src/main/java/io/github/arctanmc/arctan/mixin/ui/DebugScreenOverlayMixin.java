package io.github.arctanmc.arctan.mixin.ui;

import io.github.arctanmc.arctan.ArctanClient;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {

	@Inject(method = "getSystemInformation", at = @At("RETURN"))
	public void arctan$appendInformation(CallbackInfoReturnable<List<String>> cir) {
		List<String> strings = cir.getReturnValue();
		strings.add("");
		strings.add(String.format("[Arctan] Version: %s", ArctanClient.getVersion()));
		strings.add("");
	}

}
