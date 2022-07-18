package io.github.arctanmc.arctan.rpc;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.DiscordEventAdapter;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.activity.ActivityPartySize;
import de.jcm.discordgamesdk.user.DiscordUser;
import de.jcm.discordgamesdk.user.Relationship;
import io.github.arctanmc.arctan.ArctanClient;
import net.minecraft.SharedConstants;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static io.github.arctanmc.arctan.rpc.RPCUtils.*;

public class DiscordRPC {

	private static Core core;
	private static final Instant INSTANT = Instant.now();
	private static final Cache<Long, String> CACHE = Caffeine.newBuilder()
			.expireAfterWrite(30, TimeUnit.MINUTES)
			.build();

	public static void init() throws IOException {
		Core.initDownload();
		CreateParams params = new CreateParams();
		params.setClientID(ArctanClient.CLIENT_ID);
		params.setFlags(CreateParams.getDefaultFlags());
		params.registerEventHandler(new DiscordEventAdapter() {
			// These are the events that I think we will need to handle.
			@Override
			public void onActivityJoin(String secret) {
				ActivityJoinHandler.handle(CACHE, secret);
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
		} catch (Exception e) {
			ArctanClient.LOGGER.error("Failed to initialize Rich Presence", e);
		}
		updateActivity("In the Menu", null, null);
	}

	public static Core getCore() {
		return core;
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
		core.activityManager().updateActivity(activity, result -> {
			switch (result) {
				// TODO handle errors
				case OK -> {
					ArctanClient.LOGGER.info("Updated Rich Presence");
				}
			}
		});
	}

}
