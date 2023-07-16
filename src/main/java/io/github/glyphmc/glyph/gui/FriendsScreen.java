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
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.text.SpruceTextFieldWidget;
import io.github.glyphmc.glyph.util.API;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.concurrent.atomic.AtomicReference;

public class FriendsScreen extends SpruceScreen {

	private final Screen parent;

	public FriendsScreen(Screen parent) {
		super(Component.literal("Friends"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		super.init();

		AtomicReference<String> input = new AtomicReference<>("");

		SpruceTextFieldWidget widget = new SpruceTextFieldWidget(
			Position.of(this, this.width / 2 - 100, this.height / 2 - 10),
			200, 20, Component.literal(""));
		widget.setChangedListener(input::set);
		this.addRenderableWidget(widget);

		SpruceButtonWidget button = new SpruceButtonWidget(
			Position.of(this, this.width / 2 - 100, this.height / 2 + 20),
			200, 20, Component.literal("Add Friend"), b -> {
			widget.setText("");
			SystemToast toast = SystemToast.multiline(this.minecraft, SystemToast.SystemToastIds.PERIODIC_NOTIFICATION,
					Component.literal("Added Friend"), Component.literal("Added " + input.get() + " to your friends list."));
			Minecraft.getInstance().getToasts().addToast(toast);
			API.addFriend(input.get());
		});
		this.addRenderableWidget(button);

		this.addRenderableWidget(new SpruceButtonWidget(
			Position.of(this, this.width / 2, this.height - 30),
			150, 20, SpruceTexts.GUI_DONE, b -> this.minecraft.setScreen(this.parent)).asVanilla());
	}

	@Override
	public void renderTitle(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		graphics.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
	}

}
