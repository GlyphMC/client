package io.github.arctanmc.arctan.module;

import io.github.arctanmc.arctan.ArctanClient;

/**
 * @author Maximumpower55
 */
public class SimpleModule implements Module {
	private final String id;

	public SimpleModule(String id) {
		this.id = id;
	}

	@Override
	public void onEnable() {
		if (ArctanClient.DEBUG) ArctanClient.LOGGER.info("{} Module Enabled.", id);
	}

	@Override
	public void onDisable() {
		if (ArctanClient.DEBUG) ArctanClient.LOGGER.info("{} Module Disabled.", id);
	}

	@Override
	public String getId() {
		return id;
	}
}
