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

package io.github.glyphmc.glyph.mixin.modules.damagetilt;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public class BobHurtMixin {
	/**
	 * @author Akashii_Kun
	 */
	@Redirect(method = "bobHurt", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;hurtDir:F"))
	private float editHurtDirection(LivingEntity entity) {
		return (float) (Mth.atan2(Mth.lerp(Minecraft.getInstance().getFrameTime(), entity.zo - entity.getZ(), entity.getZ()), Mth.lerp(Minecraft.getInstance().getFrameTime(), entity.xo - entity.getX(), entity.getX())) * (180D / Math.PI) - (double) entity.getYRot());
	}
}
