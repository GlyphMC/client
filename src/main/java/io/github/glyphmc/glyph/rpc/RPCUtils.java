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

package io.github.glyphmc.glyph.rpc;

import de.jcm.discordgamesdk.activity.Activity;
import io.github.glyphmc.glyph.GlyphClient;
import net.minecraft.SharedConstants;

import java.time.Instant;

public class RPCUtils {

	private static final Instant INSTANT = Instant.now();

	public static void updateActivity(String state) {
		Activity activity = new Activity();
		activity.setDetails("Playing Minecraft " + SharedConstants.getCurrentVersion().getName());
		activity.setState(state);
		activity.timestamps().setStart(INSTANT);
		activity.assets().setLargeImage("glyph_client");
		DiscordRPC.getCore().activityManager().updateActivity(activity, result -> {
			switch (result) {
				// TODO handle errors
				case OK -> GlyphClient.LOGGER.debug("Updated Rich Presence -> {}", state);
			}
		});
	}

}
