/*
 * Copyright (c) 2022 ArctanMC
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

package io.github.arctanmc.arctan.mixin.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.arctanmc.arctan.ArctanClient;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractWidget.class)
public class AbstractWidgetMixin {
	private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation(ArctanClient.MOD_ID, "textures/gui/widgets.png");

	@Redirect(method = "renderButton", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"))
	private void renderImage(int i, ResourceLocation resourceLocation) {
		RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
	}
}
