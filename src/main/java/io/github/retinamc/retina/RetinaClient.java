package io.github.retinamc.retina;

import io.github.retinamc.retina.rpc.DiscordRPC;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RetinaClient implements ClientModInitializer {
    public static final String MOD_ID = "retina";
    public static final Logger LOGGER = LogManager.getLogger("RetinaClient");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initialized");
		DiscordRPC.init();
    }

}
