package io.github.arctanmc.arctan.rpc;

import com.github.benmanes.caffeine.cache.Cache;
import de.jcm.discordgamesdk.LobbyManager;
import de.jcm.discordgamesdk.Result;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.activity.ActivityPartySize;
import de.jcm.discordgamesdk.user.DiscordUser;
import io.github.arctanmc.arctan.ArctanClient;
import io.github.arctanmc.arctan.util.API;
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
		 * @param cache Our Discord ID and Minecraft UUID cache.
		 * @param secret The activity secret.
		 */
		public static void handle(Cache<Long, String> cache, String secret) {
			log("Trying to join activity with secret " + secret);
			LobbyManager lobbyManager = DiscordRPC.getCore().lobbyManager();
			List<String> uuidList = new ArrayList<>();
			lobbyManager.connectLobbyWithActivitySecret(secret, (result, lobby) -> {
				if (result == Result.OK) {
					long lobbyId = lobby.getId();
					log("Joined activity with secret " + secret);
					log("Lobby ID: " + lobbyId);
					lobbyManager.getMemberUserIds(lobbyId).forEach(userId -> {
						String uuid = null;
						if (cache.getIfPresent(userId) == null) {
							uuid = API.getMinecraftUuid(userId);
							cache.put(userId, uuid);
							debug("Cached user " + userId + " with UUID " + uuid);
						} else {
							debug("User " + userId + " is already cached");
						}
						uuidList.add(uuid);
					});
				}
			});
			// TODO: Handle this in Minecraft.
			uuidList.forEach(RPCUtils::log);
		}

	}

	private static void log(String message) {
		ArctanClient.LOGGER.info(message);
	}

	private static void error(String message) {
		ArctanClient.LOGGER.error(message);
	}

	private static void debug(String message) {
		ArctanClient.LOGGER.debug(message);
	}

	public static void updateActivity(String state, String partyId, String secret) {
		Activity activity = new Activity();
		activity.setDetails("Playing Minecraft " + SharedConstants.getCurrentVersion().getName());
		activity.setState(state);
		activity.timestamps().setStart(INSTANT);
		activity.assets().setLargeImage("arctan_client");
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
				case OK -> {
					ArctanClient.LOGGER.info("Updated Rich Presence");
				}
			}
		});
	}

	public enum RPCState {
		INIT,
		IN_GAME,
		MAIN_MENU
	}

	public enum GameType {
		SINGLEPLAYER,
		MULTIPLAYER
	}

}
