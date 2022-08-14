package io.github.arctanmc.arctan.rpc;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.DiscordEventAdapter;
import de.jcm.discordgamesdk.user.DiscordUser;
import de.jcm.discordgamesdk.user.Relationship;
import io.github.arctanmc.arctan.ArctanClient;

import java.io.IOException;

import static io.github.arctanmc.arctan.rpc.RPCUtils.*;

public class DiscordRPC {

	private static Core core;

	public static void init() throws IOException {
		Core.initDownload();
		CreateParams params = new CreateParams();
		params.setClientID(ArctanClient.CLIENT_ID);
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
			ArctanClient.LOGGER.info("Initialized Rich Presence");
			new RPCEvents();
		} catch (Exception e) {
			ArctanClient.LOGGER.error("Failed to initialize Rich Presence", e);
		}
		updateActivity("In the Menu", null, null);
	}

	public static Core getCore() {
		return core;
	}

}
