package io.github.arctanmc.arctan;

import io.github.arctanmc.arctan.rpc.DiscordRPC;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArctanClient implements ClientModInitializer {
	public static final String MOD_ID = "arctan";
	public static final Logger LOGGER = LogManager.getLogger("ArctanClient");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initialized");
		DiscordRPC.init();
	}
}
