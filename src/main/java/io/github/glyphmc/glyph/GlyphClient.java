/*
 * Copyright (c) 2023 GlyphMC
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

package io.github.glyphmc.glyph;

import io.github.glyphmc.glyph.config.GlyphConfig;
import io.github.glyphmc.glyph.rpc.DiscordRPC;
import net.minecraft.ChatFormatting;
import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.Version;
import org.quiltmc.loader.api.config.QuiltConfig;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import java.io.IOException;

public class GlyphClient implements ClientModInitializer {

	public static final String MOD_ID = "glyph";
	public static final Long CLIENT_ID = 947514926589693983L;
	private static Version VERSION;
	public static final Logger LOGGER = LogManager.getLogger("GlyphClient");
	public static final OkHttpClient OKHTTP = new OkHttpClient();
	public static final GlyphConfig CONFIG = QuiltConfig.create("glyph", "config", GlyphConfig.class);

	@Override
	public void onInitializeClient(ModContainer mod) {
		LOGGER.info("Initialized");
		VERSION = mod.metadata().version();
		FriendsSystem friendsSystem = new FriendsSystem();
		try {
			if (CONFIG.general.discordRPC) {
				DiscordRPC.init();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getVersion() {
		return ChatFormatting.GREEN + VERSION.raw();
	}

}
