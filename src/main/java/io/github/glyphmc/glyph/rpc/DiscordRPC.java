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

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.DiscordEventAdapter;
import de.jcm.discordgamesdk.user.DiscordUser;
import de.jcm.discordgamesdk.user.Relationship;
import io.github.glyphmc.glyph.GlyphClient;

import java.io.IOException;

import static io.github.glyphmc.glyph.rpc.RPCUtils.*;

public class DiscordRPC {

	private static Core core;

	public static void init() throws IOException {
		Core.initDownload();
		CreateParams params = new CreateParams();
		params.setClientID(GlyphClient.CLIENT_ID);
		params.setFlags(CreateParams.getDefaultFlags());
		params.registerEventHandler(new DiscordEventAdapter() {
			// These are the events that I think we will need to handle.
			@Override
			public void onActivityJoin(String secret) {
				ActivityJoinHandler.handle(secret);
			}

			@Override
			public void onActivityJoinRequest(DiscordUser user) {
				ActivityJoinRequestHandler.handle(user);
			}

			@Override
			public void onRelationshipRefresh() {
				super.onRelationshipRefresh();
			}

			@Override
			public void onRelationshipUpdate(Relationship relationship) {
				super.onRelationshipUpdate(relationship);
			}

			@Override
			public void onLobbyUpdate(long lobbyId) {
				super.onLobbyUpdate(lobbyId);
			}

			@Override
			public void onMemberConnect(long lobbyId, long userId) {
				super.onMemberConnect(lobbyId, userId);
			}

			@Override
			public void onMemberDisconnect(long lobbyId, long userId) {
				super.onMemberDisconnect(lobbyId, userId);
			}
		});
		try {
			core = new Core(params);
			GlyphClient.LOGGER.info("Initialized Rich Presence");
			new RPCEvents();
		} catch (Exception e) {
			GlyphClient.LOGGER.error("Failed to initialize Rich Presence", e);
		}
		updateActivity("In the Menu", null, null);
	}

	public static Core getCore() {
		return core;
	}

}
