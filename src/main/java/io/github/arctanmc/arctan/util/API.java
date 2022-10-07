package io.github.arctanmc.arctan.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.arctanmc.arctan.ArctanClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class API {

	public static final String BASE_URL = "http://localhost:8080/api/v1/players";
	public static final Cache<String, Player> PLAYER_CACHE = Caffeine.newBuilder()
			.expireAfterWrite(30, TimeUnit.MINUTES)
			.build();

	public static @Nullable Player getPlayerByUuid(String uuid) {
		Player player = PLAYER_CACHE.getIfPresent(uuid);
		if (player != null) {
			return player;
		}
		Request request = new Request.Builder()
				.url(BASE_URL + "/minecraft/" + uuid)
				.build();
		try (Response response = ArctanClient.HTTP_CLIENT.newCall(request).execute()) {
			String res = response.body().string();
			JSONObject json = new JSONObject(res);
			JSONObject userObject = json.getJSONObject("discordUser");
			DiscordUser discordUser = new DiscordUser(userObject.getString("id"), userObject.getString("tag"));
			Player p = new Player(uuid, discordUser);
			PLAYER_CACHE.put(uuid, p);
			player = p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return player;
	}

	public static @Nullable Player getPlayerByDiscordId(String id) {
		Player player = PLAYER_CACHE.getIfPresent(id);
		if (player != null) {
			return player;
		}
		Request request = new Request.Builder()
				.url(BASE_URL + "/discord/" + id)
				.build();
		try (Response response = ArctanClient.HTTP_CLIENT.newCall(request).execute()) {
			String res = response.body().string();
			JSONObject json = new JSONObject(res);
			JSONObject userObject = json.getJSONObject("discordUser");
			DiscordUser discordUser = new DiscordUser(userObject.getString("id"), userObject.getString("tag"));
			Player p = new Player(json.getString("minecraftUuid"), discordUser);
			PLAYER_CACHE.put(id, p);
			player = p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return player;
	}

}
