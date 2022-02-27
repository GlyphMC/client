package io.github.retinamc.retina.renderer.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class RetinaButton extends RetinaAbstractButton {

	public static final RetinaButton.OnTooltip NO_TOOLTIP = (button, matrices, mouseX, mouseY) -> {};
	protected final RetinaButton.OnPress onPress;
	protected final RetinaButton.OnTooltip onTooltip;

	@Environment(EnvType.CLIENT)
	public static interface OnTooltip {
		void onTooltip(RetinaButton param1Button, PoseStack param1PoseStack, int param1Int1, int param1Int2);

		default void narrateTooltip(Consumer<Component> consumer) {}
	}

	public RetinaButton(int i, int j, int k, int l, Component component, RetinaButton.OnPress onPress) {
		this(i, j, k, l, component, onPress, NO_TOOLTIP);
	}

	public RetinaButton(int i, int j, int k, int l, Component component, RetinaButton.OnPress onPress, RetinaButton.OnTooltip onTooltip) {
		super(i, j, k, l, component);

		this.onPress = onPress;
		this.onTooltip = onTooltip;
	}


	public void onPress() {
		this.onPress.onPress(this);
	}


	public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		super.renderButton(poseStack, mouseX, mouseY, partialTick);

		if (isHoveredOrFocused()) {
			renderToolTip(poseStack, mouseX, mouseY);
		}
	}


	public void renderToolTip(PoseStack poseStack, int relativeMouseX, int relativeMouseY) {
		this.onTooltip.onTooltip(this, poseStack, relativeMouseX, relativeMouseY);
	}


	public void updateNarration(NarrationElementOutput narrationElementOutput) {
		defaultButtonNarrationText(narrationElementOutput);
		this.onTooltip.narrateTooltip(text -> narrationElementOutput.add(NarratedElementType.HINT, text));
	}

	@Environment(EnvType.CLIENT)
	public static interface OnPress {
		void onPress(RetinaButton param1Button);
	}

}
