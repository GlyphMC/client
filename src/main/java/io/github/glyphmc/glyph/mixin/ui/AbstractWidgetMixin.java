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

package io.github.glyphmc.glyph.mixin.ui;

import io.github.glyphmc.glyph.GlyphClient;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractButton.class)
public class AbstractWidgetMixin {
	@Unique
	private static final ResourceLocation GLYPH_WIDGETS_LOCATION = new ResourceLocation(GlyphClient.MOD_ID, "textures/gui/widgets.png");
	@Redirect(method = "renderWidget", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/components/AbstractButton;WIDGETS_LOCATION:Lnet/minecraft/resources/ResourceLocation;"))
	private ResourceLocation renderImage() {
		return GLYPH_WIDGETS_LOCATION;
	}
}
