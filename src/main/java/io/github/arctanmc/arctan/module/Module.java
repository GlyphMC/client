package io.github.arctanmc.arctan.module;

import io.github.arctanmc.arctan.ArctanClient;
import io.github.arctanmc.arctan.module.rpc.DiscordRPCModule;

/**
 * @author Maximumpower55
 */
public interface Module {
	static final Module DAMAGETILT = register(new SimpleModule("damagetilt"), true);
	static final Module DISCORD_RPC = register(new DiscordRPCModule(), true);

	private static Module register(Module module, boolean enabledByDefault) {
		ArctanClient.CONFIG.enabledModules.putIfAbsent(module.getId(), enabledByDefault);

		if (module.isEnabled()) module.onEnable();

		try {
			ArctanClient.CONFIG.save();
		} catch (Exception e) {
			//TODO: handle exception
		}

		return module;
	}

	// Had to make this because of class loading stuff.
	static void loadAll() {}

	String getId();

	default void onEnable() {}
	default void onDisable() {}

	default boolean isEnabled() {
		return ArctanClient.CONFIG.enabledModules.get(getId());
	}
}
