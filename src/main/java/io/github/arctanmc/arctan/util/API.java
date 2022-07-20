package io.github.arctanmc.arctan.util;

import io.github.arctanmc.arctan.ArctanClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class API {

	/**
	 * Gets the UUID of a user from our API.
	 * @param discordId The Discord ID of the user.
	 * @return The UUID of the user with the given Discord ID, or null if the user is not found.
	 */
	public static String getMinecraftUuid(Long discordId) {
		String uuid = null;
		Request request = new Request.Builder()
				.url(String.format("http://localhost:8080/api/v1/discord/%s", discordId)) // TODO: Replace with actual endpoint
				.build();
		try (Response response = ArctanClient.HTTP_CLIENT.newCall(request).execute()) {
			if (response.code() == 200) {
				String res = response.body().string();
				JSONObject json = new JSONObject(res);
				if (discordId == json.getLong("discordId")) {
					uuid = json.getString("minecraftUuid");
				} else {
					ArctanClient.LOGGER.error("Unknown discordId " + discordId);
				}
			} else {
				ArctanClient.LOGGER.error("Failed to get UUID for Discord ID " + discordId);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uuid;
	}

	/**
	 * Gets the Discord ID of a user from our API.
	 * @param minecraftUuid The Minecraft UUID of the user.
	 * @return The UUID of the user with the given UUID, or null if the user is not found.
	 */
	public static Long getDiscordId(String minecraftUuid) {
		Long discordId = null;
		Request request = new Request.Builder()
				.url(String.format("http://localhost:8080/api/v1/minecraft/%s", minecraftUuid)) // TODO: Replace with actual endpoint
				.build();
		try (Response response = ArctanClient.HTTP_CLIENT.newCall(request).execute()) {
			if (response.code() == 200) {
				String res = response.body().string();
				JSONObject json = new JSONObject(res);
				if (minecraftUuid.equals(json.getString("minecraftUuid"))) {
					discordId = json.getLong("discordId");
				} else {
					ArctanClient.LOGGER.error("Unknown minecraftUuid " + minecraftUuid);
				}
			} else {
				ArctanClient.LOGGER.error("Failed to get Discord ID for Minecraft UUID " + minecraftUuid);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return discordId;
	}

}
