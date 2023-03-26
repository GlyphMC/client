package io.github.arctanmc.arctan.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.SpruceTexts;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.option.SpruceSimpleActionOption;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class SettingsScreen extends SpruceScreen {

	private final Screen parent;
	private final SpruceOption discord;

	public SettingsScreen(Screen parent) {
		super(Component.translatable("arctan.client.settings.title"));
		this.parent = parent;
		this.discord = SpruceSimpleActionOption.of("arctan.client.settings.discord", button -> this.minecraft.setScreen(new DiscordSettingsScreen(this)));
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
	public void renderTitle(PoseStack matrices, int mouseX, int mouseY, float delta) {
		drawCenteredString(matrices, this.font, this.title, this.width / 2, 8, Color.WHITE.getRGB());
	}

}
