package io.github.retinamc.retina.renderer.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RetinaTitleScreen extends Screen {

	public static final CubeMap CUBE_MAP = new CubeMap(new ResourceLocation("textures/gui/title/background/panorama"));
	private final PanoramaRenderer panorama = new PanoramaRenderer(CUBE_MAP);

	protected RetinaTitleScreen(Component component) {
		super(component);
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		this.panorama.render(partialTick, 1.0F /* TODO: fade*/);
	}
}
