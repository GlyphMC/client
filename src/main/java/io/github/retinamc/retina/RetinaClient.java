package io.github.retinamc.retina;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.EffectInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RetinaClient implements ClientModInitializer {
    public static final String MOD_ID = "retina";
    public static final Logger LOGGER = LogManager.getLogger("RetinaClient");

	public static EffectInstance blur;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initialized");

	}

}
