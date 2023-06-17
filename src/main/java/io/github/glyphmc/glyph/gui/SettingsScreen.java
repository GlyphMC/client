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

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.SpruceTexts;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.option.SpruceSimpleActionOption;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class SettingsScreen extends SpruceScreen {

	private final Screen parent;
	private final SpruceOption discord;

	public SettingsScreen(Screen parent) {
		super(Component.translatable("glyph.client.settings.title"));
		this.parent = parent;
		this.discord = SpruceSimpleActionOption.of("glyph.client.settings.discord", button -> this.minecraft.setScreen(new DiscordSettingsScreen(this)));
	}

	@Override
	protected void init() {
		super.init();
		int buttonHeight = 20;
		this.addRenderableWidget(this.discord.createWidget(Position.of(this, this.width / 2 - 75, this.height / 2 - buttonHeight), 150));
		this.addRenderableWidget(new SpruceButtonWidget(Position.of(this, this.width / 2 - 75, this.height - 29), 150, buttonHeight, SpruceTexts.GUI_DONE, button -> {
			this.minecraft.setScreen(this.parent);
		}));
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(this.parent);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		graphics.drawCenteredString(this.font, this.title, this.width / 2, 8, Color.WHITE.getRGB());
	}
}
