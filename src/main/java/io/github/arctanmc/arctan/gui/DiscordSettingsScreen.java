package io.github.arctanmc.arctan.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.SpruceTexts;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.option.SpruceSimpleActionOption;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.SpruceLabelWidget;
import io.github.arctanmc.arctan.ArctanClient;
import io.github.arctanmc.arctan.util.API;
import io.github.arctanmc.arctan.util.User;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import okhttp3.HttpUrl;

import java.awt.*;

public class DiscordSettingsScreen extends SpruceScreen {

	private final Screen parent;
	private final SpruceOption linkToDiscord;
	private final String uuid = Minecraft.getInstance().getUser().getUuid().replace("-", "");


	public DiscordSettingsScreen(Screen parent) {
		super(Component.translatable("arctan.client.settings.discord.title"));
		this.parent = parent;
		this.linkToDiscord = SpruceSimpleActionOption.of("arctan.client.settings.discord.link", button -> {
			HttpUrl url = new HttpUrl.Builder()
					.scheme("https")
					.host("discord.com")
					.addPathSegment("oauth2")
					.addPathSegment("authorize")
					.addQueryParameter("response_type", "code")
					.addQueryParameter("client_id", ArctanClient.CLIENT_ID.toString())
					.addQueryParameter("scope", "identify")
					.addQueryParameter("state", Minecraft.getInstance().getUser().getUuid().replace("-", ""))
					.addQueryParameter("prompt", "consent")
					.addQueryParameter("redirect_uri", "http://localhost:8080/verify") // TODO CHANGE THIS
					.build();
			Util.getPlatform().openUri(String.valueOf(url));
		}, Component.translatable("arctan.client.settings.discord.link.tooltip"));
	}

	@Override
	protected void init() {
		super.init();
		int buttonHeight = 20;
		this.addRenderableWidget(new SpruceButtonWidget(Position.of(this, this.width / 2 - 75, this.height - 29), 150, buttonHeight, SpruceTexts.GUI_DONE, button -> {
			this.minecraft.setScreen(this.parent);
		}));
		User user = API.getUserByUuid(uuid);
		if (user != null) { // TODO ADD GUI ELEMENTS
			this.addRenderableWidget(new SpruceLabelWidget(Position.of(this, this.width / 2 - 150, this.height / 2 + buttonHeight), Component.literal(user.tag()), 300));
		} else {
			this.addRenderableWidget(this.linkToDiscord.createWidget(Position.of(this, this.width / 2 - 75, this.height / 2 - buttonHeight), 150));
		}
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(this.parent);
	}

	@Override
	public void renderTitle(PoseStack matrices, int mouseX, int mouseY, float delta) {
		drawCenteredString(matrices, this.font, this.title, this.width / 2, 8, Color.WHITE.getRGB());
	}

}
