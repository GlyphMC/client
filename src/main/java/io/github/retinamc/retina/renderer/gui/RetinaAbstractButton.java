package io.github.retinamc.retina.renderer.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public abstract class RetinaAbstractButton extends RetinaAbstractWidget {

	public RetinaAbstractButton(int i, int j, int k, int l, Component component) {
		super(i, j, k, l, component);
	}

	public abstract void onPress();

	public void onClick(double mouseX, double mouseY) {
		onPress();
	}


	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (!this.active || !this.visible) {
			return false;
		}
		if (keyCode == 257 || keyCode == 32 || keyCode == 335) {
			playDownSound(Minecraft.getInstance().getSoundManager());
			onPress();
			return true;
		}
		return false;
	}

}
