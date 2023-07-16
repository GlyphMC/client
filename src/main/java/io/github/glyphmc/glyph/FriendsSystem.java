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
