package io.github.arctanmc.arctan.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.arctanmc.arctan.ArctanClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class API {

	public static final String BASE_URL = "http://localhost:8080/api/v1/";
	public static final Cache<String, User> UUID_CACHE = Caffeine.newBuilder()
			.expireAfterWrite(30, TimeUnit.MINUTES)
			.build();
	public static final Cache<Long, User> ID_CACHE = Caffeine.newBuilder()
			.expireAfterWrite(30, TimeUnit.MINUTES)
			.build();

	/**
	 * Gets the user from our API.
	 *
	 * @param discordId The Discord ID.
	 */
	public static @Nullable User getUserById(Long discordId) {
		User ifPresent = ID_CACHE.getIfPresent(discordId);
		if (ifPresent != null) {
			return ifPresent;
		}
		Request request = new Request.Builder()
				.url(BASE_URL + "discord/" + discordId)
				.build();
		User user;
		try (Response response = ArctanClient.HTTP_CLIENT.newCall(request).execute()) {
			if (response.code() == 200) {
				String res = response.body().string();
				JSONObject json = new JSONObject(res);
				JSONObject userObj = json.getJSONObject("user");
				if (discordId.equals(userObj.getLong("userId"))) {
					user = new User(discordId, userObj.getString("tag"), userObj.getString("avatarUrl"));
					UUID_CACHE.put(json.getString("minecraftUuid"), user);
					ID_CACHE.put(discordId, user);
				} else {
					ArctanClient.LOGGER.error("Unknown discordId " + discordId);
					return null;
				}
			} else {
				ArctanClient.LOGGER.error("Failed to get User info for Discord ID " + discordId);
				return null;
			}
		} catch (IOException e) {
			return null;
		}
		return user;
	}

	/**
	 * Gets the user from our API.
	 * @param uuid The Minecraft UUID.
	 * @return The user, or null.
	 */
	public static @Nullable User getUserByUuid(String uuid) {
		User ifPresent = UUID_CACHE.getIfPresent(uuid);
		if (ifPresent != null) {
			return ifPresent;
		}
		Request request = new Request.Builder()
				.url(BASE_URL + "minecraft/" + uuid)
				.build();
		User user;
		try (Response response = ArctanClient.HTTP_CLIENT.newCall(request).execute()) {
			if (response.code() == 200) {
				String res = response.body().string();
				JSONObject json = new JSONObject(res);
				JSONObject userObj = json.getJSONObject("user");
				String minecraftUuid = json.getString("minecraftUuid");
				if (uuid.equals(minecraftUuid)) {
					user = new User(userObj.getLong("userId"), userObj.getString("tag"), userObj.getString("avatarUrl"));
					UUID_CACHE.put(minecraftUuid, user);
					ID_CACHE.put(userObj.getLong("userId"), user);
				} else {
					ArctanClient.LOGGER.error("Unknown uuid " + uuid);
					return null;
				}
			} else {
				ArctanClient.LOGGER.error("Failed to get User info for UUID " + uuid);
				return null;
			}
		} catch (IOException e) {
			return null;
		}
		return user;
	}


}
