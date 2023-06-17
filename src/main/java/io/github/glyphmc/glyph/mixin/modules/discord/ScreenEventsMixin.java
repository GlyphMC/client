/*
 * Copyright (c) 2023 GlyphMC
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

package io.github.glyphmc.glyph.mixin.modules.discord;

import io.github.glyphmc.glyph.rpc.RPCEvents;
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
