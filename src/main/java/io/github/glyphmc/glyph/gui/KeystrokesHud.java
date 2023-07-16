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

package io.github.glyphmc.glyph.gui;

import dev.lambdaurora.spruceui.hud.Hud;
import io.github.glyphmc.glyph.GlyphClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class KeystrokesHud extends Hud {

	public static int cps = 0;

	public KeystrokesHud() {
		super(new ResourceLocation(GlyphClient.MOD_ID, "textures/gui/keystrokes.png"));
	}

	@Override
	public void init(Minecraft client, int screenWidth, int screenHeight) {
		super.init(client, screenWidth, screenHeight);
	}

	@Override
	public void render(GuiGraphics graphics, float tickDelta) {
		String text = "CPS: " + cps;
		System.out.println(text);
	}

}
