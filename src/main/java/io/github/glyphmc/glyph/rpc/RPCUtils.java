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

import de.jcm.discordgamesdk.LobbyManager;
import de.jcm.discordgamesdk.Result;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.activity.ActivityPartySize;
import de.jcm.discordgamesdk.user.DiscordUser;
import io.github.glyphmc.glyph.GlyphClient;
import io.github.glyphmc.glyph.util.API;
import io.github.glyphmc.glyph.util.Player;
import net.minecraft.SharedConstants;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RPCUtils {

	private static final Instant INSTANT = Instant.now();

	public static class ActivityJoinRequestHandler {

		/**
		 * Called when a user clicks the "Ask to join" button.
		 * @param user The user that clicked the button.
		 */
		public static void handle(DiscordUser user) {
			log("Activity join request from " + user.getUsername());
		}

	}

	public static class ActivityJoinHandler {

		/**
		 * Called when a user joins from an invite.
		 * @param secret The activity secret.
		 */
		public static void handle(String secret) {
			log("Trying to join activity with secret " + secret);
			LobbyManager lobbyManager = DiscordRPC.getCore().lobbyManager();
			List<Player> playerList = new ArrayList<>();
			lobbyManager.connectLobbyWithActivitySecret(secret, (result, lobby) -> {
				if (result == Result.OK) {
					long lobbyId = lobby.getId();
					log("Joined activity with secret " + secret);
					log("Lobby ID: " + lobbyId);
					lobbyManager.getMemberUserIds(lobbyId).forEach(userId -> {
						Player player = API.getPlayerByDiscordId(String.valueOf(userId));
						playerList.add(player);
					});
				}
			});
			// TODO: Handle this in Minecraft.
			playerList.forEach(player -> log("Player " + player.discordUser().tag() + " joined from activity with secret " + secret));
		}

	}

	private static void log(String message) {
		GlyphClient.LOGGER.info(message);
	}

	public static void updateActivity(String state, String partyId, String secret) {
		Activity activity = new Activity();
		activity.setDetails("Playing Minecraft " + SharedConstants.getCurrentVersion().getName());
		activity.setState(state);
		activity.timestamps().setStart(INSTANT);
		activity.assets().setLargeImage("glyph_client");
		if (partyId != null) {
			activity.party().setID(partyId);
		}
		if (secret != null) {
			activity.secrets().setJoinSecret(secret);
		}
		if ((partyId != null) && (secret != null)) {
			ActivityPartySize size = activity.party().size();
			size.setCurrentSize(1);
			size.setMaxSize(10);
		}
		DiscordRPC.getCore().activityManager().updateActivity(activity, result -> {
			switch (result) {
				// TODO handle errors
				case OK -> GlyphClient.LOGGER.info("Updated Rich Presence -> {}", state);
			}
		});
	}

}
