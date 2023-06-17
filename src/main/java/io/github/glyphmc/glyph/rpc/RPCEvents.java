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

package io.github.glyphmc.glyph.rpc;

import static io.github.glyphmc.glyph.rpc.RPCUtils.updateActivity;

public class RPCEvents {

	private static RPCEvents INSTANCE;
	private RPCState state = RPCState.INIT;
	private GameType gameType = GameType.SINGLEPLAYER;

	public RPCEvents() {
		INSTANCE = this;
		initRPC();
	}

	private void initRPC() {
		this.state = RPCState.INIT;
		updateRPC(0);
	}

	public void menuRPC() {
		this.state = RPCState.MAIN_MENU;
		updateRPC(0);
	}

	public void gameRPC(GameType type) {
		this.state = RPCState.IN_GAME;
		this.gameType = type;
		updateRPC(0);
	}

	public void gameRPC(GameType type, int size) {
		this.state = RPCState.IN_GAME;
		this.gameType = type;
		updateRPC(size);
	}

	private void updateRPC(int size) {
		switch (state) {
			case INIT -> updateActivity("Loading...");
			case MAIN_MENU -> updateActivity("In the Menu");
			case IN_GAME -> {
				switch (gameType) {
					case SINGLEPLAYER -> {
						updateActivity("Singleplayer");
					}
					case MULTIPLAYER -> {
						String state = "Multiplayer";
						if (size != 0) {
							state += " (" + size + " players)";
						}
						updateActivity(state);
					}
				}
			}
		}
	}

	public static RPCEvents getInstance() {
		return INSTANCE;
	}

}
