package io.github.tangentmc.tangent;

import io.github.tangentmc.tangent.rpc.DiscordRPC;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TangentClient implements ClientModInitializer {
	public static final String MOD_ID = "tangent";
	public static final Logger LOGGER = LogManager.getLogger("TangentClient");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initialized");
		DiscordRPC.init();
	}
}
