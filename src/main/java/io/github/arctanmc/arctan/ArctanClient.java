/*
 * Copyright (c) 2022 ArctanMC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.arctanmc.arctan;

import io.github.arctanmc.arctan.config.ArctanConfig;
import io.github.arctanmc.arctan.module.Module;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class ArctanClient implements ClientModInitializer {
	public static final String MOD_ID = "arctan";
	public static final Logger LOGGER = LogManager.getLogger("ArctanClient");

	public static final ArctanConfig CONFIG = ArctanConfig.load(QuiltLoader.getConfigDir().resolve(MOD_ID+ ".json"));
	public static final boolean DEBUG = QuiltLoader.isDevelopmentEnvironment();
 
	@Override
	public void onInitializeClient(ModContainer mod) {
		Module.loadAll();
		LOGGER.info("Initialized");
	}
}
