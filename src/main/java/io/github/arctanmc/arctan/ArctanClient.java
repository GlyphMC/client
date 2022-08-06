package io.github.arctanmc.arctan;

import io.github.arctanmc.arctan.rpc.DiscordRPC;
import net.minecraft.ChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.Version;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class ArctanClient implements ClientModInitializer {

	public static final String MOD_ID = "arctan";
	private static Version VERSION;
	public static final Logger LOGGER = LogManager.getLogger("ArctanClient");

	@Override
	public void onInitializeClient(ModContainer mod) {
		LOGGER.info("Initialized");
		VERSION = mod.metadata().version();
		//DiscordRPC.init();
	}

	public static String getVersion() {
		return ChatFormatting.GREEN + VERSION.raw();
	}

}
