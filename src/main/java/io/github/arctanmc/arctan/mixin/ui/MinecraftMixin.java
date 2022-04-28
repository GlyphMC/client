package io.github.arctanmc.arctan.mixin.ui;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Minecraft.class)
public class MinecraftMixin {

	/*
	 * This mixin is for changing the window title prefix from "Minecraft* <version>" to "Arctan Client * <version>"
	 */
	@ModifyArg(method = "createTitle", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;<init>(Ljava/lang/String;)V"))
	public String arctan$createTitle(String str) {
		return "Arctan Client ";
	}
}
