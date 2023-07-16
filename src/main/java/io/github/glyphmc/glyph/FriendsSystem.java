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

package io.github.glyphmc.glyph;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.glyphmc.glyph.GlyphClient.LOGGER;
import static io.github.glyphmc.glyph.GlyphClient.OKHTTP;

public class FriendsSystem extends WebSocketListener {

	public FriendsSystem() {
		Request request = new Request.Builder()
			.url("ws://localhost:8080/ws/friend")
			.build();
		OKHTTP.newWebSocket(request, this);
	}

	@Override
	public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
		super.onOpen(webSocket, response);
		LOGGER.info("WebSocket opened: " + response.message());
	}

	@Override
	public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
		super.onMessage(webSocket, text);
		LOGGER.info("WebSocket message: " + text);
	}

	@Override
	public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
		super.onClosing(webSocket, code, reason);
		LOGGER.info("WebSocket closing: " + reason);
	}

	@Override
	public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
		super.onClosed(webSocket, code, reason);
		LOGGER.info("WebSocket closed: " + reason);
	}

	@Override
	public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
		super.onFailure(webSocket, t, response);
		LOGGER.info("WebSocket failure: " + t.getMessage());
	}

}
