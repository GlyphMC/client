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

package io.github.glyphmc.glyph.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.glyphmc.glyph.GlyphClient;
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
		try (Response response = GlyphClient.HTTP_CLIENT.newCall(request).execute()) {
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
		try (Response response = GlyphClient.HTTP_CLIENT.newCall(request).execute()) {
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
