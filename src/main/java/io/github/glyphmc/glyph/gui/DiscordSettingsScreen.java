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
import dev.lambdaurora.spruceui.widget.SpruceLabelWidget;
import io.github.glyphmc.glyph.GlyphClient;
import io.github.glyphmc.glyph.util.API;
import io.github.glyphmc.glyph.util.Player;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import okhttp3.HttpUrl;

import java.awt.*;

public class DiscordSettingsScreen extends SpruceScreen {

	private final Screen parent;
	private final SpruceOption linkToDiscord;
	private final String uuid = Minecraft.getInstance().getUser().getUuid().replace("-", "");


	public DiscordSettingsScreen(Screen parent) {
		super(Component.translatable("glyph.client.settings.discord.title"));
		this.parent = parent;
		this.linkToDiscord = SpruceSimpleActionOption.of("glyph.client.settings.discord.link", button -> {
			HttpUrl url = new HttpUrl.Builder()
					.scheme("https")
					.host("discord.com")
					.addPathSegment("oauth2")
					.addPathSegment("authorize")
					.addQueryParameter("response_type", "code")
					.addQueryParameter("client_id", GlyphClient.CLIENT_ID.toString())
					.addQueryParameter("scope", "identify")
					.addQueryParameter("state", Minecraft.getInstance().getUser().getUuid().replace("-", ""))
					.addQueryParameter("prompt", "consent")
					.addQueryParameter("redirect_uri", "http://localhost:8080/verify") // TODO CHANGE THIS
					.build();
			Util.getPlatform().openUri(String.valueOf(url));
		}, Component.translatable("glyph.client.settings.discord.link.tooltip"));
	}

	@Override
	protected void init() {
		super.init();
		int buttonHeight = 20;
		this.addRenderableWidget(new SpruceButtonWidget(Position.of(this, this.width / 2 - 75, this.height - 29), 150, buttonHeight, SpruceTexts.GUI_DONE, button -> {
			this.minecraft.setScreen(this.parent);
		}));
		Player player = API.getPlayerByUuid(uuid);
		if (player != null) { // TODO ADD GUI ELEMENTS
			this.addRenderableWidget(new SpruceLabelWidget(Position.of(this, this.width / 2 - 150, this.height / 2 + buttonHeight), Component.literal(player.discordUser().tag()), 300));
		} else {
			this.addRenderableWidget(this.linkToDiscord.createWidget(Position.of(this, this.width / 2 - 75, this.height / 2 - buttonHeight), 150));
		}
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(this.parent);
	}

	@Override
	public void renderTitle(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		graphics.drawCenteredString(this.font, this.title, this.width / 2, 8, Color.WHITE.getRGB());
	}
}
