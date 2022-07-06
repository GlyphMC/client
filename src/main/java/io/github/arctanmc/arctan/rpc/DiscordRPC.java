package io.github.arctanmc.arctan.rpc;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.DiscordEventAdapter;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.user.DiscordUser;
import de.jcm.discordgamesdk.user.Relationship;
import io.github.arctanmc.arctan.ArctanClient;
import net.minecraft.SharedConstants;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

public class DiscordRPC {

	private static Core core;

	public static void init() throws IOException {
		Core.initDownload();
		String partyId = UUID.randomUUID().toString();
		String secret = UUID.randomUUID().toString();
		CreateParams params = new CreateParams();
		params.setClientID(ArctanClient.CLIENT_ID);
		params.setFlags(CreateParams.getDefaultFlags());
		params.registerEventHandler(new DiscordEventAdapter() {
			// These are the events that I think we will need to handle.
			@Override
			public void onActivityJoin(String secret) {
				super.onActivityJoin(secret);
			}

			@Override
			public void onActivityJoinRequest(DiscordUser user) {
				ArctanClient.LOGGER.info("Join request from " + user.getDiscriminator());
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
			ArctanClient.LOGGER.info("Initialized Rich Presence");
		} catch (Exception e) {
			ArctanClient.LOGGER.error("Failed to initialize Rich Presence", e);
		}
		Activity activity = new Activity();
		activity.setDetails("Playing Minecraft " + SharedConstants.getCurrentVersion().getName());
		activity.setState("Arctan Client");
		activity.timestamps().setStart(Instant.now());
		activity.assets().setLargeImage("arctan_client");
		activity.party().setID(partyId);
		activity.secrets().setJoinSecret(secret);
		core.activityManager().updateActivity(activity);
	}

	public static Core getCore() {
		return core;
	}

}
